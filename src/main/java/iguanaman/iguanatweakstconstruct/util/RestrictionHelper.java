package iguanaman.iguanatweakstconstruct.util;

import net.minecraft.item.ItemStack;
import tconstruct.library.util.IToolPart;
import tconstruct.tools.TinkerTools;

import java.util.HashMap;
import java.util.Map;

// todo: refactor this properly with a Map or something when i need to restrict more than vanilla
public abstract class RestrictionHelper {
    // the tool parts
    public static final String[] patternNames = new String[] { "ingot", "rod", "pickaxe", "shovel", "axe", "swordblade", "largeguard", "mediumguard", "crossbar", "binding", "frypan", "sign",
            "knifeblade", "chisel", "largerod", "toughbinding", "largeplate", "broadaxe", "scythe", "excavator", "largeblade", "hammerhead", "fullguard", "bowstring", "fletching", "arrowhead" };

    public static String getPatternName(ItemStack pattern)
    {
        if(pattern.getItem() == TinkerTools.woodPattern) {
            if (pattern.getItemDamage() > patternNames.length)
                return "";

            return patternNames[pattern.getItemDamage()];
        }
        return null;
    }
}
