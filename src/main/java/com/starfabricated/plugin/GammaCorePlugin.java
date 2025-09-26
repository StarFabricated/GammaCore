package com.starfabricated.plugin;

import com.starfabricated.plugin.NameGen.IncrementingEnhancedProvider;
import com.starfabricated.plugin.NameGen.KeywordNeutralizerProvider;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import software.coley.recaf.analytics.logging.Logging;
import software.coley.recaf.plugin.Plugin;
import software.coley.recaf.plugin.PluginInformation;
import software.coley.recaf.services.config.ConfigManager;
import software.coley.recaf.services.mapping.gen.naming.AbstractNameGeneratorProvider;
import software.coley.recaf.services.mapping.gen.naming.DeconflictingNameGenerator;
import software.coley.recaf.services.mapping.gen.naming.NameGeneratorProviders;

import java.util.List;

@Dependent
@SuppressWarnings("unused")
@PluginInformation(id = "##ID##", version = "##VERSION##", name = "##NAME##", description = "##DESC##")
public class GammaCorePlugin implements Plugin {
	private static final Logger logger = Logging.get(GammaCorePlugin.class);
	private static final List<AbstractNameGeneratorProvider<? extends DeconflictingNameGenerator>> NAME_GENERATOR_PROVIDERS =
			List.of(
					new KeywordNeutralizerProvider(),
					new IncrementingEnhancedProvider()
			) ;


	@Inject
	public GammaCorePlugin(NameGeneratorProviders nameGeneratorProviders , ConfigManager configManager) {
		for (AbstractNameGeneratorProvider<? extends DeconflictingNameGenerator> provider : NAME_GENERATOR_PROVIDERS){
			nameGeneratorProviders.registerProvider(provider);
		}
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
	}
}
