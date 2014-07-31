package iguanaman.iguanatweakstconstruct.claybuckets.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iguanaman.iguanatweakstconstruct.claybuckets.IguanaItems;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.smeltery.items.FilledBucket;

public class ClayBucketTinkerLiquids extends FilledBucket {
    private final boolean isHot;

    public ClayBucketTinkerLiquids(Block b) {
        super(b);

        this.setUnlocalizedName(Reference.MOD_ID + ".clayBucketTinkerLiquid");
        this.setContainerItem(IguanaItems.clayBucketFired);

        // all fluids above 1000° are hot. Lava has 1300.
        Fluid fluid = FluidRegistry.lookupFluidForBlock(b);
        if(fluid == null)
            isHot = false;
        else
            isHot = fluid.getTemperature() >= 1000;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        ItemStack result = super.onItemRightClick(itemStack, world, player);

        if(result.getItem() == Items.bucket)
        {
            FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(itemStack);
            // bucket is destroyed if it's a hot fluid
            if(fluidStack != null && fluidStack.getFluid().getTemperature() >= 1000)
            {
                itemStack.stackSize--;
                return itemStack;
            }

            return new ItemStack(IguanaItems.clayBucketFired);
        }
        return result;
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
