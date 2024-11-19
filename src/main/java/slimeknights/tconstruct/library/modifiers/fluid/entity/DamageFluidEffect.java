package slimeknights.tconstruct.library.modifiers.fluid.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import slimeknights.mantle.data.loadable.primitive.EnumLoadable;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.modifiers.fluid.EffectLevel;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffect;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffectContext;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffectContext.Entity;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;

/**
 * Effect that damages an entity
 *
 * @param type   damage type to apply
 * @param damage Amount of damage to apply
 */
public record DamageFluidEffect(FluidDamageType type,
                                float damage) implements FluidEffect<FluidEffectContext.Entity> {

  public DamageFluidEffect(float damage, FluidDamageType type) {
    this(type, damage);
  }

  /**
   * Loader for this effect
   */
  public static final RecordLoadable<DamageFluidEffect> LOADER = RecordLoadable.create(
    new EnumLoadable<>(FluidDamageType.class).requiredField("type", e -> e.type),
    FloatLoadable.FROM_ZERO.requiredField("damage", e -> e.damage),
    DamageFluidEffect::new);

  @Override
  public RecordLoadable<DamageFluidEffect> getLoader() {
    return LOADER;
  }

  @Override
  public float apply(FluidStack fluid, EffectLevel level, Entity context, FluidAction action) {
    float value = level.value();
    if (action.simulate()) {
      return value;
    }
    DamageSource source = type.apply(context);
    return ToolAttackUtil.attackEntitySecondary(source, this.damage * value, context.getTarget(), context.getLivingTarget(), true) ? value : 0;
  }
}
