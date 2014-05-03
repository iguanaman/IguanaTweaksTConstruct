package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.modifiers.IguanaActiveToolMod;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModAttack;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModClean;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModHeads;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModLapis;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModRedstone;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModRepair;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModUpgrade;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tconstruct.common.TContent;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMod;
import tconstruct.modifiers.tools.ModDurability;
import tconstruct.modifiers.tools.ModExtraModifier;
import tconstruct.modifiers.tools.ModInteger;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModifierTweaks
{

    public static void init ()
    {

        // REMOVE OLD MODIFIERS
        IguanaLog.log("Removing old modifiers");
        Iterator<ToolMod> i = ToolBuilder.instance.toolMods.iterator();
        while (i.hasNext())
        {
            ToolMod mod = i.next();
            if (mod.key == "Emerald" || mod.key == "Diamond" || mod.key == "Tier1Free" && IguanaConfig.toolLeveling || mod.key == "Tier2Free" || mod.key == "Moss" || mod.key == "Lapis"
                    || mod.key == "ModAttack" || mod.key == "Redstone" || mod.key == "")
                //IguanaLog.log("Removing old " + mod.key + " modifier");
                i.remove();
        }

        // Change recipes
        if (IguanaConfig.moreExpensiveSilkyCloth)
        {
            RecipeRemover.removeAnyRecipe(new ItemStack(TContent.materials, 1, 25));
            GameRegistry.addRecipe(new ItemStack(TContent.materials, 1, 25), "sss", "sns", "sss", 'n', new ItemStack(TContent.materials, 1, 14), 's', new ItemStack(Item.silk)); //Silky Cloth
            GameRegistry.addRecipe(new ItemStack(TContent.materials, 1, 25), "sss", "sns", "sss", 'n', new ItemStack(Item.ingotGold), 's', new ItemStack(Item.silk)); //Silky Cloth
        }

        if (IguanaConfig.moreExpensiveSilkyJewel)
        {
            RecipeRemover.removeAnyRecipe(new ItemStack(TContent.materials, 1, 26));
            GameRegistry.addRecipe(new ItemStack(TContent.materials, 1, 26), " c ", "cec", " c ", 'c', new ItemStack(TContent.materials, 1, 25), 'e', new ItemStack(Block.blockEmerald)); //Silky Jewel
        }

        // REPLACE OLD MODIFIERS
        IguanaLog.log("Replacing old modifiers");

        if (IguanaConfig.partReplacement)
            ToolBuilder.registerToolMod(new IguanaModUpgrade());
        ToolBuilder.registerToolMod(new IguanaModRepair());
        if (!IguanaConfig.toolLevelingRandomBonuses)
            ToolBuilder.registerToolMod(new ModExtraModifier(new ItemStack[] { new ItemStack(Item.skull, 1, 6), new ItemStack(Item.skull, 1, 7) }, "Tier2Free"));
        ToolBuilder.registerToolMod(new ModInteger(new ItemStack[] { new ItemStack(TContent.materials, 1, 6) }, 4, "Moss", IguanaConfig.mossRepairSpeed, "\u00a72", "Auto-Repair"));
        ToolBuilder.registerToolMod(new ModDurability(new ItemStack[] { new ItemStack(Item.emerald) }, 1, 0, 0.5f, TConstructRegistry.getMaterial("Bronze").harvestLevel(), "Emerald",
                "\u00a72Durability +50%", "\u00a72"));
        ToolBuilder.registerToolMod(new ModDurability(new ItemStack[] { new ItemStack(Item.diamond) }, 0, 500, 0f, 0, "Diamond", "\u00a7bDurability +500", "\u00a7b"));

        ItemStack lapisItem = new ItemStack(Item.dyePowder, 1, 4);
        ItemStack lapisBlock = new ItemStack(Block.blockLapis);
        ToolBuilder.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisBlock, lapisItem }, 10, new int[] { 9, 1 }));

        ItemStack quartzItem = new ItemStack(Item.netherQuartz);
        ItemStack quartzBlock = new ItemStack(Block.blockNetherQuartz, 1, Short.MAX_VALUE);
        ToolBuilder.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem }, 11, 1));
        ToolBuilder.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem, quartzItem }, 11, 2));
        ToolBuilder.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzBlock }, 11, 4));
        ToolBuilder.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem, quartzBlock }, 11, 5));
        ToolBuilder.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzBlock, quartzBlock }, 11, 8));

        ItemStack redstoneItem = new ItemStack(Item.redstone);
        ItemStack redstoneBlock = new ItemStack(Block.blockRedstone);
        ToolBuilder.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneItem }, 2, 1));
        ToolBuilder.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneItem, redstoneItem }, 2, 2));
        ToolBuilder.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneBlock }, 2, 9));
        ToolBuilder.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneItem, redstoneBlock }, 2, 10));
        ToolBuilder.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneBlock, redstoneBlock }, 2, 18));

        // CLEAN MODIFIER
        if (IguanaConfig.addCleanModifier)
            ToolBuilder.registerToolMod(new IguanaModClean());

        // MINING BOOST MODIFIERS
        if (IguanaConfig.mobHeadPickaxeBoost)
        {
            IguanaLog.log("Adding mob head modifiers");

            // add modifers
            ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.skull, 1, 0) }, 20, TConstructRegistry.getMaterial("Iron").harvestLevel(), "Skeleton Skull",
                    "\u00a7fBoosted", "\u00a7f"));
            ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.skull, 1, 2) }, 21, TConstructRegistry.getMaterial("Iron").harvestLevel(), "Zombie Head",
                    "\u00a72Boosted", "\u00a72"));
            ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.skull, 1, 4) }, 22, TConstructRegistry.getMaterial("Bronze").harvestLevel(), "Creeper Head",
                    "\u00a7aBoosted", "\u00a7a"));
            ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.skull, 1, 5) }, 23, TConstructRegistry.getMaterial("Obsidian").harvestLevel(), "Enderman Head",
                    "\u00a78Boosted", "\u00a78"));
            ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.skull, 1, 1) }, 24, TConstructRegistry.getMaterial("Ardite").harvestLevel(), "Wither Skeleton Skull",
                    "\u00a78Boosted", "\u00a78"));
            ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Item.netherStar) }, 25, TConstructRegistry.getMaterial("Cobalt").harvestLevel(), "Nether Star",
                    "\u00a73Boosted", "\u00a73"));

            // rendering code
            ToolCore[] tools = new ToolCore[] { TContent.pickaxe, TContent.hammer };
            int[] modifierIds = new int[] { 20, 21, 22, 23, 24, 25 };
            String[] renderNames = new String[] { "skeletonskull", "zombiehead", "creeperhead", "endermanhead", "witherskeletonskull", "netherstar" };

            for (ToolCore tool : tools)
                for (int index = 0; index < modifierIds.length; ++index)
                    TConstructClientRegistry.addEffectRenderMapping(tool, modifierIds[index], "iguanatweakstconstruct", renderNames[index], true);
        }

        // LEVELING MODIFIER
        if (IguanaConfig.toolLeveling)
        {
            IguanaLog.log("Adding leveling active modifier");
            TConstructRegistry.activeModifiers.add(0, new IguanaActiveToolMod());
        }
    }

}
