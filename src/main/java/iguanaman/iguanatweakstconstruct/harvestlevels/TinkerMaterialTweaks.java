package iguanaman.iguanatweakstconstruct.harvestlevels;

import cpw.mods.fml.common.Loader;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.Log;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.tools.TinkerTools;

import static iguanaman.iguanatweakstconstruct.util.HarvestLevels.*;

/**
 * Used to modify the harvest levels and speeds of the tinker tool parts.
 * Has to be used together with the HarvestLevel Tweaks or everything will be messed up. :)
 */
public abstract class TinkerMaterialTweaks {
    /*
    Stat Baseline:
    0 - Stone:      100, 200, 1, 0.5
    1 - Flint:      150, 400, 2, 0.5
    2 - Copper:     200, 500, 2, 1.0
    3 - Iron:       300, 600, 3, 1.1
    4 - Bronze:     400, 650, 3, 1.2
    5 - Diamond:    450, 700, 3, 1.3
    6 - Obsidian:   500, 800, 3, 1.3
    7 - Ardite:     600, 850, 4, 1.7
    8 - Cobalt      800,1000, 4, 1.9
    9 - Manyullyn: 1100, 900, 5, 2.5
     */
    private static float durabilityMod;
    private static float speedMod;

    public static void modifyToolMaterials()
    {
        Log.info("Modifying TConstruct materials");

        // save constant factor to apply to all durabilities and speeds
        durabilityMod = Config.durabilityPercentage / 100f;
        speedMod = Config.miningSpeedPercentage / 100f;

        // modify the base materials added by tinkers construct
        modifyTcon();
        if(Loader.isModLoaded("ExtraTiC"))
            modifyExtraTiC();

        Log.info("Finished modifying TConstruct materials");
    }

    private static void modifyTcon()
    {
        // mining level 0: stone/wood
        updateMaterial(_0_stone, "Paper",       21, 100, 1, 0.3f); // accessory material for extra modifier
        updateMaterial(_0_stone, "Wood",        50, 150, 1, 1.0f); // very weak base, but always a good handle
        updateMaterial(_0_stone, "Stone",      100, 150, 1, 0.3f); // wood mit more durability, but terrible handle.
        updateMaterial(_0_stone, "Cactus",     110, 500, 3, 1.0f); // Preferred weapon material, but also a fast stone-mining material.
        updateMaterial(_0_stone, "Netherrack", 123, 456, 1, 1.2f); // good handle, stonebound. Also fast speed because it's from the nether
        updateMaterial(_0_stone, "BlueSlime", 1200, 150, 1, 1.5f); // Best durability because TConstruct
        updateMaterial(_0_stone, "Slime",      500, 100, 1, 2.0f); // Best handle because hard to get

        // mining level 1: Flint and bone
        updateMaterial(_1_flint, "Flint", 151, 400, 2, 0.5f);  // Head-Material
        updateMaterial(_1_flint, "Bone",  201, 300, 2, 1.08f); // Accessory/Handle Material

        // mining level 2: Copper
        updateMaterial(_2_copper, "Copper", 180, 500, 2, 1.1f); // All around material

        // mining level 3: Iron
        updateMaterial(_3_iron, "Iron", 250, 600, 3, 1.2f); // All around material
        if(TinkerTools.thaumcraftAvailable)
            updateMaterial(_3_iron, "Thaumium", 200, 650, 3, 1.18f); // a bit faster iron, but slightly worse durability. But extra modifier. Awesome.

        // mining level 4: Bronze and better metals
        int bronzeLevel = _4_bronze;
        if(!Config.nerfBronze)
            bronzeLevel++;
        updateMaterial(bronzeLevel, "Bronze", 380, 650, 3, 1.25f); // All around material

        // mining level 5: diamond
        updateMaterial(_5_diamond, "Steel",   400, 700, 3, 1.3f); // All around material
        updateMaterial(_5_diamond, "PigIron", 667, 780, 3, 1.35f); // Very good all around material, because hard to get

        // mining level 6: Obsidian and alumite
        updateMaterial(_6_obsidian, "Obsidian", 89, 650, 2, 0.8f); // Poor mans Obsidian-Level Head, but good accessory for reinforced
        updateMaterial(_6_obsidian, "Alumite", 550, 790, 4, 1.3f); // Good All around material

        // mining level 7: Ardite
        updateMaterial(_7_ardite, "Ardite", 606, 800, 4, 2.1f); // Best Handle Ever, because it has stonebound

        // mining level 8: Cobalt
        updateMaterial(_8_cobalt, "Cobalt", 800, 1100, 4, 1.75f); // Best head ever, highest speed

        // mining level 9: Manyullyn
        updateMaterial(_9_manyullym, "Manyullyn", 1200, 900, 5, 2.5f); // Best weapon ever, pewpew
    }

    private static void modifyExtraTiC()
    {
        int bronzeLevel = _4_bronze;
        if(!Config.nerfBronze)
            bronzeLevel++;

        modifyMetallurgyBasePrecious(bronzeLevel);
        modifyMetallurgyFantasy(bronzeLevel);
        modifyMetallurgyNether(bronzeLevel);
        modifyMetallurgyEnd(bronzeLevel);
        modifyNatura(bronzeLevel);
        modifyMekanism();

        // other mods or extratic itself
        updateMaterial(_1_flint, "Nether Quartz", 101, 300, 1, 0.7f);
        updateMaterial(_7_ardite, "Fairy", 250, 750, 2, 1.2f); // ardite + obsidian + blood
        updateMaterial(_8_cobalt, "Pokefennium", 500, 850, 2, 1.5f); // cobalt + iron + blood

        // Applied Energistics 2
        updateMaterial(_3_iron, "Certus Quartz", 250, 600, 2, 1.4f);

        // Biomes o' Plenty
        updateMaterial(_5_diamond, "Amethyst", 1548, 1500, 5, 1.2f);
    }

    private static void modifyMetallurgyBasePrecious(int bronzeLevel)
    {
        updateMaterial(bronzeLevel, "Hepatizon",      225, 750, 3, 0.8f); // Bronze + Gold, precious-ish material. higher speed
        updateMaterial(bronzeLevel, "Damascus Steel", 515, 500, 4, 0.9f); // Bronze + Iron, harder damage item
        updateMaterial(_4_bronze,   "Angmallen",      300, 600, 3, 1.35f); // Iron + Gold, hard handle with fancyness. high handle modifier therefore!

        updateMaterial(_2_copper,   "Brass",     15,  750, 2, 0.2f); // Copper + Zinc
        updateMaterial(_3_iron,     "Silver",    25,  850, 2, 0.2f);
        updateMaterial(_4_bronze,   "Electrum", 100,  950, 2, 0.75f); // Silver + Gold
        updateMaterial(_6_obsidian, "Platinum", 100, 1300, 2, 0.75f);
    }

    private static void modifyMetallurgyFantasy(int bronzeLevel)
    {
        updateMaterial(_0_stone,     "Prometheum",   100, 300, 1, 0.7f); // Basically Stone.
        updateMaterial(_1_flint,     "Deep Iron",    250, 450, 2, 0.8f); // Reinforced 1, better Flint
        updateMaterial(_2_copper,    "Black Steel",  300, 550, 2, 0.9f); // Infuscolium (hlvl2) + deep iron, Reinforced 2, Better Copper
        updateMaterial(_3_iron,      "Oureclase",    330, 700, 3, 1.0f); // better iron
        updateMaterial(bronzeLevel,  "Astral Silver", 35, 700, 2, 0.35f);
        updateMaterial(_5_diamond,   "Carmot",        50, 800, 2, 0.4f);
        updateMaterial(_5_diamond,   "Mithril",      700, 720, 3, 1.1f);
        updateMaterial(_5_diamond,   "Quicksilver",  600, 880, 4, 1.2f); // Mithril + Silver, enhanced mithril at the cost of durability
        updateMaterial(_6_obsidian,  "Haderoth",     810, 800, 4, 1.3f); // Rubracium (hlvl6) + Mithril
        updateMaterial(_7_ardite,    "Orichalcum",  1010, 900, 4, 1.5f);
        updateMaterial(_7_ardite,    "Celenegil",     600,1400, 3, 0.7f); // Orichalcum + Platin
        updateMaterial(_8_cobalt,    "Adamantine",  1550,1000, 5, 2.3f); // Reinforced 2
        updateMaterial(_9_manyullym, "Atlarus",     1750,1200, 5, 2.5f);
        updateMaterial(_9_manyullym, "Tartarite",   2000,1500, 6, 3.33f); // Adamantine + Atlarus
    }

    private static void modifyMetallurgyNether(int bronzeLevel)
    {
        updateMaterial(bronzeLevel, "Ignatius",     200, 400, 3, 0.3f); // Ignite 1
        updateMaterial(bronzeLevel, "Shadow Iron",  300, 400, 3, 1.2f); // Weakness 1 + Reinforced 1
        updateMaterial(bronzeLevel, "Shadow Steel", 400, 600, 4, 1.3f); // Shadow Iron + Lemurite, Weakness 2 + Reinforced 2
        updateMaterial(_5_diamond,  "Midasium",     111,1111, 4, 1.1f);
        updateMaterial(_5_diamond,  "Vyroxeres",    300, 700, 3, 0.8f); // Poison 1
        updateMaterial(_6_obsidian, "Ceruclase",    500, 707, 4, 1.4f);
        updateMaterial(_6_obsidian, "Inolashite",   900, 800, 3, 0.81f); // Alduorite + Ceruclase, Poison 2
        updateMaterial(_7_ardite,   "Kalendrite",  1000, 500, 4, 1.1f);
        updateMaterial(_7_ardite,   "Amordrine",    900,1400, 3, 1.0f); // Kalendrite + Platinum, Life Steal 1
        updateMaterial(_8_cobalt,   "Vulcanite",   1500,1000, 4, 0.7f); // Ignite 2
        updateMaterial(_8_cobalt,   "Sanguinite",   150, 200, 6, 0.5f); // Wither 1, Combat material. Requires manyullyn to mine, but can't mine it
    }

    private static void modifyMetallurgyEnd(int bronzeLevel)
    {
        updateMaterial(_5_diamond,  "Eximite",     1000, 800, 4, 1.3f);
        updateMaterial(_6_obsidian, "Desichalkos", 1800,1000, 5, 2.75f); // Eximite + Meutoite
    }

    private static void modifyNatura(int bronzelevel)
    {
        updateMaterial(_1_flint,    "Ghostwood",  31, 300, 1, 2.0f);
        updateMaterial(_2_copper,   "Darkwood",  131, 400, 1, 1.5f);
        updateMaterial(_3_iron,     "Fusewood",  250, 600, 2, 1.66f);
        updateMaterial(bronzelevel, "Bloodwood", 350, 700, 3, 1.84f);
    }

    private static void modifyMekanism()
    {
        updateMaterial(_6_obsidian, "Osmium", 500, 1000, 4, 1.3f); // basically better steel
        updateMaterial(_6_obsidian, "Refined Glowstone", 300, 1400, 5, 1.75f); // "enchanted" osmium!
    }


    private static void updateMaterial(int harvestLevel, String matName, int newDurability, int newSpeed, int newAttack, float newHandleModifier)
    {
        ToolMaterial old = TConstructRegistry.getMaterial(matName);
        if(old == null)
        {
            Log.error(String.format("Could not find material for modification: %s", matName));
            return;
        }
        // construct modified material
        int durability = Math.round(newDurability * durabilityMod);
        int speed = Math.round(newSpeed * speedMod);
        // round everything
        ToolMaterial newMaterial = new ToolMaterial(old.materialName, old.displayName, harvestLevel, durability, speed, newAttack, newHandleModifier, old.reinforced, old.stonebound, old.tipStyle, old.ability);

        // replace old material
        Integer id = getMaterialID(old);
        if(id > -1) {
            TConstructRegistry.toolMaterials.remove(id);
            TConstructRegistry.toolMaterialStrings.remove(old.materialName);
            TConstructRegistry.addtoolMaterial(id, newMaterial);
        }
        else
            Log.error("Couldn't find ToolMaterial ID for " + old.name());
    }

    private static Integer getMaterialID(ToolMaterial material)
    {
        for(int k : TConstructRegistry.toolMaterials.keySet())
            if(TConstructRegistry.toolMaterials.get(k) == material)
                return k;

        return -1;
    }
}
