package iguanaman.iguanatweakstconstruct.modifiers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.ModPiston;

public class IguanaModPiston extends ModPiston {

	public IguanaModPiston(ItemStack[] items, int effect, int inc) {
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
