package com.applenick.RegionFX.nomove;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
	
	
}
