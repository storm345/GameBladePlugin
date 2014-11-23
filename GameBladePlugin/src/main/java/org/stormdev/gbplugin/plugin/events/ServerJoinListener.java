package org.stormdev.gbplugin.plugin.events;

import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotmc.ProtocolInjector.PacketTitle.Action;
import org.stormdev.chattranslator.api.TranslatorToolkit;
import org.stormdev.gbapi.UUIDAPI.PlayerIDFinder;
import org.stormdev.gbapi.cosmetics.Rank;
import org.stormdev.gbapi.storm.misc.Popups;
import org.stormdev.gbplugin.plugin.core.Config;
import org.stormdev.gbplugin.plugin.core.GameBlade;
import org.stormdev.gbplugin.plugin.ranks.RankSQL;

public class ServerJoinListener implements Listener {
	
	private Rank joinRank;
	
	public ServerJoinListener(){
		Bukkit.getPluginManager().registerEvents(this, GameBlade.plugin);
		this.joinRank = Rank.valueOf(Config.joinRank.getValue());
	}
	
	@EventHandler
	void onFullJoin(AsyncPlayerPreLoginEvent event){
		UUID uuid = event.getUniqueId();
		if(event.getLoginResult().equals(Result.KICK_FULL)){
			if(RankSQL.getRankByUUID(PlayerIDFinder.toUUIDString(uuid)).getCosmeticRank().canUse(Rank.VIP_PLUS)){
				event.setLoginResult(Result.ALLOWED);
				event.allow();
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	void onJoinGame(PlayerJoinEvent event){
		final Player player = event.getPlayer();
		
		//Popups.showTitle(player, ChatColor.RED+"Game Blade", 20, 500, 40, Action.TITLE);
		String header = ChatColor.RED + "➹ " + ChatColor.WHITE + "Welcome to the " + 
		ChatColor.GOLD + "" + ChatColor.BOLD + "Game" + ChatColor.BLUE + "" + ChatColor.BOLD + "Blade" + 
				ChatColor.WHITE + "" + ChatColor.BOLD + " Network" + ChatColor.WHITE + "!" + ChatColor.RED + " ➹";
		
		String footer = ChatColor.DARK_GREEN + "Vote for daily tokens! Vote now on " + 
		ChatColor.GREEN + "www.gamebla.de/vote" + ChatColor.DARK_GREEN + "!";
		
		Popups.setTabHeader(player, header+"\n", "\n"+footer);
		
		if(joinRank.equals(Rank.DEFAULT)){
			return;
		}
		Rank r = Rank.getRank(player);
		if(!r.canUse(joinRank)){
			event.setJoinMessage(null);
			player.kickPlayer(Config.joinKickMsg.getValue());
		}
		
		Bukkit.getScheduler().runTaskLater(GameBlade.plugin, new Runnable(){

			@Override
			public void run() {
				if(!TranslatorToolkit.getToolkit().getLanguageManager().hasSetLang(player)){
					player.sendMessage(ChatColor.RED+"You haven't got a language set! Please use /lang to set your language!");
				}
				return;
			}}, 20*5l);
	}
}
