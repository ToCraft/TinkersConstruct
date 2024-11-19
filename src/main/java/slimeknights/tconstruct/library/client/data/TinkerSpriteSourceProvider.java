package slimeknights.tconstruct.library.client.data;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.client.modifiers.ModifierIconManager;
import slimeknights.tconstruct.tables.client.PatternGuiTextureLoader;

import java.util.Optional;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class TinkerSpriteSourceProvider extends SpriteSourceProvider {

  public TinkerSpriteSourceProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
    super(generator.getPackOutput(), fileHelper, TConstruct.MOD_ID);
  }

  @Override
  protected void addSources() {
    ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

    SourceList sourceList = atlas(BLOCKS_ATLAS);
    Consumer<ResourceLocation> addSprite = resourceLocation -> sourceList.addSource(new SingleFile(new ResourceLocation(resourceLocation.toString().replace(".png", "").replace("textures/", "")), Optional.empty()));
    ModifierIconManager.onTextureStitch(addSprite, resourceManager);
    PatternGuiTextureLoader.INSTANCE.onTextureStitch(addSprite, resourceManager);
  }
}
