package iguanaman.iguanatweakstconstruct.old.tweaks;

import cpw.mods.fml.common.registry.GameRegistry;
import iguanaman.iguanatweakstconstruct.old.IguanaConfig;
import iguanaman.iguanatweakstconstruct.old.modifiers.*;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.tools.ModDurability;
import tconstruct.modifiers.tools.ModInteger;
import tconstruct.tools.TinkerTools;

import java.util.Iterator;

public class ModifierTweaks
{

    public static void init ()
    {

        // REMOVE OLD MODIFIERS
        Log.info("Removing old modifiers");
        Iterator<ItemModifier> i = ModifyBuilder.instance.itemModifiers.iterator();
        while (i.hasNext())
        {
            ItemModifier mod = i.next();
            if (mod.key == "Emerald" || mod.key == "Diamond" || mod.key == "Tier1Free" && IguanaConfig.toolLeveling || mod.key == "Tier2Free" || mod.key == "Moss" || mod.key == "Lapis"
                    || mod.key == "ModAttack" || mod.key == "Redstone" || mod.key == "")
                //IguanaLog.log("Removing old " + mod.key + " modifier");
                i.remove();
        }

        // Change recipes
        if (IguanaConfig.moreExpensiveSilkyCloth)
        {
            RecipeRemover.removeAnyRecipe(new ItemStack(TinkerTools.materials, 1, 25));
            GameRegistry.addRecipe(new ItemStack(TinkerTools.materials, 1, 25), "sss", "sns", "sss", 'n', new ItemStack(TinkerTools.materials, 1, 14), 's', new ItemStack(Items.string)); //Silky Cloth
            GameRegistry.addRecipe(new ItemStack(TinkerTools.materials, 1, 25), "sss", "sns", "sss", 'n', new ItemStack(Items.gold_ingot), 's', new ItemStack(Items.string)); //Silky Cloth
        }

        if (IguanaConfig.moreExpensiveSilkyJewel)
        {
            RecipeRemover.removeAnyRecipe(new ItemStack(TinkerTools.materials, 1, 26));
            GameRegistry.addRecipe(new ItemStack(TinkerTools.materials, 1, 26), " c ", "cec", " c ", 'c', new ItemStack(TinkerTools.materials, 1, 25), 'e', new ItemStack(Blocks.emerald_block)); //Silky Jewel
        }

        // REPLACE OLD MODIFIERS
        Log.info("Replacing old modifiers");

        if (IguanaConfig.partReplacement)
        	ModifyBuilder.registerModifier(new IguanaModUpgrade());
        ModifyBuilder.registerModifier(new IguanaModRepair());
        // TODO: Find a way to replace vanilla skullItem
        /*if (!IguanaIguanaConfig.toolLevelingRandomBonuses)
        	ModifyBuilder.registerModifier(new ModExtraModifier(new ItemStack[] { new ItemStack(Items.sk, 1, 6), new ItemStack(Item.skullItem, 1, 7) }, "Tier2Free"));*/
        ModifyBuilder.registerModifier(new ModInteger(new ItemStack[] { new ItemStack(TinkerTools.materials, 1, 6) }, 4, "Moss", IguanaConfig.mossRepairSpeed, "\u00a72", "Auto-Repair"));
        ModifyBuilder.registerModifier(new ModDurability(new ItemStack[] { new ItemStack(Items.emerald) }, 1, 0, 0.5f, TConstructRegistry.getMaterial("Bronze").harvestLevel(), "Emerald",
                "\u00a72Durability +50%", "\u00a72"));
        ModifyBuilder.registerModifier(new ModDurability(new ItemStack[] { new ItemStack(Items.diamond) }, 0, 500, 0f, 0, "Diamond", "\u00a7bDurability +500", "\u00a7b"));

        ItemStack lapisItem = new ItemStack(Items.dye, 1, 4);
        ItemStack lapisBlock = new ItemStack(Blocks.lapis_block);
        ModifyBuilder.registerModifier(new IguanaModLapis(new ItemStack[] { lapisBlock, lapisItem }, 10, new int[] { 9, 1 }));

        ItemStack quartzItem = new ItemStack(Items.quartz);
        ItemStack quartzBlock = new ItemStack(Blocks.quartz_block, 1, Short.MAX_VALUE);
        ModifyBuilder.registerModifier(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem }, 11, 1));
        ModifyBuilder.registerModifier(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem, quartzItem }, 11, 2));
        ModifyBuilder.registerModifier(new IguanaModAttack("Quartz", new ItemStack[] { quartzBlock }, 11, 4));
        ModifyBuilder.registerModifier(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem, quartzBlock }, 11, 5));
        ModifyBuilder.registerModifier(new IguanaModAttack("Quartz", new ItemStack[] { quartzBlock, quartzBlock }, 11, 8));

        ItemStack redstoneItem = new ItemStack(Items.redstone);
        ItemStack redstoneBlock = new ItemStack(Blocks.redstone_block);
        ModifyBuilder.registerModifier(new IguanaModRedstone(new ItemStack[] { redstoneItem }, 2, 1));
        ModifyBuilder.registerModifier(new IguanaModRedstone(new ItemStack[] { redstoneItem, redstoneItem }, 2, 2));
        ModifyBuilder.registerModifier(new IguanaModRedstone(new ItemStack[] { redstoneBlock }, 2, 9));
        ModifyBuilder.registerModifier(new IguanaModRedstone(new ItemStack[] { redstoneItem, redstoneBlock }, 2, 10));
        ModifyBuilder.registerModifier(new IguanaModRedstone(new ItemStack[] { redstoneBlock, redstoneBlock }, 2, 18));

        // CLEAN MODIFIER
        if (IguanaConfig.addCleanModifier)
        	ModifyBuilder.registerModifier(new IguanaModClean());

        // MINING BOOST MODIFIERS
        if (IguanaConfig.mobHeadPickaxeBoost)
        {
            Log.info("Adding mob head modifiers");

            // add modifers
            ModifyBuilder.registerModifier(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 0) }, 20, TConstructRegistry.getMaterial("Iron").harvestLevel(), "Skeleton Skull",
                    "\u00a7fBoosted", "\u00a7f"));
            ModifyBuilder.registerModifier(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 2) }, 21, TConstructRegistry.getMaterial("Iron").harvestLevel(), "Zombie Head",
                    "\u00a72Boosted", "\u00a72"));
            ModifyBuilder.registerModifier(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 4) }, 22, TConstructRegistry.getMaterial("Bronze").harvestLevel(), "Creeper Head",
                    "\u00a7aBoosted", "\u00a7a"));
            ModifyBuilder.registerModifier(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 5) }, 23, TConstructRegistry.getMaterial("Obsidian").harvestLevel(), "Enderman Head",
                    "\u00a78Boosted", "\u00a78"));
            ModifyBuilder.registerModifier(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 1) }, 24, TConstructRegistry.getMaterial("Ardite").harvestLevel(), "Wither Skeleton Skull",
                    "\u00a78Boosted", "\u00a78"));
            ModifyBuilder.registerModifier(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.nether_star) }, 25, TConstructRegistry.getMaterial("Cobalt").harvestLevel(), "Nether Star",
                    "\u00a73Boosted", "\u00a73"));

            // rendering code
            ToolCore[] tools = new ToolCore[] { TinkerTools.pickaxe, TinkerTools.hammer };
            int[] modifierIds = new int[] { 20, 21, 22, 23, 24, 25 };
            String[] renderNames = new String[] { "skeletonskull", "zombiehead", "creeperhead", "endermanhead", "witherskeletonskull", "netherstar" };

            for (ToolCore tool : tools)
                for (int index = 0; index < modifierIds.length; ++index)
                    TConstructClientRegistry.addEffectRenderMapping(tool, modifierIds[index], "iguanatweakstconstruct", renderNames[index], true);
        }

        // LEVELING MODIFIER
        if (IguanaConfig.toolLeveling)
        {
            Log.info("Adding leveling active modifier");
            //TConstructRegistry.activeModifiers.add(0, new IguanaActiveToolMod());
        }
    }

}
