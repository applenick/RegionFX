package com.applenick.RegionFX.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.applenick.RegionFX.RegionFX;

/************************************************
			 Created By AppleNick
Copyright © 2016 , AppleNick, All rights reserved.
			http://applenick.com
 *************************************************/
public class RegionListener implements Listener {

	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		if(RegionFX.get().getNoMoveRegionManager().isNoMover(event.getPlayer())){
			event.setCancelled(true);
			return;
		}else{
			return;
		}
	}
	
}

