package com.applenick.RegionFX.commands;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.applenick.RegionFX.RegionFX;
import com.applenick.RegionFX.regions.EffectRegion;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/************************************************
			 Created By AppleNick
Copyright © 2016 , AppleNick, All rights reserved.
			http://applenick.com
 *************************************************/
public class RegionCommands {
	
	
	public static class RegionParent{
		@Command(
				aliases = {"regionfx" , "rfx"},
				desc = "Main RegionFX Command"
				)
		@CommandPermissions("regionfx.admin")
		@NestedCommand({RegionCommands.class})
		public static void regionFXCommand(final CommandContext args, final CommandSender sender) throws CommandException{
		}
	}

	
	// /regionfx create [Name] [Effect] [Level] 
	@Command(
			aliases = {"create"},
			desc = "Create an Effect Region",
			min = 0,
			max = 3
			)
	@CommandPermissions("regionfx.admin")
	public static void createRegionCommand(final CommandContext args, final CommandSender sender) throws CommandException{
		
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "Sorry, this command can only be performed by a player.");
			return;
		}
		
		
		Player player = (Player) sender;		
		
		if(args.argsLength() < 3){
			player.sendMessage(ChatColor.RED + "Please provide a [name] [effect] [level]");
			return;
		}
		
		
		if(args.argsLength() == 3){
			String name = args.getString(0);
			String effect = args.getString(1);
			int level = args.getInteger(2);
			
			if(name != null){
				ProtectedRegion region = RegionFX.get().getEffectRegionManager().getRegion(name, player.getWorld());
				if(region != null){
					if(PotionEffectType.getByName(effect) != null){
						EffectRegion eRegion = new EffectRegion(name , PotionEffectType.getByName(effect), level , true, player.getWorld());
						
						//Set the Region
						eRegion.setRegion(region);
						
						//Save to Config
						eRegion.save();
						
						//Add to Manager
						RegionFX.get().getEffectRegionManager().addEffectRegion(eRegion);

						player.sendMessage(ChatColor.AQUA + name + ChatColor.GREEN + " has been suscessfully setup!");
						return;
					}else{
						player.sendMessage(ChatColor.RED + "Please provide a valid Potion Effect");
						return;
					}
				}else{
					player.sendMessage(ChatColor.RED + "Sorry, this region does not exist.");
					return;
				}
			}else{
				player.sendMessage(ChatColor.RED + "Please provide a name.");
				return;
			}
		}	
	}
	
	
	// /regionfx delete [name]
	@Command(
			aliases = {"delete"},
			desc = "Create an Effect Region"
			)
	@CommandPermissions("regionfx.admin")
	public static void deleteRegionCommand(final CommandContext args, final CommandSender sender) throws CommandException{
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "Sorry, this command can only be performed by a player.");
			return;
		}		
		Player player = (Player) sender;	
		
		if(args.argsLength() == 0){
			player.sendMessage(ChatColor.RED + "Please provide a region [name]");
			return;
		}
		
		String name = args.getString(0);
		
		if(RegionFX.get().getEffectRegionManager().getEffectRegion(name) != null){
			EffectRegion region = RegionFX.get().getEffectRegionManager().getEffectRegion(name);
			
			List<String> regions = RegionFX.get().getConfig().getStringList("regions");
			
			for(String s : regions){
				String[] info = StringUtils.splitByWholeSeparator(s, ":");
				if(info[0].equalsIgnoreCase(name)){
					regions.remove(s);
				}
			}
			
			RegionFX.get().getConfig().set("regions", regions);
			RegionFX.get().saveConfig();
			
			
			RegionFX.get().console(ChatColor.RED + name + ChatColor.GREEN + " has been deleted from the config.");
			player.sendMessage(ChatColor.RED + name + ChatColor.GREEN + " has been deleted.");
			return;
		}else{
			player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.RED + " is not a valid region name.");
			return;
		}
		
		

	}
	
	// /regionfx edit [Name] [New Effect] [New Level] [New Time]
	@Command(
			aliases = {"list"},
			desc = "List active Effect Regions"
			)
	@CommandPermissions("regionfx.admin")
	public static void listRegionCommand(final CommandContext args, final CommandSender sender) throws CommandException{
		if(RegionFX.get().getEffectRegionManager().getLoadedRegions().size() == 0){
			sender.sendMessage(ChatColor.RED + "There are no active regions...");
			return;
		}

		sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "-----" + ChatColor.AQUA + "Region" + ChatColor.LIGHT_PURPLE + "FX" + ChatColor.GOLD + "-" + ChatColor.GREEN + "Active Regions" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "-----");
	
		for(EffectRegion rg : RegionFX.get().getEffectRegionManager().getLoadedRegions().values()){
			sender.sendMessage(ChatColor.AQUA + rg.getName() + ChatColor.GRAY +  " - " + ChatColor.GREEN + rg.getType().getName().toLowerCase() +  ChatColor.GRAY + " - " + ChatColor.YELLOW + rg.getLevel());
		}
	
		sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "-----------------------");
		return;
	}
}
