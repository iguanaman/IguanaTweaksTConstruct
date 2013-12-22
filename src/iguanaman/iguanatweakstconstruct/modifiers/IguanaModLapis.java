package iguanaman.iguanatweakstconstruct.modifiers;

import net.minecraft.item.ItemStack;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.ModLapis;

public class IguanaModLapis extends ModLapis {

	public IguanaModLapis(ItemStack[] items, int effect, int inc) {
		super(items, effect, inc);
	}

	@Override
	public boolean canModify (ItemStack tool, ItemStack[] input) {
		return super.canModify(tool, input);
	}

	@Override
	public void midStreamModify (ItemStack tool, ToolCore toolItem) {}

}
