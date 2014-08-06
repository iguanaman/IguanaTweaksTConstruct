package iguanaman.iguanatweakstconstruct.util;

import net.minecraft.item.ItemStack;
import tconstruct.tools.TinkerTools;

// todo: refactor this properly with a Map or something when i need to restrict more than vanilla
public abstract class RestrictionHelper {
    public static String getPatternName(ItemStack pattern)
    {
        if(pattern.getItem() == TinkerTools.woodPattern) {
            if (pattern.getItemDamage() > patternNames.length)
                return "";

            return patternNames[pattern.getItemDamage()];
        }
        return null;
    }

    // the tool parts
    public static final String[] patternNames = new String[] {
            "ingot",        //  0
            "rod",          //  1
            "pickaxe",      //  2
            "shovel",       //  3
            "axe",          //  4
            "swordblade",   //  5
            "largeguard",   //  6
            "mediumguard",  //  7
            "crossbar",     //  8
            "binding",      //  9
            "frypan",       // 10
            "sign",         // 11
            "knifeblade",   // 12
            "chisel",       // 13
            "largerod",     // 14
            "toughbinding", // 15
            "largeplate",   // 16
            "broadaxe",     // 17
            "scythe",       // 18
            "excavator",    // 19
            "largeblade",   // 20
            "hammerhead",   // 21
            "fullguard",    // 22
            "bowstring",    // 23
            "fletching",    // 24
            "arrowhead"     // 25
    };

    public static final String[] defaultRestrictions = new String[] {
            // Wood:
            "Wood:pickaxe",
            "Wood:axe",
            "Wood:swordblade",
            "Wood:largeguard",
            "Wood:mediumguard",
            "Wood:frypan",
            "Wood:chisel",
            "Wood:largerod",
            "Wood:toughbinding",
            "Wood:largeplate",
            "Wood:broadaxe",
            "Wood:scythe",
            "Wood:excavator",
            "Wood:largeblade",
            "Wood:hammerhead",
            "Wood:fullguard",
            "Wood:bowstring",
            "Wood:fletching",

            // Flint:
            "Flint:rod",
            "Flint:swordblade",
            "Flint:largeguard",
            "Flint:mediumguard",
            "Flint:crossbar",
            "Flint:binding",
            "Flint:frypan",
            "Flint:sign",
            "Flint:largerod",
            "Flint:toughbinding",
            "Flint:largeplate",
            "Flint:broadaxe",
            "Flint:scythe",
            "Flint:excavator",
            "Flint:largeblade",
            "Flint:hammerhead",
            "Flint:fullguard",
            "Flint:bowstring",
            "Flint:fletching",

            // Bone
            "Bone:pickaxe",
            "Bone:swordblade",
            "Bone:largeguard",
            "Bone:mediumguard",
            "Bone:binding",
            "Bone:frypan",
            "Bone:sign",
            "Bone:chisel",
            "Bone:largerod",
            "Bone:toughbinding",
            "Bone:largeplate",
            "Bone:broadaxe",
            "Bone:scythe",
            "Bone:excavator",
            "Bone:largeblade",
            "Bone:hammerhead",
            "Bone:fullguard",
            "Bone:bowstring",
            "Bone:fletching",

            // Cactus
            "Cactus:pickaxe",
            "Cactus:shovel",
            "Cactus:axe",
            "Cactus:swordblade",
            "Cactus:largeguard",
            "Cactus:mediumguard",
            "Cactus:crossbar",
            "Cactus:frypan",
            "Cactus:sign",
            "Cactus:knifeblade",
            "Cactus:chisel",
            "Cactus:largerod",
            "Cactus:toughbinding",
            "Cactus:largeplate",
            "Cactus:broadaxe",
            "Cactus:scythe",
            "Cactus:excavator",
            "Cactus:largeblade",
            "Cactus:hammerhead",
            "Cactus:fullguard",
            "Cactus:bowstring",
            "Cactus:fletching",
            "Cactus:arrowhead",

            // Paper:
            "Paper:pickaxe",
            "Paper:shovel",
            "Paper:axe",
            "Paper:swordblade",
            "Paper:largeguard",
            "Paper:mediumguard",
            "Paper:crossbar",
            "Paper:frypan",
            "Paper:sign",
            "Paper:knifeblade",
            "Paper:chisel",
            "Paper:largerod",
            "Paper:toughbinding",
            "Paper:largeplate",
            "Paper:broadaxe",
            "Paper:scythe",
            "Paper:excavator",
            "Paper:largeblade",
            "Paper:hammerhead",
            "Paper:fullguard",
            "Paper:bowstring",
            "Paper:arrowhead",

            // Slime:
            "Slime:pickaxe",
            "Slime:shovel",
            "Slime:axe",
            "Slime:swordblade",
            "Slime:largeguard",
            "Slime:mediumguard",
            "Slime:crossbar",
            "Slime:frypan",
            "Slime:sign",
            "Slime:knifeblade",
            "Slime:chisel",
            "Slime:largerod",
            "Slime:toughbinding",
            "Slime:largeplate",
            "Slime:broadaxe",
            "Slime:scythe",
            "Slime:excavator",
            "Slime:largeblade",
            "Slime:hammerhead",
            "Slime:fullguard",
            "Slime:bowstring",
            "Slime:fletching",
            "Slime:arrowhead"
    };
}
