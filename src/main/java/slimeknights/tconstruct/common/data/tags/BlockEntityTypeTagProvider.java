package slimeknights.tconstruct.common.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tables.TinkerTables;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class BlockEntityTypeTagProvider extends TagsProvider<BlockEntityType<?>> {

  @SuppressWarnings("deprecation")
  public BlockEntityTypeTagProvider(DataGenerator generatorIn, CompletableFuture<HolderLookup.Provider> lookup, @Nullable ExistingFileHelper existingFileHelper) {
    super(generatorIn.getPackOutput(), Registries.BLOCK_ENTITY_TYPE, lookup, TConstruct.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    this.tag(TinkerTags.TileEntityTypes.CRAFTING_STATION_BLACKLIST)
      .add(
        getBlockETypeResourceKeys(
          BlockEntityType.FURNACE, BlockEntityType.BLAST_FURNACE, BlockEntityType.SMOKER, BlockEntityType.BREWING_STAND,
          TinkerTables.craftingStationTile.get(), TinkerTables.tinkerStationTile.get(), TinkerTables.partBuilderTile.get(),
          TinkerTables.partChestTile.get(), TinkerTables.tinkersChestTile.get(), TinkerTables.castChestTile.get(),
          TinkerSmeltery.basin.get(), TinkerSmeltery.table.get(),
          TinkerSmeltery.melter.get(), TinkerSmeltery.smeltery.get(), TinkerSmeltery.foundry.get())
      );

  }

  @Override
  public String getName() {
    return "Tinkers' Construct Block Entity Type Tags";
  }

  private static ResourceKey<BlockEntityType<?>>[] getBlockETypeResourceKeys(BlockEntityType<?>... blockEntityTypes) {
    //noinspection unchecked
    return Arrays.stream(blockEntityTypes)
      .map(blockEntityType -> ForgeRegistries.BLOCK_ENTITY_TYPES.getResourceKey(blockEntityType).orElseThrow(() -> new IllegalStateException("Block not found in forge registry: " + blockEntityType)))
      .toArray(ResourceKey[]::new);
  }
}
