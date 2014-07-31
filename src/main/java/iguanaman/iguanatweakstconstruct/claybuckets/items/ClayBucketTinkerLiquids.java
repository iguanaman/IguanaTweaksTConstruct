package iguanaman.iguanatweakstconstruct.claybuckets.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iguanaman.iguanatweakstconstruct.claybuckets.IguanaItems;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import tconstruct.smeltery.items.FilledBucket;

public class ClayBucketTinkerLiquids extends FilledBucket {

    public ClayBucketTinkerLiquids(Block b) {
        super(b);

        this.setUnlocalizedName(Reference.MOD_ID + ".clayBucketTinkerLiquid");
        this.setContainerItem(IguanaItems.clayBucketFired);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon(Reference.resource("clayBucket_" + textureNames[i]));
        }
    }

    @Override
    public String getUnlocalizedName (ItemStack stack)
    {
        int arr = MathHelper.clamp_int(stack.getItemDamage(), 0, materialNames.length);
        return Reference.MOD_ID + ".clayBucketTinkerLiquid." + materialNames[arr];
    }
}
