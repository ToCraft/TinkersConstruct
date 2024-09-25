package slimeknights.tconstruct.smeltery.client.screen.module;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import slimeknights.mantle.client.screen.ScalableElementScreen;
import slimeknights.tconstruct.smeltery.block.entity.controller.HeatingStructureBlockEntity;
import slimeknights.tconstruct.smeltery.client.screen.HeatingStructureScreen;
import slimeknights.tconstruct.tables.client.inventory.module.SideInventoryScreen;
import slimeknights.tconstruct.tables.menu.module.SideInventoryContainer;

public class HeatingStructureSideInventoryScreen extends SideInventoryScreen<HeatingStructureScreen, SideInventoryContainer<? extends HeatingStructureBlockEntity>> {

  public static final ResourceLocation SLOT_LOCATION = HeatingStructureScreen.BACKGROUND;

  // TODO: read from a proper place
  public HeatingStructureSideInventoryScreen(HeatingStructureScreen parent, SideInventoryContainer<? extends HeatingStructureBlockEntity> container, Inventory playerInventory, int slotCount, int columns) {
    super(parent, container, playerInventory, Component.empty(), slotCount, columns, false, true);
    slot = new ScalableElementScreen(0, 166, 22, 18, 256, 256);
    slotEmpty = new ScalableElementScreen(22, 166, 22, 18, 256, 256);
    yOffset = 0;
  }

  @Override
  protected boolean shouldDrawName() {
    return false;
  }

  @Override
  protected void updateSlots() {
    // adjust for the heat bar
    xOffset += 4;
    super.updateSlots();
    xOffset -= 4;
  }

  @Override
  protected int drawSlots(GuiGraphics graphics, int xPos, int yPos) {
    RenderSystem.setShaderTexture(0, SLOT_LOCATION);
    int ret = super.drawSlots(graphics, xPos, yPos);
    RenderSystem.setShaderTexture(0, GENERIC_INVENTORY);
    return ret;
  }

  @Override
  public void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
    super.renderLabels(graphics, mouseX, mouseY);
  }

  @Override
  protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
    super.renderTooltip(graphics, mouseX, mouseY);
    if (parent.melting != null) {
      parent.melting.drawHeatTooltips(graphics, mouseX, mouseY);
    }
  }
}
