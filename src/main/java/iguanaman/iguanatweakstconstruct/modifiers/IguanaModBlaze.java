package iguanaman.iguanatweakstconstruct.modifiers;

import net.minecraft.item.ItemStack;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.tools.ModBlaze;

public class IguanaModBlaze extends ModBlaze {


	public IguanaModBlaze(ItemStack[] items, int effect, int[] values) {
		super(effect, items, values);
	}

	public boolean canModify (ItemStack tool, ItemStack[] input)
	{
		ToolCore toolItem = (ToolCore) tool.getItem();
		if (!validType(toolItem))
			return false;
		else
			return true;
	}

}
