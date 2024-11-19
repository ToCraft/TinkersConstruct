package slimeknights.tconstruct.smeltery.client.screen.module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import slimeknights.mantle.client.screen.ScalableElementScreen;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.client.GuiUtil;
import slimeknights.tconstruct.smeltery.block.entity.module.MeltingModuleInventory;

import java.util.function.IntSupplier;
import java.util.function.Predicate;

public class GuiMeltingModule {

  // progress bars
  private final ScalableElementScreen PROGRESS_BAR;
  private final ScalableElementScreen NO_HEAT_BAR;
  private final ScalableElementScreen NO_SPACE_BAR;
  private final ScalableElementScreen UNMELTABLE_BAR;

  // progress bar tooltips
  private static final Component TOOLTIP_NO_HEAT = Component.translatable(TConstruct.makeTranslationKey("gui", "melting.no_heat"));
  private static final Component TOOLTIP_NO_SPACE = Component.translatable(TConstruct.makeTranslationKey("gui", "melting.no_space"));
  private static final Component TOOLTIP_UNMELTABLE = Component.translatable(TConstruct.makeTranslationKey("gui", "melting.no_recipe"));

  private final AbstractContainerScreen<?> screen;
  private final MeltingModuleInventory inventory;
  private final IntSupplier temperature;
  private final Predicate<Slot> slotPredicate;

  public GuiMeltingModule(ResourceLocation background, AbstractContainerScreen<?> screen, MeltingModuleInventory inventory, IntSupplier temperature, Predicate<Slot> slotPredicate) {
    this.screen = screen;
    this.inventory = inventory;
    this.temperature = temperature;
    this.slotPredicate = slotPredicate;

    // progress bars
    PROGRESS_BAR = new ScalableElementScreen(background, 176, 150, 3, 16, 256, 256);
    NO_HEAT_BAR = new ScalableElementScreen(background, 179, 150, 3, 16, 256, 256);
    NO_SPACE_BAR = new ScalableElementScreen(background, 182, 150, 3, 16, 256, 256);
    UNMELTABLE_BAR = new ScalableElementScreen(background, 185, 150, 3, 16, 256, 256);

  }


  /**
   * Draws the heat bars on each slot
   */
  public void drawHeatBars(GuiGraphics graphics) {
    int temperature = this.temperature.getAsInt();
    for (int i = 0; i < inventory.getSlots(); i++) {
      Slot slot = screen.getMenu().slots.get(i);
      if (slot.hasItem() && slotPredicate.test(slot)) {
        // determine the bar to draw and the progress
        ScalableElementScreen bar = PROGRESS_BAR;

        int index = slot.getSlotIndex();
        int currentTemp = inventory.getCurrentTime(index);
        int requiredTime = inventory.getRequiredTime(index);

        // no required time means unmeltable
        float progress = 1f;
        if (requiredTime == 0) {
          bar = UNMELTABLE_BAR;
        } else if (inventory.getRequiredTemp(index) > temperature) {
          bar = NO_HEAT_BAR;
        }
        // -1 error state if no space
        else if (currentTemp < 0) {
          bar = NO_SPACE_BAR;
          progress = 1f;
        }
        // scale back normal progress if too large
        else if (currentTemp <= requiredTime) {
          progress = currentTemp / (float) requiredTime;
        }

        // draw the bar
        GuiUtil.drawProgressUp(graphics, bar, slot.x - 4, slot.y, progress);
      }
    }
  }

  /**
   * Draws the tooltip for the hovered hear slot
   *
   * @param mouseX Mouse X position
   * @param mouseY Mouse Y position
   */
  public void drawHeatTooltips(GuiGraphics graphics, int mouseX, int mouseY) {
    int checkX = mouseX - screen.leftPos;
    int checkY = mouseY - screen.topPos;
    int temperature = this.temperature.getAsInt();
    for (int i = 0; i < inventory.getSlots(); i++) {
      Slot slot = screen.getMenu().slots.get(i);
      // must have a stack
      if (slot.hasItem() && slotPredicate.test(slot)) {
        // mouse must be within the slot
        if (GuiUtil.isHovered(checkX, checkY, slot.x - 5, slot.y - 1, PROGRESS_BAR.w + 1, PROGRESS_BAR.h + 2)) {
          int index = slot.getSlotIndex();
          Component tooltip = null;

          // NaN means 0 progress for 0 need, unmeltable
          if (inventory.getRequiredTime(index) == 0) {
            tooltip = TOOLTIP_UNMELTABLE;
          }
          // -1 error state if temperature is too low
          else if (inventory.getRequiredTemp(slot.getSlotIndex()) > temperature) {
            tooltip = TOOLTIP_NO_HEAT;
          }
          // 2x error state if no space
          else if (inventory.getCurrentTime(index) < 0) {
            tooltip = TOOLTIP_NO_SPACE;
          }

          // draw tooltip if relevant
          if (tooltip != null) {
            graphics.renderTooltip(Minecraft.getInstance().font, tooltip, mouseX, mouseY);
          }

          // cannot hover two slots, so done
          break;
        }
      }
    }
  }
}
