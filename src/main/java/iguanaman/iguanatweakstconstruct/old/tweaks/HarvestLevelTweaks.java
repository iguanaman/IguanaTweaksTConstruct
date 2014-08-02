package iguanaman.iguanatweakstconstruct.old.tweaks;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import iguanaman.iguanatweakstconstruct.old.IguanaConfig;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.TConstructRegistry;
import tconstruct.world.TinkerWorld;

public class HarvestLevelTweaks {

	public static int boostMod = 0;

	// HarvestLevels
	public static String[][] oreDictLevels = {
		{},
		{"Copper", "Coal", "Tetrahedrite", "Aluminum", "Aluminium", "NaturalAluminum", "AluminumBrass", "Shard", "Bauxite", "Zinc"},
		{"Iron", "Pyrite", "Lead", "Silver"},
		{"Tin", "Cassiterite", "Gold", "Lapis", "Steel", "Galena", "Nickel", "Invar", "Electrum", "Sphalerite"},
		{"Diamond", "Emerald", "Redstone", "Ruby", "Sapphire", "Cinnabar", "Quartz",
			"Obsidian", "CertusQuartz", "Tungstate", "Sodalite", "GreenSapphire", "BlackGranite", "RedGranite"},
			{"Ardite", "Uranium", "Olivine", "Sheldonite", "Osmium", "Platinum"},
			{"Cobalt", "Iridium", "Cooperite", "Titanium"},
			{"Manyullyn"}
	};

	public static void init()
	{
		// TOOLS
		Log.info("Modifying harvest levels of tools");
		ForgeHooks hooks = new ForgeHooks();
		Field f = null;
		try {
			f = ForgeHooks.class.getDeclaredField("toolClasses");
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("Could not access toolClasses field, report please");
		}

		f.setAccessible(true);
		HashMap<Item, List> toolClasses = null;
		try {
			toolClasses = (HashMap<Item, List>) f.get(hooks);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Could not access toolClasses field, report please");
		}

		int harvestLevelWood = TConstructRegistry.getMaterial("Wood").harvestLevel();
		int harvestLevelFlint = TConstructRegistry.getMaterial("Flint").harvestLevel();
		int harvestLevelIron = TConstructRegistry.getMaterial("Iron").harvestLevel();
		int harvestLevelObsidian = TConstructRegistry.getMaterial("Obsidian").harvestLevel();
		int harvestLevelArdite = TConstructRegistry.getMaterial("Ardite").harvestLevel();
		int harvestLevelCobalt = TConstructRegistry.getMaterial("Cobalt").harvestLevel();
		int harvestLevelManyullyn = TConstructRegistry.getMaterial("Manyullyn").harvestLevel();

		Iterator<Map.Entry<Item, List>> it = toolClasses.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry<Item, List> entry = it.next();
			Item item = entry.getKey();
			List info = entry.getValue();

			Object[] tmp = info.toArray();
			String toolClass = (String)tmp[0];
			int harvestLevel = (Integer)tmp[1];
			int newHarvestLevel = harvestLevel;

			if (item != null && toolClass.equals("pickaxe"))
			{

				// TODO: Find a better way to implement pickaxe overrides
				/*
				if (IguanaIguanaConfig.pickaxeOverrides.containsKey(prefix.getUnlocalizedName()))
					harvestLevel = IguanaIguanaConfig.pickaxeOverrides.get(prefix.itemID);*/

				switch (harvestLevel)
				{
				case 0: newHarvestLevel = harvestLevelWood; break;
				case 1: newHarvestLevel = harvestLevelFlint; break;
				case 2: newHarvestLevel = harvestLevelIron; break;
				case 3: newHarvestLevel = harvestLevelObsidian; break;
				case 4: newHarvestLevel = harvestLevelArdite; break;
				case 5: newHarvestLevel = harvestLevelCobalt; break;
				case 6: newHarvestLevel = harvestLevelManyullyn; break;
				default: newHarvestLevel = harvestLevelManyullyn + harvestLevel - 6;
				}

				if (harvestLevel != newHarvestLevel)
				{
					if (IguanaConfig.logMiningLevelChanges) Log.info("Changing mining level of " + entry.getKey().getUnlocalizedName() + " from " + harvestLevel + " to " + newHarvestLevel);
					entry.setValue(Arrays.asList(toolClass, newHarvestLevel));
				}

			}
		}


		//BLOCKS
		if (IguanaConfig.pickaxeBoostRequired) boostMod = 1;

		int harvestLevelCopper = TConstructRegistry.getMaterial("Copper").harvestLevel() + boostMod;
		harvestLevelIron += boostMod;
		int harvestLevelBronze = TConstructRegistry.getMaterial("Bronze").harvestLevel() + boostMod;
		harvestLevelObsidian += boostMod;
		harvestLevelArdite += boostMod;
		harvestLevelCobalt += boostMod;
		harvestLevelManyullyn += boostMod;

		Log.info("Modifying required harvest levels of blocks");
		try {
			f = ForgeHooks.class.getDeclaredField("toolHarvestLevels");
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("Could not access toolHarvestLevels field, report please");
		}

		f.setAccessible(true);
		HashMap<List, Integer> toolHarvestLevels = null;
		try {
			toolHarvestLevels = (HashMap<List, Integer>) f.get(hooks);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Could not access toolHarvestLevels field, report please");
		}

		Iterator<Map.Entry<List, Integer>> it2 = toolHarvestLevels.entrySet().iterator();
		while (it2.hasNext())
		{
			Map.Entry<List, Integer> entry = it2.next();

			List key = entry.getKey();

			Object[] tmp = key.toArray();
			Block block = (Block)tmp[0];
			int metadata = (Integer)tmp[1];
			String toolClass = (String)tmp[2];

			if (toolClass.equals("pickaxe"))
			{

				int requiredHarvestLevel = entry.getValue();
				int newRequiredHarvestLevel = requiredHarvestLevel;

				switch (requiredHarvestLevel)
				{
				case 0: newRequiredHarvestLevel = harvestLevelWood; break;
				case 1: newRequiredHarvestLevel = harvestLevelCopper; break;
				case 2: newRequiredHarvestLevel = harvestLevelIron; break;
				case 3: newRequiredHarvestLevel = harvestLevelBronze; break;
				case 4: newRequiredHarvestLevel = harvestLevelObsidian; break;
				case 5: newRequiredHarvestLevel = harvestLevelArdite; break;
				case 6: newRequiredHarvestLevel = harvestLevelCobalt; break;
				case 7: newRequiredHarvestLevel = harvestLevelManyullyn; break;
				default: newRequiredHarvestLevel = harvestLevelManyullyn + requiredHarvestLevel - 7;
				}

				if (requiredHarvestLevel != newRequiredHarvestLevel)
				{
					if (IguanaConfig.logHarvestLevelChanges) Log.info("Changing required harvest level of " + block.getUnlocalizedName() + ":" + metadata + " from " + requiredHarvestLevel + " to " + newRequiredHarvestLevel);
					entry.setValue(newRequiredHarvestLevel);
				}

			}
		}

		for (int i = 0; i < oreDictLevels.length; ++i)
		{
			int level = i;
			if (i > 1) level += boostMod;

			for (String materialName : oreDictLevels[i]) {
				for (ItemStack oreStack : OreDictionary.getOres("ore" + materialName)) SetHarvestLevel(oreStack, level);
				for (ItemStack oreStack : OreDictionary.getOres("oreNether" + materialName)) SetHarvestLevel(oreStack, level);
				for (ItemStack oreStack : OreDictionary.getOres("prefix" + materialName)) SetHarvestLevel(oreStack, level);
				for (ItemStack oreStack : OreDictionary.getOres("stone" + materialName)) SetHarvestLevel(oreStack, level);
			}
		}

		Log.info("Modifying required harvest levels of vanilla blocks");
		Blocks.obsidian.setHarvestLevel("pickaxe", harvestLevelBronze);
		Blocks.diamond_ore.setHarvestLevel("pickaxe", harvestLevelBronze);
		Blocks.diamond_block.setHarvestLevel("pickaxe", harvestLevelBronze);
		Blocks.gold_block.setHarvestLevel("pickaxe", harvestLevelIron);
		Blocks.iron_block.setHarvestLevel("pickaxe", harvestLevelCopper);
		Blocks.iron_bars.setHarvestLevel("pickaxe", harvestLevelCopper);
		Blocks.lapis_block.setHarvestLevel("pickaxe", harvestLevelIron);
		Blocks.redstone_ore.setHarvestLevel("pickaxe", harvestLevelBronze);
		Blocks.lit_redstone_ore.setHarvestLevel("pickaxe", harvestLevelBronze);

		TinkerWorld.oreGravel.setHarvestLevel("shovel", harvestLevelIron, 0);
		TinkerWorld.oreGravel.setHarvestLevel("shovel", harvestLevelIron, 1);
		TinkerWorld.oreGravel.setHarvestLevel("shovel", harvestLevelFlint, 2);
		TinkerWorld.oreGravel.setHarvestLevel("shovel", harvestLevelIron, 3);
		TinkerWorld.oreGravel.setHarvestLevel("shovel", harvestLevelFlint, 4);
		TinkerWorld.oreGravel.setHarvestLevel("shovel", harvestLevelArdite, 5);

		List[] harvestLevelIds = {
				IguanaConfig.harvestLevel0Ids, IguanaConfig.harvestLevel1Ids, IguanaConfig.harvestLevel2Ids,
				IguanaConfig.harvestLevel3Ids, IguanaConfig.harvestLevel4Ids, IguanaConfig.harvestLevel5Ids,
				IguanaConfig.harvestLevel6Ids, IguanaConfig.harvestLevel7Ids
		};

		for (int i = 0; i < oreDictLevels.length; ++i)
		{
			int level = i;
			if (i > 1) level += boostMod;

			for (String idline : (List<String>)harvestLevelIds[i])
			{
				int blockId = -1;
				int meta = OreDictionary.WILDCARD_VALUE;

				try {
					if (idline.contains(":"))
					{
						String[] idlinesplit = idline.split(":");
						blockId = Integer.parseInt(idlinesplit[0]);
						meta = Integer.parseInt(idlinesplit[1]);
					} else
						blockId = Integer.parseInt(idline);
				} catch (Exception e) {
					throw new RuntimeException("IguanaConfig setting harvestLevel" + i + "Ids contains an invalid line (" + idline + ").  Each id must be on a separate line and in this format: id or id:meta");
				}

				// TODO: find a way to implement prefix harvest override
				//SetHarvestLevel(new ItemStack(blockId, 1, meta), level);
			}
		}

	}

	public static void SetHarvestLevel(ItemStack oreStack, int level)
	{
		Block block = Block.getBlockFromItem(oreStack.getItem());
		if (block == Blocks.air)
		{
			Log.warn("Warning: " + oreStack.getUnlocalizedName() + " is invalid prefix");
			return;
		}
		
		if (oreStack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
			block.setHarvestLevel("pickaxe", level);
		//FMLLog.warning("IguanaTweaksTConstruct: Setting required harvest level of " + oreStack.getUnlocalizedName() + " to " + level);
		else
			block.setHarvestLevel("pickaxe", level, oreStack.getItemDamage());
		//FMLLog.warning("IguanaTweaksTConstruct: Setting required harvest level of " + oreStack.getUnlocalizedName() + ":" + oreStack.getItemDamage() + " to " + level);
	}

}
