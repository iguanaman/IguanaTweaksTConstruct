package iguanaman.iguanatweakstconstruct.harvestlevels;

import iguanaman.iguanatweakstconstruct.harvestlevels.IguanaHarvestLevelTweaks.HarvestLevels;

import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

import static net.minecraft.init.Blocks.*;
import static net.minecraft.init.Blocks.lit_redstone_ore;

/**
 * Used to modify the harvest levels of all known/findable tools and blocks. Vanilla and modded.
 * Has to be used with the Tinker Tool Tweaks or you'll be very unhappy with unmineable blocks.
 */
public abstract class HarvestLevelTweaks {
    public static void modifyHarvestLevels() {
        Log.info("Modifying HarvestLevel of blocks and items");

        modifyVanillaBlocks();

        modifyTools();

        Log.info("Finished modifying HarvestLevel of blocks and items");
    }

    private static void modifyVanillaBlocks()
    {
        // ensure that the forgehooks are in place
        new ForgeHooks(); // this ensures that the static initializer of ForgeHooks is called already. Otherwise it overwrites our Harvestlevel changes.
        // see ForgeHooks.initTools()
        Blocks.obsidian.setHarvestLevel("pickaxe", HarvestLevels._4_diamond);
        for (Block block : new Block[]{emerald_ore, emerald_block, diamond_ore, diamond_block, gold_ore, gold_block, redstone_ore, lit_redstone_ore})
        {
            block.setHarvestLevel("pickaxe", HarvestLevels._4_diamond);
        }
        Blocks.iron_ore.setHarvestLevel("pickaxe", HarvestLevels._2_copper);
        Blocks.iron_block.setHarvestLevel("pickaxe", HarvestLevels._2_copper);
        Blocks.lapis_ore.setHarvestLevel("pickaxe", HarvestLevels._2_copper);
        Blocks.lapis_block.setHarvestLevel("pickaxe", HarvestLevels._2_copper);

        Log.trace("Modified vanilla blocks");
    }

    private static void modifyTools()
    {
        ItemStack tmp = new ItemStack(Items.stick); // we need one as argument, it's never actually accessed...
        // search for all items that have pickaxe harvestability
        for(Object o : Item.itemRegistry)
        {
            Item item = (Item) o;
            if(!item.getToolClasses(tmp).contains("pickaxe"))
                continue;

            // adapt harvest levels
            int old = item.getHarvestLevel(tmp, "pickaxe");
            // wood/gold tool unchanged
            if(old <= 0)
                continue;

            int hlvl = 0;
            switch(old)
            {
                // stone tool: nerfed to wood level
                case 1: hlvl = HarvestLevels._0_stone; break;
                // iron tool
                case 2: hlvl = HarvestLevels._3_iron; break;
                // diamond tool
                case 3: hlvl = HarvestLevels._4_diamond;  break;
                // default... we just increase it?
                default: hlvl = old+1;
            }

            item.setHarvestLevel("pickaxe", hlvl);
            Log.trace(String.format("Changed Harvest Level of %s from %d to %d", item.getUnlocalizedName(), old, hlvl));
        }

        Log.trace("Modified tools");
    }
}
