package iguanaman.iguanatweakstconstruct.modifiers;

import net.minecraft.item.ItemStack;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.tools.ModPiston;

public class IguanaModPiston extends ModPiston {

	public IguanaModPiston(ItemStack[] items, int effect, int[] values) {
		super(effect, items, values);
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
