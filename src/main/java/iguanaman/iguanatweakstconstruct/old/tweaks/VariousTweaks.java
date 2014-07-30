package iguanaman.iguanatweakstconstruct.old.tweaks;

import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.Log;
import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import iguanaman.iguanatweakstconstruct.old.IguanaPartBuildRecipe;
import iguanaman.iguanatweakstconstruct.old.IguanaToolBuildRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tconstruct.tools.items.Pattern;
import tconstruct.world.TinkerWorld;
import cpw.mods.fml.common.registry.GameRegistry;

public class VariousTweaks {

	public static void init()
	{

		// SUPPRESS MISSING TOOL LOGS
		try
		{
			Class clazz = Class.forName(tconstruct.tools.TinkerTools.class.getName());
			Field fld = clazz.getField("supressMissingToolLogs");
			fld.setBoolean(fld, true);
		}
		catch (Exception e)
		{
			Log.warn("Failed to suppress missing tool logs");
			e.printStackTrace();
		}

		//REMOVE STONE TORCH
		if (Config.removeStoneTorchRecipe)
		{
			Log.info("Removing stone torch recipe");
			RecipeRemover.removeAnyRecipe(new ItemStack(TinkerWorld.stoneTorch, 4));
		}


		// GRAVEL TO FLINT RECIPE
		if (Config.addFlintRecipe) {
			Log.info("Adding gravel to flint recipe");
			GameRegistry.addShapelessRecipe(new ItemStack(Items.flint), new Object[] {Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel});
		}


		//SOFTEN SEARED BLOCKS
		Log.info("Softening seared blocks");
		TinkerSmeltery.smeltery.setHardness(1.5F);
		TinkerSmeltery.lavaTank.setHardness(1.5F);
		TinkerSmeltery.searedBlock.setHardness(1.5F);
		TinkerSmeltery.castingChannel.setHardness(1.5F);


		// REUSABLE PARTS
		Log.info("Making non-metal parts reusable in part builder");

		int[] nonMetals = { 0, 1, 3, 4, 5, 6, 7, 8, 9, 17 };
		PatternBuilder pb = PatternBuilder.instance;
		for (int p = 0; p < IguanaTweaksTConstruct.toolParts.size(); ++p)
			for (int m = 0; m < nonMetals.length; ++m)
			{
				ToolMaterial mat = TConstructRegistry.getMaterial(m);
				int cost = ((Pattern)TinkerTools.woodPattern).getPatternCost(new ItemStack(TinkerTools.woodPattern, 1, p + 1));
				cost = Math.round(cost / 2f - 0.5f);
				if (cost > 0)
				{
					Item part = IguanaTweaksTConstruct.toolParts.get(p);
					ItemStack partStack = new ItemStack(part, 1, nonMetals[m]);
					pb.registerMaterial(partStack, cost, mat.name());
				}
			}


		// REMOVE RESTRICTED PARTS FROM TINKERS HOUSE LOOT
		Log.info("Removing restricted parts from Tinker House chest");

		for (int i = 0; i < IguanaTweaksTConstruct.toolParts.size(); ++i)
		{
			Item part = IguanaTweaksTConstruct.toolParts.get(i);

			if (Config.restrictedWoodParts.contains(i+1))
				TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 0));
			if (!Config.allowStoneTools || Config.restrictedStoneParts.contains(i+1))
				TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 1));
			if (Config.restrictedFlintParts.contains(i+1))
				TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 3));
			if (Config.restrictedCactusParts.contains(i+1))
				TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 4));
			if (Config.restrictedBoneParts.contains(i+1))
				TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 5));
			if (Config.restrictedPaperParts.contains(i+1))
				TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 9));
			if (Config.restrictedSlimeParts.contains(i+1))
			{
				TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 8));
				TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 17));
			}
			TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 2)); //iron
			TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 6)); //obsidian
			TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 10)); //cobalt
			TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 11)); //ardite
			TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 12)); //manyullum
			TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 14)); //bronze
			TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 15)); //alumite
			TinkerWorld.tinkerHouseChest.removeItem(new ItemStack(part, 1, 16)); //steel
		}


		// SIMPLE WOOD PATTERN CRAFTING RECIPE
		if (Config.easyBlankPatternRecipe)
		{
			Log.info("Adding easy blank pattern recipe");
			GameRegistry.addShapedRecipe(new ItemStack(TinkerTools.blankPattern), "ss", "ss", 's', new ItemStack(Items.stick));
		}


		//ROTATING PATTERN CRAFTING
		if (Config.easyPatternCrafting)
		{
			Log.info("Adding rotating pattern crafting recipes");

			// TODO: Find Crafting Handler replacement 
			//GameRegistry.registerCraftingHandler(new IguanaPatternCraftingHandler());

			String[] patternName = new String[] {
					"ingot", "rod", "pickaxe", "shovel", "axe", "swordblade", "largeguard", "mediumguard", "crossbar",
					"binding", "frypan", "sign", "knifeblade", "chisel", "largerod", "toughbinding", "largeplate", "broadaxe",
					"scythe", "excavator", "largeblade", "hammerhead", "fullguard", "bowstring", "fletching", "arrowhead"
			};

			List<Integer> patternIds = new ArrayList<Integer>();

			for (int x = 1; x < patternName.length; x++)
				if (!Config.restrictedBoneParts.contains(x) || !Config.restrictedCactusParts.contains(x)
						|| !Config.restrictedFlintParts.contains(x) || !Config.restrictedPaperParts.contains(x)
						|| !Config.restrictedSlimeParts.contains(x) || !Config.restrictedWoodParts.contains(x)
						|| Config.allowStoneTools && !Config.restrictedFlintParts.contains(x)
						|| x >= 23)
					patternIds.add(x);

			GameRegistry.addShapelessRecipe(new ItemStack(TinkerTools.woodPattern, 1, patternIds.get(0)), new ItemStack(TinkerTools.blankPattern, 1, 0));
			for (int x = 0; x < patternIds.size(); x++)
			{
				int pmeta = patternIds.get(x);

				if (x == patternIds.size() - 1)
					GameRegistry.addShapelessRecipe(new ItemStack(TinkerTools.woodPattern, 1, patternIds.get(0)), new ItemStack(TinkerTools.woodPattern, 1, pmeta));
				else
					GameRegistry.addShapelessRecipe(new ItemStack(TinkerTools.woodPattern, 1, patternIds.get(x+1)), new ItemStack(TinkerTools.woodPattern, 1, pmeta));
			}
		}


		// ALTERNATIVE PART CRAFTING
		if (Config.easyPartCrafting)
		{
			Log.info("Adding easy part crafting");
			GameRegistry.addRecipe(new IguanaPartBuildRecipe());
			// TODO: Find crafting Handler replacement
			//GameRegistry.registerCraftingHandler(new IguanaPartCraftingHandler());
		}


		// ALTERNATIVE TOOL CRAFTING
		if (Config.easyToolCreation || Config.easyToolModification)
		{
			Log.info("Adding easy tool crafting");
			GameRegistry.addRecipe(new IguanaToolBuildRecipe());
		}

	}

}
