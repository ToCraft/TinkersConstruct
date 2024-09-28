package slimeknights.tconstruct.fluids.data;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import slimeknights.mantle.data.GenericDataProvider;

import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

/**
 * Quick and dirty data provider to generate blockstate files for fluids
 */
public class FluidBlockstateModelProvider extends GenericDataProvider {

  private final String modId;

  public FluidBlockstateModelProvider(DataGenerator generator, String modId) {
    super(generator.getPackOutput(), PackOutput.Target.RESOURCE_PACK, "blockstates");
    this.modId = modId;
  }

  @Override
  public CompletableFuture<?> run(CachedOutput cache) {
    // statically created JSON to reference block/fluid, which is just a dummy model
    JsonObject normal = new JsonObject();
    normal.addProperty("model", "tconstruct:block/fluid");
    JsonObject variants = new JsonObject();
    variants.add("", normal);
    JsonObject blockstate = new JsonObject();
    blockstate.add("variants", variants);

    // loop over all liquid blocks, adding a blockstate for them
    for (Entry<ResourceKey<Block>, Block> entry : BuiltInRegistries.BLOCK.entrySet()) {
      ResourceLocation id = entry.getKey().location();
      if (id.getNamespace().equals(modId) && entry.getValue() instanceof LiquidBlock) {
        saveJson(cache, id, blockstate);
      }
    }
    return CompletableFuture.completedFuture(null);
  }

  @Override
  public String getName() {
    return modId + " Fluid Blockstate Model Provider";
  }
}
