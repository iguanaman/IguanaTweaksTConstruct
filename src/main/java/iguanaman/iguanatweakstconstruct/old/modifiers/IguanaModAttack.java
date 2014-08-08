package iguanaman.iguanatweakstconstruct.old.modifiers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.modifier.IModifyable;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.ToolCore;

import java.util.Arrays;
import java.util.List;

public class IguanaModAttack extends ItemModifier {

	String tooltipName;
	int increase;
	int max = 120;
	int amount = Math.round(max / 3f);
	String guiType;

	public IguanaModAttack(String type, ItemStack[] items, int effect, int inc)
	{
		super(items, effect, "ModAttack");
		tooltipName = "\u00a7fSharpness";
		guiType = type;
		increase = inc;
	}

	public boolean canModify (ItemStack tool, ItemStack[] input, boolean automated)
	{
		ToolCore toolItem = (ToolCore) tool.getItem();
		if (!validType(toolItem))
			return false;

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

		if (automated)
		{
			if (tags.hasKey(key))
			{
				int keyPair[] = tags.getIntArray(key);
				if (keyPair[0] + increase <= max)
					return true;
			} else
				return true;
		}
		else
		{
			if (!tags.hasKey(key))
				return tags.getInteger("Modifiers") > 0;

				int keyPair[] = tags.getIntArray(key);

				if (keyPair[0] % amount == 0)
					return tags.getInteger("Modifiers") > 0;

					if (keyPair[0] + increase <= keyPair[1])
						return true;
		}

		return false;
	}

	@Override
	public boolean canModify (ItemStack tool, ItemStack[] input)
	{
		return canModify(tool, input, false);
	}

	@Override
	public void modify (ItemStack[] input, ItemStack tool)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		if (tags.hasKey(key))
		{

			int[] keyPair = tags.getIntArray(key);
			if (keyPair[0] % amount == 0)
			{
				int modifiers = tags.getInteger("Modifiers");
				modifiers -= 1;
				tags.setInteger("Modifiers", modifiers);
			}

			if (keyPair[0] + increase >= keyPair[1])
			{
				int attack = tags.getInteger("Attack");
				attack += 1;
				tags.setInteger("Attack", attack);

				keyPair[1] += amount;
			}

			keyPair[0] += increase;
			tags.setIntArray(key, keyPair);

			updateModTag(tool, keyPair);

		}
		else
		{
			int modifiers = tags.getInteger("Modifiers");
			modifiers -= 1;
			tags.setInteger("Modifiers", modifiers);
			String modName = "\u00a7f" + guiType + " (" + increase + "/" + max + ")";
			int tooltipIndex = addToolTip(tool, tooltipName, modName);
			int[] keyPair = new int[] { increase, amount, tooltipIndex };
			tags.setIntArray(key, keyPair);
		}
	}

	void updateModTag (ItemStack tool, int[] keys)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		String tip = "ModifierTip" + keys[2];
		String modName = "\u00a7f" + guiType + " (" + keys[0] + "/" + keys[1] + ")";
		tags.setString(tip, modName);
	}

	@Override
	public boolean validType (IModifyable input)
	{
		return input.getModifyType().equals("Tool");
		/*List list = Arrays.asList(tool.toolCategories());
        return list.contains("melee") || list.contains("harvest") || list.contains("ammo");*/
	}

	public boolean nerfType (IModifyable input)
	{
		List<String> list = Arrays.asList(input.getTraits());
		return list.contains("throwing") || list.contains("ammo");
	}

}
