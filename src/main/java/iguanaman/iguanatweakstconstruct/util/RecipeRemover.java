package iguanaman.iguanatweakstconstruct.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.List;
import java.util.ListIterator;

public class RecipeRemover {

    @SuppressWarnings("unchecked")
    public static void removeAnyRecipe (ItemStack resultItem)
    {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        ListIterator<IRecipe> iter = recipes.listIterator();
        while(iter.hasNext())
        {
            IRecipe recipe = iter.next();
            ItemStack recipeResult = recipe.getRecipeOutput();
            if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
                iter.remove();
        }
    }

    @SuppressWarnings("unchecked")
    public static void removeAnyRecipeFor(Item item)
    {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        ListIterator<IRecipe> iter = recipes.listIterator();
        while(iter.hasNext())
        {
            IRecipe recipe = iter.next();
            ItemStack recipeResult = recipe.getRecipeOutput();
            if(recipeResult == null)
                continue;
            if(recipeResult.getItem() == item)
                iter.remove();
        }
    }
}
