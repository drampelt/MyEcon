package com.bukkit.DanielR.MyEcon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.entity.Player;

public class Accounting {
	public static boolean containsKey(Player p, File file){
		Properties pro = new Properties();
		String player = p.getName();
		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			if(pro.containsKey(player)){
				return true;
			}
		}catch(IOException ex){}
		return false;
	}

	public static void write(Player p, int startingB, File file) {
		Properties pro = new Properties();
		String startingValue = (new Integer(startingB)).toString();
		String player = p.getName();
		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			pro.setProperty(player, startingValue);
			pro.store(new FileOutputStream(file), null);
		}catch(IOException ex){}
	}
	
	public static int getBalance(Player p, File file){
		Properties pro = new Properties();
		String player = p.getName();
		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			String string = pro.getProperty(player);
			int balance = Integer.parseInt(string);
			return balance;
		}catch(IOException ex){}
		return 0;
	}
}
