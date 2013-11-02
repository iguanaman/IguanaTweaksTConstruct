package iguanaman.iguanatweakstconstruct;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

public class RemoveVanillaTools {

	public static void init() 
	{
        //Remove vanilla tools
		IguanaLog.log("Removing vanilla tool recipes");
    	
    	//Wood
    	if (IguanaConfig.restrictedWoodParts.contains(1) || IguanaConfig.restrictedWoodParts.contains(2) || IguanaConfig.restrictedWoodParts.contains(9))
    		RemoveVanillaTool(Item.pickaxeWood);
    	
    	if (IguanaConfig.restrictedWoodParts.contains(1) || IguanaConfig.restrictedWoodParts.contains(3))
    		RemoveVanillaTool(Item.shovelWood);
    	
    	if (IguanaConfig.restrictedWoodParts.contains(1) || IguanaConfig.restrictedWoodParts.contains(4))
    		RemoveVanillaTool(Item.axeWood);
    	
    	if (IguanaConfig.restrictedWoodParts.contains(1) || IguanaConfig.restrictedWoodParts.contains(5) || IguanaConfig.restrictedWoodParts.contains(6))
    		RemoveVanillaTool(Item.swordWood);
    	
    	//Stone
    	if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedStoneParts.contains(1) 
    			|| IguanaConfig.restrictedStoneParts.contains(2) || IguanaConfig.restrictedStoneParts.contains(9))
    		RemoveVanillaTool(Item.pickaxeStone);
    	
    	if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedStoneParts.contains(1) || IguanaConfig.restrictedStoneParts.contains(3))
    		RemoveVanillaTool(Item.shovelStone);
    	
    	if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedStoneParts.contains(1) || IguanaConfig.restrictedStoneParts.contains(4))
    		RemoveVanillaTool(Item.axeStone);
    	
    	if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedStoneParts.contains(1) || IguanaConfig.restrictedStoneParts.contains(5) || IguanaConfig.restrictedStoneParts.contains(6))
    		RemoveVanillaTool(Item.swordStone);
    	
    	if (!IguanaConfig.allowStoneTools) RemoveVanillaTool(Item.hoeStone);
	}
    
    public static void RemoveVanillaTool(Item item)
    {
        RecipeRemover.removeAnyRecipe(new ItemStack(item));
    	ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).removeItem(new ItemStack(item));
    }
}
