package slimeknights.tconstruct.library.json.predicate.modifier;

import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.registry.GenericLoaderRegistry.IGenericLoader;
import slimeknights.tconstruct.library.modifiers.ModifierId;

/**
 * Predicate matching a single modifier
 */
public record SingleModifierPredicate(ModifierId modifier) implements ModifierPredicate {

  public static final RecordLoadable<SingleModifierPredicate> LOADER = RecordLoadable.create(ModifierId.PARSER.requiredField("modifier", SingleModifierPredicate::modifier), SingleModifierPredicate::new);

  @Override
  public boolean matches(ModifierId input) {
    return input.equals(modifier);
  }

  @Override
  public IGenericLoader<? extends ModifierPredicate> getLoader() {
    return LOADER;
  }
}
