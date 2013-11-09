package iguanaman.iguanatweakstconstruct.util;

import iguanaman.iguanatweakstconstruct.IguanaLog;
import tconstruct.TConstruct;
import tconstruct.common.TContent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ICraftingHandler;

public class IguanaPatternCraftingHandler implements ICraftingHandler {

    @Override
    public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
    	IguanaLog.log("test");
    	if (item.getItem() == TContent.woodPattern)
    	{
            for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
            {
                ItemStack inSlot = craftMatrix.getStackInSlot(i);
                if (inSlot != null && inSlot.getItem() == TContent.woodPattern)
                {
                	if (inSlot.stackSize > 1)
                	{
                		--inSlot.stackSize;
                	}
                	else
                	{
                        craftMatrix.setInventorySlotContents(i, null);	
                	}
                }
            }
    	}
    }
    
    @Override
    public void onSmelting(EntityPlayer player, ItemStack item) {
    

    }

}
