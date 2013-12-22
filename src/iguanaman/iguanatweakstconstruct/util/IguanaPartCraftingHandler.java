package iguanaman.iguanatweakstconstruct.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import tconstruct.common.TContent;
import tconstruct.items.Pattern;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.PatternBuilder.ItemKey;
import tconstruct.library.tools.BowstringMaterial;
import tconstruct.library.tools.CustomMaterial;
import tconstruct.library.tools.FletchingMaterial;
import cpw.mods.fml.common.ICraftingHandler;

public class IguanaPartCraftingHandler implements ICraftingHandler {

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
		ItemStack pattern = null;
		ItemStack material = null;

		for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
		{
			ItemStack slot = craftMatrix.getStackInSlot(i);

			// Item in slot
			if (slot != null)
			{
				// is the item in the slot a wood pattern?
				boolean isPattern = false;
				if (slot.getItem().itemID == TContent.woodPattern.itemID) isPattern = true;

				// too many items
				if (material != null && pattern != null || material != null && !isPattern) return;

				// found a new item
				if (isPattern) pattern = slot;
				else material = slot;
			}
		}

		if (pattern == null || material == null) return;

		// part crafting occurred
		int cost = ((Pattern)TContent.woodPattern).getPatternCost(pattern);
		ItemKey key = PatternBuilder.instance.getItemKey(material);
		int value = -1;
		if (key == null)
		{
			// check if trying to craft a bow string
			CustomMaterial mat = TConstructRegistry.getCustomMaterial(material, BowstringMaterial.class);
			if (mat == null)
			{
				// check if trying to craft a fletching
				mat = TConstructRegistry.getCustomMaterial(material, FletchingMaterial.class);

				//not sure why this would ever happen, but just in case
				if (mat == null) return;
			}

			value = mat.value;
		} else
			value = key.value;

		int used = Math.max(Math.round((float)cost / (float)value), 1) - 1;

		/*
        IguanaLog.log("cost " + cost);
        IguanaLog.log("value " + value);
        IguanaLog.log("used " + used);
		 */

		if (used > 0)
			if (material.stackSize < used) material = null;
			else material.stackSize -= used;
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {


	}

}
