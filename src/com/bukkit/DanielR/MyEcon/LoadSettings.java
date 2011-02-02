package com.bukkit.DanielR.MyEcon;

public class LoadSettings {
	static int startingBalance;
	static String credit;

	public static void loadMain() {
		String propertiesFile = MyEcon.mainDirectory + "MainConfig.properties";
		PluginProperties properties = new PluginProperties(propertiesFile);
		properties.load();

		startingBalance = properties.getInteger("Starting-Blance", 1000);
		credit = properties.getString("Currency", "Coins");
		properties.save("===MyEcon Main Config===");
	}
}
