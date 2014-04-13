package iguanaman.iguanatweakstconstruct.modifiers;

import iguanaman.iguanatweakstconstruct.IguanaLevelingLogic;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.ActiveToolMod;
import tconstruct.library.tools.ToolCore;

public class IguanaActiveToolMod extends ActiveToolMod {

	static List<Material> materialBlacklist = Arrays.asList(
			Material.leaves, Material.vine, Material.circuits,
			Material.glass, Material.piston, Material.snow
			);

	/* Harvesting */
	@Override
	public boolean beforeBlockBreak (ToolCore tool, ItemStack stack, int x, int y, int z, EntityLivingBase entity)
	{
		if (!(entity instanceof EntityPlayer)) return false;

		int bID = entity.worldObj.getBlockId(x, y, z);
		int meta = entity.worldObj.getBlockMetadata(x, y, z);
		Block block = Block.blocksList[bID];

		if (block == null || materialBlacklist.contains(block.blockMaterial)) return false;

		NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");

		int miningSpeed = tags.hasKey("MiningSpeed") ? tags.getInteger("MiningSpeed"): -1;
		int miningSpeed2 = tags.hasKey("MiningSpeed2") ? tags.getInteger("MiningSpeed2"): -1;
		int miningSpeedHandle = tags.hasKey("MiningSpeedHandle") ? tags.getInteger("MiningSpeedHandle"): -1;
		int miningSpeedExtra = tags.hasKey("MiningSpeedExtra") ? tags.getInteger("MiningSpeedExtra"): -1;

		if (miningSpeed > 0) tags.setInteger("MiningSpeed", miningSpeed * 2);
		if (miningSpeed2 > 0) tags.setInteger("MiningSpeed2", miningSpeed2 * 2);
		if (miningSpeedHandle > 0) tags.setInteger("MiningSpeedHandle", miningSpeedHandle * 2);
		if (miningSpeedExtra > 0) tags.setInteger("MiningSpeedExtra", miningSpeedExtra * 2);

		//IguanaLog.log(tool.canHarvestBlock(block) + " " + Float.toString(tool.getStrVsBlock(stack, block, meta)));

		if (tool.canHarvestBlock(block) && tool.getStrVsBlock(stack, block, meta) > 1f)
			//IguanaLog.log("xp added");
			IguanaLevelingLogic.addXP(stack, (EntityPlayer)entity, 1L);

		if (miningSpeed > 0) tags.setInteger("MiningSpeed", miningSpeed);
		if (miningSpeed2 > 0) tags.setInteger("MiningSpeed2", miningSpeed2);
		if (miningSpeedHandle > 0) tags.setInteger("MiningSpeedHandle", miningSpeedHandle);
		if (miningSpeedExtra > 0) tags.setInteger("MiningSpeedExtra", miningSpeedExtra);

		return false;
	}

}
