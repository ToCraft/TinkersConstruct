package slimeknights.tconstruct.fluids.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Liquid block setting the entity on fire
 */
public class MobEffectLiquidBlock extends LiquidBlock {

  private final Supplier<MobEffectInstance> effect;

  public MobEffectLiquidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties, Supplier<MobEffectInstance> effect) {
    super(supplier, properties);
    this.effect = effect;
  }

  @Override
  public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
    if (entity.getFluidTypeHeight(getFluid().getFluidType()) > 0 && entity instanceof LivingEntity living) {
      MobEffectInstance effect = this.effect.get();
      effect.setCurativeItems(new ArrayList<>());
      living.addEffect(effect);
    }
  }

  /**
   * Creates a new block supplier
   */
  public static Function<Supplier<? extends FlowingFluid>, LiquidBlock> createEffect(BlockBehaviour material, int lightLevel, Supplier<MobEffectInstance> effect) {
    return fluid -> new MobEffectLiquidBlock(fluid, Properties.copy(material).lightLevel(state -> lightLevel).noCollission().strength(100f).noLootTable(), effect);
  }

  /**
   * Creates a new block supplier
   */
  public static Function<Supplier<? extends FlowingFluid>, LiquidBlock> createEffect(int lightLevel, Supplier<MobEffectInstance> effect) {
    return createEffect(Blocks.WATER, lightLevel, effect);
  }
}
