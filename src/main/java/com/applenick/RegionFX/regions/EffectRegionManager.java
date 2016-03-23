package com.applenick.RegionFX.regions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.applenick.RegionFX.RegionFX;
import com.google.common.collect.Lists;
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
	private List<EffectPlayer> effected_players;


	public EffectRegionManager(){
		this.loaded_regions = Maps.newHashMap();
		this.effected_players = Lists.newArrayList();

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
			}else{
				RegionFX.get().console(ChatColor.AQUA + name + ChatColor.RED + " is not using a valid effect  " + ChatColor.DARK_RED + effect);
			}
		}

	}
	
	public HashMap<ProtectedRegion,EffectRegion> getLoadedRegions(){
		return this.loaded_regions;
	}
	
	public EffectRegion getEffectRegion(String name){
		for(EffectRegion rg : this.getLoadedRegions().values()){
			if(rg.getName().equalsIgnoreCase(name)){
				return rg;
			}
		}
		return null;
	}
	
	private boolean insideRegion(Player player, ProtectedRegion region){
		LocalPlayer lp = RegionFX.getWorldGuard().wrapPlayer(player);
		return region.contains(lp.getPosition());
	}
	
	
	public boolean insideRegion(Player player){
		for(ProtectedRegion region : this.loaded_regions.keySet()){
			if(insideRegion(player , region)){
				return true;
			}
		}
		return false;
	}
	
	public ProtectedRegion getPlayerRegion(Player player){
		for(ProtectedRegion region : this.loaded_regions.keySet()){
			if(insideRegion(player , region)){
				return region;
			}
		}
		return null;
	}
	


	public ProtectedRegion getRegion(String name , World world){
		return RegionFX.getWorldGuard().getRegionManager(world).getRegion(name);
	}

	public void addEffectRegion(EffectRegion eRegion) {
		this.loaded_regions.put(eRegion.getRegion(), eRegion);
	}
	
	
	public void addEffectedPlayer(EffectPlayer player){
		this.effected_players.add(player);
	}
	
	public void removeEffectedPlayer(EffectPlayer player){
		this.effected_players.remove(player);
	}
	
	public List<EffectPlayer> getEffectPlayers() {
		return this.effected_players;
	}
	
	public EffectPlayer getEffectPlayer(Player player){
		for(EffectPlayer ep : this.effected_players){
			if(ep.getPlayer() == player){
				return ep;
			}
		}
		return null;
	}


	public boolean isPlayerEffected(Player player) {
		for(EffectPlayer p : this.effected_players){
			if(p.getPlayer() == player){
				return true;
			}
		}
		return false;
	}

	public void removeEffectRegion(EffectRegion region) {		
		for(Iterator<EffectPlayer> players = this.effected_players.iterator(); players.hasNext();){
			EffectPlayer player = players.next();
			if(player.getRegion() == region){
				player.removeEffects();
				players.remove();
			}
		}
		
		this.loaded_regions.remove(region.getRegion());
	}
}
