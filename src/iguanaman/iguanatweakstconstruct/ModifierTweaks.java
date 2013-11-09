package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.modifiers.IguanaActiveToolMod;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModAttack;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModHeads;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModElectric;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModLapis;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModRedstone;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModRepair;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModUpgrade;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tconstruct.common.TContent;
import tconstruct.library.ActiveToolMod;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMod;
import tconstruct.modifiers.ModDurability;
import tconstruct.modifiers.ModExtraModifier;
import tconstruct.modifiers.ModInteger;
import tconstruct.modifiers.TActiveOmniMod;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModifierTweaks {

	public static void init()
	{

		// REMOVE OLD MODIFIERS
    	IguanaLog.log("Removing old modifiers");
		Iterator<ToolMod> i = ToolBuilder.instance.toolMods.iterator();
		while (i.hasNext()) {
		   ToolMod mod = i.next();
            if (mod.key == "Emerald" || (IguanaConfig.mobHeadModifiers && mod.key == "Diamond") || (IguanaConfig.moreExpensiveElectric && mod.key == "Electric") || 
            		(mod.key == "Tier1Free" && IguanaConfig.toolLeveling) || 
            		(mod.key == "Tier2Free" && (IguanaConfig.toolLeveling  || IguanaConfig.mobHeadModifiers)) || 
            		mod.key == "Moss" || mod.key == "Lapis" || mod.key == "ModAttack" || mod.key == "Redstone"
            		|| mod.key == "") {
            	//IguanaLog.log("Removing old " + mod.key + " modifier");
        		i.remove();
        	}
		}

		
		// REPLACE OLD MODIFIERS
		IguanaLog.log("Replacing old modifiers");
		
        ToolBuilder tb = ToolBuilder.instance;
        if (IguanaConfig.partReplacement) tb.registerToolMod(new IguanaModUpgrade());
        tb.registerToolMod(new IguanaModRepair());
        if (Loader.isModLoaded("IC2") && IguanaConfig.moreExpensiveElectric) tb.registerToolMod(new IguanaModElectric());
        if (!IguanaConfig.toolLevelingRandomBonuses  && IguanaConfig.mobHeadModifiers)
        	tb.registerToolMod(new ModExtraModifier(new ItemStack[] { new ItemStack(Item.skull, 1, 6), new ItemStack(Item.skull, 1, 7) }, "Tier2Free"));
        tb.registerToolMod(new ModInteger(new ItemStack[] { new ItemStack(TContent.materials, 1, 6) }, 4, "Moss", IguanaConfig.mossRepairSpeed, "\u00a72", "Auto-Repair"));
		tb.registerToolMod(new ModDurability(new ItemStack[] { new ItemStack(Item.emerald) }, 1, 0, 0.5f, TConstructRegistry.getMaterial("Bronze").harvestLevel(), "Emerald", "\u00a72Durability +50%", "\u00a72"));

        ItemStack lapisItem = new ItemStack(Item.dyePowder, 1, 4);
        ItemStack lapisBlock = new ItemStack(Block.blockLapis);
        tb.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisItem }, 10, 1));
        tb.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisItem, lapisItem }, 10, 2));
        tb.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisBlock }, 10, 9));
        tb.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisItem, lapisBlock }, 10, 10));
        tb.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisBlock, lapisBlock }, 10, 18));
        tb.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisBlock, lapisBlock, lapisBlock }, 10, 27));
        
        ItemStack quartzItem = new ItemStack(Item.netherQuartz);
        ItemStack quartzBlock = new ItemStack(Block.blockNetherQuartz, 1, Short.MAX_VALUE);
        tb.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem }, 11, 1));
        tb.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem, quartzItem }, 11, 2));
        tb.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzBlock }, 11, 4));
        tb.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem, quartzBlock }, 11, 5));
        tb.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzBlock, quartzBlock }, 11, 8));
        
		
        ItemStack redstoneItem = new ItemStack(Item.redstone);
        ItemStack redstoneBlock = new ItemStack(Block.blockRedstone);
        tb.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneItem }, 2, 1));
        tb.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneItem, redstoneItem }, 2, 2));
        tb.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneBlock }, 2, 9));
        tb.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneItem, redstoneBlock }, 2, 10));
        tb.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneBlock, redstoneBlock }, 2, 18));
        
		if (IguanaConfig.moreExpensiveSilkTouch)
		{
            RecipeRemover.removeAnyRecipe(new ItemStack(TContent.materials, 1, 25));
            RecipeRemover.removeAnyRecipe(new ItemStack(TContent.materials, 1, 26));
            GameRegistry.addRecipe(new ItemStack(TContent.materials, 1, 25), "sss", "sns", "sss", 'n', new ItemStack(TContent.materials, 1, 14), 's', new ItemStack(Item.silk)); //Silky Cloth
            GameRegistry.addRecipe(new ItemStack(TContent.materials, 1, 25), "sss", "sns", "sss", 'n', new ItemStack(Item.ingotGold), 's', new ItemStack(Item.silk));
            GameRegistry.addRecipe(new ItemStack(TContent.materials, 1, 26), " c ", "cec", " c ", 'c', new ItemStack(TContent.materials, 1, 25), 'e', new ItemStack(Block.blockEmerald)); //Silky Jewel
		}
		

		// MINING BOOST MODIFIERS
		if (IguanaConfig.mobHeadModifiers)
		{
			tb.registerToolMod(new ModDurability(new ItemStack[] { new ItemStack(Item.diamond) }, 0, 500, 0f, 0, "Diamond", "\u00a7bDurability +500", "\u00a7b"));
			
			IguanaLog.log("Adding mob head modifiers");
			
			// add modifers
			tb.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.skull, 1, 0) }, 20, TConstructRegistry.getMaterial("Iron").harvestLevel(), "Skeleton Skull", "\u00a7fMining Level Boost", "\u00a7f"));
			tb.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.skull, 1, 2) }, 21, TConstructRegistry.getMaterial("Iron").harvestLevel(), "Zombie Head", "\u00a72Mining Level Boost", "\u00a72"));
			tb.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.skull, 1, 4) }, 22, TConstructRegistry.getMaterial("Bronze").harvestLevel(), "Creeper Head", "\u00a7aMining Level Boost", "\u00a7a"));
			tb.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.skull, 1, 5) }, 23, TConstructRegistry.getMaterial("Obsidian").harvestLevel(), "Enderman Head", "\u00a78Mining Level Boost", "\u00a78"));
			tb.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.skull, 1, 1) }, 24, TConstructRegistry.getMaterial("Ardite").harvestLevel(), "Wither Skeleton Skull", "\u00a78Mining Level Boost", "\u00a78"));
			tb.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.netherStar) }, 25, TConstructRegistry.getMaterial("Cobalt").harvestLevel(), "Nether Star", "\u00a73Mining Level Boost", "\u00a73"));

			// rendering code
			ToolCore[] tools = new ToolCore[] { TContent.pickaxe, TContent.hammer };
			int[] modifierIds = new int[] { 20, 21, 22, 23, 24, 25 };
			String[] renderNames = new String[] { "skeletonskull", "zombiehead", "creeperhead", "endermanhead", "witherskeletonskull", "netherstar" };
			
			for (ToolCore tool : tools)
			{
				for (int index = 0; index < modifierIds.length; ++index)
				{
		            TConstructClientRegistry.addEffectRenderMapping(tool, modifierIds[index], "iguanatweakstconstruct", renderNames[index], true);
				}
			}
		}
		
		
		// LEVELING MODIFIER
		IguanaLog.log("Adding leveling active modifier");
		if (IguanaConfig.toolLeveling || IguanaConfig.pickaxeLevelingBoost) TConstructRegistry.activeModifiers.add(0, new IguanaActiveToolMod());
	}
	
}
