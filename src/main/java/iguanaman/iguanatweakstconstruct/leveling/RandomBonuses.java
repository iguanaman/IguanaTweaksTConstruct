package iguanaman.iguanatweakstconstruct.leveling;

import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import tconstruct.TConstruct;
import tconstruct.items.tools.Battleaxe;
import tconstruct.items.tools.BowBase;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.Weapon;
import tconstruct.tools.TinkerTools;

import java.util.HashMap;
import java.util.Map;

/*
  On doing stuff, add data what was done. Apply data to weights. Basically on action add an NBT tag that knows how much.
!  - Redstone: mining a regular block
!  - Luck: mining a block that drops stuff. Maybe mining an ore. Hitting an enemy (simply assume every enemy drops stuff for simplicity)
  - autosmelt: ...hitting furnaces maybe? No, digging blocks that are submerged in lava! :D
  - silktouch: nope. we don't a higher silktouch chance.
  - diamond: this is probably not useful enough to warrant an increased chance. leave it at base chance.
  - emerald: same as diamond
  - repair: repairing the tool maybe? meh. Should probably simply have a good base chance and maybe decrease it once we get one level of it
!  - attack: critting? or simply doing damage in general?
!  - blaze: hitting a blaze
!  - smite: hitting a zombie
!  - bane: hitting a spider
!  - beheading: hitting an enderman
!  - lifesteal: hitting a wither skelly or pigman
!  - knockback: spring+hitting enemies

    maybe.. add a critical strike modifier, only obtainable through levelup :>
 */
public class RandomBonuses {
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
            if(Config.deactivatedModifiers.contains(mod))
                chances[i] = 0;
            else if(Config.randomBonusesAreRandom)
                chances[i] = 1;
            else if(tool.getItem() instanceof Weapon || tool.getItem() instanceof Battleaxe)
                chances[i] = getWeaponModifierWeight(mod);
            else if(tool.getItem() instanceof HarvestTool)
                chances[i] = getToolModifierWeight(mod);
            else if(tool.getItem() instanceof BowBase)
                chances[i] = getBowModifierWeight(mod);
            else
                chances[i] = 0;

            // calculate extra bonus chance
            if(chances[i] > 0 && tags.hasKey(String.format("Extra%s", mod.toString()))) {
                float bonus = tags.getInteger(String.format("Extra%s", mod.toString()));
                // relativize bonus to xp. It matters how much you've done X during the levelup after all, not in total. We don't want +100% chance :P
                // basically if we didn't do this, the higher the xp required, the higher the chance.
                bonus /= (float)LevelingLogic.getRequiredXp(tool, tags);
                // maximal bonus obtainable should be ~20
                bonus *= 20;
                chances[i] += bonus;
            }

            total += chances[i];
            i++;
        }


        // check if we have modifiers (to prevent endless loops
        if(total == 0)
            return null;

        // try modifying
        // we can do this without getting an infinite loop, because redstone, lapis,... can be applied infinitely often
        boolean modified = false;
        Modifier choice = null;
        int tries = 0;
        while(!modified) {
            // get a random decision number
            int random = TConstruct.random.nextInt(total);
            int counter = 0;
            choice = null;

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
            modified = applyModifier(choice, player, tool);

            // your configs suck. can't apply any modifier?
            if(tries++ > 100)
            {
                choice = null;
                // make sure we really can't apply ANY modifier
                for(Modifier mod : Modifier.values())
                    if(applyModifier(mod, player, tool))
                    {
                        choice = mod;
                        modified = true;
                        break;
                    }

                // we really can't apply ANY modifier. whelp.
                if(!modified)
                    break;
            }
        }

        // restore modifiers
        modifiers = Math.max(0, modifiers); // ensure it never goes to -1. Should never happen, but.. safety never hurts
        tags.setInteger("Modifiers", modifiers);

        // if we couldn't find any, log an error
        if(!modified)
        {
            Log.warn(String.format("Couldn't find any applicable modifier to reward for %s's %s", player.getDisplayName(), tool.getDisplayName()));
            return null;
        }

        if(Config.logBonusExtraChance && tags.hasKey(String.format("Extra%s", choice.toString()))) {
            // same as above
            float bonus = tags.getInteger(String.format("Extra%s", choice.toString()));
            bonus /= (float)LevelingLogic.getRequiredXp(tool, tags);
            bonus *= 20;

            // now relativize the weight bonus to the total.
            Log.debug(String.format("Bonus weight for getting %s was %f", choice.toString(), bonus));
            Log.debug(String.format("Bonus chance for getting %s was %f %%", choice.toString(), 100f*bonus/(float)total));
        }


        // remove the extra chance for the received modifier
        resetModifierExtraWeight(choice, tags);

        return choice;
    }

    private static boolean applyModifier(Modifier modifier, EntityPlayer player, ItemStack tool)
    {
        switch (modifier)
        {
            // mining mods
            case REDSTONE:  return addRedstoneModifier(player, tool);
            case LAPIS:     return addLapisModifier(player, tool);
            case AUTOSMELT: return addAutoSmeltModifier(player, tool);
            case SILKTOUCH: return addSilktouchModifier(player, tool);
            // general modifier
            case DIAMOND:   return addDiamondModifier(player, tool);
            case EMERALD:   return addEmeraldModifier(player, tool);
            case REPAIR:    return addRepairModifier(player, tool);
            case REINFORCED:return addReinforcedModifier(player, tool);
            // combat modifier
            case ATTACK:    return addAttackModifier(player, tool);
            case BLAZE:     return addBlazeModifier(player, tool);
            case SMITE:     return addSmiteModifier(player, tool);
            case BANE:      return addAntiSpiderModifier(player, tool);
            case BEHEADING: return addBeheadingModifier(player, tool);
            case LIFESTEAL: return addLifeStealModifier(player, tool);
            case KNOCKBACK: return addKnockbackModifier(player, tool);
        }

        return false;
    }

    /* Mining Enchantments */

    public static boolean addRedstoneModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] redstoneStack = new ItemStack[]{new ItemStack(Items.redstone, 1)};

        return addGenericModifier(player, tool, "Redstone", redstoneStack, 50, 1, "message.levelup.redstone", "\u00a74");
    }

    public static boolean addLapisModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] lapisStack = new ItemStack[]{new ItemStack(Items.dye, 1, 4)};

        return addGenericModifier(player, tool, "Lapis", lapisStack, 100, 100, "message.levelup.lapis", "\u00a79");
    }

    public static boolean addAutoSmeltModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Lava", "message.levelup.autosmelt", "\u00a74");
    }

    public static boolean addSilktouchModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Silk Touch", "message.levelup.silktouch", "\u00a7e");
    }


    /* Tool Enchantments */

    public static boolean addDiamondModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Diamond", "message.levelup.diamond", "\u00a7b");
    }

    public static boolean addEmeraldModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Emerald", "message.levelup.emerald", "\u00a72");
    }

    // debateable if i'll ever use this
    public static boolean addFluxModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Flux", "message.levelup.flux", "\u00a7e");
    }

    public static boolean addRepairModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Moss", "message.levelup.repair", "\u00a72");
    }

    public static boolean addReinforcedModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Reinforced", "message.levelup.reinforced", "\u00a75");
    }


    /* Combat Enchantments */
    public static boolean addAttackModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] quarzStack = new ItemStack[]{new ItemStack(Items.quartz, 1)};

        return addGenericModifier(player, tool, "ModAttack", quarzStack, 24, 1, "message.levelup.attack", "\u00a7f");
    }

    public static boolean addBlazeModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] blazePowderStack = new ItemStack[]{new ItemStack(Items.blaze_powder, 1)};

        return addGenericModifier(player, tool, "Blaze", blazePowderStack, 25, 1, "message.levelup.blaze", "\u00a76");
    }

    public static boolean addSmiteModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] consecratedEartStack = new ItemStack[]{new ItemStack(TinkerTools.craftedSoil, 1, 4)};

        return addGenericModifier(player, tool, "ModSmite", consecratedEartStack, 36, 1,  "message.levelup.smite", "\u00a7e");
    }

    public static boolean addAntiSpiderModifier(EntityPlayer player, ItemStack tool)
    {
        ItemStack[] fermentedEyeStack = new ItemStack[]{new ItemStack(Items.fermented_spider_eye, 1)};

        return addGenericModifier(player, tool, "ModAntiSpider", fermentedEyeStack, 4, 1, "message.levelup.antispider", "\u00a72");
    }

    public static boolean addBeheadingModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Beheading", "message.levelup.beheading", "\u00a7d");
    }

    public static boolean addLifeStealModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Necrotic", "message.levelup.lifesteal", "\u00a78");
    }

    public static boolean addKnockbackModifier(EntityPlayer player, ItemStack tool)
    {
        return addGenericModifier(player, tool, "Piston", "message.levelup.knockback", "\u00a77");
    }


    /* Backbone ;o */

    // simple call
    private static boolean addGenericModifier(EntityPlayer player, ItemStack tool, String key, String message, String modColor)
    {
        return addGenericModifier(player, tool, key, null, 1, 1, message, modColor);
    }
    // I really didn't want to write the same 15 lines all over again to add a modifier >_<
    private static boolean addGenericModifier(EntityPlayer player, ItemStack tool, String key, ItemStack[] stacksToAdd, int times, int displayedTimes, String message, String modColor)
    {
        ItemModifier modifier = getModifier(key);
        // something happened. ohshit.
        if (modifier == null)
            return false;

        // custom stack or from tool?
        if(stacksToAdd == null)
            stacksToAdd = stackFromModifier(modifier);

        // can we apply the modifier?
        if(!modifier.matches(stacksToAdd, tool)) // this should call canModify
            return false;

        // message!
        if (player != null && !player.worldObj.isRemote) {
            player.addChatMessage(new ChatComponentText(LevelingTooltips.getInfoString(StatCollector.translateToLocal(message), EnumChatFormatting.DARK_AQUA, String.format("+%d %s", displayedTimes, StatCollector.translateToLocal(message + ".tag")), modColor)));
        }

        // apply modifier
        modifier.addMatchingEffect(tool); // order matters, effect has to be applied before modifying
        while(times-- > 0)
            modifier.modify(stacksToAdd, tool);


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


    /**
     * Used to add an extra chance for getting a specific modifier.
     * @param modifier The modifier which gains extra chance
     * @param amount How much weight shall be added
     * @param tags The InfiTool tagcompound of the tool
     */
    public static void addModifierExtraWeight(Modifier modifier, int amount, NBTTagCompound tags)
    {
        String key = "Extra" + modifier.toString();
        int old = 0;
        if(tags.hasKey(key))
            old = tags.getInteger(key);

        tags.setInteger(key, old + amount);
    }

    /**
     * Resets the extra chance for a modifier.
     * @param modifier The modifier which loses its extra chance
     * @param tags The InfiTool tagcompound of the tool
     */
    public static void resetModifierExtraWeight(Modifier modifier, NBTTagCompound tags)
    {
        String key = "Extra" + modifier.toString();
        if(tags.hasKey(key))
            tags.removeTag(key);
    }

    /* Modifier weights */
    private static int getToolModifierWeight(Modifier mod)
    {
        // useful bonuses
        switch(mod)
        {
            // mining mods
            case REDSTONE:  return 130;
            case LAPIS:     return 77;
            case AUTOSMELT: return 20;
            case SILKTOUCH: return 15;
            // general modifiers
            case DIAMOND:   return 30;
            case EMERALD:   return 35;
            case REPAIR:    return 50;
            case REINFORCED:return 88;
        }

        // less useful bonuses
        if(Config.randomBonusesAreUseful)
            return 0;

        switch(mod)
        {
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
            case LAPIS:     return 75;
            case REPAIR:    return 55;
            // combat modifiers
            case ATTACK:    return 110;
            case BLAZE:     return 45;
            case SMITE:     return 50;
            case BANE:      return 50;
            case BEHEADING: return 50;
            case LIFESTEAL: return 30;
            case KNOCKBACK: return 50;
        }

        if(Config.randomBonusesAreUseful)
            return 0;

        switch(mod)
        {
            // mining mods
            case REDSTONE:  return 0;
            case AUTOSMELT: return 15;
            case SILKTOUCH: return 5;
            // general modifiers
            case DIAMOND:   return 15;
            case EMERALD:   return 30;
            case REINFORCED:return 35;
            default: return 0;
        }
    }

    private static int getBowModifierWeight(Modifier mod)
    {
        switch(mod)
        {
            case REDSTONE:  return 100;
            case LAPIS:     return 75;
            case REPAIR:    return 40;
            // combat modifiers
            case ATTACK:    return 100;
            case BLAZE:     return 55;
            case SMITE:     return 40;
            case BANE:      return 40;
            case BEHEADING: return 20;
            case LIFESTEAL: return 40;
            case KNOCKBACK: return 20;
        }

        if(Config.randomBonusesAreUseful)
            return 0;

        switch(mod)
        {
            case AUTOSMELT: return 1;
            case SILKTOUCH: return 1;
            // general modifiers
            case DIAMOND:   return 15;
            case EMERALD:   return 30;
            case REINFORCED:return 40;
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
        REINFORCED,
        // weapon modifiers
        ATTACK,
        BLAZE,
        SMITE,
        BANE,
        BEHEADING,
        LIFESTEAL,
        KNOCKBACK;

        // !!! DO NOT CHANGE THESE !!!
        // They're used for NBTTags and Configs. Changing them would break compatibility
        @Override
        public String toString() {
            switch(this) {
                case REDSTONE: return "Redstone";
                case LAPIS: return "LuckLooting";
                case AUTOSMELT: return "Autosmelt";
                case SILKTOUCH: return "SilkTouch";
                case DIAMOND: return "Diamond";
                case EMERALD: return "Emerald";
                case REPAIR: return "Repair";
                case REINFORCED: return "Reinforced";
                case ATTACK: return "Attack";
                case BLAZE: return "Fiery";
                case SMITE: return "Smite";
                case BANE: return "BaneOfArthropods";
                case BEHEADING: return "Beheading";
                case LIFESTEAL: return "LifeSteal";
                case KNOCKBACK: return "Knockback";
                default: return super.toString();
            }
        }
    }
}
