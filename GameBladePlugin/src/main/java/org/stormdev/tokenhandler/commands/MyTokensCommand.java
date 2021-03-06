package org.stormdev.tokenhandler.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.stormdev.gbapi.core.APIProvider;
import org.stormdev.gbplugin.plugin.core.GameBlade;

public class MyTokensCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED+"Players only!");
			return true;
		}
		final Player player = (Player) sender;
		
		Bukkit.getScheduler().runTaskAsynchronously(GameBlade.plugin, new Runnable(){

			@Override
			public void run() {
				try {
					int tokens = APIProvider.getAPI().getTokenHandler().getTokens(player);
					player.sendMessage(ChatColor.WHITE+"You have: "+ChatColor.RED+tokens+" tokens");
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED+"Failed to load your token balance, sorry :(");
				}
				
				return;
			}});
		
		return true;
	}
	
}
