package iguanaman.iguanatweakstconstruct.claybuckets.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iguanaman.iguanatweakstconstruct.claybuckets.IguanaItems;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class WearableBucket extends Item {
    private static final String[] textureTypes = new String[] {"bucket_helmet", "enderman_jaw"};
    private IIcon[] icons;

    public WearableBucket() {
        super();
        this.setContainerItem(IguanaItems.wearableBuckets);
        this.setUnlocalizedName(Reference.item(".wearable_bucket"));

        this.setMaxStackSize(1);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
        return armorType == 0; // 0 = helmet
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int i = par1ItemStack.getItemDamage();

        if (i < 0 || i >= textureTypes.length)
            i = 0;

        return Reference.item(textureTypes[i]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int j = 0; j < textureTypes.length; ++j)
            par3List.add(new ItemStack(par1, 1, j));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        icons = new IIcon[textureTypes.length];

        for (int i = 0; i < textureTypes.length; ++i)
            icons[i] = par1IconRegister.registerIcon(Reference.resource(textureTypes[i]));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
        if (par1 < 0 || par1 >= textureTypes.length)
            par1 = 0;

        return icons[par1];
    }
}
