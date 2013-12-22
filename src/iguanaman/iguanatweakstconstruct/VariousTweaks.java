package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.util.IguanaPartBuildRecipe;
import iguanaman.iguanatweakstconstruct.util.IguanaPartCraftingHandler;
import iguanaman.iguanatweakstconstruct.util.IguanaPatternCraftingHandler;
import iguanaman.iguanatweakstconstruct.util.IguanaToolBuildRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tconstruct.common.TContent;
import tconstruct.items.Pattern;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.tools.ToolMaterial;
import cpw.mods.fml.common.registry.GameRegistry;

public class VariousTweaks {

	public static void init()
	{

		// SUPPRESS MISSING TOOL LOGS
		try
		{
			Class clazz = Class.forName(tconstruct.common.TContent.class.getName());
			Field fld = clazz.getField("supressMissingToolLogs");
			fld.setBoolean(fld, true);
		}
		catch (Exception e)
		{
			IguanaLog.log("Failed to suppress missing tool logs");
			e.printStackTrace();
		}

		//REMOVE STONE TORCH
		if (IguanaConfig.removeStoneTorchRecipe)
		{
			IguanaLog.log("Removing stone torch recipe");
			RecipeRemover.removeAnyRecipe(new ItemStack(TContent.stoneTorch, 4));
		}


		// GRAVEL TO FLINT RECIPE
		if (IguanaConfig.addFlintRecipe) {
			IguanaLog.log("Adding gravel to flint recipe");
			GameRegistry.addShapelessRecipe(new ItemStack(Item.flint), new Object[] {Block.gravel, Block.gravel, Block.gravel, Block.gravel});
		}


		//SOFTEN SEARED BLOCKS
		IguanaLog.log("Softening seared blocks");
		TContent.smeltery.setHardness(1.5F);
		TContent.lavaTank.setHardness(1.5F);
		TContent.searedBlock.setHardness(1.5F);
		TContent.castingChannel.setHardness(1.5F);


		// REUSABLE PARTS
		IguanaLog.log("Making non-metal parts reusable in part builder");

		int[] nonMetals = { 0, 1, 3, 4, 5, 6, 7, 8, 9, 17 };
		PatternBuilder pb = PatternBuilder.instance;
		for (int p = 0; p < IguanaTweaksTConstruct.toolParts.size(); ++p)
			for (int m = 0; m < nonMetals.length; ++m)
			{
				ToolMaterial mat = TConstructRegistry.getMaterial(m);
				int cost = ((Pattern)TContent.woodPattern).getPatternCost(new ItemStack(TContent.woodPattern, 1, p + 1));
				cost = Math.round(cost / 2f - 0.5f);
				if (cost > 0)
				{
					Item part = IguanaTweaksTConstruct.toolParts.get(p);
					ItemStack partStack = new ItemStack(part, 1, nonMetals[m]);
					pb.registerMaterial(partStack, cost, mat.name());
				}
			}


		// REMOVE RESTRICTED PARTS FROM TINKERS HOUSE LOOT
		IguanaLog.log("Removing restricted parts from Tinker House chest");

		for (int i = 0; i < IguanaTweaksTConstruct.toolParts.size(); ++i)
		{
			Item part = IguanaTweaksTConstruct.toolParts.get(i);

			if (IguanaConfig.restrictedWoodParts.contains(i+1))
				TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 0));
			if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedStoneParts.contains(i+1))
				TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 1));
			if (IguanaConfig.restrictedFlintParts.contains(i+1))
				TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 3));
			if (IguanaConfig.restrictedCactusParts.contains(i+1))
				TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 4));
			if (IguanaConfig.restrictedBoneParts.contains(i+1))
				TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 5));
			if (IguanaConfig.restrictedPaperParts.contains(i+1))
				TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 9));
			if (IguanaConfig.restrictedSlimeParts.contains(i+1))
			{
				TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 8));
				TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 17));
			}
			TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 2)); //iron
			TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 6)); //obsidian
			TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 10)); //cobalt
			TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 11)); //ardite
			TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 12)); //manyullum
			TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 14)); //bronze
			TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 15)); //alumite
			TContent.tinkerHouseChest.removeItem(new ItemStack(part, 1, 16)); //steel
		}


		// SIMPLE WOOD PATTERN CRAFTING RECIPE
		if (IguanaConfig.easyBlankPatternRecipe)
		{
			IguanaLog.log("Adding easy blank pattern recipe");
			GameRegistry.addShapedRecipe(new ItemStack(TContent.blankPattern), "ss", "ss", 's', new ItemStack(Item.stick));
		}


		//ROTATING PATTERN CRAFTING
		if (IguanaConfig.easyPatternCrafting)
		{
			IguanaLog.log("Adding rotating pattern crafting recipes");

			GameRegistry.registerCraftingHandler(new IguanaPatternCraftingHandler());

			String[] patternName = new String[] {
					"ingot", "rod", "pickaxe", "shovel", "axe", "swordblade", "largeguard", "mediumguard", "crossbar",
					"binding", "frypan", "sign", "knifeblade", "chisel", "largerod", "toughbinding", "largeplate", "broadaxe",
					"scythe", "excavator", "largeblade", "hammerhead", "fullguard", "bowstring", "fletching", "arrowhead"
			};

			List<Integer> patternIds = new ArrayList<Integer>();

			for (int x = 1; x < patternName.length; x++)
				if (!IguanaConfig.restrictedBoneParts.contains(x) || !IguanaConfig.restrictedCactusParts.contains(x)
						|| !IguanaConfig.restrictedFlintParts.contains(x) || !IguanaConfig.restrictedPaperParts.contains(x)
						|| !IguanaConfig.restrictedSlimeParts.contains(x) || !IguanaConfig.restrictedWoodParts.contains(x)
						|| IguanaConfig.allowStoneTools && !IguanaConfig.restrictedFlintParts.contains(x)
						|| x >= 23)
					patternIds.add(x);

			GameRegistry.addShapelessRecipe(new ItemStack(TContent.woodPattern, 1, patternIds.get(0)), new ItemStack(TContent.blankPattern, 1, 0));
			for (int x = 0; x < patternIds.size(); x++)
			{
				int pmeta = patternIds.get(x);

				if (x == patternIds.size() - 1)
					GameRegistry.addShapelessRecipe(new ItemStack(TContent.woodPattern, 1, patternIds.get(0)), new ItemStack(TContent.woodPattern, 1, pmeta));
				else
					GameRegistry.addShapelessRecipe(new ItemStack(TContent.woodPattern, 1, patternIds.get(x+1)), new ItemStack(TContent.woodPattern, 1, pmeta));
			}
		}


		// ALTERNATIVE PART CRAFTING
		if (IguanaConfig.easyPartCrafting)
		{
			IguanaLog.log("Adding easy part crafting");
			GameRegistry.addRecipe(new IguanaPartBuildRecipe());
			GameRegistry.registerCraftingHandler(new IguanaPartCraftingHandler());
		}


		// ALTERNATIVE TOOL CRAFTING
		if (IguanaConfig.easyToolCreation || IguanaConfig.easyToolModification)
		{
			IguanaLog.log("Adding easy tool crafting");
			GameRegistry.addRecipe(new IguanaToolBuildRecipe());
		}

	}

}
