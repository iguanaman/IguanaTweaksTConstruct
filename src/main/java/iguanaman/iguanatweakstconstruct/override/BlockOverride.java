package iguanaman.iguanatweakstconstruct.override;

import iguanaman.iguanatweakstconstruct.harvestlevels.HarvestLevelTweaks;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.util.LinkedList;

public class BlockOverride implements IOverride {
    @Override
    public void createDefault(Configuration config) {
        Log.info("Creating Block Default File");

        // oredict entries
        for(int i = 0; i < HarvestLevelTweaks.allOreDicLevels.length; i++)
            for(String entry : HarvestLevelTweaks.allOreDicLevels[i])
                if(entry != null)
                    config.get("generaloredict", entry, i);

        // blocks can not really be added automatically because derp
        for(Object key : Block.blockRegistry.getKeys())
        {
            Block block = (Block) Block.blockRegistry.getObject(key);

            int meta = -1;
            LinkedList<Integer> metas = new LinkedList<Integer>();
            while(++meta < 16)
            {
                if(block.getHarvestLevel(meta) == -1)
                    continue;
                try {
                    String s = new ItemStack(block, 1, meta).getDisplayName();
                    if (s == null || s.isEmpty())
                        continue;
                } catch(Exception e) // bad practice to catch exception, but it ensures that mc doesn't crash if modders do weird stuff
                {
                    continue;
                }

                metas.add(meta);
            }

            if(metas.isEmpty())
                continue;

            // write it down
            for(Integer m : metas)
                config.get("blocks", key.toString() + ":" + m, block.getHarvestLevel(m));
        }
    }

    @Override
    public void processConfig(Configuration config) {
        Log.info("Loading Block Overrides");

        StringBuilder comment = new StringBuilder();
        comment.append("Mining Levels:\n");
        for (int i = 0; i <= HarvestLevels.max; i++)
            comment.append(String.format("\t%d - %s\n", i, HarvestLevels.getHarvestLevelName(i)));

        config.setCategoryComment(" Info", comment.toString());

        config.setCategoryComment("blocks", "Adapt harvestability of specific blocks here.\nFormat: <mod-id>:<name>:<metadata>");
        config.setCategoryComment("generaloredict", "Adapt harvestability of all oredicted blocks here. The name is just the postfix, so instead of 'oreIron' you use 'Iron' and it changes all the iron oredcits it can find. like oreIron, denseoreIron, blockIron,...");
        config.setCategoryComment("oredict", "Adapt harvestability of specific oredicted blocks here. The name must be exact, so use 'oreIron' instead of 'Iron' here. Overwrites harvestlevels set by generaloredict.");

        // general oredict entries first
        ConfigCategory cat = config.getCategory("generaloredict");
        for(Property prop : cat.values()) {
            HarvestLevelTweaks.modifyOredictBlock(prop.getName(), prop.getInt());
        }

        // specific oredict entries next
        cat = config.getCategory("oredict");
        for(Property prop : cat.values()) {
            for(ItemStack stack : OreDictionary.getOres(prop.getName()))
                HarvestLevelTweaks.modifyBlock(stack, prop.getInt());
        }

        // block specific harvest levels.. urgh
        cat = config.getCategory("blocks");
        for(Property prop : cat.values()) {
            // dissect the name.
            String[] foo = prop.getName().split(":");
            String metaStr = foo[foo.length-1];
            String blockStr = prop.getName().substring(0, prop.getName().lastIndexOf(':'));
            int meta;
            // check if the meta-str actually is a number
            try {
                meta = Integer.valueOf(metaStr);
            } catch(NumberFormatException e)
            {
                // no metadata string present. Is required.
                continue;
            }

            // find the block
            if(!Block.blockRegistry.containsKey(blockStr))
                continue;

            Block block = (Block) Block.blockRegistry.getObject(blockStr);
            HarvestLevelTweaks.modifyBlock(new ItemStack(block, 1, meta), prop.getInt());
        }
    }
}
