package com.applenick.RegionFX.commands;

import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;

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

	
	// /regionfx create [Name] [Effect] [Level] [Time] 
	@Command(
			aliases = {"create"},
			desc = "Create an Effect Region",
			min = 0,
			max = 4
			)
	@CommandPermissions("regionfx.admin")
	public static void createRegionCommand(final CommandContext args, final CommandSender sender) throws CommandException{
		
	}
	
	
	// /regionfx delete [name]
	@Command(
			aliases = {"delete"},
			desc = "Create an Effect Region"
			)
	@CommandPermissions("regionfx.admin")
	public static void deleteRegionCommand(final CommandContext args, final CommandSender sender) throws CommandException{
		
	}
	
	// /regionfx edit [Name] [New Effect] [New Level] [New Time]
	@Command(
			aliases = {"edit"},
			desc = "Create an Effect Region",
			min = 0,
			max = 4
			)
	@CommandPermissions("regionfx.admin")
	public static void editRegionCommand(final CommandContext args, final CommandSender sender) throws CommandException{
		
	}
}
