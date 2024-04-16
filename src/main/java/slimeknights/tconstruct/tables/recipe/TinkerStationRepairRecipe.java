package slimeknights.tconstruct.tables.recipe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.recipe.RecipeResult;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipe;
import slimeknights.tconstruct.library.recipe.tinkerstation.IMutableTinkerStationContainer;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationContainer;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationRecipe;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.ToolDefinitionData;
import slimeknights.tconstruct.library.tools.definition.module.ToolHooks;
import slimeknights.tconstruct.library.tools.definition.module.material.ToolMaterialHook;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.IRepairKitItem;
import slimeknights.tconstruct.library.tools.part.IToolPart;
import slimeknights.tconstruct.tables.TinkerTables;

import javax.annotation.Nullable;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/** Recipe for repairing tools */
@RequiredArgsConstructor
public class TinkerStationRepairRecipe implements ITinkerStationRecipe {
  protected static final RecipeResult<ItemStack> FULLY_REPAIRED = RecipeResult.failure(TConstruct.makeTranslationKey("recipe", "tool_repair.fully_repaired"));
  /** No action int consumer for recipe result */
  private static final IntConsumer NO_ACTION = i -> {};

  @Getter
  private final ResourceLocation id;

  /**
   * Checks if the tool can be repaired with the given material
   * @param tool      Tool to check
   * @param material  Material to try
   * @return  Index if can repair, -1 if invalid
   */
  public static int getRepairIndex(IToolStackView tool, MaterialId material) {
    for (int part : tool.getHook(ToolHooks.TOOL_MATERIALS).getRepairIndices(tool.getDefinition())) {
      if (tool.getMaterial(part).getId().equals(material)) {
        return part;
      }
    }
    return -1;
  }

  /**
   * Gets the material for the given slot
   * @param inv   Inventory instance
   * @param slot  Slot
   * @return  Material amount
   */
  protected static MaterialId getMaterialFrom(ITinkerStationContainer inv, int slot) {
    // try repair kit first
    ItemStack item = inv.getInput(slot);
    if (item.getItem() instanceof IRepairKitItem kit) {
      return kit.getMaterial(item).getId();
    }
    // material recipe fallback
    MaterialRecipe recipe = inv.getInputMaterial(slot);
    if (recipe != null) {
      return recipe.getMaterial().getId();
    }
    return IMaterial.UNKNOWN_ID;
  }

  /** Gets the default stats ID to use if the item is not a tool part */
  @Nullable
  public static MaterialStatsId getDefaultStatsId(IToolStackView tool, MaterialId repairMaterial) {
    int repairIndex = getRepairIndex(tool, repairMaterial);
    if (repairIndex < 0) {
      return null; // default to the first repair stats
    }
    return ToolMaterialHook.stats(tool.getDefinition()).get(repairIndex).stat();
  }

  /** Gets the amount to repair per item */
  protected float getRepairPerItem(ToolStack tool, ITinkerStationContainer inv, int slot, MaterialId repairMaterial) {
    ItemStack stack = inv.getInput(slot);
    // repair kit first
    ToolDefinitionData toolData = tool.getDefinition().getData();
    if (stack.getItem() instanceof IRepairKitItem kit) {
      // multiply by 2 (part cost), divide again by the repair factor to get the final percent
      return MaterialRecipe.getRepairDurability(toolData, repairMaterial, getDefaultStatsId(tool, repairMaterial)) * kit.getRepairAmount() / MaterialRecipe.INGOTS_PER_REPAIR;
    } else {
      // material recipe fallback
      MaterialRecipe recipe = inv.getInputMaterial(slot);
      if (recipe != null) {
        if (stack.getItem() instanceof IToolPart) {
          return recipe.getRepairPerItem(toolData, ((IToolPart)stack.getItem()).getStatType());
        }
        return recipe.getRepairPerItem(toolData, getDefaultStatsId(tool, repairMaterial));
      }
    }
    return 0;
  }

  @Override
  public boolean matches(ITinkerStationContainer inv, Level world) {
    // must be repairable
    ItemStack tinkerable = inv.getTinkerableStack();
    // must be repairable and multipart to use this recipe
    // if its not multipart, different recipe will be used to repair it (as it has a dedicated repair item)
    if (tinkerable.isEmpty()
        || !tinkerable.is(TinkerTags.Items.MULTIPART_TOOL)
        || !tinkerable.is(TinkerTags.Items.DURABILITY)) {
      return false;
    }

    // validate materials
    MaterialId material = null;
    ToolStack tool = ToolStack.from(tinkerable);
    // not sure why you are tagging a tool with no materials as multipart, you are wrong and should feel ashamed of yourself
    if (!tool.getDefinition().hasMaterials()) {
      return false;
    }
    for (int i = 0; i < inv.getInputCount(); i++) {
      // skip empty slots
      ItemStack stack = inv.getInput(i);
      if (stack.isEmpty()) {
        continue;
      }

      // ensure we have a material
      MaterialId inputMaterial = getMaterialFrom(inv, i);
      if (inputMaterial.equals(IMaterial.UNKNOWN_ID)) {
        return false;
      }

      // on first match, store and validate the material. For later matches, just ensure material matches
      if (material == null) {
        material = inputMaterial;
        if (getRepairIndex(tool, material) < 0) {
          return false;
        }
      } else if (!material.equals(inputMaterial)) {
        return false;
      }
    }

    // must have a material (will only be null if all slots were empty at this point)
    return material != null;
  }

  @Override
  public int shrinkToolSlotBy() {
    return 1;
  }

  @Override
  public RecipeResult<ItemStack> getValidatedResult(ITinkerStationContainer inv) {
    ToolStack tool = ToolStack.from(inv.getTinkerableStack());
    if (tool.getDefinition() == ToolDefinition.EMPTY) {
      return RecipeResult.pass();
    }
    // ensure input needs repair
    if (!tool.isBroken() && tool.getDamage() == 0) {
      return FULLY_REPAIRED;
    }

    // first, determine how much we can repair
    int repairNeeded = tool.getDamage();
    int repairRemaining = repairNeeded;

    // iterate stacks, adding up amount we can repair, assumes the material is correct per #matches()
    for (int i = 0; i < inv.getInputCount() && repairRemaining > 0; i++) {
      repairRemaining -= repairFromSlot(tool, inv, repairRemaining, i, NO_ACTION);
    }

    // did we actually repair something?
    if (repairRemaining < repairNeeded) {
      tool = tool.copy();
      ToolDamageUtil.repair(tool, repairNeeded - repairRemaining);

      // repair remaining can be negative
      return RecipeResult.success(tool.createStack());
    }

    // for some odd reason, did not repair anything
    return RecipeResult.pass();
  }

  @Override
  public void updateInputs(ItemStack result, IMutableTinkerStationContainer inv, boolean isServer) {
    ToolStack inputTool = ToolStack.from(inv.getTinkerableStack());
    ToolStack resultTool = ToolStack.from(result);

    // iterate stacks, removing items as we repair
    int repairRemaining = inputTool.getDamage() - resultTool.getDamage();
    for (int i = 0; i < inv.getInputCount() && repairRemaining > 0; i++) {
      final int slot = i;
      repairRemaining -= repairFromSlot(inputTool, inv, repairRemaining, i, count -> inv.shrinkInput(slot, count));
    }

    if (repairRemaining > 0) {
      TConstruct.LOG.error("Recipe repair on {} consumed too few items. {} durability unaccounted for", result, repairRemaining);
    }
  }

  /** Gets the repair weight for the given material */
  public static float getRepairWeight(IToolStackView tool, MaterialId repairMaterial) {
    ToolDefinition definition = tool.getDefinition();
    ToolMaterialHook materialStats = definition.getHook(ToolHooks.TOOL_MATERIALS);
    // return the weight of the largest part matching this material
    return IntStream.of(materialStats.getRepairIndices(definition))
                    .filter(i -> tool.getMaterial(i).matches(repairMaterial))
                    .map(i -> materialStats.getStatTypes(definition).get(i).weight())
                    .max().orElse(1)
           / (float)materialStats.maxRepairWeight(definition);
  }

  /**
   * Gets the amount to repair from the given slot
   * @param tool            Tool instance
   * @param inv             Inventory instance
   * @param repairNeeded    Amount of remaining repair needed
   * @param slot            Input slot
   * @param amountConsumer  Action to perform on repair, input is the amount consumed
   * @return  Repair from this slot
   */
  protected int repairFromSlot(ToolStack tool, ITinkerStationContainer inv, int repairNeeded, int slot, IntConsumer amountConsumer) {
    ItemStack stack = inv.getInput(slot);
    if (!stack.isEmpty()) {
      // we have a recipe with matching stack, find out how much we can repair
      MaterialId repairMaterial = getMaterialFrom(inv, slot);
      if (!repairMaterial.equals(IMaterial.UNKNOWN_ID)) {
        float durabilityPerItem = getRepairPerItem(tool, inv, slot, repairMaterial);
        if (durabilityPerItem > 0) {
          // if not the primary material, reduced effectiveness
          durabilityPerItem *= getRepairWeight(tool, repairMaterial);

          // adjust the factor based on modifiers
          // main example is wood, +25% per level
          for (ModifierEntry entry : tool.getModifierList()) {
            durabilityPerItem = entry.getHook(TinkerHooks.REPAIR_FACTOR).getRepairFactor(tool, entry, durabilityPerItem);
            if (durabilityPerItem <= 0) {
              return 0;
            }
          }

          // apply this recipe as many times as we need (if stack has more than enough to repair) or can (if stack will not fully repair)
          int applied = Math.min(stack.getCount(), (int)Math.ceil(repairNeeded / durabilityPerItem));
          amountConsumer.accept(applied);
          return (int)(applied * durabilityPerItem);
        }
      }
    }

    return 0;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return TinkerTables.tinkerStationRepairSerializer.get();
  }

  /** @deprecated Use {@link #assemble(ITinkerStationContainer)} */
  @Deprecated
  @Override
  public ItemStack getResultItem() {
    return ItemStack.EMPTY;
  }
}
