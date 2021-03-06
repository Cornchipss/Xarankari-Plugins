package com.cornchipss.oregenerator.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cornchipss.oregenerator.OreGeneratorPlugin;
import com.cornchipss.oregenerator.generators.GeneratorUtils;
import com.cornchipss.oregenerator.upgrades.UpgradeUtils;

public class CommandManager 
{
	public static boolean runThroughCommands(final Command command, final CommandSender sender, final String[] args, final OreGeneratorPlugin plugin) 
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("You must be a player!");
			return true;
		}
		
		Player p = (Player)sender;
		String cmd = command.getName().toLowerCase();
		
		if(cmd.equalsIgnoreCase("oregenerator") || cmd.equalsIgnoreCase("oregen") || cmd.equals("gen"))
		{
			if(!p.hasPermission("oregenerator.givegenerator"))
			{
				p.sendMessage(ChatColor.RED + "Invalid permissions");
				return true;
			}
			
			if(args.length == 0)
			{
				CommandInventories.openGetGeneratorGUI(p, plugin);
				return true;
			}
			
			int genId = 0;
			
			switch(args[0].toLowerCase())
			{
			case "coal":
				genId = GeneratorUtils.GENERATOR_COAL_ID;
				break;
			case "iron":
				genId = GeneratorUtils.GENERATOR_IRON_ID;
				break;
			case "redstone":
				genId = GeneratorUtils.GENERATOR_REDSTONE_ID;
				break;
			case "lapis":
				genId = GeneratorUtils.GENERATOR_LAPIS_ID;
				break;
			case "gold":
				genId = GeneratorUtils.GENERATOR_GOLD_ID;
				break;
			case "diamond":
				genId = GeneratorUtils.GENERATOR_DIAMOND_ID;
				break;
			case "emerald":
				genId = GeneratorUtils.GENERATOR_EMERALD_ID;
			default:
				p.sendMessage(ChatColor.RED + "Invalid generator: " + args[0]);
				return true;
			}
			
			ItemStack is = GeneratorUtils.createGeneratorItemStack(GeneratorUtils.GENERATOR_COAL_ID, plugin.getGeneratorMaterial(genId));
			p.getInventory().addItem(is);
			return true;
			
		}
		
		if(cmd.equalsIgnoreCase("upgrades") || cmd.equalsIgnoreCase("upgrade"))
		{
			if(!p.hasPermission("oregenerator.giveupgrade"))
			{
				p.sendMessage(ChatColor.RED + "Invalid permissions");
				return true;
			}
			
			if(args.length == 0)
			{
				CommandInventories.openGetUpgradesGUI(p, plugin);
			}
			else
			{
				int upId = 0;
				
				switch(args[0].toLowerCase())
				{
					case "speed":
						upId = UpgradeUtils.UPGRADE_SPEED_ID;
						break;
					case "x":
						upId = UpgradeUtils.UPGRADE_X_RANGE_ID;
						break;
					case "y":
						upId = UpgradeUtils.UPGRADE_Y_RANGE_ID;
						break;
					case "z":
						upId = UpgradeUtils.UPGRADE_Z_RANGE_ID;
						break;
					default:
						p.sendMessage(ChatColor.RED + "Invalid generator: " + args[0]);
						return true;
				}
				
				ItemStack is = UpgradeUtils.createUpgradeItemStack(upId, plugin.getUpgradeMaterial(upId));
				p.getInventory().addItem(is);
			}
			return true;
			
		}
		
		return true;
	}
}
