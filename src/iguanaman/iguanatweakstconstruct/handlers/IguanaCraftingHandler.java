package iguanaman.iguanatweakstconstruct.handlers;

import tconstruct.TConstruct;
import tconstruct.common.TContent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ICraftingHandler;

public class IguanaCraftingHandler implements ICraftingHandler {

    @Override
    public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
    	
    	if (item.getItem() == TContent.woodPattern)
            for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
                craftMatrix.setInventorySlotContents(i, null);
    }
    
    @Override
    public void onSmelting(EntityPlayer player, ItemStack item) {
    

    }

}
