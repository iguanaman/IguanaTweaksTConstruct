package iguanaman.iguanatweakstconstruct.items;

import iguanaman.iguanatweakstconstruct.IguanaConfig;

import java.util.List;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class IguanaItemSkull extends ItemSkull {

    private static final String[] skullTypes = new String[] {"skeleton", "wither", "zombie", "char", "creeper", "enderman", "pigman", "blaze"};
    public static final String[] field_94587_a = new String[] {"skeleton", "wither", "zombie", "steve", "creeper", "skull_enderman", "skull_pigman", "skull_blaze"};
    
	public IguanaItemSkull(int par1) {
		super(par1);
	}

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int i = par1ItemStack.getItemDamage();

        if (i < 0 || i >= skullTypes.length)
        {
            i = 0;
        }
        
        if (i >= 5)
        	return "iguanatweakstconstruct:" + field_94587_a[i];
        else
        	return super.getUnlocalizedName() + "." + skullTypes[i];
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int j = 0; j < skullTypes.length; ++j)
        {
            par3List.add(new ItemStack(par1, 1, j));
        }
    }
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
    	if (IguanaConfig.mobHeadModifiers)
    	{
	    	int meta = par1ItemStack.getItemDamage();
	    	if (meta < 7) par3List.add("Pickaxe modifier"); 
	    	switch (meta)
	    	{
				case 0: par3List.add("Max tier: Copper"); break;
				case 1: par3List.add("Max tier: Alumite"); break;
				case 2: par3List.add("Max tier: Copper"); break;
				case 4: par3List.add("Max tier: Iron"); break;
				case 5: par3List.add("Max tier: Bronze"); break;
	    	}
    	}
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int par1)
    {
        if (par1 < 0 || par1 >= skullTypes.length)
        {
            par1 = 0;
        }

        return this.field_94586_c[par1];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.field_94586_c = new Icon[field_94587_a.length];

        for (int i = 0; i < field_94587_a.length; ++i)
        {
        	if (i >= 5)
                this.field_94586_c[i] = par1IconRegister.registerIcon("iguanatweakstconstruct:" + field_94587_a[i]);
        	else
        		this.field_94586_c[i] = par1IconRegister.registerIcon(this.getIconString() + "_" + field_94587_a[i]);
        }
    }

}
