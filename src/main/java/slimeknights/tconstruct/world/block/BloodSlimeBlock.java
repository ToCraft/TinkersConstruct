package slimeknights.tconstruct.world.block;

import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.tconstruct.world.TinkerWorld;

/**
 * Slime block that only sticks to other slime blocks
 */
public class BloodSlimeBlock extends SlimeBlock {

  public BloodSlimeBlock(Properties properties) {
    super(properties);
  }

  @Override
  public boolean isSlimeBlock(BlockState state) {
    return true;
  }

  @Override
  public boolean canStickTo(BlockState state, BlockState other) {
    return TinkerWorld.slime.contains(other.getBlock());
  }
}
