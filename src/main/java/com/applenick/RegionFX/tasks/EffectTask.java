package com.applenick.RegionFX.tasks;

import org.bukkit.entity.Player;

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

	public EffectTask() {
		RegionFX.get().getServer().getScheduler().scheduleSyncRepeatingTask(RegionFX.get(), this, 0L, 10L);
	}

	public void cancle() {
		RegionFX.get().getServer().getScheduler().cancelTask(taskID);
	}

	@Override
	public void run() {
		for (Player player : RegionFX.get().getServer().getOnlinePlayers()) {
			ProtectedRegion region = RegionFX.get().getEffectRegionManager().getPlayerRegion(player);
			EffectPlayer ep = RegionFX.get().getEffectRegionManager().getEffectPlayer(player);
			EffectRegion er = RegionFX.get().getEffectRegionManager().getLoadedRegions().get(region);
			if (RegionFX.get().getEffectRegionManager().insideRegion(player) && player.getWorld().equals(er.getWorld())) {
				if (RegionFX.get().getEffectRegionManager().isPlayerEffected(player)) {
					if (ep.getRegion().getRegion() != region) {
						// Remove player from an overlapping Region & Add new
						// Region
						ep.removeEffects();
						RegionFX.get().getEffectRegionManager().removeEffectedPlayer(ep);

						RegionFX.get().getEffectRegionManager().addEffectedPlayer(new EffectPlayer(player, er));
					}
				} else {
					RegionFX.get().getEffectRegionManager().addEffectedPlayer(new EffectPlayer(player, er));
				}
			} else {
				if (RegionFX.get().getEffectRegionManager().isPlayerEffected(player)) {
					if (ep != null) {
						ep.removeEffects();
						RegionFX.get().getEffectRegionManager().removeEffectedPlayer(ep);
					}
				}
			}
		}
	}
}

