package iguanaman.iguanatweakstconstruct.claybuckets.items;

import iguanaman.iguanatweakstconstruct.claybuckets.IguanaItems;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class ClayBucket extends ItemBucket
{
    private final boolean isHot;

    public ClayBucket(Block contents, String name, String texture)
    {
        this(contents);

        this.setUnlocalizedName(Reference.MOD_ID + "." + name);
        this.setTextureName(Reference.resource(texture));
    }

    public ClayBucket(Block contents)
    {
        super(contents);
        this.setContainerItem(iguanaman.iguanatweakstconstruct.claybuckets.IguanaItems.clayBucketFired);

        if(contents == Blocks.flowing_lava)
            isHot = true;
        else
            isHot = false;
    }


    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        ItemStack result = super.onItemRightClick(itemStack, world, player);

        if(result.getItem() == Items.bucket) {
            // bucket is destroyed if it's a hot fluid
            if(isHot)
            {
                itemStack.stackSize--;
                return itemStack;
            }

            return new ItemStack(IguanaItems.clayBucketFired);
        }
        // water/lava pickup is handled in the ClayBucketHandler
        //if(result.getItem() == Items.water_bucket) return new ItemStack(IguanaItems.clayBucketWater);
        //if(result.getItem() == Items.lava_bucket) return new ItemStack(IguanaItems.clayBucketLava);
        return result;
    }
}
