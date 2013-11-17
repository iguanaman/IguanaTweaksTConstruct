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
    	if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedWoodParts.contains(1) 
    			|| IguanaConfig.restrictedFlintParts.contains(2) || IguanaConfig.restrictedWoodParts.contains(9))
    		RemoveVanillaTool(Item.pickaxeStone);
    	
    	if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedWoodParts.contains(1) || IguanaConfig.restrictedFlintParts.contains(3))
    		RemoveVanillaTool(Item.shovelStone);
    	
    	if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedWoodParts.contains(1) || IguanaConfig.restrictedFlintParts.contains(4))
    		RemoveVanillaTool(Item.axeStone);
    	
    	if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedWoodParts.contains(1) || IguanaConfig.restrictedFlintParts.contains(5) || IguanaConfig.restrictedWoodParts.contains(6))
    		RemoveVanillaTool(Item.swordStone);
	}
    
    public static void RemoveVanillaTool(Item item)
    {
        RecipeRemover.removeAnyRecipe(new ItemStack(item));
    	ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).removeItem(new ItemStack(item));
    }
}
