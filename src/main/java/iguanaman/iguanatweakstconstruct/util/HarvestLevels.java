package iguanaman.iguanatweakstconstruct.util;

import static net.minecraft.util.EnumChatFormatting.*;

// strength of the tool-material. stone == strength of a stone pick etc.
public abstract class HarvestLevels {
    public static int _0_stone = 0;
    public static int _1_flint = 1;
    public static int _2_copper = 2;
    public static int _3_iron = 3;
    public static int _4_bronze = 4;
    public static int _5_diamond = 5;
    public static int _6_obsidian = 6;
    public static int _7_ardite = 7;
    public static int _8_cobalt = 8;
    public static int _9_manyullym = 9;

    public static int max = 8;

    private static boolean vanilla = false;

    // needed if HarvestLevels module is deactivated to achieve vanilla mining levels
    public static void adjustToVanillaLevels()
    {
        _1_flint = 1;
        _2_copper = 1;
        _3_iron = 2;
        _4_bronze = 2;
        _5_diamond = 3;
        _6_obsidian = 4;
        _7_ardite = 5;
        _8_cobalt = 6;
        _9_manyullym = 7;

        max = 6;

        vanilla = true;
    }

    public static String getHarvestLevelName (int num)
    {
        if(vanilla) return getVanillaHarvestLevelName(num);

        //if (Config.pickaxeBoostRequired && num > 1) --num;
        switch (num)
        {
        case 0: return GRAY + "Stone";
        case 1: return GOLD + "Copper";
        case 2: return DARK_RED + "Iron";
        case 3: return WHITE + "Tin";
        case 4: return RED + "Redstone";
        case 5: return LIGHT_PURPLE + "Obsidian";
        case 6: return RED + "Ardite";
        case 7: return BLUE + "Cobalt";
        case 8: return DARK_PURPLE + "Manyullyn";
        case 9: return DARK_PURPLE + "Manyullyn" + LIGHT_PURPLE + "+";
        default: return String.format("%s%s%s%s%s%s", OBFUSCATED ,OBFUSCATED, OBFUSCATED, OBFUSCATED, OBFUSCATED, OBFUSCATED);
        }
    }

    public static String getVanillaHarvestLevelName (int num)
    {
        //if (Config.pickaxeBoostRequired && num > 1) --num;
        switch (num)
        {
            case 0: return GRAY + "Stone";
            case 1: return DARK_RED + "Iron";
            case 2: return RED + "Redstone";
            case 3: return LIGHT_PURPLE + "Obsidian";
            case 4: return BLUE + "Cobalt";
            case 5: return DARK_PURPLE + "Manyullyn";
            case 6: return DARK_PURPLE + "Manyullyn" + LIGHT_PURPLE + "+";
            default: return String.format("%s%s%s%s%s%s", OBFUSCATED ,OBFUSCATED, OBFUSCATED, OBFUSCATED, OBFUSCATED, OBFUSCATED);
        }
    }


}
