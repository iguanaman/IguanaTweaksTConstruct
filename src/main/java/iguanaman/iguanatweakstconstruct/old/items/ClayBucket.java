package iguanaman.iguanatweakstconstruct.old.items;

import cpw.mods.fml.common.eventhandler.Event;
import iguanaman.iguanatweakstconstruct.old.IguanaItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class ClayBucket extends ItemBucket
{

	protected Block isFull = Blocks.air; 
	
    public ClayBucket(Block contents)
    {
        super(contents);
        isFull = contents;
    }

    /**
     * Called whenever this prefix is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick (ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        boolean flag = isFull == Blocks.air;
        MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, flag);
        if (movingobjectposition == null)
            return par1ItemStack;
        else
        {
            FillBucketEvent event = new FillBucketEvent(par3EntityPlayer, par1ItemStack, par2World, movingobjectposition);
            if (MinecraftForge.EVENT_BUS.post(event))
                return par1ItemStack;

            if (event.getResult() == Event.Result.ALLOW)
            {
                if (par3EntityPlayer.capabilities.isCreativeMode)
                    return par1ItemStack;

                if (--par1ItemStack.stackSize <= 0)
                    return event.result;

                if (!par3EntityPlayer.inventory.addItemStackToInventory(event.result))
                    par3EntityPlayer.dropPlayerItemWithRandomChoice(event.result, false);

                return par1ItemStack;
            }

            if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK)
            {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;
                Block bf =  par2World.getBlock(i, j, k);
                if (bf instanceof IFluidBlock)
                {
                    Fluid fl = FluidRegistry.lookupFluidForBlock(bf);
                    if (fl != null)
                    {
                       String name = fl.getUnlocalizedName().toLowerCase();
                       if (name.contains("witchwater") || !(name.contains("water") || name.contains("lava") || name.contains("milk")))
                          return par1ItemStack;
                    }
                }
                if (!par2World.canMineBlock(par3EntityPlayer, i, j, k))
                    return par1ItemStack;

                if (flag)
                {
                    if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, par1ItemStack))
                        return par1ItemStack;

                    Material material = par2World.getBlock(i, j, k).getMaterial();
                    int blockMeta = par2World.getBlockMetadata(i, j, k);
                    
                    if (material == Material.water && blockMeta == 0)
                    {
                        par2World.setBlockToAir(i, j, k);

                        if (par3EntityPlayer.capabilities.isCreativeMode)
                            return par1ItemStack;

                        if (--par1ItemStack.stackSize <= 0)
                            return new ItemStack(IguanaItems.clayBucketWater);

                        if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(IguanaItems.clayBucketWater)))
                            par3EntityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(IguanaItems.clayBucketWater, 1, 0) , false);

                        return par1ItemStack;
                    }

                    if (material == Material.lava && blockMeta == 0)
                    {
                        par2World.setBlockToAir(i, j, k);

                        if (par3EntityPlayer.capabilities.isCreativeMode)
                            return par1ItemStack;

                        if (--par1ItemStack.stackSize <= 0)
                            return new ItemStack(IguanaItems.clayBucketLava);

                        if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(IguanaItems.clayBucketLava)))
                            par3EntityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(IguanaItems.clayBucketLava, 1, 0), false);

                        return par1ItemStack;
                    }
                }
                else
                {
                    if (isFull == Blocks.air)
                        return new ItemStack(IguanaItems.clayBucketFired);

                    if (movingobjectposition.sideHit == 0)
                        --j;

                    if (movingobjectposition.sideHit == 1)
                        ++j;

                    if (movingobjectposition.sideHit == 2)
                        --k;

                    if (movingobjectposition.sideHit == 3)
                        ++k;

                    if (movingobjectposition.sideHit == 4)
                        --i;

                    if (movingobjectposition.sideHit == 5)
                        ++i;

                    if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, par1ItemStack))
                        return par1ItemStack;

                    if (tryPlaceContainedLiquid(par2World, i, j, k) && !par3EntityPlayer.capabilities.isCreativeMode)
                        if (isFull == Blocks.flowing_lava)
                        {
                            --par1ItemStack.stackSize;
                            return par1ItemStack;
                        }
                        else
                            return new ItemStack(IguanaItems.clayBucketFired);
                }
            }
            else if (isFull == Blocks.air && movingobjectposition.entityHit instanceof EntityCow)
                return new ItemStack(IguanaItems.clayBucketMilk);

            return par1ItemStack;
        }
    }

}
