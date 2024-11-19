package slimeknights.tconstruct.shared;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import slimeknights.tconstruct.TConstruct;

public class TinkerDamageTypes {

  public static final ResourceKey<DamageType> SELF_DESTRUCT = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("self_destruct"));
  public static final ResourceKey<DamageType> BLEEDING = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("bleed"));
  public static final ResourceKey<DamageType> SMELTERY_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("smeltery_heat"));
  public static final ResourceKey<DamageType> SMELTERY_MAGIC = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("smeltery_magic"));
  public static final ResourceKey<DamageType> PLAYER_ATTACK_BYPASS_ARMOR = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("player_attack_bypass_armor"));
  public static final ResourceKey<DamageType> MOB_ATTACK_BYPASS_ARMOR = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("mob_attack_bypass_armor"));
  public static final ResourceKey<DamageType> PLAYER_ATTACK_EXPLOSION = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("player_attack_explosion"));
  public static final ResourceKey<DamageType> MOB_ATTACK_EXPLOSION = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("mob_attack_explosion"));
  public static final ResourceKey<DamageType> PLAYER_ATTACK_FIRE = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("player_attack_fire"));
  public static final ResourceKey<DamageType> MOB_ATTACK_FIRE = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("mob_attack_fire"));
  public static final ResourceKey<DamageType> PLAYER_ATTACK_MAGIC = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("player_attack_magic"));
  public static final ResourceKey<DamageType> MOB_ATTACK_MAGIC = ResourceKey.create(Registries.DAMAGE_TYPE, TConstruct.getResource("mob_attack_magic"));

  public static void bootstrap(BootstapContext<DamageType> context) {
    context.register(SELF_DESTRUCT, new DamageType(TConstruct.prefix("self_destruct"), 0.1F));
    context.register(BLEEDING, new DamageType(TConstruct.prefix("bleed"), 0.1F));
    context.register(SMELTERY_DAMAGE, new DamageType(TConstruct.prefix("smeltery_heat"), 0.1F));
    context.register(SMELTERY_MAGIC, new DamageType(TConstruct.prefix("smeltery_magic"), 0.1F));
    context.register(PLAYER_ATTACK_BYPASS_ARMOR, new DamageType("player", 0.1F));
    context.register(MOB_ATTACK_BYPASS_ARMOR, new DamageType("mob", 0.1F));
    context.register(PLAYER_ATTACK_EXPLOSION, new DamageType("player", 0.1F));
    context.register(MOB_ATTACK_EXPLOSION, new DamageType("mob", 0.1F));
    context.register(PLAYER_ATTACK_FIRE, new DamageType("player", 0.1F));
    context.register(MOB_ATTACK_FIRE, new DamageType("mob", 0.1F));
    context.register(PLAYER_ATTACK_MAGIC, new DamageType("player", 0.1F));
    context.register(MOB_ATTACK_MAGIC, new DamageType("mob", 0.1F));
  }

  public static DamageSource getSource(RegistryAccess registryAccess, ResourceKey<DamageType> damageType) {
    return new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType));
  }
}
