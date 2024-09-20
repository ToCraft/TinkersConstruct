package slimeknights.tconstruct.library.client;

import net.minecraft.resources.ResourceLocation;
import slimeknights.mantle.client.screen.ElementScreen;
import slimeknights.tconstruct.TConstruct;

public interface Icons {

  ResourceLocation ICONS = TConstruct.getResource("textures/gui/icons.png");

  //ElementScreen ANVIL = new ElementScreen(ICONS, 18 * 3, 0, 18, 18, 256, 256);
  ElementScreen PATTERN = new ElementScreen(ICONS, 0, 18 * 12, 18, 18, 256, 256);
  //ElementScreen SHARD = new ElementScreen(ICONS, 18, 18 * 12, 18, 18, 256, 256);
  //ElementScreen BLOCK = new ElementScreen(ICONS, 18 * 2, 18 * 12, 18, 18, 256, 256);
  //ElementScreen PICKAXE = new ElementScreen(ICONS, 0, 18 * 13, 18, 18, 256, 256);
  //ElementScreen DUST = new ElementScreen(ICONS, 18, 18 * 13, 18, 18, 256, 256);
  //ElementScreen LAPIS = new ElementScreen(ICONS, 18 * 2, 18 * 13, 18, 18, 256, 256);
  ElementScreen INGOT = new ElementScreen(ICONS, 18 * 3, 18 * 13, 18, 18, 256, 256);
  //ElementScreen GEM = new ElementScreen(ICONS, 18 * 4, 18 * 13, 18, 18, 256, 256);
  //ElementScreen QUARTZ = new ElementScreen(ICONS, 18 * 5, 18 * 13, 18, 18, 256, 256);
  ElementScreen BUTTON = new ElementScreen(ICONS, 180, 216, 18, 18, 256, 256);
  ElementScreen BUTTON_HOVERED = new ElementScreen(ICONS, 180 + 18 * 2, 216, 18, 18, 256, 256);
  ElementScreen BUTTON_PRESSED = new ElementScreen(ICONS, 180 - 18 * 2, 216, 18, 18, 256, 256);

  ElementScreen PIGGYBACK_1 = new ElementScreen(ICONS, 18 * 13, 0, 18, 18, 256, 256);
  ElementScreen PIGGYBACK_2 = new ElementScreen(ICONS, 18 * 13, 18, 18, 18, 256, 256);
  ElementScreen PIGGYBACK_3 = new ElementScreen(ICONS, 18 * 13, 18 * 2, 18, 18, 256, 256);
}
