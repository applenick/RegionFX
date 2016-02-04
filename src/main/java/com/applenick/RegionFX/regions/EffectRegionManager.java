package com.applenick.RegionFX.regions;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.applenick.RegionFX.RegionFX;
import com.google.common.collect.Maps;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/************************************************
			 Created By AppleNick
Copyright © 2016 , AppleNick, All rights reserved.
			http://applenick.com
 *************************************************/
public class EffectRegionManager {

	private HashMap<ProtectedRegion,EffectRegion> loaded_regions;


	public EffectRegionManager(){
		this.loaded_regions = Maps.newHashMap();

		for(String s : RegionFX.get().getConfig().getStringList("regions")){
			String[] info = StringUtils.splitByWholeSeparator(s, ":");

			if(info.length < 5 || info == null){
				RegionFX.get().console(ChatColor.DARK_RED + "There was an empty or incomplete entry saved. Please delete config or restore it to an eariler save.");
				RegionFX.get().getServer().getPluginManager().disablePlugin(RegionFX.get());
				break;
			}

			String name = info[0];
			String effect = info[1];
			int level = Integer.parseInt(info[2]);
			boolean active = Boolean.parseBoolean(info[3]);
			String world = info[4];

			if(PotionEffectType.getByName(effect) != null){

				if(Bukkit.getWorld(world) != null){
					ProtectedRegion region = getRegion(name , Bukkit.getWorld(world));
					
					if(region != null){
						EffectRegion eRegion = new EffectRegion(name , PotionEffectType.getByName(effect) , level, active , Bukkit.getWorld(world));
						eRegion.setRegion(region);
						this.loaded_regions.put(region, eRegion);
						RegionFX.get().console(name + ChatColor.GREEN + " has been loaded and activated");
					}else{
						RegionFX.get().console(name + ChatColor.GREEN + "'s region could not be found. \n Please use /regionfx delete" + name + " if this region has been deleted by World Guard");
					}
										
				}else{
					RegionFX.get().console(ChatColor.AQUA + name + ChatColor.RED + " could not locate it's world " + ChatColor.GRAY + world);
					return;
				}

			}
		}

	}
	
	public HashMap<ProtectedRegion,EffectRegion> getLoadedRegions(){
		return this.loaded_regions;
	}
	
	public boolean insideRegion(Player player, ProtectedRegion region){
		LocalPlayer lp = RegionFX.getWorldGuard().wrapPlayer(player);
		return region.contains(lp.getPosition());
	}



	public ProtectedRegion getRegion(String name , World world){
		return RegionFX.getWorldGuard().getRegionManager(world).getRegion(name);
	}

	public void addEffectRegion(EffectRegion eRegion) {
		this.loaded_regions.put(eRegion.getRegion(), eRegion);
	}

}
