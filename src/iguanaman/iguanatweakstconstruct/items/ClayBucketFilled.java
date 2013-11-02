package iguanaman.iguanatweakstconstruct.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tconstruct.items.FilledBucket;

public class ClayBucketFilled extends FilledBucket {

	public ClayBucketFilled(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		ItemStack result = super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		if (result.itemID == Item.bucketEmpty.itemID) {
            --par1ItemStack.stackSize;
    		return par1ItemStack;
		}
		return result;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("iguanatweakstconstruct:clayBucket_" + textureNames[i]);
        }
    }

}
