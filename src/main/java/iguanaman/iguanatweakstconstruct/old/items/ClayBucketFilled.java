package iguanaman.iguanatweakstconstruct.old.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tconstruct.smeltery.items.FilledBucket;

public class ClayBucketFilled extends FilledBucket {

	public ClayBucketFilled(Block contents) {
		super(contents);
	}

	/**
	 * Called whenever this prefix is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		ItemStack result = super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		if (result.getItem() == Items.bucket) {
			--par1ItemStack.stackSize;
			return par1ItemStack;
		}
		return result;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons (IIconRegister iconRegister)
	{
		icons = new IIcon[textureNames.length];

		for (int i = 0; i < icons.length; ++i)
			icons[i] = iconRegister.registerIcon("iguanatweakstconstruct:clayBucket_" + textureNames[i]);
	}

}
