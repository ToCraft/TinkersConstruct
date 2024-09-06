package slimeknights.tconstruct.common.data.loot;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import slimeknights.tconstruct.TConstruct;

import java.util.HashSet;
import java.util.Map;

public class TConstructLootTableProvider extends LootTableProvider {

  public TConstructLootTableProvider(DataGenerator gen) {
    super(gen.getPackOutput(), new HashSet<>(),
      ImmutableList.of(new SubProviderEntry(BlockLootTableProvider::new, LootContextParamSets.BLOCK), new SubProviderEntry(AdvancementLootTableProvider::new, LootContextParamSets.ADVANCEMENT_REWARD), new SubProviderEntry(EntityLootTableProvider::new, LootContextParamSets.ENTITY)));
  }

  @Override
  protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
    map.forEach((loc, table) -> table.validate(validationtracker));
    // Remove vanilla's tables, which we also loaded so we can redirect stuff to them.
    // This ensures the remaining generator logic doesn't write those to files.
    map.keySet().removeIf((loc) -> !loc.getNamespace().equals(TConstruct.MOD_ID));
  }
}
