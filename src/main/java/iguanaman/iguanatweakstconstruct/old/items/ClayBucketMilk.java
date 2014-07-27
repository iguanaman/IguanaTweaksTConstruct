package iguanaman.iguanatweakstconstruct.old.items;

import iguanaman.iguanatweakstconstruct.old.IguanaItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ClayBucketMilk extends ItemBucketMilk {

	public ClayBucketMilk() {
		super();
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		ItemStack result = super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
		if (result.getItem()== Items.bucket) return new ItemStack(IguanaItems.clayBucketFired);
		return result;
	}

}
