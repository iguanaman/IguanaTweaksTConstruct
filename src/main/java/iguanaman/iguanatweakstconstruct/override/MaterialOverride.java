package iguanaman.iguanatweakstconstruct.override;

import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class MaterialOverride {
    public static void doOverride(File configFile)
    {
        Log.info("Loading Material Overrides");
        // load the file
        Configuration config = new Configuration(configFile);
        config.load();

        StringBuilder comment = new StringBuilder();
        comment.append("Mining Levels:\n");
        for(int i = 0; i <= HarvestLevels.max; i++)
            comment.append(String.format("\t%d - %s\n", i, HarvestLevels.getHarvestLevelName(i)));

        comment.append("\n");
        comment.append("Stonebound and Jagged are both in the 'shoddy' property. A positive number is stonebound, a negative number jagged.\n");
        comment.append("\n");
        comment.append("Possible Colors:\n");
        for(EnumChatFormatting format : EnumChatFormatting.values())
            comment.append(String.format("\t%s\n", format.getFriendlyName()));


        config.setCategoryComment(" Info", comment.toString());



        // cycle through all materials
        HashMap<Integer, ToolMaterial> newMaterials = new HashMap<Integer, ToolMaterial>();
        for(Map.Entry<Integer, ToolMaterial> entry : TConstructRegistry.toolMaterials.entrySet())
        {
            ToolMaterial mat = entry.getValue();
            String category = mat.materialName;

            // get material stats
            int harvestLevel = config.get(category, "harvestLevel", mat.harvestLevel()).getInt();
            int durability = config.get(category, "durability", mat.durability()).getInt();
            int miningspeed = config.get(category, "miningSpeed", mat.toolSpeed()).getInt();
            int attack = config.get(category, "attack", mat.attack()).getInt();
            float handleModifier = (float)config.get(category, "handleModifier", mat.handleDurability()).getDouble();
            int reinforced = config.get(category, "reinforced", mat.reinforced()).getInt();
            float stonebound = (float)config.get(category, "shoddy", mat.shoddy()).getDouble();
            String tipStyle; // enum
            String oldStyle = stringToEnum(mat.style()).getFriendlyName();
            tipStyle = config.get(category, "color", oldStyle).getString();
            // convert the enum string to the actual color
            EnumChatFormatting newStyle = EnumChatFormatting.getValueByName(tipStyle);
            if(newStyle != null)
                tipStyle = newStyle.toString();
            else
                tipStyle = mat.style();

            // reconstruct the material
            ToolMaterial matOverride = new ToolMaterial(mat.materialName, harvestLevel, durability, miningspeed, attack, handleModifier, reinforced, stonebound, tipStyle, mat.ability());


            // we cannot replace the material while cycling through, since it'd cause a ConcurrentModificationException
            newMaterials.put(entry.getKey(), matOverride);
        }

        TConstructRegistry.toolMaterials = newMaterials;

        if(config.hasChanged())
            config.save();
    }

    private static EnumChatFormatting stringToEnum(String s)
    {
        for(EnumChatFormatting color : EnumChatFormatting.values())
            if(color.toString().equals(s))
                return color;

        return EnumChatFormatting.GRAY;
    }
}
