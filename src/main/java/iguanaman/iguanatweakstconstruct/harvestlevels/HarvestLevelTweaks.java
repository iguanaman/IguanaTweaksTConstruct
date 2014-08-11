package iguanaman.iguanatweakstconstruct.harvestlevels;

import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.world.blocks.GravelOre;

/**
 * Used to modify the harvest levels of all known/findable tools and blocks. Vanilla and modded.
 * Has to be used with the Tinker Tool Tweaks or you'll be very unhappy with unmineable blocks.
 */
public abstract class HarvestLevelTweaks {
    public static void modifyHarvestLevels() {
        Log.info("Modifying HarvestLevel of blocks and items");

        modifyOredictBlocks();
        modifyVanillaBlocks();

        modifyTools();

        Log.info("Finished modifying HarvestLevel of blocks and items");
    }

    private static void modifyVanillaBlocks()
    {
        // ensure that the forgehooks are in place
        new ForgeHooks(); // this ensures that the static initializer of ForgeHooks is called already. Otherwise it overwrites our Harvestlevel changes.
        // see ForgeHooks.initTools()

        Blocks.iron_ore.setHarvestLevel("pickaxe", HarvestLevels._2_copper);
        Blocks.iron_block.setHarvestLevel("pickaxe", HarvestLevels._2_copper);
        Blocks.iron_bars.setHarvestLevel("pickaxe", HarvestLevels._2_copper);
        Blocks.lapis_ore.setHarvestLevel("pickaxe", HarvestLevels._2_copper);
        Blocks.lapis_block.setHarvestLevel("pickaxe", HarvestLevels._2_copper);

        Blocks.gold_ore.setHarvestLevel("pickaxe", HarvestLevels._3_iron);
        Blocks.gold_block.setHarvestLevel("pickaxe", HarvestLevels._3_iron);
        Blocks.redstone_ore.setHarvestLevel("pickaxe", HarvestLevels._3_iron);
        Blocks.lit_redstone_ore.setHarvestLevel("pickaxe", HarvestLevels._3_iron);

        Blocks.diamond_ore.setHarvestLevel("pickaxe", HarvestLevels._4_bronze); // yes, diamond requires diamond level. good thing there's bronze/steel ;)
        Blocks.diamond_block.setHarvestLevel("pickaxe", HarvestLevels._4_bronze);
        Blocks.emerald_ore.setHarvestLevel("pickaxe", HarvestLevels._4_bronze);
        Blocks.emerald_block.setHarvestLevel("pickaxe", HarvestLevels._4_bronze);

        Blocks.obsidian.setHarvestLevel("pickaxe", HarvestLevels._5_diamond);

        if(Config.logHarvestLevelChanges)
            Log.debug("Modified vanilla blocks");
    }

    private static void modifyOredictBlocks()
    {
        String[][][] lists = new String[][][] {oreDictLevels, oreDictLevelsMetallurgyFantasy, oreDictLevelsMetallurgyNether, oreDictLevelsMetallurgyEnd};
        for(String[][] odll : lists)
            for (int i = 0; i < odll.length; ++i)
                for (String materialName : odll[i]) {
                    // regular ore variants
                    for (ItemStack oreStack : OreDictionary.getOres("ore" + materialName)) modifyBlock(oreStack, i);
                    // dense ore variants
                    for (ItemStack oreStack : OreDictionary.getOres("denseore" + materialName)) modifyBlock(oreStack, i);
                    // nether ore variants
                    for (ItemStack oreStack : OreDictionary.getOres("oreNether" + materialName)) modifyBlock(oreStack, i);
                    // dense nether ore variants
                    for (ItemStack oreStack : OreDictionary.getOres("denseoreNether" + materialName)) modifyBlock(oreStack, i);
                    // full blocks (metal-blocks)
                    for (ItemStack oreStack : OreDictionary.getOres("block" + materialName)) modifyBlock(oreStack, i);
                    // stone-ores? dunno which mod adds that. leave it in for compatibility
                    for (ItemStack oreStack : OreDictionary.getOres("stone" + materialName)) modifyBlock(oreStack, i);
                    // bricks from metallurgy
                    for (ItemStack oreStack : OreDictionary.getOres("brick" + materialName)) modifyBlock(oreStack, i);
                }

        // metal-blocks
        if(Config.logHarvestLevelChanges)
            Log.debug("Modified oredicted blocks");
    }



    private static void modifyBlock(ItemStack stack, int harvestLevel)
    {
        Block block = Block.getBlockFromItem(stack.getItem());

        if(Config.logHarvestLevelChanges) {
            Log.debug(String.format("Changed Harvest Level of %s from %d to %d", stack.getUnlocalizedName(), block.getHarvestLevel(stack.getItemDamage()), harvestLevel));
        }

        // gravelore gets shovel level instead of pickaxe.
        if(block instanceof GravelOre)
            block.setHarvestLevel("shovel", harvestLevel, stack.getItemDamage());
        else
            block.setHarvestLevel("pickaxe", harvestLevel, stack.getItemDamage());
    }

    private static void modifyTools()
    {
        ItemStack tmp = new ItemStack(Items.stick); // we need one as argument, it's never actually accessed...
        // search for all items that have pickaxe harvestability
        for(Object o : Item.itemRegistry)
        {
            Item item = (Item) o;
            // cycle through all toolclasses. usually this'll either be pickaxe, shovel or axe. But mods could add items with multiple.
            for(String toolClass : item.getToolClasses(tmp)) {
                // adapt harvest levels
                int old = item.getHarvestLevel(tmp, toolClass);
                // wood/gold tool unchanged
                if (old <= 0)
                    continue;

                int hlvl = 0;
                switch (old) {
                    // stone tool: nerfed to wood level
                    case 1:
                        hlvl = HarvestLevels._0_stone;
                        break;
                    // iron tool
                    case 2:
                        hlvl = HarvestLevels._3_iron;
                        break;
                    // diamond tool
                    case 3:
                        hlvl = HarvestLevels._5_diamond;
                        break;
                    // default... we just increase it?
                    default:
                        hlvl = old + 1;
                }

                item.setHarvestLevel(toolClass, hlvl);
                if (Config.logMiningLevelChanges)
                    Log.debug(String.format("Changed Harvest Level for %s of %s from %d to %d", toolClass, item.getUnlocalizedName(), old, hlvl));
            }
        }

        if(Config.logMiningLevelChanges)
            Log.debug("Modified tools");
    }

    // HarvestLevels
    public static String[][] oreDictLevels = {
            // 0: Stone
            {},
            // 1: Flint
            {"Copper", "Coal", "Tetrahedrite", "Aluminum", "Aluminium", "NaturalAluminum", "AluminumBrass", "Shard", "Bauxite", "Zinc"},
            // 2: Copper
            {"Iron", "Pyrite", "Silver", "Lapis"},
            // 3: Iron
            {"Tin", "Cassiterite", "Gold", "Lead", "Redstone", "Steel", "Galena", "Nickel", "Invar", "Electrum", "Sphalerite", "Osmium"},
            // 4: Bronze
            {"Diamond", "Emerald", "Ruby", "Sapphire", "Cinnabar", "GreenSapphire", "BlackGranite", "RedGranite", "Manganese"},
            // 5: Redstone/Diamond
            {"Obsidian", "Tungstate", "Sodalite", "Quartz", "CertusQuartz", "SkyStone"},
            // 6: Obsidian/Alumite
            {"Ardite", "Uranium", "Olivine", "Sheldonite", "Platinum"},
            // 7: Ardite
            {"Cobalt", "Iridium", "Cooperite", "Titanium"},
            // 8: Cobalt
            {"Manyullyn"}
            // 9: Manyullyn (empty)
    };

    public static String[][] oreDictLevelsMetallurgyFantasy = {
            // 0: Stone
            {"Prometheum"},
            // 1: Flint
            {"DeepIron"},
            // 2: Copper
            {"Infuscolium"},
            // 3: Iron
            {"Oureclase"},
            // 4: Bronze
            {"AstralSilver"},
            // 5: Redstone/Diamond
            {"Carmot", "Mithril"},
            // 6: Obsidian/Alumite
            {"Rubracium"},
            // 7: Ardite
            {"Orichalcum"},
            // 8: Cobalt
            {"Adamantine"},
            // 9: Manyullyn
            {"Atlarus"}
    };

    public static String[][] oreDictLevelsMetallurgyNether = {
            // 0: Stone
            {},
            // 1: Flint
            {},
            // 2: Copper
            {},
            // 3: Iron
            {"Lemurite"},
            // 4: Bronze
            {"Ignatius", "ShadowIron"},
            // 5: Redstone/Diamond
            {"Midasium", "Vyroxeres"},
            // 6: Obsidian/Alumite
            {"Ceruclase", "Alduorite"},
            // 7: Ardite
            {"Kalendrite"},
            // 8: Cobalt
            {"Vulcanite"},
            // 9: Manyullyn
            {"Sanguinite"}
    };

    public static String[][] oreDictLevelsMetallurgyEnd = {
            // 0: Stone
            {},
            // 1: Flint
            {},
            // 2: Copper
            {},
            // 3: Iron
            {},
            // 4: Bronze
            {},
            // 5: Redstone/Diamond
            {"Eximite"},
            // 6: Obsidian/Alumite
            {"Meutoite"},
            // 7: Ardite
            {},
            // 8: Cobalt
            {},
            // 9: Manyullyn
            {},
    };
}
