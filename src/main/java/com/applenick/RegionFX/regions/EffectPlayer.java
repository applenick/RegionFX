package com.applenick.RegionFX.regions;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

/************************************************
			 Created By AppleNick
Copyright © 2016 , AppleNick, All rights reserved.
			http://applenick.com
 *************************************************/
public class EffectPlayer {
	
	private Player player;
	private EffectRegion region;

	public EffectPlayer(Player player, EffectRegion region){
		this.player = player;
		this.region = region;
		
		this.applyEffects();
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public EffectRegion getRegion(){
		return region;
	}
		
	public void applyEffects(){
		player.addPotionEffect(new PotionEffect(region.getType() , Integer.MAX_VALUE, region.getLevel(), true, false), true);
	}
	
	public void removeEffects(){
		player.removePotionEffect(region.getType());
	}
	
}
