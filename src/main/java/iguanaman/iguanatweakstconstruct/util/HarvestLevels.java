package iguanaman.iguanatweakstconstruct.util;

import net.minecraft.util.StatCollector;

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

        switch (num)
        {
            case 0: return GRAY + StatCollector.translateToLocal("mininglevel.stone");
            case 1: return GOLD + StatCollector.translateToLocal("mininglevel.copper");
            case 2: return DARK_RED + StatCollector.translateToLocal("mininglevel.iron");
            case 3: return WHITE + StatCollector.translateToLocal("mininglevel.tin");
            case 4: return RED + StatCollector.translateToLocal("mininglevel.redstone");
            case 5: return LIGHT_PURPLE + StatCollector.translateToLocal("mininglevel.obsidian");
            case 6: return RED + StatCollector.translateToLocal("mininglevel.ardite");
            case 7: return BLUE + StatCollector.translateToLocal("mininglevel.cobalt");
            case 8: return DARK_PURPLE + StatCollector.translateToLocal("mininglevel.manyullyn");
            case 9: return DARK_PURPLE + StatCollector.translateToLocal("mininglevel.manyullyn") + LIGHT_PURPLE + "+";
            default: return String.format("%s%s%s%s%s%s", OBFUSCATED ,OBFUSCATED, OBFUSCATED, OBFUSCATED, OBFUSCATED, OBFUSCATED);
        }
    }

    public static String getVanillaHarvestLevelName (int num)
    {
        switch (num)
        {
            case 0: return GRAY + StatCollector.translateToLocal("mininglevel.stone");
            case 1: return DARK_RED + StatCollector.translateToLocal("mininglevel.iron");
            case 2: return RED + StatCollector.translateToLocal("mininglevel.redstone");
            case 3: return LIGHT_PURPLE + StatCollector.translateToLocal("mininglevel.obsidian");
            case 4: return BLUE + StatCollector.translateToLocal("mininglevel.cobalt");
            case 5: return DARK_PURPLE + StatCollector.translateToLocal("mininglevel.manyullyn");
            case 6: return DARK_PURPLE + StatCollector.translateToLocal("mininglevel.manyullyn") + LIGHT_PURPLE + "+";
            default: return String.format("%s%s%s%s%s%s", OBFUSCATED ,OBFUSCATED, OBFUSCATED, OBFUSCATED, OBFUSCATED, OBFUSCATED);
        }
    }


}
