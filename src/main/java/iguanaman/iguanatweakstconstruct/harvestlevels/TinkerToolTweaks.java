package iguanaman.iguanatweakstconstruct.harvestlevels;

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
public abstract class TinkerToolTweaks {
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
        // todo: modify mod parts.
        // modify ExtraTic/Metallurgy

        Log.info("Finished modifying TConstruct materials");
    }

    private static void modifyTcon()
    {
        // mining level 0: stone/wood
        updateMaterial(_0_stone, TConstructRegistry.getMaterial("Wood"));
        updateMaterial(_0_stone, TConstructRegistry.getMaterial("Stone"));
        updateMaterial(_0_stone, TConstructRegistry.getMaterial("Cactus"));
        updateMaterial(_0_stone, TConstructRegistry.getMaterial("Paper"));
        updateMaterial(_0_stone, TConstructRegistry.getMaterial("Slime"));
        updateMaterial(_0_stone, TConstructRegistry.getMaterial("BlueSlime"));
        updateMaterial(_0_stone, TConstructRegistry.getMaterial("Netherrack"));

        // mining level 1: Flint and bone
        updateMaterial(_1_flint, TConstructRegistry.getMaterial("Flint"));
        updateMaterial(_1_flint, TConstructRegistry.getMaterial("Bone"));

        // mining level 2: Copper
        updateMaterial(_2_copper, TConstructRegistry.getMaterial("Copper"));

        // mining level 3: Iron
        updateMaterial(_3_iron, TConstructRegistry.getMaterial("Iron"));
        if(TinkerTools.thaumcraftAvailable)
            updateMaterial(_3_iron, TConstructRegistry.getMaterial("Thaumium"));

        // mining level 4: Bronze and better metals
        updateMaterial(_4_bronze, TConstructRegistry.getMaterial("Bronze"));
        updateMaterial(_4_bronze, TConstructRegistry.getMaterial("PigIron"));

        // mining level 5: diamond
        updateMaterial(_5_diamond, TConstructRegistry.getMaterial("Steel"));

        // mining level 6: Obsidian and alumite
        updateMaterial(_6_obsidian, TConstructRegistry.getMaterial("Obsidian"));
        updateMaterial(_6_obsidian, TConstructRegistry.getMaterial("Alumite"));

        // mining level 7: Ardite
        updateMaterial(_7_ardite, TConstructRegistry.getMaterial("Ardite"));

        // mining level 8: Cobalt
        updateMaterial(_8_cobalt, TConstructRegistry.getMaterial("Cobalt"));

        // mining level 9: Manyullyn
        updateMaterial(_9_manyullym, TConstructRegistry.getMaterial("Manyullyn"));
    }

    private static void updateMaterial(int harvestLevel, ToolMaterial old)
    {
        // construct modified material
        int durability = Math.round(old.durability * durabilityMod);
        int speed = Math.round(old.miningspeed * speedMod);
        ToolMaterial newMaterial = new ToolMaterial(old.materialName, old.displayName, harvestLevel, durability, speed, old.attack, old.handleModifier, old.reinforced, old.stonebound, old.tipStyle, old.ability);

        // replace old material
        Integer id = getMaterialID(old);
        if(id > -1) {
            TConstructRegistry.toolMaterials.remove(id);
            TConstructRegistry.toolMaterialStrings.remove(old.materialName);
            // todo: uncomment once the bug that it always adds null is fixed
            //TConstructRegistry.addtoolMaterial(id, newMaterial);
            if(TConstructRegistry.toolMaterials.containsKey(id))
                throw new IllegalArgumentException("[TCon API] Material ID " + id + " is already occupied");
            TConstructRegistry.toolMaterials.put(id, newMaterial);
            TConstructRegistry.toolMaterialStrings.put(newMaterial.name(), newMaterial);

            if(Config.logToolMaterialChanges)
                Log.debug(String.format("Modified tool material %d: %s (level: %d, durability: %d, speed: %d)", id, old.name(), harvestLevel, durability, speed));
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
