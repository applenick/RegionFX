package com.applenick.RegionFX.nomove;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.applenick.RegionFX.RegionFX;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/************************************************
			 Created By AppleNick
Copyright © 2016 , AppleNick, All rights reserved.
			http://applenick.com
 *************************************************/
public class NoMoveRegion {

	private String name;
	private ProtectedRegion region;
	private World world;
	
	
	public NoMoveRegion(String name , World world){
		this.name = name;
		this.world = world;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the region
	 */
	public ProtectedRegion getRegion() {
		return region;
	}


	/**
	 * @param region the region to set
	 */
	public void setRegion(ProtectedRegion region) {
		this.region = region;
	}


	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}


	/**
	 * @param world the world to set
	 */
	public void setWorld(World world) {
		this.world = world;
	}
		
	public void save(){
		ConfigurationSection section = RegionFX.get().getConfig().getConfigurationSection("no-move-regions");
		section.set(name, world.getName());
		RegionFX.get().saveConfig();
	}

	
}
