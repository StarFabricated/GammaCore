package com.starfabricated.plugin.NameGen;

import jakarta.annotation.Nonnull;
import software.coley.recaf.services.mapping.gen.naming.AbstractNameGeneratorProvider;

public class KeywordNeutralizerProvider extends AbstractNameGeneratorProvider<KeywordNeutralizer> {

    public KeywordNeutralizerProvider() {
        super("neutralizer");
    }

    @Nonnull
    @Override
    public KeywordNeutralizer createGenerator() {
        return new KeywordNeutralizer();
    }
}
