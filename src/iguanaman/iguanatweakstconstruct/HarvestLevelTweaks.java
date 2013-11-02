package iguanaman.iguanatweakstconstruct;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.common.TContent;
import tconstruct.library.TConstructRegistry;
import cpw.mods.fml.common.FMLLog;

public class HarvestLevelTweaks {
	
	public static int boostMod = 0;

    // HarvestLevels
    public static String[][] oreDictLevels = {
    		{},
    		{"Copper", "Tetrahedrite", "Aluminum", "Aluminium", "NaturalAluminum", "AluminumBrass", "Shard", "Bauxite", "Zinc"},
    		{"Iron", "Cassiterite", "Pyrite"},
			{"Tin", "Gold", "Lapis", "Steel", "Galena", "Nickel", "Invar", "Electrum", "Sphalerite"},
			{"Diamond", "Emerald", "Redstone", "Ruby", "Sapphire", "Cinnabar", "Quartz", "Silver", 
				"Obsidian", "CertusQuartz", "Tungstate", "Sodalite", "GreenSapphire", "BlackGranite", "RedGranite"},
			{"Ardite", "Uranium", "Olivine", "Sheldonite", "Osmium", "Platinum"},
			{"Cobalt", "Iridium", "Cooperite", "Titanium"},
			{"Manyullyn"}
    };

	public static void init() 
	{
        if (IguanaConfig.pickaxeBoostRequired) boostMod = 1;
        
        List[] harvestLevelIds = { 
        		IguanaConfig.harvestLevel0Ids, IguanaConfig.harvestLevel1Ids, IguanaConfig.harvestLevel2Ids, 
        		IguanaConfig.harvestLevel3Ids, IguanaConfig.harvestLevel4Ids, IguanaConfig.harvestLevel5Ids, 
        		IguanaConfig.harvestLevel6Ids, IguanaConfig.harvestLevel7Ids 
        		};
        
        for (int i = 0; i < oreDictLevels.length; ++i)
        {
        	int level = i;
        	if (i > 1) level += boostMod;
        	
            for (String materialName : oreDictLevels[i]) {
	            for (ItemStack oreStack : OreDictionary.getOres("ore" + materialName)) SetHarvestLevel(oreStack, level);
	            for (ItemStack oreStack : OreDictionary.getOres("oreNether" + materialName)) SetHarvestLevel(oreStack, level);
	            for (ItemStack oreStack : OreDictionary.getOres("block" + materialName)) SetHarvestLevel(oreStack, level);
	            for (ItemStack oreStack : OreDictionary.getOres("stone" + materialName)) SetHarvestLevel(oreStack, level);
            }
            
            for (int blockId : (List<Integer>)harvestLevelIds[i]) SetHarvestLevel(new ItemStack(blockId, 1, 0), level);
        }
        
    	IguanaLog.log("Modifying required harvest levels of vanilla ores");
        MinecraftForge.setBlockHarvestLevel(Block.obsidian,     "pickaxe", TConstructRegistry.getMaterial("Bronze").harvestLevel() + boostMod);
        MinecraftForge.setBlockHarvestLevel(Block.blockDiamond, "pickaxe", TConstructRegistry.getMaterial("Bronze").harvestLevel() + boostMod);
        MinecraftForge.setBlockHarvestLevel(Block.blockGold,    "pickaxe", TConstructRegistry.getMaterial("Iron").harvestLevel() + boostMod);
        MinecraftForge.setBlockHarvestLevel(Block.blockIron,   "pickaxe", TConstructRegistry.getMaterial("Copper").harvestLevel() + boostMod);
        MinecraftForge.setBlockHarvestLevel(Block.blockLapis,   "pickaxe", TConstructRegistry.getMaterial("Iron").harvestLevel() + boostMod);
        MinecraftForge.setBlockHarvestLevel(Block.oreRedstoneGlowing, "pickaxe", TConstructRegistry.getMaterial("Bronze").harvestLevel() + boostMod);

        MinecraftForge.setBlockHarvestLevel(TContent.oreGravel, 0, "shovel", TConstructRegistry.getMaterial("Copper").harvestLevel() + boostMod);
        MinecraftForge.setBlockHarvestLevel(TContent.oreGravel, 1, "shovel", TConstructRegistry.getMaterial("Iron").harvestLevel() + boostMod);
        MinecraftForge.setBlockHarvestLevel(TContent.oreGravel, 2, "shovel", TConstructRegistry.getMaterial("Flint").harvestLevel());
        MinecraftForge.setBlockHarvestLevel(TContent.oreGravel, 3, "shovel", TConstructRegistry.getMaterial("Iron").harvestLevel() + boostMod);
        MinecraftForge.setBlockHarvestLevel(TContent.oreGravel, 4, "shovel", TConstructRegistry.getMaterial("Flint").harvestLevel());
        MinecraftForge.setBlockHarvestLevel(TContent.oreGravel, 5, "shovel", TConstructRegistry.getMaterial("Ardite").harvestLevel() + boostMod);
	}
    
    public static void SetHarvestLevel(ItemStack oreStack, int level)
    {
    	if (oreStack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
    	{
    		MinecraftForge.setBlockHarvestLevel(Block.blocksList[oreStack.itemID], "pickaxe", level);
    		FMLLog.warning("IguanaTweaksTConstruct: Setting required harvest level of " + oreStack.getUnlocalizedName() + " to " + level);
    	}
    	else
    	{
    		MinecraftForge.setBlockHarvestLevel(Block.blocksList[oreStack.itemID], oreStack.getItemDamage(), "pickaxe", level);
    		FMLLog.warning("IguanaTweaksTConstruct: Setting required harvest level of " + oreStack.getUnlocalizedName() + ":" + oreStack.getItemDamage() + " to " + level);
    	}
    }
	
}
