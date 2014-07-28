package iguanaman.iguanatweakstconstruct.old.modifiers;

import java.util.ArrayList;
import java.util.List;

import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.items.tools.Hammer;
import tconstruct.items.tools.Pickaxe;
import tconstruct.library.modifier.ItemModifier;

public class IguanaModHeads extends ItemModifier {

	String tooltipName;
	String color;
	int miningLevel;

	public IguanaModHeads(ItemStack[] items, int effect, int level, String k, String tip, String c)
	{
		super(items, effect, k);
		miningLevel = level;
		tooltipName = tip;
		color = c;
	}

	@Override
	protected boolean canModify (ItemStack tool, ItemStack[] input)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		if (tags.hasKey("MobHead")) return false;

		if (tool.getItem() instanceof Pickaxe || tool.getItem() instanceof Hammer)
		{
			if (tags.hasKey("HarvestLevelModified")) return false;
			int mLevel = tags.getInteger("HarvestLevel");
			if (mLevel > 1 && mLevel < miningLevel) return true;
		}

		return false;
	}

	@Override
	public void modify (ItemStack[] input, ItemStack tool)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

		tags.setBoolean("MobHead", true);
		tags.setBoolean(key, true);

		if (tool.getItem() instanceof Pickaxe)
		{
			tags.setInteger("HarvestLevel", tags.getInteger("HarvestLevel") + 1);
			tags.setBoolean("HarvestLevelModified", true);
			overwriteToolTip(tool);
		}

		String modTip = color + key;
		addToolTip(tool, tooltipName, modTip);
	}

	public void overwriteToolTip(ItemStack tool)
	{
		NBTTagCompound toolTag = tool.getTagCompound().getCompoundTag("InfiTool");

		//tooltip lists
		List<String> tips = new ArrayList<String>();
		List<String> modifierTips = new ArrayList<String>();

		tips.add("Mining Level: " + HarvestLevels.getHarvestLevelName(toolTag.getInteger("HarvestLevel")));
		modifierTips.add("");


		//get and remove tooltips
		int tipNum = 0;
		while (true)
		{
			String tip = "Tooltip" + ++tipNum;
			if (toolTag.hasKey(tip))
			{
				String tipString = toolTag.getString(tip);
				if (!tipString.startsWith("Head XP:") && !tipString.startsWith("Boost XP:")
						&& !tipString.startsWith("Mining Level:") && !tipString.contains("Requires boost"))
				{
					tips.add(toolTag.getString(tip));
					modifierTips.add(toolTag.getString("ModifierTip" + tipNum));
				}
				toolTag.removeTag(tip);
				toolTag.removeTag("ModifierTip" + tipNum);
			}
			else break;
		}

		//write tips
		for (int i = 1; i <= tips.size(); ++i)
			if (tips.get(i - 1) != null)
			{
				toolTag.setString("Tooltip" + i, tips.get(i - 1));
				if (modifierTips.get(i - 1) != null)
					toolTag.setString("ModifierTip" + i, modifierTips.get(i - 1));
				else
					toolTag.setString("ModifierTip" + i, "");
			}

	}

}
