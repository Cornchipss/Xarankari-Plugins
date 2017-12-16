package com.cornchipss.oregenerator.generators.types;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.cornchipss.oregenerator.OreGeneratorPlugin;
import com.cornchipss.oregenerator.generators.Generator;
import com.cornchipss.oregenerator.generators.GeneratorItemForge;
import com.cornchipss.oregenerator.ref.Vector3;
import com.cornchipss.oregenerator.upgrades.GeneratorUpgrade;

public class EmeraldGenerator extends Generator
{
	public EmeraldGenerator(Block generatorBlock, OreGeneratorPlugin plugin, ArrayList<GeneratorUpgrade> upgrades) 
	{
		super(new Vector3(3, 3, 3), 7, GeneratorItemForge.GENERATOR_EMERALD_ID, 5, generatorBlock, plugin, upgrades);
	}
	
	public EmeraldGenerator(Block generatorBlock, OreGeneratorPlugin plugin) 
	{
		super(new Vector3(3, 3, 3), 7, GeneratorItemForge.GENERATOR_EMERALD_ID, 5, generatorBlock, plugin);
	}

	@Override
	public void run() 
	{
		// Get the block right below				
		for(int x = -((int)Math.floor(getRange().getX() / 2)); x <= (int)Math.floor(getRange().getX() / 2); x++)
		{
			for(int z = -((int)Math.floor(getRange().getZ() / 2)); z <= (int)Math.floor(getRange().getZ() / 2); z++)
			{
				for(int y = -((int)Math.floor(getRange().getY() / 2)); y <= (int)Math.floor(getRange().getY() / 2); y++)
				{
					if(shouldTransmute())
					{
						Block transmutableBlock = getGeneratorBlock().getRelative(x, y, z);
						if(transmutableBlock.getType() == getPlugin().getTransmutableBlock())
							transmutableBlock.setType(Material.EMERALD_ORE);
					}
				}
			}
		}
	}
}