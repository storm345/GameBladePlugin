package org.stormdev.gbplugin.api.core;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.stormdev.gbapi.bans.BanHandler;
import org.stormdev.gbapi.bans.PunishmentLogs;
import org.stormdev.gbapi.cosmetics.Cosmetics;
import org.stormdev.gbapi.servers.ServerInfo;
import org.stormdev.gbapi.stars.Stars;
import org.stormdev.gbapi.storm.tokens.Tokens;
import org.stormdev.gbapi.villagers.VillagerManager;
import org.stormdev.gbplugin.bans.PunishmentLogger;
import org.stormdev.gbplugin.plugin.core.GameBlade;
import org.stormdev.gbplugin.plugin.cosmetics.CosmeticPanel;

public class GameBladeAPI implements org.stormdev.gbapi.core.GameBladeAPI{
	private static final double VERSION = 0.1;
	
	@Override
	public double getAPIVersionNumber() {
		return VERSION;
	}

	@Override
	public Tokens getTokenHandler() {
		return org.stormdev.gbplugin.plugin.stormapis.Tokens.getInstance();
	}

	@Override
	public ServerInfo getCurrentServerInfo() {
		return GameBlade.serverInfo;
	}

	@Override
	public void showServerSelector(Player player) {
		GameBlade.plugin.selector.open(player);
	}

	@Override
	public Plugin getGBPlugin() {
		return GameBlade.plugin;
	}

	@Override
	public Stars getStarsHandler() {
		return org.stormdev.gbplugin.api.stars.Stars.getInstance();
	}

	@Override
	public BanHandler getBans() {
		return GameBlade.banHandler;
	}

	@Override
	public Cosmetics getCosmeticsHandler() {
		return GameBlade.plugin.cosmeticManager;
	}

	@Override
	public PunishmentLogs getPunishmentLogger() {
		return PunishmentLogger.getInstance();
	}

	@Override
	public VillagerManager getMenuVillagerManager() {
		return org.stormdev.gbplugin.api.villagers.VillagerManager.instance;
	}

	@Override
	public void showCosmeticPanel(Player player) {
		CosmeticPanel.getInstance().open(player);
	}

}
