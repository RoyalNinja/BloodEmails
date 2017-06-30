package com.royalninja.bloodemails;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.royalninja.bloodemails.utils.EmailUtil;

public class Main extends JavaPlugin {

	public static Plugin plugin;
	
	FileConfiguration cfg = getConfig();
	
	public void onEnable() {
		plugin=this;
		saveDefaultConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("websitetoken")) {
			if (!(sender.hasPermission("bloodemails.websitetoken"))) {
				sender.sendMessage(color(cfg.getString("messages.no-permission")));
				return true;
			}
			if (args.length != 1) {
				sender.sendMessage(color(cfg.getString("messages.invalid-arguments-website")));
				return true;
			}
			String email = cfg.getString("email");
			String sendEmail = args[0];
			String password = cfg.getString("password");
			String text = cfg.getString("email-text-website");
			String subject = cfg.getString("email-subject-website");
			String smtp = cfg.getString("smtp");
			String port = cfg.getString("port");
			List<String> tokens = cfg.getStringList("website-tokens");
			if (!email.contains("@")) {
				sender.sendMessage(color(cfg.getString("messages.invalid-email")));
				return true;
			}
			if (tokens.isEmpty()) {
				sender.sendMessage(color(cfg.getString("messages.tokens-empty")));
				return true;
			}
			EmailUtil.sendEmail(smtp, port, email, password, sendEmail, subject, text.replaceAll("%securitytoken%", tokens.get(0)));
			sender.sendMessage(color(cfg.getString("messages.send-email-website").replaceAll("%email%", email)));
			tokens.remove(0);
			cfg.set("website-tokens",tokens);
			return true;
		}
		return true;
	}

	public static String color(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
}
