package iguanaman.iguanatweakstconstruct.modifiers;

import ic2.api.item.IElectricItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.ModElectric;

public class IguanaModElectric extends ModElectric {

	public IguanaModElectric() {
		super();

		/* IC2 */
		ItemStack reBattery = ic2.api.item.Items.getItem("reBattery");
		if (reBattery != null)
			batteries.add(reBattery);
		ItemStack chargedReBattery = ic2.api.item.Items.getItem("chargedReBattery");
		if (chargedReBattery != null)
			batteries.add(chargedReBattery);

		ItemStack electronicCircuit = ic2.api.item.Items.getItem("electronicCircuit");
		if (electronicCircuit != null)
			circuits.add(electronicCircuit);
	}

	/**
	 * 
	 * @param tool Tool to compare against
	 * @return Whether the tool can be modified
	 */
	@Override
	protected boolean canModify (ItemStack tool, ItemStack[] input)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		return tags.getInteger("Modifiers") > 1;
	}


	@Override
	public void modify (ItemStack[] input, ItemStack tool)
	{
		NBTTagCompound tags = tool.getTagCompound();

		if (!tags.hasKey(key))
		{
			tags.setBoolean(key, true);

			int modifiers = tags.getCompoundTag("InfiTool").getInteger("Modifiers");
			modifiers -= 2;
			tags.getCompoundTag("InfiTool").setInteger("Modifiers", modifiers);
			tags.getCompoundTag("InfiTool").setInteger("Damage", 0);
			int charge = 0;
			if (input[0].getItem() instanceof IElectricItem && input[0].hasTagCompound())
				charge = input[0].getTagCompound().getInteger("charge");
			if (input[1].getItem() instanceof IElectricItem && input[1].hasTagCompound())
				charge = input[1].getTagCompound().getInteger("charge");
			tags.setInteger("charge", charge);
			tags.setInteger(key, 1);
			addModifierTip(tool, "\u00a7eElectric");
			ToolCore toolcore = (ToolCore) tool.getItem();
			tool.setItemDamage(1 + (toolcore.getMaxCharge(tool) - charge) * (tool.getMaxDamage() - 1) / toolcore.getMaxCharge(tool));
		}
	}
}
