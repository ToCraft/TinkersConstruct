package slimeknights.tconstruct.tables.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.mantle.data.listener.ResourceValidator;

import java.util.function.Consumer;

/**
 * Stitches all GUI part textures into the texture sheet
 */
public class PatternGuiTextureLoader extends ResourceValidator {
  public static PatternGuiTextureLoader INSTANCE = new PatternGuiTextureLoader();

  /**
   * Initializes the loader
   */
  public static void init() {
  }

  private PatternGuiTextureLoader() {
    super("textures/gui/tinker_pattern", "textures", ".png");
  }

  /**
   * Called during texture stitch to add the textures in
   */
  public void onTextureStitch(Consumer<ResourceLocation> spriteAdder, ResourceManager manager) {
    // manually call reload to ensure it runs at the proper time
    this.onReloadSafe(Minecraft.getInstance().getResourceManager());
    this.resources.forEach(spriteAdder);
    this.clear();
  }
}
