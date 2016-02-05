package com.applenick.RegionFX.regions;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.potion.PotionEffectType;

import com.applenick.RegionFX.RegionFX;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/************************************************
			 Created By AppleNick
Copyright © 2016 , AppleNick, All rights reserved.
			http://applenick.com
 *************************************************/
public class EffectRegion {
	
	
	private String name;
	private PotionEffectType type;
	private int level;
	private boolean active;
	private World world;
	private ProtectedRegion region;
	
	
	public EffectRegion(String name, PotionEffectType type, int level, boolean active , World world){
		this.name = name;
		this.type = type;
		this.level = level;
		this.active = active;
		this.world = world;
	}


	public ProtectedRegion getRegion(){
		return region;
	}
	
	public void setRegion(ProtectedRegion region){
		this.region = region;
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name of the region
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the Potion Type
	 */
	public PotionEffectType getType() {
		return type;
	}


	/**
	 * @param type the PotionEffect for the Region
	 */
	public void setType(PotionEffectType type) {
		this.type = type;
	}


	/**
	 * @return the level of potion multiplier
	 */
	public int getLevel() {
		return level;
	}


	/**
	 * @param level the level of the PotionEffect for the Region
	 */
	public void setLevel(int level) {
		this.level = level;
	}


	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}


	/**
	 * @param active Set if the current region is active (Gives Potion Effects or not)
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public World getWorld() {
		return world;
	}


	public void setWorld(World world) {
		this.world = world;
	}


	@Override
	public String toString(){
		return this.getName() + ":" + this.getType().getName() + ":" + this.getLevel() + ":" + this.isActive() + ":" + this.getWorld().getName();
	}
	
	public void save(){
		List<String> regions = RegionFX.get().getConfig().getStringList("regions");
		regions.add(this.toString());
		RegionFX.get().getConfig().set("regions", regions);
		RegionFX.get().saveConfig();
		RegionFX.get().console(ChatColor.AQUA +  this.name + ChatColor.GREEN + " was saved to the Config");
	}

}
