package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.reference.Reference;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import tconstruct.library.tools.ToolCore;

public class ToolUpdateRecipe implements IRecipe {
    static {
        // register the recipe with the recipesorter
        RecipeSorter.register(Reference.MOD_ID + ":update", ToolUpdateRecipe.class, RecipeSorter.Category.SHAPELESS, "");
    }

    private ItemStack updatedTool = null;

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        ItemStack tool = null;

        // check for a tool
        for(int i = 0; i < inventoryCrafting.getSizeInventory(); i++)
        {
            ItemStack slot = inventoryCrafting.getStackInSlot(i);
            // empty slot
            if(slot == null)
                continue;

            // is it the tool?
            if(slot.getItem() instanceof ToolCore) {
                if(tool != null)
                    return false;
                tool = slot;
            }
        }
        // no tool found?
        if(tool == null)
            return false;

        // check if it needs updating
        if(OldToolConversionHandler.toolNeedsUpdating(tool))
        {
            updatedTool = tool.copy();
            OldToolConversionHandler.updateItem(updatedTool);
            return true;
        }
        else
            updatedTool = null;

        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        return updatedTool;
    }

    @Override
    public int getRecipeSize() {
        return 1;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return updatedTool;
    }
}
