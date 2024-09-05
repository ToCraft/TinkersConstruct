package slimeknights.tconstruct.library.modifiers.hook.display;

import net.minecraft.network.chat.Component;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

/**
 * Gets the display name of a given modifier. Used for incremental modifiers and swappable modifiers to add their extra info.
 * Can also be used to cap displayed levels to a certain amount.
 * Unlikely other modifier hooks, this is not supported by every modifier, mainly composable supports it. See {@link Modifier#getDisplayName(IToolStackView, slimeknights.tconstruct.library.modifiers.ModifierEntry)} for ordinary modifiers.
 */
public interface DisplayNameModifierHook {

  /**
   * Allows the modifier to transform the modifier's display name.
   *
   * @param tool  Tool instance
   * @param entry Modifier transforming the name
   * @param name  Name after previous modifiers made their changes
   * @return Changed name
   */
  Component getDisplayName(IToolStackView tool, ModifierEntry entry, Component name);

  /**
   * Merger that composes one function after another
   */
  record ComposeMerger(Collection<DisplayNameModifierHook> modules) implements DisplayNameModifierHook {

    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry, Component name) {
      for (DisplayNameModifierHook module : modules) {
        name = module.getDisplayName(tool, entry, name);
      }
      return name;
    }
  }
}
