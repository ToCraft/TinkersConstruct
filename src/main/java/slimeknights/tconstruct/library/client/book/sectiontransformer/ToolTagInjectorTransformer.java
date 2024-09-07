package slimeknights.tconstruct.library.client.book.sectiontransformer;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.client.book.data.content.PageContent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.client.book.content.ContentTool;

/**
 * Injects tools into a section based on a tag
 */
public class ToolTagInjectorTransformer extends AbstractTagInjectingTransformer<Item> {

  public static final ToolTagInjectorTransformer INSTANCE = new ToolTagInjectorTransformer();

  private ToolTagInjectorTransformer() {
    super(Registries.ITEM, TConstruct.getResource("load_tools"), ContentTool.ID);
  }

  @Override
  protected ResourceLocation getId(Item item) {
    return ForgeRegistries.ITEMS.getKey(item);
  }

  @Override
  protected PageContent createFallback(Item item) {
    return new ContentTool(item);
  }
}
