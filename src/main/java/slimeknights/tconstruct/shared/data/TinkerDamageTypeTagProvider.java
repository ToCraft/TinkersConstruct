package slimeknights.tconstruct.shared.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.shared.TinkerDamageTypes;

import java.util.concurrent.CompletableFuture;

public class TinkerDamageTypeTagProvider extends TagsProvider<DamageType> {

  public TinkerDamageTypeTagProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> registriesProvider, ExistingFileHelper existingFileHelper) {
    super(generator.getPackOutput(), Registries.DAMAGE_TYPE, registriesProvider, TConstruct.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    getOrCreateRawBuilder(DamageTypeTags.BYPASSES_ARMOR).addElement(TinkerDamageTypes.SELF_DESTRUCT.location()).addElement(TinkerDamageTypes.PLAYER_ATTACK_BYPASS_ARMOR.location()).addElement(TinkerDamageTypes.MOB_ATTACK_BYPASS_ARMOR.location());
    getOrCreateRawBuilder(DamageTypeTags.IS_EXPLOSION).addElement(TinkerDamageTypes.SELF_DESTRUCT.location()).addElement(TinkerDamageTypes.PLAYER_ATTACK_EXPLOSION.location()).addElement(TinkerDamageTypes.MOB_ATTACK_EXPLOSION.location());

    getOrCreateRawBuilder(DamageTypeTags.BYPASSES_EFFECTS).addElement(TinkerDamageTypes.BLEEDING.location()).addElement(TinkerDamageTypes.PLAYER_ATTACK_MAGIC.location()).addElement(TinkerDamageTypes.MOB_ATTACK_MAGIC.location());

    getOrCreateRawBuilder(DamageTypeTags.IS_FIRE).addElement(TinkerDamageTypes.SMELTERY_DAMAGE.location()).addElement(TinkerDamageTypes.PLAYER_ATTACK_FIRE.location()).addElement(TinkerDamageTypes.MOB_ATTACK_FIRE.location());
  }
}
