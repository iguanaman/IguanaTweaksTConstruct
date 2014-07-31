package iguanaman.iguanatweakstconstruct.leveling;

import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import tconstruct.TConstruct;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.modifiers.tools.ModRedstone;
import tconstruct.tools.TinkerTools;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RandomBonusses {
    private static Map<String, ItemModifier> modCache = new HashMap<String, ItemModifier>();

    public static void tryModifying(EntityPlayer player, ItemStack tool)
    {
        boolean modified = false;
        // add a modifier for it
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        int modifiers = tags.getInteger("Modifiers");
        tags.setInteger("Modifiers", modifiers+1);

        // we can do this without getting an infinite loop, because redstone, lapis,... can be applied infinitely often
        while(!modified) {
            int i = TConstruct.random.nextInt(14);
            Log.info("Trying modifier # " + i);
            if (i-- == 0) modified = addRedstoneModifier(player, tool);  //0
            if (i-- == 0) modified = addLapisModifier(player, tool);     //1
            if (i-- == 0) modified = addAutoSmeltModifier(player, tool); //2
            if (i-- == 0) modified = addSilktouchModifier(player, tool); //3
            if (i-- == 0) modified = addDiamondModifier(player, tool);   //4
            if (i-- == 0) modified = addEmeraldModifier(player, tool);   //5
            if (i-- == 0) modified = addRepairModifier(player, tool);    //6
            if (i-- == 0) modified = addAttackModifier(player, tool);    //7
            if (i-- == 0) modified = addBlazeModifier(player, tool);     //8
            if (i-- == 0) modified = addSmiteModifier(player, tool);     //9
            if (i-- == 0) modified = addAntiSpiderModifier(player, tool);//10
            if (i-- == 0) modified = addBeheadingModifier(player, tool); //11
            if (i-- == 0) modified = addLifeStealModifier(player, tool); //12
            if (i-- == 0) modified = addKnockbackModifier(player, tool); //13
        }

        // restore modifiers
        tags.setInteger("Modifiers", modifiers);
    }


    /* Mining Enchantments */

    public static boolean addRedstoneModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] redstoneStack = new ItemStack[]{new ItemStack(Items.redstone, 1)};

        return addGenericModifier(player, tool, "Redstone", redstoneStack, 50, "\u00a79You spin it around with a flourish (+1 haste)");
    }

    public static boolean addLapisModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] lapisStack = new ItemStack[]{new ItemStack(Items.dye, 1, 4)};

        return addGenericModifier(player, tool, "Lapis", lapisStack, 100, "\u00a79Perhaps holding on to it will bring you luck? (+100 luck)");
    }

    public static boolean addAutoSmeltModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Lava", "\u00a79You should report this missing string .");
    }

    public static boolean addSilktouchModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Silk Touch", "\u00a79You should report this missing string .");
    }


    /* Tool Enchantments */

    public static boolean addDiamondModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Diamond", "\u00a79You should report this missing string .");
    }

    public static boolean addEmeraldModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Emerald", "\u00a79You should report this missing string .");
    }

    // debateable if i'll ever use this
    public static boolean addFluxModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Flux", "\u00a79You should report this missing string .");
    }

    public static boolean addRepairModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Moss", "\u00a79It seems to have accumulated a patch of moss (+1 repair)");
    }


    /* Combat Enchantments */
    public static boolean addAttackModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] quarzStack = new ItemStack[]{new ItemStack(Items.quartz, 1)};

        return addGenericModifier(player, tool, "ModAttack", quarzStack, 24, "\u00a79You take the time to sharpen the dull edges of the blade (+1 attack)");
    }

    public static boolean addBlazeModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] blazePowderStack = new ItemStack[]{new ItemStack(Items.blaze_powder, 1)};

        return addGenericModifier(player, tool, "Blaze", blazePowderStack, 25, "\u00a79It starts to feels more hot to the touch (+1 fire aspect)");
    }

    public static boolean addSmiteModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] consecratedEartStack = new ItemStack[]{new ItemStack(TinkerTools.craftedSoil, 1, 4)};

        return addGenericModifier(player, tool, "ModSmite", consecratedEartStack, 36, "\u00a79It begins to radiate a slight glow (+1 smite)");
    }

    public static boolean addAntiSpiderModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] fermentedEyeStack = new ItemStack[]{new ItemStack(Items.fermented_spider_eye, 1)};

        return addGenericModifier(player, tool, "ModAntiSpider", fermentedEyeStack, 4, "\u00a79A strange odor emanates from the weapon (+1 bane of arthropods)");
    }

    public static boolean addBeheadingModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Beheading", "\u00a79You could take someones head off with that! (+1 beheading)");
    }

    public static boolean addLifeStealModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Necrotic", "\u00a79It shudders with a strange energy (+1 life steal)");
    }

    public static boolean addKnockbackModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Piston", "\u00a79Feeling more confident, you can more easily keep your assailants at bay (+1 knockback)");
    }


    /* Backbone ;o */

    // simple call
    private static boolean addGenericModifier(EntityPlayer player, ItemStack tool, String key, String message)
    {
        return addGenericModifier(player, tool, key, null, 1, message);
    }
    // I really didn't want to write the same 15 lines all over again to add a modifier >_<
    private static boolean addGenericModifier(EntityPlayer player, ItemStack tool, String key, ItemStack[] stacksToAdd, int times, String message)
    {
        ItemModifier modifier = getModifier(key);
        // something happened. ohshit.
        if (modifier == null)
            return false;

        // custom stack or from tool?
        if(stacksToAdd == null)
            stacksToAdd = stackFromModifier(modifier);

        // can we apply the modifier?
        if(!modifier.matches(stacksToAdd, tool))
            return false;

        // apply modifier
        modifier.addMatchingEffect(tool); // order matters, effect has to be applied before modifying
        while(times-- > 0)
            modifier.modify(stacksToAdd, tool);

        // message!
        if (!player.worldObj.isRemote)
            player.addChatMessage(new ChatComponentText(message));

        return true;
    }

    /**
     * Returns the cached modifier, or loads it and caches it.
     * @return The modifier, or null if it doesn't exist
     */
    private static ItemModifier getModifier(String key)
    {
        if(modCache.containsKey(key))
            return modCache.get(key);

        if(key.equals("ModAttack"))
        {
            modCache.put(key, TinkerTools.modAttack);
            return TinkerTools.modAttack;
        }


        for(ItemModifier modifier : ModifyBuilder.instance.itemModifiers)
            if(modifier.key.equals(key)) {
                modCache.put(key, modifier);
                return modifier;
            }

        Log.error("Couldn't detect " + key + " modifier when applying random bonus");
        return null;
    }

    // herpderp
    private static ItemStack[] stackFromModifier(ItemModifier modifier)
    {
        ItemStack[] stack = new ItemStack[modifier.stacks.size()];
        int i = 0;
        for(Object s : modifier.stacks)
            stack[i++] = (ItemStack)s;

        return stack;
    }
}
