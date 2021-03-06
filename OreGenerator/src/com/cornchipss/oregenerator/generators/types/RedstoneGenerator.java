package com.cornchipss.oregenerator.generators.types;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.cornchipss.oregenerator.OreGeneratorPlugin;
import com.cornchipss.oregenerator.generators.Generator;
import com.cornchipss.oregenerator.generators.GeneratorUtils;
import com.cornchipss.oregenerator.ref.Vector3;
import com.cornchipss.oregenerator.upgrades.GeneratorUpgrade;

public class RedstoneGenerator extends Generator
{
	public RedstoneGenerator(Block generatorBlock, OreGeneratorPlugin plugin, List<GeneratorUpgrade> upgrades) 
	{
		super(new Vector3(3, 3, 3), 3, GeneratorUtils.GENERATOR_REDSTONE_ID, generatorBlock, plugin, upgrades);
	}
	
	public RedstoneGenerator(Block generatorBlock, OreGeneratorPlugin plugin) 
	{
		super(new Vector3(3, 3, 3), 3, GeneratorUtils.GENERATOR_REDSTONE_ID, generatorBlock, plugin);
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
						transmutableBlock.setType(Material.REDSTONE_ORE);
					}
				}
			}
		}
	}
}
