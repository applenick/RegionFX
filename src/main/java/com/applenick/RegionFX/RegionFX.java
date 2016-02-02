package com.applenick.RegionFX;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.applenick.RegionFX.commands.RegionCommands;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

/************************************************
			 Created By AppleNick
Copyright © 2016 , AppleNick, All rights reserved.
			http://applenick.com
 *************************************************/
public class RegionFX extends JavaPlugin {
	
	private static String PREFIX = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "Region" + ChatColor.AQUA + "FX" + ChatColor.GRAY + "]";
	
	private static RegionFX plugin;
	public static RegionFX get(){
		return plugin;
	}
	
	private static WorldGuardPlugin wgp;
	public static WorldGuardPlugin getWorldGuard(){
		return wgp;
	}

	
	@Override
	public void onEnable(){
		plugin = this;
		
		this.saveDefaultConfig();
		
		//Setup Commands
		this.setupCommandManager();
	}
	
	
	@Override
	public void onDisable(){
		
	}
	
	
	/**
	 * World Guard Dependence
	 */
	
	private void registerWorldGuard(){
		PluginManager PM = this.getServer().getPluginManager();
		
		if(PM.getPlugin("WorldGuard") != null){
			wgp = (WorldGuardPlugin) PM.getPlugin("WorldGuard");
			console(ChatColor.GREEN + "WorldGuard Detected");
			return;
		}else{
			console(ChatColor.RED + "WorldGuard Not Found - Please enable WorldGuard");
			return;
		}
		
	}
	
	
	/**
	 * Command Framework & Registration
	 */
	
	private void setupCommands(){
		setupCommandManager();
		
		CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, commands);
		cmdRegister.register(RegionCommands.RegionParent.class);
		
	}
	

	private CommandsManager<CommandSender> commands;
	public void setupCommandManager(){
		this.commands = new CommandsManager<CommandSender>() {
			@Override public boolean hasPermission(CommandSender sender, String perm) {
				return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
			}
		};
	}
		
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			this.commands.execute(cmd.getName(), args, sender, sender);
		} catch (CommandPermissionsException e) {
			sender.sendMessage(ChatColor.RED + "You don't have permission.");
		} catch (MissingNestedCommandException e) {
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (CommandUsageException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (WrappedCommandException e) {
			if (e.getCause() instanceof NumberFormatException) {
				sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
			} else {
				sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
				e.printStackTrace();
			}
		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}
		return true;
	}
	
	/**
	 * Utils
	 */
	
	public void console(String msg){
		getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RESET + msg);
	}

}
