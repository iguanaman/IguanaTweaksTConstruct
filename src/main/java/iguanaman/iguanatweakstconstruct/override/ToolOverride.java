package iguanaman.iguanatweakstconstruct.override;

import iguanaman.iguanatweakstconstruct.harvestlevels.HarvestLevelTweaks;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public final class ToolOverride {
    private ToolOverride() {} // non-instantiable

    public static void doOverride(File configFile) {
        Log.info("Loading Tool Overrides");
        // load the file
        Configuration config = new Configuration(configFile);
        config.load();

        StringBuilder comment = new StringBuilder();
        comment.append("Mining Levels:\n");
        for(int i = 0; i <= HarvestLevels.max; i++)
            comment.append(String.format("\t%d - %s\n", i, HarvestLevels.getHarvestLevelName(i)));

        comment.append("\nYou cannot add different harvest classes, like adding pickaxe to a shovel. Only changes to the created entries will have an effect.");

        config.setCategoryComment(" Info", comment.toString());

        // cycle through all objects. yay.
        for(Object identifier : Item.itemRegistry.getKeys())
        {
            Object o = Item.itemRegistry.getObject(identifier);
            if(!(o instanceof Item))
                continue;

            Item item = (Item)o;
            ItemStack stack = new ItemStack(item); // let's assume there are no sick bastards who use metadata to group tools into a singular id ._.

            String saneCategory = identifier.toString().replace(Configuration.CATEGORY_SPLITTER, "_"); // replace '.' in string.. blah. this sucks

            for(String tool : item.getToolClasses(stack))
            {
                int level = item.getHarvestLevel(stack, tool);
                int newLevel = config.get(saneCategory, tool, level).getInt();

                // update tool
                if(level != newLevel)
                    HarvestLevelTweaks.updateToolHarvestLevel(item, tool, newLevel);
            }
        }

        if(config.hasChanged())
            config.save();
    }
}
