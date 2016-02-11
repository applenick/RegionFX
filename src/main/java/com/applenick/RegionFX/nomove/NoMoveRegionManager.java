package com.applenick.RegionFX.nomove;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.applenick.RegionFX.RegionFX;
import com.google.common.collect.Maps;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/************************************************
			 Created By AppleNick
Copyright © 2016 , AppleNick, All rights reserved.
			http://applenick.com
 *************************************************/
public class NoMoveRegionManager {
	
	private HashMap<NoMoveRegion , ProtectedRegion> nomove_regions;
	private List<Player> nomove_players;
	
	public NoMoveRegionManager(){
		this.nomove_regions = Maps.newHashMap();
		
		this.fetchNoMoveRegions();
	}
	
	public void addRegion(NoMoveRegion region , ProtectedRegion wgRegion){
		this.nomove_regions.put(region, wgRegion);
	}
	
	public void removeRegion(NoMoveRegion region){
		this.nomove_regions.remove(region);
	}
	
	public Set<NoMoveRegion> getNoMoveRegions(){
		return this.nomove_regions.keySet();
	}

	public ProtectedRegion getRegion(NoMoveRegion region){
		return this.nomove_regions.get(region);
	}
	
	
	public void addPlayer(Player player){
		this.nomove_players.add(player);
	}
	
	public void removePlayer(Player player){
		this.nomove_players.remove(player);
	}
	
	public boolean isNoMover(Player player){
		for(Player p : this.nomove_players){
			if(p == player){
				return true;
			}
		}
		return false;
	}
	
	private boolean insideNoMoveRegion(Player player, ProtectedRegion region){
		LocalPlayer lp = RegionFX.getWorldGuard().wrapPlayer(player);
		return region.contains(lp.getPosition());
	}
	
	
	public boolean insideNoMoveRegion(Player player){
		for(ProtectedRegion region : this.nomove_regions.values()){
			if(insideNoMoveRegion(player , region)){
				return true;
			}
		}
		return false;
	}
	
	public void fetchNoMoveRegions(){		
		ConfigurationSection section = RegionFX.get().getConfig().getConfigurationSection("no-move-regions");
		for(String s : section.getKeys(false)){
			World w = Bukkit.getWorld(section.getString(s));
			if(w != null){
				NoMoveRegion region = new NoMoveRegion(s , w);
				ProtectedRegion rg = RegionFX.getWorldGuard().getRegionManager(w).getRegion(s);	
				if(rg != null){
					region.setRegion(rg);
					this.nomove_regions.put(region, rg);
				}
			}
		}
	}
}
