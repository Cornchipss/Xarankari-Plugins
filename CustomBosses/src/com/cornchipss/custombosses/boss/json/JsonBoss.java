package com.cornchipss.custombosses.boss.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.cornchipss.custombosses.boss.Boss;
import com.cornchipss.custombosses.boss.json.equipment.ArmorJson;
import com.cornchipss.custombosses.boss.json.equipment.BossEquipmentJson;
import com.cornchipss.custombosses.boss.json.equipment.HandJson;
import com.cornchipss.custombosses.util.Vector2;

public class JsonBoss 
{
	private int startHealth;
	private String displayName, mobType;
	private BossEquipmentJson equipment;
	Map<String, String> drops;
	
	public JsonBoss(int startHealth, String displayName, String mobType, BossEquipmentJson equipment, Map<String, String> drops) 
	{
		this.startHealth = startHealth;
		this.displayName = displayName;
		this.mobType = mobType;
		this.equipment = equipment;
		this.drops = drops;
	}
	
	public Boss createBoss()
	{
		// Take the armor from a String to actual armor, then add enchants
		List<ArmorJson> armorJson = equipment.getArmor();		
		ItemStack[] armor = new ItemStack[4];		
		for(int i = 0; i < armorJson.size(); i++)
		{
			ArmorJson json = armorJson.get(0);
			armor[i] = new ItemStack(Material.valueOf(json.getMaterial()));
			for(String s : json.getEnchants().keySet())
			{
				armor[i].addUnsafeEnchantment(Enchantment.getByName(s), json.getEnchants().get(s));
			}
		}
		
		// Create the item held in the hand from the material name and add enchants
		HandJson handJson = equipment.getHand();
		ItemStack hand = new ItemStack(Material.valueOf(handJson.getMaterial()), 1);
		for(String s : handJson.getEnchants().keySet())
		{
			hand.addUnsafeEnchantment(Enchantment.getByName(s), handJson.getEnchants().get(s));
		}
		
		// Create the drops it will have
		Map<ItemStack, Vector2<Integer, Integer>> dropsComplete = new HashMap<>();
		for(String itemName : drops.keySet())
		{
			ItemStack item = new ItemStack(Material.valueOf(itemName));
			String[] split = drops.get(itemName).replaceAll(" ", "").split("-");
			Vector2<Integer, Integer> dropRange;
			if(split.length == 1)
			{
				int val = Integer.parseInt(split[0]);
				dropRange = new Vector2<>(val, val);
			}
			else
				dropRange = new Vector2<>(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
			
			dropsComplete.put(item, dropRange);
		}
		
		return new Boss(startHealth, EntityType.valueOf(mobType), displayName, hand, armor, dropsComplete);
	}
	
	public static JsonBoss fromBoss(Boss b)
	{
		int startHealth = b.getStartingHealth();
		String displayName = b.getDisplayName();
		String mobType = b.getEntityType().name();
		
		ItemStack hand = b.getHandEquipment();
		Map<Enchantment, Integer> enchants = hand.getEnchantments();
		Map<String, Integer> enchantsJson = new HashMap<>(); 
		for(Enchantment k : enchants.keySet())
		{
			enchantsJson.put(k.getName(), enchants.get(k));
		}
		HandJson hjson = new HandJson(hand.getType().name(), hand.getItemMeta().getDisplayName(), hand.getItemMeta().getLore(), enchantsJson, new ArrayList<String>());
		
		List<ArmorJson> ajson = new ArrayList<>();
		for(int i = 0; i < 4; i++)
		{
			ItemStack piece = b.getArmor(i);
			Map<Enchantment, Integer> armorEnchants = piece.getEnchantments();
			Map<String, Integer> armorEnchantsJson = new HashMap<>(); 
			for(Enchantment k : armorEnchants.keySet())
			{
				armorEnchantsJson.put(k.getName(), armorEnchants.get(k));
			}
			ajson.add(new ArmorJson(piece.getType().name(), piece.getItemMeta().getDisplayName(), piece.getItemMeta().getLore(), armorEnchantsJson, new ArrayList<>()));
		}
		
		BossEquipmentJson ejson = new BossEquipmentJson(ajson, hjson);
		Map<String, String> drops = new HashMap<>();
		
		JsonBoss jsonBoss = new JsonBoss(startHealth, displayName, mobType, ejson, drops);
		
		return jsonBoss;
	}
	
	@Override
	public String toString()
	{
		return "JsonBoss [" + getDisplayName() + ":" + getStartHealth() + "; " + getEquipment() + "; Mob Type: " + getMobType() + "; " + getDrops() + "]";
	}
	
	public int getStartHealth() { return startHealth; }
	public void setStartHealth(int startHealth) { this.startHealth = startHealth; }

	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }

	public String getMobType() { return mobType; }
	public void setMobType(String mobType) { this.mobType = mobType; }

	public BossEquipmentJson getEquipment() { return equipment; }
	public void setEquipment(BossEquipmentJson equipment) { this.equipment = equipment; }
	
	public Map<String, String> getDrops() { return drops; }
	public void setDrops(Map<String, String> drops) { this.drops = drops; }
}