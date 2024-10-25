package slimeknights.tconstruct.world.data;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSet.StructureSelectionEntry;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride.BoundingBoxType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.holdersets.AndHolderSet;
import net.minecraftforge.registries.holdersets.NotHolderSet;
import net.minecraftforge.registries.holdersets.OrHolderSet;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.world.TinkerWorld;
import slimeknights.tconstruct.world.block.FoliageType;
import slimeknights.tconstruct.world.worldgen.islands.IslandStructure;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.core.HolderSet.direct;
import static slimeknights.tconstruct.TConstruct.getResource;
import static slimeknights.tconstruct.world.TinkerStructures.bloodIsland;
import static slimeknights.tconstruct.world.TinkerStructures.bloodSlimeIslandFungus;
import static slimeknights.tconstruct.world.TinkerStructures.clayIsland;
import static slimeknights.tconstruct.world.TinkerStructures.earthSlimeIsland;
import static slimeknights.tconstruct.world.TinkerStructures.earthSlimeIslandTree;
import static slimeknights.tconstruct.world.TinkerStructures.endSlimeIsland;
import static slimeknights.tconstruct.world.TinkerStructures.enderSlimeTree;
import static slimeknights.tconstruct.world.TinkerStructures.enderSlimeTreeTall;
import static slimeknights.tconstruct.world.TinkerStructures.skySlimeIsland;
import static slimeknights.tconstruct.world.TinkerStructures.skySlimeIslandTree;

public class WorldgenDatapackRegistryProvider extends DatapackBuiltinEntriesProvider {

  public WorldgenDatapackRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, createRegistrySet(), Set.of(TConstruct.MOD_ID));
  }

  private static RegistrySetBuilder createRegistrySet() {
    return new RegistrySetBuilder()
      .add(Registries.STRUCTURE, WorldgenDatapackRegistryProvider::bootstrapStructures)
      .add(Registries.STRUCTURE_SET, WorldgenDatapackRegistryProvider::bootstrapStructureSets)
      .add(ForgeRegistries.Keys.BIOME_MODIFIERS, WorldgenDatapackRegistryProvider::bootstrapBiomeModifiers);
  }

  private static void bootstrapStructures(BootstapContext<Structure> context) {


    // earthslime island
    context.register(earthSlimeIsland, IslandStructure.seaBuilder()
      .addDefaultTemplates(getResource("islands/earth/"))
      .addTree(reference(earthSlimeIslandTree), 1)
      .addSlimyGrass(FoliageType.EARTH)
      .build(new StructureSettings(tag(context, TinkerTags.Biomes.EARTHSLIME_ISLANDS), monsterOverride(EntityType.SLIME, 4, 4), Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
    // skyslime island
    context.register(skySlimeIsland, IslandStructure.skyBuilder()
      .addDefaultTemplates(getResource("islands/sky/"))
      .addTree(reference(skySlimeIslandTree), 1)
      .addSlimyGrass(FoliageType.SKY)
      .vines(TinkerWorld.skySlimeVine.get())
      .build(new StructureSettings(tag(context, TinkerTags.Biomes.SKYSLIME_ISLANDS), monsterOverride(TinkerWorld.skySlimeEntity.get(), 3, 4), Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
    // clay island
    context.register(clayIsland, IslandStructure.skyBuilder().addDefaultTemplates(getResource("islands/dirt/"))
      .addTree(reference(context, TreeFeatures.OAK), 4)
      .addTree(reference(context, TreeFeatures.BIRCH), 3)
      .addTree(reference(context, TreeFeatures.SPRUCE), 2)
      .addTree(reference(context, TreeFeatures.ACACIA), 1)
      .addTree(reference(context, TreeFeatures.JUNGLE_TREE_NO_VINE), 1)
      .addGrass(Blocks.GRASS, 7)
      .addGrass(Blocks.FERN, 1)
      .build(new StructureSettings(tag(context, TinkerTags.Biomes.CLAY_ISLANDS), monsterOverride(TinkerWorld.terracubeEntity.get(), 2, 4), Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
    // blood island
    context.register(bloodIsland, IslandStructure.seaBuilder().addDefaultTemplates(getResource("islands/blood/"))
      .addTree(reference(bloodSlimeIslandFungus), 1)
      .addSlimyGrass(FoliageType.BLOOD)
      .build(new StructureSettings(tag(context, TinkerTags.Biomes.BLOOD_ISLANDS), monsterOverride(EntityType.MAGMA_CUBE, 4, 6), Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.NONE)));
    // enderslime
    context.register(endSlimeIsland, IslandStructure.skyBuilder().addDefaultTemplates(getResource("islands/ender/"))
      .addTree(reference(enderSlimeTree), 3)
      .addTree(reference(enderSlimeTreeTall), 17)
      .addSlimyGrass(FoliageType.ENDER)
      .vines(TinkerWorld.enderSlimeVine.get())
      .build(new StructureSettings(tag(context, TinkerTags.Biomes.ENDERSLIME_ISLANDS), monsterOverride(TinkerWorld.enderSlimeEntity.get(), 4, 4), Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
  }

  private static void bootstrapStructureSets(BootstapContext<StructureSet> context) {
    // structure sets
    context.register(ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(TConstruct.MOD_ID, "overworld_ocean_island")), structureSet(new RandomSpreadStructurePlacement(35, 25, RandomSpreadType.LINEAR, 25988585), entry(context, earthSlimeIsland, 1)));
    context.register(ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(TConstruct.MOD_ID, "overworld_sky_island")), structureSet(new RandomSpreadStructurePlacement(40, 15, RandomSpreadType.LINEAR, 14357800), entry(context, skySlimeIsland, 4), entry(context, clayIsland, 1)));
    context.register(ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(TConstruct.MOD_ID, "nether_ocean_island")), structureSet(new RandomSpreadStructurePlacement(15, 10, RandomSpreadType.LINEAR, 65245622), entry(context, bloodIsland, 1)));
    context.register(ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(TConstruct.MOD_ID, "end_sky_island")), structureSet(new RandomSpreadStructurePlacement(25, 12, RandomSpreadType.LINEAR, 368963602), entry(context, endSlimeIsland, 1)));
  }

  private static void bootstrapBiomeModifiers(BootstapContext<BiomeModifier> context) {
    // biome modifiers
    HolderSet<Biome> overworld = tag(context, BiomeTags.IS_OVERWORLD);
    HolderSet<Biome> nether = tag(context, BiomeTags.IS_NETHER);
    HolderSet<Biome> end = tag(context, BiomeTags.IS_END);

    context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TConstruct.MOD_ID, "cobalt_ore")), new AddFeaturesBiomeModifier(nether, direct(reference(TinkerWorld.placedSmallCobaltOre), reference(TinkerWorld.placedLargeCobaltOre)), Decoration.UNDERGROUND_DECORATION));
    // geodes
    context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TConstruct.MOD_ID, "earth_geode")), new AddFeaturesBiomeModifier(overworld, direct(reference(TinkerWorld.placedEarthGeode)), Decoration.LOCAL_MODIFICATIONS));
    context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TConstruct.MOD_ID, "sky_geode")), new AddFeaturesBiomeModifier(and(overworld, not(context.registryLookup(Registries.BIOME).orElseThrow(), or(tag(context, BiomeTags.IS_OCEAN), tag(context, BiomeTags.IS_DEEP_OCEAN), tag(context, BiomeTags.IS_BEACH), tag(context, BiomeTags.IS_RIVER)))), direct(reference(TinkerWorld.placedSkyGeode)), Decoration.LOCAL_MODIFICATIONS));
    context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TConstruct.MOD_ID, "ichor_geode")), new AddFeaturesBiomeModifier(nether, direct(reference(TinkerWorld.placedIchorGeode)), Decoration.LOCAL_MODIFICATIONS));
    context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TConstruct.MOD_ID, "ender_geode")), new AddFeaturesBiomeModifier(and(end, not(context.registryLookup(Registries.BIOME).orElseThrow(), direct(reference(context, Biomes.THE_END)))), direct(reference(TinkerWorld.placedEnderGeode)), Decoration.LOCAL_MODIFICATIONS));
    // spawns
    context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TConstruct.MOD_ID, "spawn_overworld_slime")), new AddSpawnsBiomeModifier(overworld, List.of(new SpawnerData(TinkerWorld.skySlimeEntity.get(), 100, 2, 4))));
    context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TConstruct.MOD_ID, "spawn_end_slime")), new AddSpawnsBiomeModifier(end, List.of(new SpawnerData(TinkerWorld.enderSlimeEntity.get(), 10, 2, 4))));
  }

  @Override
  public String getName() {
    return "Tinkers' Construct Worldgen Datapack Registries";
  }

  /* Registry helpers */

  /** Creates a reference to the given registry object */
  private static <T> Holder<T> reference(BootstapContext<?> context, ResourceKey<T> key) {
    ResourceKey<Registry<T>> registry = ResourceKey.createRegistryKey(key.registry());
    return context.registryLookup(registry).orElseThrow().getOrThrow(key);
  }

  /** Creates a reference to the given registry object */
  private static <T> Holder<T> reference(RegistryObject<T> object) {
    return object.getHolder().orElseThrow();
  }


  /* Holder sets */

  /** Creates a holder set tag for the given registry */
  private static <T> HolderSet.Named<T> tag(BootstapContext<?> context, TagKey<T> key) {
    return context.registryLookup(key.registry()).orElseThrow().getOrThrow(key);
  }

  /** Ands the holder sets together */
  @SafeVarargs
  private static <T> AndHolderSet<T> and(HolderSet<T>... sets) {
    return new AndHolderSet<>(List.of(sets));
  }

  /** Ors the holder sets together */
  @SafeVarargs
  private static <T> OrHolderSet<T> or(HolderSet<T>... sets) {
    return new OrHolderSet<>(List.of(sets));
  }

  private static <T> NotHolderSet<T> not(HolderLookup.RegistryLookup<T> lookup, HolderSet<T> set) {
    return new NotHolderSet<>(lookup, set);
  }

  /* Object creation helpers */

  /** Saves a structure set */
  private static StructureSet structureSet(StructurePlacement placement, StructureSelectionEntry... structures) {
    return new StructureSet(List.of(structures), placement);
  }

  /** Creates an entry for a registry object */
  private static StructureSelectionEntry entry(BootstapContext<?> context, ResourceKey<Structure> structure, int weight) {
    return new StructureSelectionEntry(reference(context, structure), weight);
  }

  /** Creates a spawn override for a single mob */
  private static Map<MobCategory,StructureSpawnOverride> monsterOverride(EntityType<?> entity, int min, int max) {
    return Map.of(MobCategory.MONSTER, new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create(new MobSpawnSettings.SpawnerData(entity, 1, min, max))));
  }
}
