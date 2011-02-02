package com.bukkit.DanielR.MyEcon;



import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Handle events for all Player related events
 * 
 * @author DanielR
 */
public class MyEconPlayerListener extends PlayerListener {
	private final MyEcon plugin;

	public MyEconPlayerListener(MyEcon instance) {
		plugin = instance;
	}
	public void onPlayerCommand(PlayerChatEvent event){
		String[] split = event.getMessage().split(" ");
		Player player = event.getPlayer();
		if((split[0].equalsIgnoreCase("/econ"))){
			boolean HasAccount = Accounting.containsKey(player, MyEcon.Accounts);
			if(HasAccount==false){
				Accounting.write(player, LoadSettings.startingBalance, MyEcon.Accounts);
			}else if(split.length == 1){
				int balance = Accounting.getBalance(player, MyEcon.Accounts);
				player.sendMessage(ChatColor.GREEN + "Money: " + balance + LoadSettings.credit);
			}
		}
		if(split.length >= 4){
			if((split[0].equalsIgnoreCase("/econ")) && (split[1].equalsIgnoreCase("pay"))){
				String toPlayer = split[2];
				boolean HasAccount = Accounting.containsKey(player, MyEcon.Accounts);
				if(HasAccount == true){
					int balance = Accounting.getBalance(player, MyEcon.Accounts);
					int amount = Integer.parseInt(split[3]);
					List<Player> players = plugin.getServer().matchPlayer(split[2]);
					if(players.size()==0){
						player.sendMessage(ChatColor.RED + "No matching players!");
					}else if(players.size() != 1){
						player.sendMessage(ChatColor.RED + "Matched more than 1 player. Be more specific.");
					}else{
						Player reciever = players.get(0);
						boolean ToHasAccount = Accounting.containsKey(reciever, MyEcon.Accounts);
						if(ToHasAccount == true && balance >= amount){
							int newBalance = balance - amount;
							Accounting.write(player, newBalance, MyEcon.Accounts);
							int toBalance = Accounting.getBalance(reciever, MyEcon.Accounts);
							int newToBalance = toBalance + amount;
							Accounting.write(reciever, newToBalance, MyEcon.Accounts);
							player.sendMessage(ChatColor.GREEN + "" + amount + LoadSettings.credit +" has been sent to " + toPlayer);
							reciever.sendMessage(ChatColor.GREEN + player.getName() + " has sent you " + amount + LoadSettings.credit);
						}else{
							player.sendMessage(ChatColor.RED + "You don't have enough money to do that.");
						}
					}
					
					
				}else{
					player.sendMessage(ChatColor.RED + "You need an account to send money!");
				}
			}
		}
	}
	public void onPlayerLogin(PlayerLoginEvent event){
		Player player = event.getPlayer();
		int startingValue = LoadSettings.startingBalance;
		boolean HasAccount = Accounting.containsKey(player, MyEcon.Accounts);
		if(HasAccount==false){
			Accounting.write(player, startingValue, MyEcon.Accounts);
		}
	}
}
