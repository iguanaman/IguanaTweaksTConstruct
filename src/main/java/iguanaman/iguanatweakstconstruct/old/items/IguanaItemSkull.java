package iguanaman.iguanatweakstconstruct.old.items;

import iguanaman.iguanatweakstconstruct.reference.Config;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IguanaItemSkull extends ItemSkull {

	private static final String[] skullTypes = new String[] {"skeleton", "wither", "zombie", "char", "creeper", "enderman", "pigman", "blaze"};
	public static final String[] field_94587_a = new String[] {"skeleton", "wither", "zombie", "steve", "creeper", "skull_enderman", "skull_pigman", "skull_blaze"};
	@SideOnly(Side.CLIENT)
	private IIcon field_94586_c [];

	public IguanaItemSkull() {
		super();
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
	 * different names based on their damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		int i = par1ItemStack.getItemDamage();

		if (i < 0 || i >= skullTypes.length)
			i = 0;

		if (i >= 5)
			return "iguanatweakstconstruct:" + field_94587_a[i];
		else
			return super.getUnlocalizedName() + "." + skullTypes[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int j = 0; j < skullTypes.length; ++j)
			par3List.add(new ItemStack(par1, 1, j));
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (Config.mobHeadPickaxeBoost)
		{
			int meta = par1ItemStack.getItemDamage();
			switch (meta)
			{
			case 0: par3List.add("Use on: Copper pick"); break;
			case 1: par3List.add("Use on: Copper, Iron, Bronze or Alumite pick"); break;
			case 2: par3List.add("Use on: Copper pick"); break;
			case 4: par3List.add("Use on: Copper or Iron pick"); break;
			case 5: par3List.add("Use on: Copper, Iron or Bronze pick"); break;
			}
			if (meta != 3 && meta <= 5) par3List.add("\u00a76Pickaxe booster (+1 Mining Level)");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value
	 */
	public IIcon getIconFromDamage(int par1)
	{
		if (par1 < 0 || par1 >= skullTypes.length)
			par1 = 0;

		return field_94586_c[par1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		field_94586_c = new IIcon[field_94587_a.length];

		for (int i = 0; i < field_94587_a.length; ++i)
			if (i >= 5)
				field_94586_c[i] = par1IconRegister.registerIcon("iguanatweakstconstruct:" + field_94587_a[i]);
			else
				field_94586_c[i] = par1IconRegister.registerIcon(getIconString() + "_" + field_94587_a[i]);
	}

}
