package slimeknights.tconstruct.tools.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.utils.Util;

import java.util.List;

public class CreativeSlotItem extends Item {

  private static final String NBT_KEY = "slot";
  private static final String TOOLTIP = TConstruct.makeTranslationKey("item", "creative_slot.tooltip");
  private static final Component TOOLTIP_MISSING = TConstruct.makeTranslation("item", "creative_slot.missing").withStyle(ChatFormatting.RED);

  public CreativeSlotItem(Properties properties) {
    super(properties);
  }

  /**
   * Gets the value of the slot tag from the given stack
   */
  @Nullable
  public static SlotType getSlot(ItemStack stack) {
    CompoundTag nbt = stack.getTag();
    if (nbt != null && nbt.contains(NBT_KEY, Tag.TAG_STRING)) {
      return SlotType.getIfPresent(nbt.getString(NBT_KEY));
    }
    return null;
  }

  /**
   * Makes an item stack with the given slot type
   */
  public static ItemStack withSlot(ItemStack stack, SlotType type) {
    stack.getOrCreateTag().putString(NBT_KEY, type.getName());
    return stack;
  }

  @Override
  public String getDescriptionId(ItemStack stack) {
    SlotType slot = getSlot(stack);
    String originalKey = getDescriptionId();
    if (slot != null) {
      String betterKey = originalKey + "." + slot.getName();
      if (Util.canTranslate(betterKey)) {
        return betterKey;
      }
    }
    return originalKey;
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
    SlotType slot = getSlot(stack);
    if (slot != null) {
      tooltip.add(Component.translatable(TOOLTIP, slot.getDisplayName()).withStyle(ChatFormatting.GRAY));
    } else {
      tooltip.add(TOOLTIP_MISSING);
    }
  }
}
