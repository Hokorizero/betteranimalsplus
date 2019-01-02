package its_meow.betteranimalsplus.common.config;

import java.util.HashMap;

import its_meow.betteranimalsplus.BetterAnimalsPlusMod;
import its_meow.betteranimalsplus.init.EntityContainer;
import its_meow.betteranimalsplus.init.MobRegistry;
import its_meow.betteranimalsplus.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;

public class BetterAnimalsPlusConfig {

	public static int brownBearWeight = 7;
	public static int blackBearWeight = 5;
	public static int kermodeBearWeight = 4;
	public static int deerWeight = 16;
	public static int lammergeierWeight = 7;
	public static int feralWolfWeight = 8;
	public static int coyoteWeight = 6;
	public static int foxWeight = 10;
	public static int tarantulaWeight = 13;
	public static int hirschgeistWeight = 2;
	public static int goatWeight = 10;
	public static int jellyFishWeight = 10;
	public static int pheasantWeight = 10;
	public static int reindeerWeight = 10;

	public static boolean spawnTrillium = true;
	
	public static HashMap<EntityContainer, EntityConfigurationSection> sections = new HashMap<EntityContainer, EntityConfigurationSection>();

	public static void readConfig(){
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initConfig(cfg);
		} catch (Exception e1) {
			BetterAnimalsPlusMod.logger.log(org.apache.logging.log4j.Level.ERROR, "Problem Loading Config!!", e1);
		} finally {
			if(cfg.hasChanged()){
				cfg.save();
			}
		}
	}

	public static void initConfig(Configuration cfg) {
		spawnTrillium = cfg.getBoolean("generatetrillium", "generation", true, "Does not remove item, prevents world gen");
		for(EntityContainer container : MobRegistry.entityList) {
			EntityConfigurationSection configSection = new EntityConfigurationSection(container.entityClazz, container.minGroup, container.maxGroup, container.weight);
			sections.put(container, configSection);
		}
		for(EntityContainer container : sections.keySet()) {
			EntityConfigurationSection section = sections.get(container);
			container.maxGroup = section.max;
			container.minGroup = section.min;
			container.weight = section.weight;
			container.doRegister = section.doRegister;
			container.doSpawning = section.doSpawning;
		}
	}

}
