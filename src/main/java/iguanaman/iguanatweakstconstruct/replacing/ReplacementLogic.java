package iguanaman.iguanatweakstconstruct.replacing;

import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.crafting.ToolRecipe;
import tconstruct.library.tools.ToolCore;
import tconstruct.tools.items.ToolPart;

import static iguanaman.iguanatweakstconstruct.replacing.ReplacementLogic.PartTypes.*;

public abstract class ReplacementLogic {


    public static void exchangeToolPart(ToolCore tool, NBTTagCompound tags, PartTypes type, ItemStack partStack)
    {
        ToolPart part = (ToolPart)partStack.getItem();

        // create a new tool that'd be the old one with the new part exchanged
        ItemStack headStack = null;
        ItemStack handleStack = null;
        ItemStack accessoryStack = null;
        ItemStack extraStack = null;

        if(getPart(tool, HEAD) != null)
            headStack = new ItemStack(getPart(tool, HEAD), 1, getToolPartMaterial(tags, HEAD));
        if(getPart(tool, HANDLE) != null)
            handleStack = new ItemStack(getPart(tool, HANDLE), 1, getToolPartMaterial(tags, HANDLE));
        if(getPart(tool, ACCESSORY) != null)
            accessoryStack = new ItemStack(getPart(tool, ACCESSORY), 1, getToolPartMaterial(tags, ACCESSORY));
        if(getPart(tool, EXTRA) != null)
            extraStack = new ItemStack(getPart(tool, EXTRA), 1, getToolPartMaterial(tags, EXTRA));

        int partMaterialId = part.getMaterialID(partStack);

        if(type == HEAD && headStack != null)
            headStack.setItemDamage(partMaterialId);
        if(type == HANDLE && handleStack != null)
            handleStack.setItemDamage(partMaterialId);
        if(type == ACCESSORY && accessoryStack != null)
            accessoryStack.setItemDamage(partMaterialId);
        if(type == EXTRA && extraStack != null)
            extraStack.setItemDamage(partMaterialId);

        ItemStack newTool = ToolBuilder.instance.buildTool(headStack, handleStack, accessoryStack, extraStack, "Modified Tool");
        NBTTagCompound newTags = newTool.getTagCompound();
        /*
        if(newTool == null)
            Log.info("fail");
        else {
            Log.info("-----------------------");
            // compare
            for (Object tag : newTool.getTagCompound().getCompoundTag("InfiTool").func_150296_c()) {
                if (tags.func_150296_c().contains(tag)) {
                    if (!tags.getTag((String) tag).equals(newTool.getTagCompound().getCompoundTag("InfiTool").getTag((String) tag)))
                        Log.info(tag.toString() + ": " + newTool.getTagCompound().getCompoundTag("InfiTool").getTag((String) tag).toString());
                    else
                        Log.info(tag.toString() + ": equal");
                } else
                    Log.info(tag.toString() + "(new): " + newTool.getTagCompound().getCompoundTag("InfiTool").getTag((String) tag).toString());
            }
            for(Object tag : tags.func_150296_c())
                if(! newTool.getTagCompound().getCompoundTag("InfiTool").func_150296_c().contains(tag))
                    Log.info(tag.toString() + "(missing): " + tags.getTag((String) tag).toString());
        }
        */


        // Update base stats

    }

    private static void replaceTag(String tag, NBTTagCompound source, NBTTagCompound target)
    {
        target.setTag(tag, source.getTag(tag));
    }

    /**
     * Checks if the material has an extra modifier.
     */
    public static boolean hasExtraModifier(int materialId)
    {
        // paper = 9, thaumium = 31
        return materialId == 9 || materialId == 31;
    }

    /**
     * Returns the type-part of the tool.
     */
    public static ToolPart getPart(ToolCore tool, PartTypes type)
    {
        switch (type) {
            case HEAD: return (ToolPart)tool.getHeadItem();
            case HANDLE: return (ToolPart)tool.getHandleItem();
            case ACCESSORY: return (ToolPart)tool.getAccessoryItem();
            case EXTRA: return (ToolPart)tool.getExtraItem();
        }

        return null;
    }

    /**
     * Returns the material id the tool-component is made of.
     * @param tags InfiTool Tagcompount of the tool
     */
    public static int getToolPartMaterial(NBTTagCompound tags, PartTypes type)
    {
        int mat = -1;
        switch (type) {
            case HEAD: mat = tags.getInteger("Head"); break;
            case HANDLE: mat = tags.getInteger("Handle"); break;
            case ACCESSORY: mat = tags.getInteger("Accessory"); break;
            case EXTRA: mat = tags.getInteger("Extra"); break;
        }

        return mat;
    }

    /**
     * Check if another part type could be employed. basically slot-id = part-placement.
     * @param recipe Recipe that should be checked
     * @param part The new part that replaces an old one.
     * @param currentType Where the part would currently be replaced. Call multiple times with the previous result to check all.
     * @return The new position that's applicable, or the same if nothing new has been detected.
     */
    public static PartTypes detectAdditionalPartType(ToolRecipe recipe, Item part, PartTypes currentType)
    {
        PartTypes tmpType = currentType;
        if(tmpType == HEAD)
        {
            if(recipe.validHandle(part)) return HANDLE;
            tmpType = HANDLE;
        }
        if(tmpType == HANDLE)
        {
            if(recipe.validAccessory(part)) return ACCESSORY;
            tmpType = ACCESSORY;
        }
        if(tmpType == ACCESSORY)
        {
            if(recipe.validExtra(part)) return EXTRA;
        }

        return currentType;
    }

    public enum PartTypes {
        HEAD,
        HANDLE,
        ACCESSORY,
        EXTRA
    }
}
