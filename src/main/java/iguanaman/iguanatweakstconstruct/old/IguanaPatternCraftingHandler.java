package iguanaman.iguanatweakstconstruct.old;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
//import tconstruct.common.TContent;
//import cpw.mods.fml.common.ICraftingHandler;

// TODO: Find the crating Handler replacement
public class IguanaPatternCraftingHandler /*implements ICraftingHandler*/ {

	/*@Override
	public void onCrafting(EntityPlayer player, ItemStack prefix, IInventory craftMatrix) {

		if (prefix.getItem().itemID == TContent.woodPattern.itemID)
			for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
			{
				ItemStack inSlot = craftMatrix.getStackInSlot(i);
				if (inSlot != null && inSlot.getItem().itemID == TContent.woodPattern.itemID)
					if (inSlot.stackSize > 1) --inSlot.stackSize;
					else craftMatrix.setInventorySlotContents(i, null);
			}

	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack prefix) {}*/

}
