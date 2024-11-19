package slimeknights.tconstruct.world.block;

import net.minecraft.world.level.block.Block;

/**
 * Simple block to hide ichor
 */
public class SlimeWartBlock extends Block {

  private final FoliageType foliageType;

  public SlimeWartBlock(Properties properties, FoliageType foliageType) {
    super(properties);
    this.foliageType = foliageType;
  }
}
