package iguanaman.iguanatweakstconstruct.reference;

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
        if (Config.pickaxeBoostRequired && num > 1) --num;
        switch (num)
        {
        case 0: return "\u00a77Stone";
        case 1: return "\u00a76Copper";
        case 2: return "\u00a74Iron";
        case 3: return "\u00a7fTin";
        case 4: return "\u00a7bDiamond";
        case 5: return "\u00a7cArdite";
        case 6: return "\u00a79Cobalt";
        case 7: return "\u00a75Manyullyn";
        default: return "\u00a7k\u00a7k\u00a7k\u00a7k\u00a7k\u00a7k"; // does anybody know why this repeats itself 6 times? o_O
        }
    }
}
