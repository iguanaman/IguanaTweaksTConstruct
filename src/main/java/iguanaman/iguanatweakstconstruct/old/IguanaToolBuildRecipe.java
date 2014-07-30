package iguanaman.iguanatweakstconstruct.old;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.tools.ToolCore;

public class IguanaToolBuildRecipe implements IRecipe {

	ItemStack placeholder = new ItemStack(Blocks.stone);
	ItemStack output = null;

	@Override
	public boolean matches(InventoryCrafting inv, World world) {

		output = null;
		boolean toolFound = false;
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();

		for (int x = 0; x < inv.getSizeInventory(); x++)
		{
			ItemStack slot = inv.getStackInSlot(x);

			// Item in slot
			if (slot != null)
			{
				//Too many items
				if (!IguanaConfig.easyToolCreation && inputs.size() == 3 || inputs.size() == 4) return false;

				// if a tool is found, make sure easy modifications are allowed first
				if (!IguanaConfig.easyToolModification && slot.getItem() instanceof ToolCore) return false;

				if (slot.getItem() instanceof ToolCore) toolFound = true;

				inputs.add(slot);
			}
		}

		// less than two items is useless
		if (inputs.size() < 2) return false;

		// Only modification allowed and no tool found
		if (!IguanaConfig.easyToolCreation && !toolFound) return false;

		// Make sure size is 4 "itemstacks"
		while (inputs.size() < 4) inputs.add(null);

		// loop things
		for (int i1 = 0; i1 < 4; ++i1)
		{
			ItemStack input1 = inputs.get(i1);
			if (input1 == null || !IguanaConfig.easyToolCreation && !(input1.getItem() instanceof ToolCore)) continue;

			for (int i2 = 0; i2 < 4; ++i2)
			{
				ItemStack input2 = inputs.get(i2);
				if (input2 == null) continue;

				for (int i3 = 0; i3 < 4; ++i3)
				{
					ItemStack input3 = inputs.get(i3);

					for (int i4 = 0; i4 < 4; ++i4)
					{
						ItemStack input4 = inputs.get(i4);

						// make sure each part is unique
						if (i1 == i2 || i1 == i3 || i1 == i4) continue;
						if (i2 == i3 || i2 == i4) continue;
						if (i3 == i4) continue;

						// try to build / modify something
						if (IguanaConfig.easyToolCreation && !toolFound)
							output = ToolBuilder.instance.buildTool(input1, input2, input3, input4, "");
						else if (input1.getItem() instanceof ToolCore && input1.hasTagCompound() && input1.getTagCompound().getCompoundTag("InfiTool") != null)
							output = ModifyBuilder.instance.modifyItem(input1, new ItemStack[] {input2, input3, input4});

						// If modification was successful
						if (output != null) return true;

						// Only tool modification allowed so input4 will always be null
						if (!IguanaConfig.easyToolCreation) break;
					}
				}
			}
		}

		// Otherwise no
		return false;
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
