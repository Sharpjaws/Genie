package com.djrapitops.genie.enums;

import org.bukkit.entity.EntityType;

public enum NonHostileMob {
BAT,
CHICKEN,
COW,
PIG,
SHEEP,
RABBIT,
HORSE,
SQUID,
VILLAGER,
MUSHROOM_COW,
POLAR_BEAR,
SKELETON_HORSE,
DONKEY,
WOLF,
OCELOT,
MULE,
LLAMA,
PARROT,
IRON_GOLEM,
SNOWMAN;


public static Boolean contains(EntityType e){
	for (NonHostileMob m : values()){
		if (m.toString().contains(e.toString())){
			return true;
		}
	}
	return false;
	
	}	
}

