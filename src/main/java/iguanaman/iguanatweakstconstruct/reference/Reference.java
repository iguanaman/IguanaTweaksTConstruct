package iguanaman.iguanatweakstconstruct.reference;

import static net.minecraft.util.EnumChatFormatting.*;

import static iguanaman.iguanatweakstconstruct.harvestlevels.IguanaHarvestLevelTweaks.HarvestLevels.*;

public class Reference {
    public static final String MOD_ID = "IguanaTweaksTConstruct";
    public static final String MOD_NAME = "Iguana Tweaks for Tinker's Construct";
    public static final String TCON_MOD_ID = "TConstruct";

    public static final String PULSE_LEVELING = "ToolLeveling";
    public static final String PULSE_HARVESTTWEAKS = "HarvestLevelTweaks";

    public static final String PROXY_CLIENT_CLASS = "iguanaman.iguanatweakstconstruct.proxy.ClientProxy";
    public static final String PROXY_SERVER_CLASS = "iguanaman.iguanatweakstconstruct.proxy.ServerProxy";

    public static String getHarvestLevelName (int num)
    {
        //if (Config.pickaxeBoostRequired && num > 1) --num;
        switch (num)
        {
        case _0_stone:    return GRAY + "Stone";
        case _1_flint:    return GOLD + "Copper";
        case _2_copper:   return DARK_RED + "Iron";
        case _3_iron:     return WHITE + "Tin";
        case _4_bronze:   return AQUA + "Diamond";
        case _5_diamond:  return LIGHT_PURPLE + "Obsidian";
        case _6_obsidian: return RED + "Ardite";
        case _7_ardite:   return BLUE + "Cobalt";
        case _8_cobalt:   return DARK_PURPLE + "Manyullyn";
        default: return "\u00a7k\u00a7k\u00a7k\u00a7k\u00a7k\u00a7k";
        }
    }
}
