package com.applenick.RegionFX.tasks;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.applenick.RegionFX.RegionFX;
import com.applenick.RegionFX.regions.EffectPlayer;
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
		RegionFX.get().getServer().getScheduler().scheduleSyncRepeatingTask(RegionFX.get(), this, 0L, 10L);
	}

	public void cancle(){
		RegionFX.get().getServer().getScheduler().cancelTask(taskID);
	}

	@Override
	public void run() {
		for(Player player : RegionFX.get().getServer().getOnlinePlayers()){
			for(ProtectedRegion region : RegionFX.get().getEffectRegionManager().getLoadedRegions().keySet()){
				if(RegionFX.get().getEffectRegionManager().insideRegion(player, region)){
					
					
					if(!RegionFX.get().getEffectRegionManager().isPlayerEffected(player)){
						EffectRegion er = RegionFX.get().getEffectRegionManager().getLoadedRegions().get(region);
						RegionFX.get().getEffectRegionManager().addEffectedPlayer(new EffectPlayer(player , er));
						continue;
					}else{
						EffectPlayer ep = RegionFX.get().getEffectRegionManager().getEffectPlayer(player);
						if(ep.getRegion().getRegion() != region){
							//Remove player from an overlapping Region & Add new Region
							ep.removeEffects();
							RegionFX.get().getEffectRegionManager().removeEffectedPlayer(ep);

							EffectRegion er = RegionFX.get().getEffectRegionManager().getLoadedRegions().get(region);
							RegionFX.get().getEffectRegionManager().addEffectedPlayer(new EffectPlayer(player , er));							
						}
					}
				}else{
					if(RegionFX.get().getEffectRegionManager().isPlayerEffected(player)){
						EffectPlayer ep = RegionFX.get().getEffectRegionManager().getEffectPlayer(player);
						if(ep != null){
							ep.removeEffects();
							RegionFX.get().getEffectRegionManager().removeEffectedPlayer(ep);
						}
					}
				}
			}
		}
	}
}
