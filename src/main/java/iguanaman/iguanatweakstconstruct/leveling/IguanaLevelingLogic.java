package iguanaman.iguanatweakstconstruct.leveling;

import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import iguanaman.iguanatweakstconstruct.modifiers.*;
import iguanaman.iguanatweakstconstruct.reference.IguanaConfig;
import iguanaman.iguanatweakstconstruct.reference.IguanaReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import tconstruct.items.tools.*;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.Weapon;
import tconstruct.modifiers.tools.ModAntiSpider;
import tconstruct.modifiers.tools.ModInteger;
import tconstruct.modifiers.tools.ModReinforced;
import tconstruct.modifiers.tools.ModSmite;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that takes care of all the Tool XP related things.
 */
public class IguanaLevelingLogic {
    private static final String TAG_EXP = "ToolEXP";
    private static final String TAG_LEVEL = "ToolLevel";
    private static final String TAG_BOOST_EXP = "HeadEXP"; // HeadEXP for downwards compatibility

    public static final int MAX_LEVEL = 6;

    /**
     * Add the leveling specific NBT.
     * @param tag The tag that should recieve the data. Usually InfiTool Tag.
     */
    public static void addLevelingTags(NBTTagCompound tag)
    {
        // we start with level 1
        tag.setInteger(TAG_LEVEL, 1);
        // and no xp :(
        tag.setLong(TAG_EXP, 0);
        // mining level boost
        tag.setLong(TAG_BOOST_EXP, 0);
    }

	public static String getXpString(ItemStack tool)
	{
		return getXpString(tool, null);
	}

	public static String getXpString(ItemStack tool, boolean boostXp)
	{
		return getXpToolTip(tool, null, boostXp);
	}

	public static String getXpString(ItemStack tool, NBTTagCompound tags)
	{
		return getXpToolTip(tool, tags, false);
	}

    /**
     * Returns the XP string for the ToolTip.
     * @param boostXp If true, the xp for the mining level boost will be returned instead of the xp for the next tool level.
     */
	public static String getXpToolTip(ItemStack tool, NBTTagCompound tags, boolean boostXp)
	{
		if (tags == null) tags = tool.getTagCompound().getCompoundTag("InfiTool");

		int requiredXp = getRequiredXp(tool, tags, boostXp);
		long currentXp = boostXp ? tags.getLong(TAG_BOOST_EXP) : tags.getLong(TAG_EXP);
		float xpPercentage = (float)currentXp / (float)requiredXp * 100f;
		String xpPercentageString = String.format("%.2f", xpPercentage) + "%";

		String prefix = boostXp ? "Mining Boost XP: " : "XP: ";

		if (IguanaConfig.detailedXpTooltip)
			return prefix + Long.toString(currentXp) + " / " + Integer.toString(requiredXp) + " (" + xpPercentageString + ")";
		else
			return prefix + xpPercentageString;
	}

	public static String getLevelTooltip(int level)
	{
		switch (level)
		{
		case 1: return "Skill Level: \u00a74Clumsy";
		case 2: return "Skill Level: \u00a76Comfortable";
		case 3: return "Skill Level: \u00a7eAccustomed";
		case 4: return "Skill Level: \u00a72Adept";
		case 5: return "Skill Level: \u00a73Expert";
		case 6: return "Skill Level: \u00a7dMaster";
		default: return "";
		}
	}

    /**
     * Updates the tool information with the given tool and boost xp. This SETS the xp!
     * @param player     Required for awesome *ding* sound
     * @param toolXP     Value the tool XP shall be set to. -1 for no change.
     * @param boostXP    Value the mining-boost XP shall be set to. -1 for no change.
     */
	public static void updateXP(ItemStack tool, EntityPlayer player, long toolXP, long boostXP)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

		if (!tags.hasKey(TAG_LEVEL)) return;

		int level = tags.getInteger(TAG_LEVEL);
		int hLevel = tags.hasKey("HarvestLevel") ? tags.getInteger("HarvestLevel") : -1;

		boolean leveled = false;
		boolean pickLeveled = false;

        // Update Tool XP
		if (tags.hasKey(TAG_EXP) && level > 0 && level < MAX_LEVEL && toolXP >= 0)
		{
            // set new xp value
			tags.setLong(TAG_EXP, toolXP);

			// check for levelup
			if (toolXP >= getRequiredXp(tool, tags))
			{
				levelUpTool(tool, player);
                // re-read updated level
                level = tags.getInteger(TAG_LEVEL);

				leveled = true;
			}
		}

        // handle mining boost XP
        if(IguanaConfig.levelingPickaxeBoost) {
            // already got a boost?
            if (tags.hasKey(TAG_BOOST_EXP) && !tags.hasKey("HarvestLevelModified"))
                // todo: figure out why this only applies between mining level copper and 7?
                if (hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() &&
                    (!IguanaConfig.pickaxeBoostRequired && hLevel < 6 || IguanaConfig.pickaxeBoostRequired && hLevel < 7)) {
                    tags.setLong(TAG_BOOST_EXP, boostXP);

                    // check for mining boost levelup!
                    if (boostXP >= getRequiredXp(tool, true)) {
                        levelUpPick(tool, player, leveled);
                        // re-read updated harvest level
                        hLevel = tags.getInteger("HarvestLevel");

                        pickLeveled = true;
                    }
                }
        }


        // if we got a levelup, play a sound!
		if ((leveled || pickLeveled) && !player.worldObj.isRemote)
            // TODO: investigate if there's a better way to play sounds? resourcelocation?
			player.worldObj.playSoundAtEntity(player, IguanaReference.MOD_ID.toLowerCase() + ":chime", 1.0F, 1.0F);


		//Recheck level
		//level = tags.getInteger("ToolLevel");
		//if (tags.hasKey("HarvestLevel")) hLevel = tags.getInteger("HarvestLevel");

		//tooltip lists
		List<String> tips = new ArrayList<String>();
		List<String> modifierTips = new ArrayList<String>();

		//add mining level tooltip
		if (tool.getItem() instanceof Pickaxe || tool.getItem() instanceof Hammer)
		{
			tips.add("Mining Level: " + IguanaTweaksTConstruct.getHarvestLevelName(hLevel));
			modifierTips.add("");
		}

		tips.add(getLevelTooltip(level));
		modifierTips.add("");

		if (IguanaConfig.showTooltipXP)
		{
            // display xp if we're below max level
			if (level < MAX_LEVEL)
			{
				tips.add(getXpString(tool, false));
				modifierTips.add("");
			}

            // display mining level boost xp if we're not done with it yet
			if (IguanaConfig.levelingPickaxeBoost)
				if (hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() && hLevel < TConstructRegistry.getMaterial("Manyullyn").harvestLevel()
				&& !tags.hasKey("HarvestLevelModified")
				&& (tool.getItem() instanceof Pickaxe || tool.getItem() instanceof Hammer))
				{
					tips.add(getXpString(tool, true));
					modifierTips.add("");
				}
		}

		//get and remove tooltips
		int tipNum = 0;
		while (true)
		{
			String tip = "Tooltip" + ++tipNum;
			if (tags.hasKey(tip))
			{
				String tipString = tags.getString(tip);
				if (!tipString.startsWith("XP:") && !tipString.startsWith("Mining Boost XP:")
						&& !tipString.startsWith("Skill Level:") && !tipString.startsWith("Mining Level:")
						&& !tipString.contains("Requires boost"))
				{
					tips.add(tipString);
					modifierTips.add(tags.getString("ModifierTip" + tipNum));
				}
				tags.removeTag(tip);
				tags.removeTag("ModifierTip" + tipNum);
			}
			else break;
		}


		if (pickLeveled)
		{
			tips.add("\u00a76Boosted");
			modifierTips.add("");
		}

		//write tips
		for (int i = 1; i <= tips.size(); ++i)
			if (tips.get(i - 1) != null)
			{
				tags.setString("Tooltip" + i, tips.get(i - 1));
				if (modifierTips.get(i - 1) != null)
					tags.setString("ModifierTip" + i, modifierTips.get(i - 1));
				else
					tags.setString("ModifierTip" + i, "");
			}
	}


	public static void addXP(ItemStack tool, EntityPlayer player, long xp)
	{
		if (player.capabilities.isCreativeMode) return;

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

        // tool EXP
		Long toolXp = -1L;
        if(tags.hasKey(TAG_EXP))
            toolXp = tags.getLong(TAG_EXP) + xp;

        // mininglevel boost EXP
        Long boostXp = -1L;
        if(tags.hasKey(TAG_BOOST_EXP))
            boostXp = tags.getLong(TAG_BOOST_EXP) + xp;


        // update the tool information
		updateXP(tool, player, toolXp, boostXp);
	}

	public static int getRequiredXp(ItemStack tool)
	{
		return getRequiredXp(tool, null);
	}

	public static int getRequiredXp(ItemStack tool, boolean pick)
	{
		return getRequiredXp(tool, null, pick);
	}

	public static int getRequiredXp(ItemStack tool, NBTTagCompound tags)
	{
		return getRequiredXp(tool, tags, false);
	}

	public static int getRequiredXp(ItemStack tool, NBTTagCompound tags, boolean pick)
	{
		if (tags == null) tags = tool.getTagCompound().getCompoundTag("InfiTool");

		float base = 400;

		if (tool.getItem() instanceof Weapon || tool.getItem() instanceof Shortbow)
		{
			if (tool.getItem() instanceof Scythe) base *= 1.5f;
			base *= IguanaConfig.xpRequiredWeaponsPercentage / 100f;
		}
		else
		{
			int miningSpeed = tags.getInteger("MiningSpeed");
			int divider = 1;
			if (tags.hasKey("MiningSpeed2"))
			{
				miningSpeed += tags.getInteger("MiningSpeed2");
				divider += 1;
			}
			if (tags.hasKey("MiningSpeedHandle"))
			{
				miningSpeed += tags.getInteger("MiningSpeedHandle");
				divider += 1;
			}
			if (tags.hasKey("MiningSpeedExtra"))
			{
				miningSpeed += tags.getInteger("MiningSpeedExtra");
				divider += 1;
			}

			base = 100f;
			base += (float)miningSpeed / (float)divider / 2f;

			if (tool.getItem() instanceof Hatchet) base /= 2f;
			else if (tool.getItem() instanceof Shovel) base *= 2f;
			else if (tool.getItem() instanceof Mattock) base *= 2.5f;
			else if (tool.getItem() instanceof LumberAxe) base *= 3f;
			else if (tool.getItem() instanceof Hammer) base *= 6f;
			else if (tool.getItem() instanceof Excavator) base *= 9f;

			base *= IguanaConfig.xpRequiredToolsPercentage / 100f;
		}

		if (pick)
		{
			int harvestLevelCopper = TConstructRegistry.getMaterial("Copper").harvestLevel();
			int harvestLevel = TConstructRegistry.getMaterial(tags.getInteger("Head")).harvestLevel();
			if (harvestLevel >= harvestLevelCopper) base *= Math.pow(IguanaConfig.xpPerLevelMultiplier, harvestLevel - harvestLevelCopper);
			base *= IguanaConfig.levelingPickaxeBoostXpPercentage / 100f;
		}
		else
		{
			int level = tags.getInteger("ToolLevel");
			if (level >= 1) base *= Math.pow(IguanaConfig.xpPerLevelMultiplier, level - 1);
		}

		return Math.round(base);
	}

    /**
     * Applies all the logic for increasing the tool level. This is only specific to the *tool* level, and has no relation to the mining-level-boost!
     */
	public static void levelUpTool(ItemStack stack, EntityPlayer player)
	{
		NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");
		World world = player.worldObj;

        // *ding* levelup!
		int level = tags.getInteger(TAG_LEVEL);
		tags.setInteger(TAG_LEVEL, ++level);

		boolean isTool = stack.getItem() instanceof HarvestTool;

        // reset tool xp to 0, since we're at a new level now
        tags.setLong(TAG_EXP, 0L);
		//updateXP(stack, player, 0l, -1l);

        // tell the player how awesome he is
        // Meaningful messages to the player
        if (!world.isRemote)
        {
            String message = "";
            switch (level)
            {
                case 2: message = "\u00a73You begin to feel comfortable handling the " + stack.getDisplayName(); break;
                case 3: message = "\u00a73You are now accustomed to the weight of the " + stack.getDisplayName(); break;
                case 4: message = "\u00a73You have become adept at handling the " + stack.getDisplayName(); break;
                case 5: message = "\u00a73You are now an expert at using the " + stack.getDisplayName() + "\u00a73!"; break;
                case 6: message = "\u00a73You have mastered the " + stack.getDisplayName() + "\u00a73!"; break;
            }

            if (!message.equalsIgnoreCase(""))
            {
                player.addChatMessage(new ChatComponentText(message));
            }
        }

        int currentModifiers = tags.getInteger("Modifiers");

        // Add Modifier for leveling up?
        if(IguanaConfig.toolLevelingExtraModifiers)
        {
            int modifiersToAdd = 0;
            // check if we are supposed to add a modifier at this levelup
            for(int lvl : IguanaConfig.toolModifiersAtLevels)
                if(level == lvl)
                    modifiersToAdd++;
                    // yes, no break. this means if a level is in the list multiple times, you get multiple modifiers

            if(modifiersToAdd > 0)
            {
                currentModifiers += modifiersToAdd;
                tags.setInteger("Modifiers", currentModifiers);

                // fancy message on clientside
                if(!world.isRemote) {
                    // todo: solve "modifier(s)" more beautiful because localization
                    if(world.rand.nextInt(10) < modifiersToAdd)
                        player.addChatMessage(new ChatComponentText("\u00a79More Bling for your Thing (+" + modifiersToAdd + " modifier" + (modifiersToAdd>1 ? "s" : "") + ")."));
                    else
                        player.addChatMessage(new ChatComponentText("\u00a79You notice room for improvement (+" + modifiersToAdd + " modifier" + (modifiersToAdd>1 ? "s" : "") + ")."));
                }
            }
        }


        // Add random bonuses on leveling up?
		if (IguanaConfig.toolLevelingRandomBonuses)
		{
			tags.setInteger("Modifiers", currentModifiers + 1);
			for (int i = 1; i <= 10; ++i) if (tryModify(player, stack, world.rand.nextInt(10), isTool)) break;
			tags.setInteger("Modifiers", currentModifiers);
		}
	}

	public static void levelUpPick(ItemStack stack, EntityPlayer player, boolean leveled)
	{
		NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");

		World world = player.worldObj;
		tags.getInteger(TAG_LEVEL);

        // reset miningboost xp to 0
        tags.setLong(TAG_BOOST_EXP, 0L);
		//updateXP(stack, player, -1L, 0L);

		if (!world.isRemote)
			if (!leveled)
                player.addChatMessage(new ChatComponentText("\u00a73Suddenly, a flash of light shines from the tip of your " + stack.getDisplayName() + "\u00a73 (+1 mining level)"));
			else
                player.addChatMessage(new ChatComponentText("\u00a79Suddenly, a flash of light shines from the tip of the pickaxe (+1 mining level)"));

		tags.setBoolean("HarvestLevelModified", true);
        // increase harvest level by 1
		tags.setInteger("HarvestLevel", tags.getInteger("HarvestLevel") + 1);
	}

	private static boolean tryModify(EntityPlayer player, ItemStack stack, int rnd, boolean isTool)
	{
		ItemModifier mod = null;
		Item item = stack.getItem();

		ItemStack[] nullItemStack = new ItemStack[] {};
		if (rnd < 1)
		{
			mod = new ModInteger(nullItemStack, 4, "Moss", IguanaConfig.mossRepairSpeed, "\u00a72", "Auto-Repair");
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79It seems to have accumulated a patch of moss (+1 repair)"));
		}
		else if (rnd < 2 && (!isTool && !(item instanceof Shortbow) || isTool && (item instanceof Pickaxe || item instanceof Hammer)))
		{
			mod = new IguanaModLapis(nullItemStack, 10, new int[]{100});
			if (((IguanaModLapis)mod).canModify(stack, nullItemStack)) {
				if (!player.worldObj.isRemote)
					player.addChatMessage(new ChatComponentText("\u00a79Perhaps holding on to it will bring you luck? (+100 luck)"));
			} else return false;
		}
		else if (rnd < 6 && (isTool || item instanceof Shortbow))
		{
			mod = new IguanaModRedstone(nullItemStack, 2, 50);
			if (((IguanaModRedstone)mod).canModify(stack, nullItemStack, true)) {
				if (!player.worldObj.isRemote)
					player.addChatMessage(new ChatComponentText("\u00a79You spin it around with a flourish (+1 haste)"));
			} else return false;
		}
		else if (rnd < 3 && !isTool && !(item instanceof Shortbow))
		{
			mod = new IguanaModAttack("Quartz", nullItemStack, 11, 30);
			if (((IguanaModAttack)mod).canModify(stack, nullItemStack, true)) {
				if (!player.worldObj.isRemote)
					player.addChatMessage(new ChatComponentText("\u00a79You take the time to sharpen the dull edges of the blade (+1 attack)"));
			} else return false;
		}
		else if (rnd < 4 && !isTool && !(item instanceof Shortbow))
		{
			mod = new ModInteger(nullItemStack, 13, "Beheading", 1, "\u00a7d", "Beheading");
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79You could take someones head off with that! (+1 beheading)"));
		}
		else if (rnd < 5 && !isTool && !(item instanceof Shortbow))
		{
			mod = new IguanaModBlaze(nullItemStack, 7, new int[]{25});
			if (((IguanaModBlaze)mod).canModify(stack, nullItemStack)) {
				if (!player.worldObj.isRemote)
					player.addChatMessage(new ChatComponentText("\u00a79It starts to feels more hot to the touch (+1 fire aspect)"));
			} else return false;
		}
		else if (rnd < 6 && !isTool && !(item instanceof Shortbow))
		{
			mod = new ModInteger(nullItemStack, 8, "Necrotic", 1, "\u00a78", "Life Steal");
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79It shudders with a strange energy (+1 life steal)"));
		}
		else if (rnd < 7 && !isTool && !(item instanceof Shortbow))
		{
			mod = new ModSmite("Smite", 14, nullItemStack, new int[]{ 36});
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79It begins to radiate a slight glow (+1 smite)"));
		}
		else if (rnd < 8 && !isTool && !(item instanceof Shortbow))
		{
			mod = new ModAntiSpider("Anti-Spider",15, nullItemStack, new int[]{ 4});
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79A strange odor emanates from the weapon (+1 bane of arthropods)"));
		}
		else if (rnd < 9 && !isTool)
		{
			mod = new IguanaModPiston(nullItemStack, 3, new int[]{10});
			if (((IguanaModPiston)mod).canModify(stack, nullItemStack)) {
				if (!player.worldObj.isRemote)
					player.addChatMessage(new ChatComponentText("\u00a79Feeling more confident, you can more easily keep your assailants at bay (+1 knockback)"));
			} else return false;
		}
		else if (rnd < 10)
		{
			mod = new ModReinforced(nullItemStack, 16, 1);
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79Fixing up the wear and tear should make it last a little longer (+1 reinforced)"));
		}

		if (mod == null) return false;

		mod.addMatchingEffect(stack);
		mod.modify(nullItemStack, stack);
		return true;
	}
}
