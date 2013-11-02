package iguanaman.iguanatweakstconstruct;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.common.TContent;
import tconstruct.items.Pattern;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMaterial;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;

public class VariousTweaks {

	public static void init()
	{

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
        
    	
        // REUSABLE PARTS
        IguanaLog.log("Making non-metal parts reusable in part builder");
        Item[] toolParts = { TContent.toolRod, TContent.pickaxeHead, TContent.shovelHead, TContent.hatchetHead, 
        		TContent.binding, TContent.toughBinding, TContent.toughRod, TContent.largePlate, 
        		TContent.swordBlade, TContent.wideGuard, TContent.handGuard, TContent.crossbar, 
        		TContent.knifeBlade, TContent.fullGuard, TContent.frypanHead, TContent.signHead, 
        		TContent.chiselHead, TContent.scytheBlade, TContent.broadAxeHead, TContent.excavatorHead, 
        		TContent.largeSwordBlade, TContent.hammerHead, TContent.bowstring, TContent.fletching, 
        		TContent.arrowhead };
        
        int[] nonMetals = { 0, 1, 3, 4, 5, 6, 7, 8, 9, 17 };
        PatternBuilder pb = PatternBuilder.instance;
        for (int p = 0; p < toolParts.length; ++p)
        {
        	for (int m = 0; m < nonMetals.length; ++m)
        	{
        		ToolMaterial mat = TConstructRegistry.getMaterial(m);
    			int cost = ((Pattern)TContent.woodPattern).getPatternCost(new ItemStack(TContent.woodPattern, 1, p + 1));
    			pb.registerMaterial(new ItemStack(toolParts[p], 1, nonMetals[m]), cost, mat.name());
        	}
        }
        
        
        // MELTABLE PARTS
        IguanaLog.log("Making metal parts meltable in smeltery");
        
        Fluid[] fluids = {TContent.moltenIronFluid, TContent.moltenObsidianFluid, TContent.moltenCobaltFluid, 
        		TContent.moltenArditeFluid, TContent.moltenManyullynFluid, TContent.moltenCopperFluid, 
        		TContent.moltenBronzeFluid, TContent.moltenAlumiteFluid, TContent.moltenSteelFluid};
        int[] metals = { 2, 6, 10, 11, 12, 13, 14, 15, 16 };
        int mb = TContent.metalBlock.blockID;
        int[] renderBlock = { Block.blockIron.blockID, Block.obsidian.blockID, mb, mb, mb, mb, mb, mb, mb };
        int[] renderMeta = { 0, 0, 0, 1, 2, 3, 4, 8, 9 };
        int[] temps = { 500, 750, 650, 650, 750, 100, 500, 650, 700 };
        int halfIngot = Math.round((float)TConstruct.ingotLiquidValue / 2f);

        for (int p = 0; p < toolParts.length; ++p)
        {
        	for (int m = 0; m < metals.length; ++m)
        	{
    			int cost = ((Pattern)TContent.woodPattern).getPatternCost(new ItemStack(TContent.woodPattern, 1, p + 1));
                int liquidValue = cost * Math.round((float)TConstruct.ingotLiquidValue / 2f);
                Smeltery.addMelting(new ItemStack(toolParts[p], 1, metals[m]), renderBlock[m], renderMeta[m], temps[m], new FluidStack(fluids[m], liquidValue)); 
        	}
        }
        

        // REMOVE RESTRICTED PARTS FROM TINKERS HOUSE LOOT
    	IguanaLog.log("Removing restricted parts from Tinker House chest");

    	for (int i = 0; i < toolParts.length; ++i)
    	{
    		if (IguanaConfig.restrictedWoodParts.contains(i+1))
                TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 0));
    		if (IguanaConfig.restrictedStoneParts.contains(i+1))
                TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 1));
    		if (IguanaConfig.restrictedFlintParts.contains(i+1))
                TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 3));
    		if (IguanaConfig.restrictedCactusParts.contains(i+1))
                TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 4));
    		if (IguanaConfig.restrictedBoneParts.contains(i+1))
                TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 5));
    		if (IguanaConfig.restrictedPaperParts.contains(i+1))
                TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 9));
    		if (IguanaConfig.restrictedSlimeParts.contains(i+1))
    		{
                TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 8));
                TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 17));
    		}
            TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 2)); //iron
            TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 6)); //obsidian
            TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 10)); //cobalt
            TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 11)); //ardite
            TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 12)); //manyullum
            TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 14)); //bronze
            TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 15)); //alumite
            TContent.tinkerHouseChest.removeItem(new ItemStack(toolParts[i], 1, 16)); //steel
    	}
    	

        //ROTATING PATTERN CRAFTING
    	IguanaLog.log("Adding rotating pattern crafting recipes");
    	
    	String[] patternName = new String[] { "ingot", "rod", "pickaxe", "shovel", "axe", "swordblade", "largeguard", "mediumguard", "crossbar", "binding", "frypan", "sign",
                "knifeblade", "chisel", "largerod", "toughbinding", "largeplate", "broadaxe", "scythe", "excavator", "largeblade", "hammerhead", "fullguard", "bowstring", "fletching", "arrowhead" };
    	
    	List<Integer> patternIds = new ArrayList<Integer>();
    	
        for (int x = 1; x < patternName.length; x++)
        {
        	if (!IguanaConfig.restrictedBoneParts.contains(x) || !IguanaConfig.restrictedCactusParts.contains(x)
       			 || !IguanaConfig.restrictedFlintParts.contains(x) || !IguanaConfig.restrictedPaperParts.contains(x)
    			 || !IguanaConfig.restrictedSlimeParts.contains(x) || !IguanaConfig.restrictedWoodParts.contains(x)
    			 || (IguanaConfig.allowStoneTools && !IguanaConfig.restrictedStoneParts.contains(x)))
        	{
        		patternIds.add(x);
        	}
        }
        
        ItemStack[] materialStacks = new ItemStack[] {
        		pb.getRodFromSet("Bone"), pb.getShardFromSet("Bone"), new ItemStack(Item.bone), 
        		pb.getRodFromSet("Cactus"), pb.getShardFromSet("Cactus"), new ItemStack(Block.cactus), 
        		pb.getRodFromSet("Paper"), pb.getShardFromSet("Paper"), new ItemStack(TContent.materials, 1, 0), 
        		pb.getRodFromSet("Slime"), pb.getShardFromSet("Slime"), new ItemStack(TContent.materials, 1, 1), 
        		pb.getRodFromSet("BlueSlime"), pb.getShardFromSet("BlueSlime"), new ItemStack(TContent.materials, 1, 17), 
        		pb.getRodFromSet("Flint"), pb.getShardFromSet("Flint"), new ItemStack(Item.flint),
        		pb.getRodFromSet("Wood"), pb.getShardFromSet("Wood"), 
        		new ItemStack(Block.planks, 1, 0), new ItemStack(Block.planks, 1, 1), new ItemStack(Block.planks, 1, 2), new ItemStack(Block.planks, 1, 3)};

        GameRegistry.addShapelessRecipe(new ItemStack(TContent.woodPattern, 1, patternIds.get(0)), new ItemStack(TContent.blankPattern, 1, 0));
        for (int x = 0; x < patternIds.size(); x++)
        {
        	int pmeta = patternIds.get(x);
        	
    		if (x == patternIds.size() - 1)
        		GameRegistry.addShapelessRecipe(new ItemStack(TContent.woodPattern, 1, patternIds.get(0)), new ItemStack(TContent.woodPattern, 1, pmeta));
        	else
        		GameRegistry.addShapelessRecipe(new ItemStack(TContent.woodPattern, 1, patternIds.get(x+1)), new ItemStack(TContent.woodPattern, 1, pmeta));
    		
    		int patternCost = ((Pattern)TContent.woodPattern).getPatternCost(new ItemStack(TContent.woodPattern, 1, pmeta));
    		
    		for (ItemStack materialStack : materialStacks)
    		{
        		ItemStack[] parts = pb.getToolPart(materialStack, new ItemStack(TContent.woodPattern, 1, pmeta), null);
        		if (parts != null)
            		GameRegistry.addShapelessRecipe(parts[0], new ItemStack(TContent.woodPattern, 1, pmeta), materialStack);
    		}
        }
        
        
        // ALTERNATIVE TOOL CRAFTING
        IguanaLog.log("Adding alternative tool crafting");
    	
    	ToolCore[] tools = { TContent.pickaxe, TContent.shovel, TContent.hatchet, TContent.broadsword, 
    			TContent.longsword, TContent.rapier, TContent.cutlass, TContent.frypan, 
    			TContent.battlesign, TContent.mattock, TContent.chisel};
    	
    	for (ToolCore tool : tools)
    	{
    		IguanaLog.log("Adding recipes for " + tool.getUnlocalizedName());
    		
    		Item headItem = tool.getHeadItem();
    		Item handleItem = tool.getHandleItem();
    		Item accessoryItem = tool.getAccessoryItem();
    		
    		int maxId = 17;

			int maxAccessoryId = 0;
			if (accessoryItem != null) maxAccessoryId = maxId;

    		for (int headItemId = 0; headItemId <= maxId ; ++headItemId)
    		{
        		for (int handleItemId = 0; handleItemId <= maxId ; ++handleItemId)
        		{
            		for (int accessoryItemId = 0; accessoryItemId <= maxAccessoryId ; ++accessoryItemId)
            		{
            			ItemStack head = new ItemStack(headItem, 1, headItemId);
            			
            			ItemStack handle = new ItemStack(handleItem, 1, handleItemId);
            			
            			ItemStack accessory = null;
            			if (accessoryItem != null) accessory = new ItemStack(accessoryItem, 1, accessoryItemId);
            			
            			ItemStack output = ToolBuilder.instance.buildTool(head, handle, accessory, null, null);
            			
            			if (output != null)
            			{
            				if (head != null && handle != null && accessory != null)
            					GameRegistry.addShapelessRecipe(output, head, handle, accessory);
            				else if (head != null && handle != null)
            					GameRegistry.addShapelessRecipe(output, head, handle);
            			}
            		}
        		}
    		}
    	}
	}
	
}
