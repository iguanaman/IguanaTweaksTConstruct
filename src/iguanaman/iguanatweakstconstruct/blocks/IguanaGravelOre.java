package iguanaman.iguanatweakstconstruct.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.world.World;

import tconstruct.blocks.GravelOre;

public class IguanaGravelOre extends GravelOre {

	public IguanaGravelOre(int id) {
		super(id);
	}

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            if (canFallBelow(par1World, par2, par3 - 1, par4) && par3 >= 0)
            {
                byte b0 = 32;

                if (!fallInstantly && par1World.checkChunksExist(par2 - b0, par3 - b0, par4 - b0, par2 + b0, par3 + b0, par4 + b0))
                {
                    if (!par1World.isRemote)
                    {
                        EntityFallingSand entityfallingsand = new EntityFallingSand(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), this.blockID, par1World.getBlockMetadata(par2, par3, par4));
                        entityfallingsand.shouldDropItem = false;
                        this.onStartFalling(entityfallingsand);
                        par1World.spawnEntityInWorld(entityfallingsand);
                    }
                }
                else
                {
                    par1World.setBlockToAir(par2, par3, par4);

                    while (canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0)
                    {
                        --par3;
                    }

                    if (par3 > 0)
                    {
                        par1World.setBlock(par2, par3, par4, this.blockID);
                    }
                }
            }
        }
    }


}
