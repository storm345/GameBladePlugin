package org.stormdev.gbplugin.plugin.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.stormdev.gbplugin.plugin.core.Config;
import org.stormdev.gbplugin.plugin.core.GameBlade;
import org.stormdev.gbplugin.plugin.utils.Colors;

public class ChatManager implements Listener {
	public static ChatColor getMsgColour(Player player){
		if(player.hasPermission("mta.chatcolor.staff")){
			return ChatColor.AQUA;
		}
		else if(player.hasPermission("mta.chatcolor.vip")){
			return ChatColor.WHITE;
		}
		else {
			return ChatColor.GRAY;
		}
	}
	
	public ChatManager(){
		if(!Config.overrideChat.getValue()){ //DONT override the chat
			return;
		}
		
		Bukkit.getPluginManager().registerEvents(this, GameBlade.plugin);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	void onChat(AsyncPlayerChatEvent event){
		if(event.isCancelled()){
			return;
		}
		
		Player player = event.getPlayer();
		chat(player, event.getMessage());
		event.setCancelled(true);
	}
	
	private void chat(Player player, String msg){
		ChatColor color = getMsgColour(player);
		boolean colour = false;
		colour = player.hasPermission("mta.chat.colors");
		msg = Colors.colorise(msg);
		if(!colour){
			msg = ChatColor.stripColor(msg);
		}
		
		String sender = getSenderDisplayName(player);
		String message = ChatColor.WHITE + Colors.colorise(sender) + ChatColor.DARK_GRAY + " » " + color + msg;
		Bukkit.broadcastMessage(message);
		GameBlade.logger.info(message);
	}
	
	private String getSenderDisplayName(Player player){
		String fullName = getFullPrefixSuffixName(player);
		String dispName = fullName + "&f";

		String coloured = ChatColor.translateAlternateColorCodes('&', dispName);
		return coloured;
	}
	
	public String getFullPrefixName(Player player) {
		String prefix = getPrefixSuffix(player, "prefix");

		String coloured = ChatColor.translateAlternateColorCodes('&', prefix);

		return coloured;
	}

	public String getFullPrefixSuffixName(Player player) {
		String prefix = getPrefixSuffix(player, "prefix");
		String suffix = getPrefixSuffix(player, "suffix");
		String name = player.getName();

		String full = prefix + name + suffix;

		return full;
	}

	public String getPrefixSuffix(Player player, String type) {
		if (player.hasMetadata(type)) {
			return player.getMetadata(type).get(0).asString();
		}
		else{
			return "";
		}
	}
}
