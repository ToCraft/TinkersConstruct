package slimeknights.tconstruct.shared.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.tconstruct.shared.block.ClearStainedGlassBlock.GlassColor;

import javax.annotation.Nullable;

public class ClearStainedGlassPaneBlock extends ClearGlassPaneBlock {

  private final GlassColor glassColor;

  public ClearStainedGlassPaneBlock(Properties builder, GlassColor glassColor) {
    super(builder);
    this.glassColor = glassColor;
  }

  @Nullable
  @Override
  public float[] getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
    return this.glassColor.getRgb();
  }
}
