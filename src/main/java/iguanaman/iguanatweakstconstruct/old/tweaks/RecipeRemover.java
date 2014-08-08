package iguanaman.iguanatweakstconstruct.old.tweaks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RecipeRemover {

	@SuppressWarnings("unchecked")
	public static void removeAnyRecipe (ItemStack resultItem)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			IRecipe tmpRecipe = recipes.get(i);
			ItemStack recipeResult = tmpRecipe.getRecipeOutput();
			if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
				recipes.remove(i--);
		}
	}

	@SuppressWarnings("unchecked")
	public static void removeFurnaceRecipe (Item item, int metadata)
	{
		Map<List<Integer>, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
		recipes.remove(Arrays.asList(item, metadata));
	}

}
