package slimeknights.tconstruct.library.modifiers.fluid.entity;

import lombok.Getter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffectContext;
import slimeknights.tconstruct.shared.TinkerDamageTypes;

@Getter
public enum FluidDamageType {
  NORMAL {
    @Override
    public DamageSource apply(FluidEffectContext.Entity context) {
      Player player = context.getPlayer();
      if (player != null) {
        return context.getLevel().damageSources().playerAttack(player);
      } else {
        return context.getLevel().damageSources().mobAttack(context.getEntity());
      }
    }
  },
  FIRE {
    @Override
    public DamageSource apply(FluidEffectContext.Entity context) {
      Player player = context.getPlayer();
      if (player != null) {
        return context.getLevel().damageSources().source(TinkerDamageTypes.PLAYER_ATTACK_FIRE, player);
      } else {
        return context.getLevel().damageSources().source(TinkerDamageTypes.MOB_ATTACK_FIRE, context.getEntity());
      }
    }
  },
  MAGIC {
    @Override
    public DamageSource apply(FluidEffectContext.Entity context) {
      Player player = context.getPlayer();
      if (player != null) {
        return context.getLevel().damageSources().source(TinkerDamageTypes.PLAYER_ATTACK_MAGIC, player);
      } else {
        return context.getLevel().damageSources().source(TinkerDamageTypes.MOB_ATTACK_MAGIC, context.getEntity());
      }
    }
  },
  EXPLOSION {
    @Override
    public DamageSource apply(FluidEffectContext.Entity context) {
      Player player = context.getPlayer();
      if (player != null) {
        return context.getLevel().damageSources().source(TinkerDamageTypes.PLAYER_ATTACK_EXPLOSION, player);
      } else {
        return context.getLevel().damageSources().source(TinkerDamageTypes.MOB_ATTACK_EXPLOSION, context.getEntity());
      }
    }
  },
  PIERCING {
    @Override
    public DamageSource apply(FluidEffectContext.Entity context) {
      Player player = context.getPlayer();
      if (player != null) {
        return context.getLevel().damageSources().source(TinkerDamageTypes.PLAYER_ATTACK_BYPASS_ARMOR, player);
      } else {
        return context.getLevel().damageSources().source(TinkerDamageTypes.MOB_ATTACK_BYPASS_ARMOR, context.getEntity());
      }
    }
  };

  private final String name = this.name().toLowerCase();

  /**
   * Applies this effect to the given damage source
   */
  public abstract DamageSource apply(FluidEffectContext.Entity context);
}
