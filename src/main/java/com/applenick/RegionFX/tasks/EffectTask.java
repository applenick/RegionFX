package com.applenick.RegionFX.tasks;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.applenick.RegionFX.RegionFX;
import com.applenick.RegionFX.regions.EffectRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/************************************************
			 Created By AppleNick
Copyright © 2016 , AppleNick, All rights reserved.
			http://applenick.com
 *************************************************/
public class EffectTask implements Runnable {

	private int taskID;
	
	public EffectTask(){
		RegionFX.get().getServer().getScheduler().scheduleSyncRepeatingTask(RegionFX.get(), this, 0L, 20L);
	}
	
	public void cancle(){
		RegionFX.get().getServer().getScheduler().cancelTask(taskID);
	}
	
	@Override
	public void run() {
		for(Player player : RegionFX.get().getServer().getOnlinePlayers()){
			for(ProtectedRegion region : RegionFX.get().getEffectRegionManager().getLoadedRegions().keySet()){
				if(RegionFX.get().getEffectRegionManager().insideRegion(player, region)){
					if(player.getActivePotionEffects().contains(RegionFX.get().getEffectRegionManager().getLoadedRegions().get(region))){
						return;
					}else{
						EffectRegion er = RegionFX.get().getEffectRegionManager().getLoadedRegions().get(region);
						player.addPotionEffect(new PotionEffect(er.getType() , Integer.MAX_VALUE, er.getLevel(), true, false), true);
					}
				}
			}
		}
	}
}
