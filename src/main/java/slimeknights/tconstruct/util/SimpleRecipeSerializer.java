package slimeknights.tconstruct.util;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public record SimpleRecipeSerializer<R extends Recipe<?>>(
  Function<ResourceLocation, R> create
) implements RecipeSerializer<R>
{
  @Override
  public @NotNull R fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe)
  {
    return create.apply(pRecipeId);
  }

  @Override
  public @Nullable R fromNetwork(@NotNull ResourceLocation pRecipeId, @NotNull FriendlyByteBuf pBuffer)
  {
    return create.apply(pRecipeId);
  }

  @Override
  public void toNetwork(@NotNull FriendlyByteBuf pBuffer, @NotNull R pRecipe)
  {
  }
}
