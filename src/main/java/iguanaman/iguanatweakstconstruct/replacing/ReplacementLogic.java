package iguanaman.iguanatweakstconstruct.replacing;

import iguanaman.iguanatweakstconstruct.leveling.LevelingLogic;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.crafting.ToolRecipe;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.modifiers.tools.ModAttack;
import tconstruct.modifiers.tools.ModRedstone;
import tconstruct.tools.items.ToolPart;

import java.util.logging.Level;

import static iguanaman.iguanatweakstconstruct.replacing.ReplacementLogic.PartTypes.*;

public abstract class ReplacementLogic {


    public static void exchangeToolPart(ToolCore tool, NBTTagCompound tags, PartTypes type, ItemStack partStack, ItemStack toolStack)
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

        ItemStack originalTool = ToolBuilder.instance.buildTool(headStack, handleStack, accessoryStack, extraStack, "Original Tool");
        if(originalTool == null) {
            Log.error("Tool to modify is impossible?");
        }

        int partMaterialId = part.getMaterialID(partStack);
        int oldMaterialId = -1;

        if(type == HEAD && headStack != null) {
            headStack.setItemDamage(partMaterialId);
            oldMaterialId = getToolPartMaterial(tags, HEAD);
        }
        if(type == HANDLE && handleStack != null) {
            handleStack.setItemDamage(partMaterialId);
            oldMaterialId = getToolPartMaterial(tags, HANDLE);
        }
        if(type == ACCESSORY && accessoryStack != null) {
            accessoryStack.setItemDamage(partMaterialId);
            oldMaterialId = getToolPartMaterial(tags, ACCESSORY);
        }
        if(type == EXTRA && extraStack != null) {
            extraStack.setItemDamage(partMaterialId);
            oldMaterialId = getToolPartMaterial(tags, EXTRA);
        }

        ItemStack newTool = ToolBuilder.instance.buildTool(headStack, handleStack, accessoryStack, extraStack, "Modified Tool");
        NBTTagCompound newTags = newTool.getTagCompound().getCompoundTag("InfiTool");

        // Things that can change from replacing a part:
        // - durability
        // - mining speed
        // - mining level
        // - attack
        // - reinforced
        // - shoddy/stonebound
        // - Material Traits/Abilities that are not reinforced/stonebound (mostly from other mods)
        // - XP

        // update part materials and rendering
        updateTag(newTags, tags, "Head");
        updateTag(newTags, tags, "RenderHead");
        updateTag(newTags, tags, "Handle");
        updateTag(newTags, tags, "RenderHandle");
        updateTag(newTags, tags, "Accessory");
        updateTag(newTags, tags, "RenderAccessory");
        updateTag(newTags, tags, "Extra");
        updateTag(newTags, tags, "RenderExtra");

        // update durability
        int base = newTags.getInteger("BaseDurability");
        int bonus = tags.getInteger("BonusDurability"); // not changed through material, modifier
        float modDur = tags.getFloat("ModDurability"); // not changed through material, modifier

        int total = Math.max((int) ((base + bonus) * (modDur + 1f)), 1);
        tags.setInteger("BaseDurability", base);
        tags.setInteger("TotalDurability", total);

        // update damage
        updateTag(newTags, tags, "Attack");

        // update mining speed
        updateTag(newTags, tags, "MiningSpeed");
        updateTag(newTags, tags, "MiningSpeed2");
        updateTag(newTags, tags, "MiningSpeedHandle");
        updateTag(newTags, tags, "MiningSpeedExtra");

        // update harvest level (boosting/xp will be applied later)
        updateTag(newTags, tags, "HarvestLevel");
        updateTag(newTags, tags, "HarvestLevel2");
        updateTag(newTags, tags, "HarvestLevelHandle");
        updateTag(newTags, tags, "HarvestLevelExtra");

        // handle Leveling/xp
        if(LevelingLogic.hasXp(tags))
        {
            // do a percentage wise transfer
            float percentage = LevelingLogic.getXp(tags) / LevelingLogic.getRequiredXp(toolStack, tags);
            int newXp = Math.round(LevelingLogic.getRequiredXp(newTool, newTags) * percentage);
            tags.setInteger(LevelingLogic.TAG_EXP, newXp);
        }

        // handle boost leveling/xp
        if(LevelingLogic.hasBoostXp(tags))
        {
            // do a percentage wise transfer
            float percentage = LevelingLogic.getBoostXp(tags) / LevelingLogic.getRequiredBoostXp(toolStack);
            int newXp = Math.round(LevelingLogic.getRequiredBoostXp(newTool) * percentage);
            tags.setInteger(LevelingLogic.TAG_BOOST_EXP, newXp);

            // already full xp?
            if(LevelingLogic.isBoosted(tags))
                tags.setInteger("HarvestLevel", tags.getInteger("HarvestLevel") + 1);
        }

        // stonebound. Shoddy is always present and never changed, we can simply update it.
        updateTag(newTags, tags, "Shoddy");

        // reinforced is kinda complicated, since the actual level you get out of the materials is complicated
        // simply calculate difference between current and newly built tool to know how much has been added afterwards
        int currentReinforced = tags.getInteger("Unbreaking");
        int oldReinforced = originalTool.getTagCompound().getCompoundTag("InfiTool").getInteger("Unbreaking");
        int newReinforced = newTags.getInteger("Unbreaking");
        newReinforced += currentReinforced - oldReinforced;
        tags.setInteger("Unbreaking", newReinforced);
        // reinforced tooltip is handled internally by tcon automagically

        // now for the scary part... handle material traits >_<
        handleMaterialTraits(tags, oldMaterialId, partMaterialId);

        // redstone modifier
        reapplyRedstone(tags, toolStack);
        // quartz/attack modifier
        reapplyAttack(tags, toolStack);
    }

    // update tag if it already exists.
    // this saves us lots of hasFoo code,since there should be no general situation where a tag doesn't exist on the target
    private static void updateTag(NBTTagCompound from, NBTTagCompound to, String tag)
    {
        if(to.hasKey(tag))
            to.setTag(tag, from.getTag(tag));
    }


    // List of known material traits:
    // - Writeable
    // - Thaumic
    private static void handleMaterialTraits(NBTTagCompound tags, int oldMaterialId, int newMaterialId)
    {
        // nothing to do fi they're the same :)
        if(oldMaterialId == newMaterialId)
            return;
        ToolMaterial oldMat = TConstructRegistry.getMaterial(oldMaterialId);
        ToolMaterial newMat = TConstructRegistry.getMaterial(newMaterialId);

        // stonebound/jagged has to be handeled separately, see exchangeToolPart

        /************ first add the new traits *************/
        String ability = newMat.ability();
        // writeable & thaumic (equal since we only exchange 1 part)
        if(ability.equals(StatCollector.translateToLocal("materialtraits.writable")) ||
           ability.equals(StatCollector.translateToLocal("materialtraits.thaumic"))) {
            tags.setInteger("Modifiers", tags.getInteger("Modifiers") + 1);
        }



        /************ then remove the old traits *************/
        ability = oldMat.ability();
        // writeable & thaumic (equal since we only exchange 1 part)
        if(ability.equals(StatCollector.translateToLocal("materialtraits.writable")) ||
           ability.equals(StatCollector.translateToLocal("materialtraits.thaumic"))) {
            int newMods = tags.getInteger("Modifiers") - 1;
            // theoretically this should never happen, but.. rather be safe than sorry
            if(newMods < 0) newMods = 0;
            tags.setInteger("Modifiers", newMods);
        }
        // tasty - handled per ActiveToolMod, no NBT required
    }

    // strips the redstone modifier off a tool and reapplies it (to get the correct mining level modification)
    private static void reapplyRedstone(NBTTagCompound tags, ItemStack itemStack)
    {
        // only do if we actually have redstone
        if(!tags.hasKey("Redstone"))
            return;

        // find the redstone modifier
        for(ItemModifier mod : ModifyBuilder.instance.itemModifiers)
            if(mod.key.equals("Redstone"))
            {
                ModRedstone modRedstone = (ModRedstone)mod;
                int[] keyPair = tags.getIntArray("Redstone");
                // get amount of redstone applied
                int rLvl = keyPair[0];
                // reset redstone modifier
                tags.removeTag("Redstone");

                // remove the old tooltip
                int tipIndex = keyPair[2];
                tags.removeTag("Tooltip" + tipIndex);
                tags.removeTag("ModifierTip" + tipIndex);


                // reapply redstone
                while(rLvl-- > 0)
                    modRedstone.modify(new ItemStack[]{new ItemStack(Items.redstone)}, itemStack); // tags belong to oldTool

                // check if it actually used the same tipindex
                Log.info(tipIndex + "   " + tags.getIntArray("Redstone")[2]);
            }
    }

    // same as reapplyRedstone but for Attack modifier
    private static void reapplyAttack(NBTTagCompound tags, ItemStack itemStack)
    {
        // only do if we actually have attack modifier
        if(!tags.hasKey("ModAttack"))
            return;

        // find the redstone modifier
        for(ItemModifier mod : ModifyBuilder.instance.itemModifiers)
            if(mod.key.equals("ModAttack"))
            {
                ModAttack modAttack = (ModAttack)mod;
                int[] keyPair = tags.getIntArray("ModAttack");
                // get amount of redstone applied
                int qLvl = keyPair[0];
                // reset redstone modifier
                tags.removeTag("ModAttack");

                // remove the old tooltip
                int tipIndex = keyPair[2];
                tags.removeTag("Tooltip" + tipIndex);
                tags.removeTag("ModifierTip" + tipIndex);


                // reapply redstone
                while(qLvl-- > 0)
                    modAttack.modify(new ItemStack[]{new ItemStack(Items.quartz)}, itemStack); // tags belong to oldTool

                // check if it actually used the same tipindex
                Log.info(tipIndex + "   " + tags.getIntArray("ModAttack")[2]);
            }
    }

    // todo: remove this function stub :D
    private static void adjustModifierTag(NBTTagCompound tags, String tag, boolean decrease)
    {
        NBTBase baseTag = tags.getTag(tag);

        // boolean modifier.. tag gets added or removed
        //if(baseTag.getId() == NBTB)

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
