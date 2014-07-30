package iguanaman.iguanatweakstconstruct.old.modifiers;

import iguanaman.iguanatweakstconstruct.old.IguanaConfig;
import iguanaman.iguanatweakstconstruct.reference.Config;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.AbilityHelper;
import tconstruct.library.tools.ToolCore;

public class IguanaModRepair extends ItemModifier {


	public IguanaModRepair()
	{
		super(new ItemStack[0], 0, "");
	}

	@Override
	public boolean matches (ItemStack[] input, ItemStack tool)
	{
		return canModify(tool, input);
	}

	@Override
	protected boolean canModify (ItemStack tool, ItemStack[] input)
	{
		if (input[0] == null && input[1] == null || input[0] != null && input[1] != null) //Only valid for one itemstack
			return false;

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		if (tags.getInteger("Damage") > 0)
		{
			int headID = tags.getInteger("Head");
			int matID = -1;
			if (input[0] != null)
				matID = PatternBuilder.instance.getPartID(input[0]);
			else
				matID = PatternBuilder.instance.getPartID(input[1]);

			if (matID == headID && amountToRepair(input, tool) > 0)
				return true;
		}
		return false;
	}

	@Override
	public void modify (ItemStack[] input, ItemStack tool)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		tags.setBoolean("Broken", false);

		int increase = amountToRepair(input, tool);

		int damage = tags.getInteger("Damage");
		int olddamage = damage;
		int repair = tags.getInteger("RepairCount");

		damage -= increase;
		if (damage < 0)
			damage = 0;
		tags.setInteger("Damage", damage);

		//repair += Math.round(((float)(olddamage - damage) / (float)durtotal) * 100f);
		repair += olddamage - damage;

		tags.setInteger("RepairCount", repair);

		AbilityHelper.damageTool(tool, 0, null, true);
		//tool.setItemDamage(damage * 100 / dur);

	}

	@Override
	public void addMatchingEffect (ItemStack tool) {}

	public int amountToRepair(ItemStack[] input, ItemStack tool)
	{

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		int dur = tags.getInteger("BaseDurability");

		int materialValue = 0;
		if (input[0] != null)
			materialValue = PatternBuilder.instance.getPartValue(input[0]);
		else
			materialValue = PatternBuilder.instance.getPartValue(input[1]);

		int increase = (int) (50 + dur * 0.4f * materialValue);
		//System.out.println("Increase: "+increase);

		int modifiers = tags.getInteger("Modifiers");
		float mods = 1.0f;
		if (modifiers == 2)
			mods = 0.8f;
		else if (modifiers == 1)
			mods = 0.6f;
		else if (modifiers == 0)
			mods = 0.4f;

		increase *= mods;
		increase /= ((ToolCore) tool.getItem()).getRepairCost();

		int durtotal = tags.getInteger("TotalDurability");
		int repair = tags.getInteger("RepairCount");

		float repairCount = (float)repair / (float)durtotal;

		if (IguanaConfig.repairLimitActive && repairCount >= IguanaConfig.repairLimit)
			return 0;

		increase = Math.round(increase / (IguanaConfig.repairCostPercentage / 100f));

		if (IguanaConfig.repairCostScaling)
		{
			repairCount /= IguanaConfig.repairScalingModifier;
			repairCount += 1F;
			increase = Math.round(increase / repairCount);
		}

		return increase;
	}

}
