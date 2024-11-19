package slimeknights.tconstruct.tables.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.BooleanSupplier;

/**
 * Retextured block that conditionally enables show all variants
 */
public class TableBlockItem extends BlockItem {

  private final BooleanSupplier showAllCondition;

  public TableBlockItem(Block block, TagKey<Item> textureTag, Item.Properties builder, BooleanSupplier showAllCondition) {
    super(block, builder);
    this.showAllCondition = showAllCondition;
  }

  // XXX Are any fillItemCategory() methods needed now given how creative tabs are handled?
//  @Override
//  public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
//    if (this.allowedIn(group)) {
//      if (showAllCondition.getAsBoolean()) {
//        addTagVariants(this.getBlock(), this.textureTag, items, true);
//      } else {
//        items.add(new ItemStack(this));
//      }
//    }
//  }
}
