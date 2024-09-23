package slimeknights.tconstruct.tables.client.inventory.module;

import net.minecraft.resources.ResourceLocation;
import slimeknights.mantle.client.screen.ScalableElementScreen;
import slimeknights.mantle.client.screen.ElementScreen;
import slimeknights.tconstruct.TConstruct;

public class GenericScreen {

  public static final ResourceLocation LOCATION = TConstruct.getResource("textures/gui/generic.png");

  // first one sets default texture w/h
  public static final ElementScreen cornerTopLeft = new ElementScreen(LOCATION, 0, 0, 7, 7, 64, 64);
  public static final ElementScreen cornerTopRight = new ElementScreen(LOCATION, 64 - 7, 0, 7, 7, 64, 64);
  public static final ElementScreen cornerBottomLeft = new ElementScreen(LOCATION, 0, 64 - 7, 7, 7, 64, 64);
  public static final ElementScreen cornerBottomRight = new ElementScreen(LOCATION, 64 - 7, 64 - 7, 7, 7, 64, 64);

  public static final ScalableElementScreen borderTop = new ScalableElementScreen(LOCATION, 7, 0, 64 - 7 - 7, 7, 64, 64);
  public static final ScalableElementScreen borderBottom = new ScalableElementScreen(LOCATION, 7, 64 - 7, 64 - 7 - 7, 7, 64, 64);
  public static final ScalableElementScreen borderLeft = new ScalableElementScreen(LOCATION, 0, 7, 7, 64 - 7 - 7, 64, 64);
  public static final ScalableElementScreen borderRight = new ScalableElementScreen(LOCATION, 64 - 7, 7, 7, 64 - 7 - 7, 64, 64);

  public static final ScalableElementScreen overlap = new ScalableElementScreen(LOCATION, 21, 45, 7, 14, 64, 64);
  public static final ElementScreen overlapTopLeft = new ElementScreen(LOCATION, 7, 40, 7, 7, 64, 64);
  public static final ElementScreen overlapTopRight = new ElementScreen(LOCATION, 14, 40, 7, 7, 64, 64);
  public static final ElementScreen overlapBottomLeft = new ElementScreen(LOCATION, 7, 47, 7, 7, 64, 64);
  public static final ElementScreen overlapBottomRight = new ElementScreen(LOCATION, 14, 47, 7, 7, 64, 64);

  public static final ScalableElementScreen textBackground = new ScalableElementScreen(LOCATION, 7 + 18, 7, 18, 10, 64,64);

  public static final ScalableElementScreen slot = new ScalableElementScreen(LOCATION, 7, 7, 18, 18, 64, 64);
  public static final ScalableElementScreen slotEmpty = new ScalableElementScreen(LOCATION, 7 + 18, 7, 18, 18, 64, 64);

  public static final ElementScreen sliderNormal = new ElementScreen(LOCATION, 7, 25, 10, 15, 64, 64);
  public static final ElementScreen sliderLow = new ElementScreen(LOCATION, 17, 25, 10, 15, 64, 64);
  public static final ElementScreen sliderHigh = new ElementScreen(LOCATION, 27, 25, 10, 15, 64, 64);
  public static final ElementScreen sliderTop = new ElementScreen(LOCATION, 43, 7, 12, 1, 64, 64);
  public static final ElementScreen sliderBottom = new ElementScreen(LOCATION, 43, 38, 12, 1, 64, 64);
  public static final ScalableElementScreen sliderBackground = new ScalableElementScreen(LOCATION, 43, 8, 12, 30, 64, 64);
}
