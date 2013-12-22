package iguanaman.iguanatweakstconstruct.util;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import tconstruct.common.TContent;
import tconstruct.library.crafting.PatternBuilder;

public class IguanaPartBuildRecipe implements IRecipe {

	ItemStack placeholder = new ItemStack(Block.stone);
	ItemStack output = null;

	@Override
	public boolean matches(InventoryCrafting inv, World world) {

		// setup
		output = null;
		ItemStack material = null;
		ItemStack pattern = null;

		// check inputs
		for (int x = 0; x < inv.getSizeInventory(); x++)
		{
			ItemStack slot = inv.getStackInSlot(x);

			// Item in slot
			if (slot != null)
			{
				// is the item in the slot a wood pattern?
				boolean isPattern = false;
				if (slot.getItem().itemID == TContent.woodPattern.itemID) isPattern = true;

				// too many items
				if (material != null && pattern != null || material != null && !isPattern) return false;

				// found a new item
				if (isPattern) pattern = slot;
				else material = slot;
			}
		}

		// try to build a part
		ItemStack[] outputs = PatternBuilder.instance.getToolPart(material, pattern, null);

		// part crafting failed
		if (outputs == null) return false;

		// part crafting succeeded
		output = outputs[0];
		return true;

	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		if (output != null) return output;
		else return placeholder;
	}

	@Override
	public int getRecipeSize() { return 3; }

	@Override
	public ItemStack getRecipeOutput() {
		if (output != null) return output.copy();
		else return placeholder.copy();
	}
}
