package slimeknights.tconstruct.tools;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerModule;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import slimeknights.tconstruct.tools.item.RepairKitItem;
import slimeknights.tconstruct.tools.stats.GripMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.LimbMaterialStats;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class TinkerToolParts extends TinkerModule {
  // Concurrent in case Forge loads something asynchronous
  private static final List<ItemLike> TAB_TOOL_PARTS_ITEMS = new CopyOnWriteArrayList<>();
  /**
   * Tab for all tool parts
   */
  public static final CreativeModeTab TAB_TOOL_PARTS = CreativeModeTab.builder().title(Component.translatable("itemGroup.tconstruct.tool_parts")).icon(() -> {
    List<IMaterial> materials = new ArrayList<>(MaterialRegistry.getInstance().getVisibleMaterials());
    if (materials.isEmpty()) {
      return new ItemStack(TinkerToolParts.pickHead);
    }
    return TinkerToolParts.pickHead.get().withMaterial(materials.get(TConstruct.RANDOM.nextInt(materials.size())).getIdentifier());
  }).displayItems((itemDisplayParameters, output) -> {
    for (ItemLike item : TAB_TOOL_PARTS_ITEMS) {
      output.accept(item);
    }
  }).build();
  private static final Item.Properties PARTS_PROPS = new Item.Properties();

  // repair kit, technically a head so it filters to things useful for repair
  public static final ItemObject<RepairKitItem> repairKit = ITEMS.register("repair_kit", () -> addToTabItemList(new RepairKitItem(PARTS_PROPS), TAB_TOOL_PARTS_ITEMS));

  // rock
  public static final ItemObject<ToolPartItem> pickHead = ITEMS.register("pick_head", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, HeadMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  public static final ItemObject<ToolPartItem> hammerHead = ITEMS.register("hammer_head", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, HeadMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  // axe
  public static final ItemObject<ToolPartItem> smallAxeHead = ITEMS.register("small_axe_head", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, HeadMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  public static final ItemObject<ToolPartItem> broadAxeHead = ITEMS.register("broad_axe_head", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, HeadMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  // blades
  public static final ItemObject<ToolPartItem> smallBlade = ITEMS.register("small_blade", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, HeadMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  public static final ItemObject<ToolPartItem> broadBlade = ITEMS.register("broad_blade", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, HeadMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  // plates
  public static final ItemObject<ToolPartItem> roundPlate = ITEMS.register("round_plate", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, HeadMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  public static final ItemObject<ToolPartItem> largePlate = ITEMS.register("large_plate", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, HeadMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  // bows
  public static final ItemObject<ToolPartItem> bowLimb = ITEMS.register("bow_limb", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, LimbMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  public static final ItemObject<ToolPartItem> bowGrip = ITEMS.register("bow_grip", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, GripMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  public static final ItemObject<ToolPartItem> bowstring = ITEMS.register("bowstring", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, StatlessMaterialStats.BOWSTRING.getIdentifier()), TAB_TOOL_PARTS_ITEMS));
  // other parts
  public static final ItemObject<ToolPartItem> toolBinding = ITEMS.register("tool_binding", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, StatlessMaterialStats.BINDING.getIdentifier()), TAB_TOOL_PARTS_ITEMS));
  public static final ItemObject<ToolPartItem> toolHandle = ITEMS.register("tool_handle", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, HandleMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  public static final ItemObject<ToolPartItem> toughHandle = ITEMS.register("tough_handle", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, HandleMaterialStats.ID), TAB_TOOL_PARTS_ITEMS));
  // armor
  public static final EnumObject<ArmorSlotType, ToolPartItem> plating = ITEMS.registerEnum(ArmorSlotType.values(), "plating", type -> addToTabItemList(new ToolPartItem(PARTS_PROPS, PlatingMaterialStats.TYPES.get(type.getIndex()).getId()), TAB_TOOL_PARTS_ITEMS));
  public static final ItemObject<ToolPartItem> maille = ITEMS.register("maille", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, StatlessMaterialStats.MAILLE.getIdentifier()), TAB_TOOL_PARTS_ITEMS));
  public static final ItemObject<ToolPartItem> shieldCore = ITEMS.register("shield_core", () -> addToTabItemList(new ToolPartItem(PARTS_PROPS, StatlessMaterialStats.SHIELD_CORE.getIdentifier()), TAB_TOOL_PARTS_ITEMS));

}
