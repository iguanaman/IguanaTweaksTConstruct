package iguanaman.iguanatweakstconstruct.util;

import cpw.mods.fml.common.Loader;

public abstract class ModSupportHelper {
    public static final boolean tiCTooltips = Loader.isModLoaded("TiCTooltips");
    public static final boolean ExtraTiC = Loader.isModLoaded("ExtraTiC");
    public static final boolean BiomesOPlenty = Loader.isModLoaded("BiomesOPlenty");
    public static final boolean Mekanism = Loader.isModLoaded("Mekanism");
    public static final boolean Metallurgy = Loader.isModLoaded("Metallurgy");
    public static final boolean Natura = Loader.isModLoaded("Natura");
    public static final boolean AppliedEnergistics2 = Loader.isModLoaded("appliedenergistics2");

    public static final boolean ThermalFoundation = Loader.isModLoaded("ThermalFoundation");
}
