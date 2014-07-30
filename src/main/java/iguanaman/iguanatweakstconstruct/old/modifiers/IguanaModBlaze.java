package iguanaman.iguanatweakstconstruct.old.modifiers;

import net.minecraft.item.ItemStack;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.tools.ModBlaze;

public class IguanaModBlaze extends ModBlaze {


	public IguanaModBlaze(ItemStack[] items, int effect, int[] inc) {
		super(effect, items, inc);
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
