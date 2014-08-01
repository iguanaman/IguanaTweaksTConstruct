package iguanaman.iguanatweakstconstruct.leveling;

import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import tconstruct.TConstruct;
import tconstruct.items.tools.BowBase;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.Weapon;
import tconstruct.modifiers.tools.ModRedstone;
import tconstruct.tools.TinkerTools;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RandomBonusses {
    private static Map<String, ItemModifier> modCache = new HashMap<String, ItemModifier>();

    public static Modifier tryModifying(EntityPlayer player, ItemStack tool)
    {
        // add a modifier for it
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        int modifiers = tags.getInteger("Modifiers");
        tags.setInteger("Modifiers", modifiers+1);

        // construct possibility "matrix"
        Modifier[] mods = Modifier.values();
        int[] chances = new int[mods.length];
        int total = 0;
        int i = 0;
        for(Modifier mod : mods)
        {
            if(Config.randomBonusesAreRandom)
                chances[i] = 1;
            else if(tool.getItem() instanceof HarvestTool)
                chances[i] = getToolModifierWeight(mod);
            else if(tool.getItem() instanceof Weapon)
                chances[i] = getWeaponModifierWeight(mod);
            else if(tool.getItem() instanceof BowBase)
                chances[i] = getBowModifierWeight(mod);

            total += chances[i];
            i++;
        }


        // try modifying
        // we can do this without getting an infinite loop, because redstone, lapis,... can be applied infinitely often
        boolean modified = false;
        Modifier choice = null;
        while(!modified) {
            // get a random decision number
            int random = TConstruct.random.nextInt(total);
            int counter = 0;

            // determine which modifier to use. basically we increase by each weight and check if the random number landed there.
            i = 0;
            for(Modifier mod : mods)
            {
                counter += chances[i];
                i++;

                if(counter > random)
                {
                    choice = mod;
                    break;
                }
            }

            if(choice == null)
                return null;

            // try to apply chosen modifier. WTB function pointers..
            switch (choice)
            {
                // mining mods
                case REDSTONE:  modified = addRedstoneModifier(player, tool); break;
                case LAPIS:     modified = addLapisModifier(player, tool); break;
                case AUTOSMELT: modified = addAutoSmeltModifier(player, tool); break;
                case SILKTOUCH: modified = addSilktouchModifier(player, tool); break;
                // general modifiers
                case DIAMOND:   modified = addDiamondModifier(player, tool); break;
                case EMERALD:   modified = addEmeraldModifier(player, tool); break;
                case REPAIR:    modified = addRepairModifier(player, tool); break;
                // combat modifiers
                case ATTACK:    modified = addAttackModifier(player, tool); break;
                case BLAZE:     modified = addBlazeModifier(player, tool); break;
                case SMITE:     modified = addSmiteModifier(player, tool); break;
                case BANE:      modified = addAntiSpiderModifier(player, tool); break;
                case BEHEADING: modified = addBeheadingModifier(player, tool); break;
                case LIFESTEAL: modified = addLifeStealModifier(player, tool); break;
                case KNOCKBACK: modified = addKnockbackModifier(player, tool); break;
                default: modified = false;
            }
        }

        // restore modifiers
        tags.setInteger("Modifiers", modifiers);

        return choice;
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


    /* Modifier weights */
    private static int getToolModifierWeight(Modifier mod)
    {
        switch(mod)
        {
            // mining mods
            case REDSTONE:  return 100;
            case LAPIS:     return 100;
            case AUTOSMELT: return 20;
            case SILKTOUCH: return 15;
            // general modifiers
            case DIAMOND:   return 30;
            case EMERALD:   return 35;
            case REPAIR:    return 50;
            // combat modifiers
            case ATTACK:    return 15;
            case BLAZE:     return 5;
            case SMITE:     return 5;
            case BANE:      return 5;
            case BEHEADING: return 5;
            case LIFESTEAL: return 5;
            case KNOCKBACK: return 10;
            default: return 0;
        }
    }

    private static int getWeaponModifierWeight(Modifier mod)
    {
        switch(mod)
        {
            // mining mods
            case REDSTONE:  return 0;
            case LAPIS:     return 75;
            case AUTOSMELT: return 15;
            case SILKTOUCH: return 5;
            // general modifiers
            case DIAMOND:   return 15;
            case EMERALD:   return 30;
            case REPAIR:    return 55;
            // combat modifiers
            case ATTACK:    return 110;
            case BLAZE:     return 45;
            case SMITE:     return 50;
            case BANE:      return 50;
            case BEHEADING: return 50;
            case LIFESTEAL: return 30;
            case KNOCKBACK: return 50;
            default: return 0;
        }
    }

    private static int getBowModifierWeight(Modifier mod)
    {
        switch(mod)
        {
            // mining mods
            case REDSTONE:  return 100;
            case LAPIS:     return 75;
            case AUTOSMELT: return 1;
            case SILKTOUCH: return 1;
            // general modifiers
            case DIAMOND:   return 15;
            case EMERALD:   return 30;
            case REPAIR:    return 50;
            // combat modifiers
            case ATTACK:    return 100;
            case BLAZE:     return 55;
            case SMITE:     return 40;
            case BANE:      return 40;
            case BEHEADING: return 30;
            case LIFESTEAL: return 40;
            case KNOCKBACK: return 40;
            default: return 0;
        }
    }

    public enum Modifier {
        // mining modifiers
        REDSTONE,
        LAPIS,
        AUTOSMELT,
        SILKTOUCH,
        // general modifiers
        DIAMOND,
        EMERALD,
        REPAIR,
        // weapon modifiers
        ATTACK,
        BLAZE,
        SMITE,
        BANE,
        BEHEADING,
        LIFESTEAL,
        KNOCKBACK
    }
}
