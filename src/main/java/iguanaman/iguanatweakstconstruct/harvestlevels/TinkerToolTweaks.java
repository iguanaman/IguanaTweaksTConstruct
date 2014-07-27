package iguanaman.iguanatweakstconstruct.harvestlevels;

import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.Log;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.tools.TinkerTools;

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
        updateMaterial(0, TConstructRegistry.getMaterial("Wood"));
        updateMaterial(0, TConstructRegistry.getMaterial("Stone"));
        updateMaterial(0, TConstructRegistry.getMaterial("Cactus"));
        updateMaterial(0, TConstructRegistry.getMaterial("Paper"));
        updateMaterial(0, TConstructRegistry.getMaterial("Slime"));
        updateMaterial(0, TConstructRegistry.getMaterial("BlueSlime"));
        updateMaterial(0, TConstructRegistry.getMaterial("Netherrack"));

        // mining level 1: Flint and bone
        updateMaterial(1, TConstructRegistry.getMaterial("Flint"));
        updateMaterial(1, TConstructRegistry.getMaterial("Bone"));

        // mining level 2: Copper
        updateMaterial(2, TConstructRegistry.getMaterial("Copper"));

        // mining level 3: Iron
        updateMaterial(3, TConstructRegistry.getMaterial("Iron"));
        if(TinkerTools.thaumcraftAvailable)
            updateMaterial(3, TConstructRegistry.getMaterial("Thaumium"));

        // mining level 4: Bronze and better metals
        updateMaterial(4, TConstructRegistry.getMaterial("Bronze"));
        updateMaterial(4, TConstructRegistry.getMaterial("Steel"));
        updateMaterial(4, TConstructRegistry.getMaterial("PigIron"));

        // mining level 5: Obsidian and alumite
        updateMaterial(5, TConstructRegistry.getMaterial("Obsidian"));
        updateMaterial(5, TConstructRegistry.getMaterial("Alumite"));

        // mining level 6: Ardite
        updateMaterial(6, TConstructRegistry.getMaterial("Ardite"));

        // mining level 7: Cobalt
        updateMaterial(7, TConstructRegistry.getMaterial("Cobalt"));

        // mining level 8: Manyullyn
        updateMaterial(8, TConstructRegistry.getMaterial("Manyullyn"));
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

            Log.trace(String.format("Modified tool material %d: %s (level: %d, durability: %d, speed: %d)", id, old.name(), harvestLevel, durability, speed));
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
