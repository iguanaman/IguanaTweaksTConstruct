package iguanaman.iguanatweakstconstruct.old.modifiers;

import iguanaman.iguanatweakstconstruct.reference.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.items.tools.Hammer;
import tconstruct.items.tools.Pickaxe;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.modifier.IModifyable;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.ToolCore;

public class IguanaModRedstone extends ItemModifier {

	String tooltipName;
	int increase;
	int max;

	public IguanaModRedstone(ItemStack[] items, int effect, int inc) {
		super(items, effect, "Redstone");
		tooltipName = "\u00a74Haste";
		increase = inc;
		max = 50;
	}

	@Override
	protected boolean canModify (ItemStack tool, ItemStack[] input)
	{
		return canModify(tool, input, false);
	}

	public boolean canModify (ItemStack tool, ItemStack[] input, boolean auto)
	{
		ToolCore toolItem = (ToolCore) tool.getItem();
		if (!validType(toolItem))
			return false;

		if (auto) return true;

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		if (!tags.hasKey(key))
			return tags.getInteger("Modifiers") > 0;

			int keyPair[] = tags.getIntArray(key);
			if (keyPair[0] + increase <= keyPair[1])
				return true;

			else if (keyPair[0] == keyPair[1])
				return tags.getInteger("Modifiers") > 0;

				else
					return false;
	}

	@Override
	public void modify (ItemStack[] input, ItemStack tool)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

		int[] keyPair;
		if (tags.hasKey(key))
		{
			keyPair = tags.getIntArray(key);
			if (keyPair[0] % max == 0)
			{
				keyPair[0] += increase;
				keyPair[1] += max;
				tags.setIntArray(key, keyPair);

				int modifiers = tags.getInteger("Modifiers");
				modifiers -= 1;
				tags.setInteger("Modifiers", modifiers);
			}
			else
			{
				keyPair[0] += increase;
				tags.setIntArray(key, keyPair);
			}
			updateModTag(tool, keyPair);
		}
		else
		{
			int modifiers = tags.getInteger("Modifiers");
			modifiers -= 1;
			tags.setInteger("Modifiers", modifiers);
			String modName = "\u00a74Redstone (" + increase + "/" + max + ")";
			int tooltipIndex = addToolTip(tool, tooltipName, modName);
			keyPair = new int[] { increase, max, tooltipIndex };
			tags.setIntArray(key, keyPair);
		}

		// Get current xp as a percentage
		float xpPercentage = -1f;
		if (tags.hasKey("ToolEXP"))
		{
			//int requiredXp = IguanaLevelingLogic.getRequiredXp(tool, tags, false);
			long currentXp = tags.getLong("ToolEXP");
			//xpPercentage = (float)currentXp / (float)requiredXp;
		}

		// Get current pick xp as a percentage
		float headXpPercentage = -1f;
		if (tags.hasKey("HeadEXP"))
		{
			//int requiredXp = IguanaLevelingLogic.getRequiredXp(tool, tags, true);
			long currentXp = tags.getLong("HeadEXP");
			//headXpPercentage = (float)currentXp / (float)requiredXp;
		}


		int miningSpeed = tags.getInteger("MiningSpeed");
		miningSpeed += increase * 4;
		tags.setInteger("MiningSpeed", miningSpeed);

		if (tags.hasKey("MiningSpeed2"))
		{
			int miningSpeed2 = tags.getInteger("MiningSpeed2");
			miningSpeed2 += increase * 4;
			tags.setInteger("MiningSpeed2", miningSpeed2);
		}

		if (tags.hasKey("DrawSpeed"))
		{
			//int drawSpeed = tags.getInteger("DrawSpeed");
			int baseDrawSpeed = tags.getInteger("BaseDrawSpeed");
			int drawSpeed = (int) (baseDrawSpeed - 0.1f * baseDrawSpeed * (keyPair[0] / 50f));
			tags.setInteger("DrawSpeed", drawSpeed);
		}

		// Reset xp based on previous percentage
		if (tags.hasKey("ToolEXP"))
		{
			//int newRequiredXp = IguanaLevelingLogic.getRequiredXp(tool, tags, false);
			//long newXp = Math.round(newRequiredXp * xpPercentage);
			//tags.setLong("ToolEXP", newXp);
		}

		// Reset head xp based on previous percentage
		if (tags.hasKey("HeadEXP"))
		{
			//int newRequiredXp = IguanaLevelingLogic.getRequiredXp(tool, tags, true);
			//long newXp = Math.round(newRequiredXp * headXpPercentage);
			//tags.setLong("HeadEXP", newXp);
		}

		//tooltip lists
		List<String> tips = new ArrayList<String>();
		List<String> modifierTips = new ArrayList<String>();

		if (Config.showTooltipXP)
		{
			int level = tags.getInteger("ToolLevel");
			if (level <= 5)
			{
				//tips.add(IguanaLevelingTooltips.getXpString(tool, false));
				modifierTips.add("");
			}

			if (Config.levelingPickaxeBoost)
			{
				int hLevel = tags.hasKey("HarvestLevel") ? hLevel = tags.getInteger("HarvestLevel") : -1;
				if (hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() && hLevel < TConstructRegistry.getMaterial("Manyullyn").harvestLevel()
						&& !tags.hasKey("HarvestLevelModified")
						&& (tool.getItem() instanceof Pickaxe || tool.getItem() instanceof Hammer))
				{
					//tips.add(IguanaLevelingTooltips.getXpString(tool, true));
					modifierTips.add("");
				}
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
				if (!tipString.startsWith("XP:") && !tipString.startsWith("Head XP:") && !tipString.startsWith("Boost XP:"))
				{
					tips.add(tipString);
					modifierTips.add(tags.getString("ModifierTip" + tipNum));
				}
				tags.removeTag(tip);
				tags.removeTag("ModifierTip" + tipNum);
			}
			else break;
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

	void updateModTag (ItemStack tool, int[] keys)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		String tip = "ModifierTip" + keys[2];
		String modName = "\u00a74Redstone (" + keys[0] + "/" + keys[1] + ")";
		tags.setString(tip, modName);
	}

	@Override
	public boolean validType (IModifyable input)
	{
		List<String> list = Arrays.asList(input.getTraits());
		return list.contains("harvest") || list.contains("utility") || list.contains("bow");
	}

}
