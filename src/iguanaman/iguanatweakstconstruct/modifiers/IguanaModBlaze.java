package iguanaman.iguanatweakstconstruct.modifiers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.ModBlaze;

public class IguanaModBlaze extends ModBlaze {

	
	public IguanaModBlaze(ItemStack[] items, int effect, int inc) {
		super(items, effect, inc);
	}

    @Override
    public boolean canModify (ItemStack tool, ItemStack[] input)
    {
        ToolCore toolItem = (ToolCore) tool.getItem();
        if (!validType(toolItem))
            return false;
        else
            return true;
    }

}
