package com.applenick.RegionFX.tasks;

import com.applenick.RegionFX.RegionFX;

/************************************************
			 Created By AppleNick
Copyright © 2016 , AppleNick, All rights reserved.
			http://applenick.com
 *************************************************/
public class EffectTaskManager {
	
	private EffectTask task;
	
	public EffectTaskManager(){
		RegionFX.get().getServer().getScheduler().scheduleSyncDelayedTask(RegionFX.get(), new Runnable(){
			@Override
			public void run() {
				task = new EffectTask();
			}
		}, 0L);
	}
	
	
	public EffectTask getTask(){
		return task;
	}

}
