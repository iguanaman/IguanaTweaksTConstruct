package iguanaman.iguanatweakstconstruct.replacing;

import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.crafting.ToolRecipe;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.library.util.IToolPart;
import tconstruct.tools.items.ToolPart;
import static iguanaman.iguanatweakstconstruct.replacing.ReplacementLogic.*;
import static iguanaman.iguanatweakstconstruct.replacing.ReplacementLogic.PartTypes.*;

import java.util.HashSet;
import java.util.Set;

public class ModPartReplacement extends ItemModifier {
    public ModPartReplacement() {
        // applicable for all items. canModify decides
        super(new ItemStack[0], 0, "");
    }

    @Override
    public boolean matches(ItemStack[] recipe, ItemStack input) {
        return canModify(input, recipe);
    }

    @Override
    protected boolean canModify(ItemStack itemStack, ItemStack[] parts) {
        if(!(itemStack.getItem() instanceof ToolCore))
            return false;

        ToolCore tool = (ToolCore) itemStack.getItem();
        NBTTagCompound tags = itemStack.getTagCompound().getCompoundTag("InfiTool");

        if(tags.getInteger("Damage") > 0)
            return false;

        // get the recipe of the tool
        ToolRecipe recipe = ToolBuilder.instance.recipeList.get(tool.getToolName());
        if(recipe == null)
            return false;

        // check if all parts are actually parts and compatible with the allowed parts
        Item replacementPartItem = null;
        int partIndex = -1;
        PartTypes partType = HEAD;
        for(int i = 0; i < parts.length; i++)
        {
            if(parts[i] == null)
                continue;
            Item item = parts[i].getItem();

            // is it a toolpart?
            if (!(item instanceof IToolPart || item == Items.bone || item == Items.stick))
                return false;

            // we only allow single part replacement. sorry i'm lazy. ;/
            if(replacementPartItem != null)
                return false;

            // which part is it, and is it a valid part?
            if(recipe.validHead(item))
                partType = HEAD;
            else if(recipe.validHandle(item))
                partType = HANDLE;
            else if(recipe.validAccessory(item))
                partType = ACCESSORY;
            else if(recipe.validExtra(item))
                partType = EXTRA;
            else
                return false;

            replacementPartItem = item;
            partIndex = i;
        }

        // no usable part present? :(
        if(replacementPartItem == null && replacementPartItem != Items.bone && replacementPartItem != Items.stick)
            return false;
        if(partIndex == -1)
            return false;

        // determine materials
        int newMatId = ToolBuilder.instance.getMaterialID(parts[partIndex]);
        int oldMatId = getToolPartMaterial(tags, partType);
        int modifiers = tags.getInteger("Modifiers");

        // detect possible secondary position for part (e.g. hammer has 2 plates, etc.)
        // index 0 = handle
        // index 1 = accessory
        // index 2 = extra
        // index >2 = crafting station
        // todo: special behaviour that left side of crafting station is left hammer/battleaxe part and right is right component ;)
        for(int i = partIndex; i > 0; i--)
            partType = detectAdditionalPartType(recipe, replacementPartItem, partType);

        // do we have enough modifiers left if we exchange this part?
        if(hasExtraModifier(oldMatId)) // paper or thaumium. sadly hardcoded.
            modifiers--;
        if(hasExtraModifier(newMatId))
            modifiers++;
        if(modifiers < 0)
            return false;

        // is it the same material as the one we want to replace?
        if(newMatId == oldMatId)
            return false;

        return true;
    }

    @Override
    public void modify(ItemStack[] parts, ItemStack itemStack) {
        // do all the stuff that we did in canModify again to obtain the necessary information :(
        ToolCore tool = (ToolCore) itemStack.getItem();
        ToolRecipe recipe = ToolBuilder.instance.recipeList.get(tool.getToolName());
        NBTTagCompound tags = itemStack.getTagCompound().getCompoundTag("InfiTool");

        // get item
        Item replacementPartItem = null;
        int partIndex = -1;
        for(int i = 0; i < parts.length; i++) {
            if (parts[i] == null)
                continue;
            replacementPartItem = parts[i].getItem();
            partIndex = i;
        }

        // detect which part to replace
        PartTypes partType = detectAdditionalPartType(recipe, replacementPartItem, HEAD);
        for(int i = partIndex; i > 0; i--)
            partType = detectAdditionalPartType(recipe, replacementPartItem, partType);

        // actually do the exchange
        exchangeToolPart(tool, tags, partType, parts[partIndex], itemStack);
    }



    @Override
    public void addMatchingEffect(ItemStack input) {
    }
}
