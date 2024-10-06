package slimeknights.tconstruct.plugin.jsonthings.item;

import dev.gigaherz.jsonthings.things.IFlexItem;
import dev.gigaherz.jsonthings.things.StackContext;
import dev.gigaherz.jsonthings.things.events.FlexEventHandler;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ranged.ModifiableCrossbowItem;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class FlexModifiableCrossbowItem extends ModifiableCrossbowItem implements IFlexItem {

  private final Map<String, FlexEventHandler> eventHandlers = new HashMap<>();
  private final Set<CreativeModeTab> tabs = new HashSet<>();

  private final boolean allowFireworks;

  public FlexModifiableCrossbowItem(Properties properties, ToolDefinition toolDefinition, boolean allowFireworks) {
    super(properties, toolDefinition);
    this.allowFireworks = allowFireworks;
  }

  @Override
  public Predicate<ItemStack> getSupportedHeldProjectiles() {
    return allowFireworks ? ARROW_OR_FIREWORK : ARROW_ONLY;
  }



  /* not honestly sure what events do, but trivial to support */

  @Override
  public void addEventHandler(String name, FlexEventHandler flexEventHandler) {
    this.eventHandlers.put(name, flexEventHandler);
  }

  @Nullable
  @Override
  public FlexEventHandler getEventHandler(String name) {
    return this.eventHandlers.get(name);
  }
}
