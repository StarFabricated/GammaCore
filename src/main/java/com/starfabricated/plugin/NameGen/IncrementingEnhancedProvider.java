package com.starfabricated.plugin.NameGen;

import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import software.coley.observables.ObservableBoolean;
import software.coley.observables.ObservableString;
import software.coley.recaf.config.BasicConfigValue;
import software.coley.recaf.services.mapping.gen.naming.AbstractNameGeneratorProvider;

@ApplicationScoped
public class IncrementingEnhancedProvider extends AbstractNameGeneratorProvider<IncrementingEnhanced> {
    public static final String ID = "incrementingEnhanced";
    private final ObservableString class_prefix = new ObservableString("Class_");
    private final ObservableString field_prefix = new ObservableString("field_");
    private final ObservableString method_prefix = new ObservableString("method_");
    private final ObservableString var_prefix = new ObservableString("var");
    private final ObservableBoolean save_package = new ObservableBoolean(false);


    public IncrementingEnhancedProvider() {
        super(ID);
        addValue(new BasicConfigValue<>("class_prefix", String.class, class_prefix));
        addValue(new BasicConfigValue<>("field_prefix", String.class, field_prefix));
        addValue(new BasicConfigValue<>("method_prefix", String.class, method_prefix));
        addValue(new BasicConfigValue<>("var_prefix", String.class, var_prefix));
        addValue(new BasicConfigValue<>("save_package", boolean.class, save_package));
   }


    @Nonnull
    @Override
    public IncrementingEnhanced createGenerator() {
        return new IncrementingEnhanced(class_prefix.getValue(),field_prefix.getValue(),method_prefix.getValue(),var_prefix.getValue(),save_package.getValue());
    }

}
