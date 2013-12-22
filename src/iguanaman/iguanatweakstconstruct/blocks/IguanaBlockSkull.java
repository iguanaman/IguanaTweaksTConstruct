package iguanaman.iguanatweakstconstruct.blocks;

import net.minecraft.block.BlockSkull;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class IguanaBlockSkull extends BlockSkull {

	public IguanaBlockSkull(int par1) {
		super(par1);
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World par1World)
	{
		return new IguanaTileEntitySkull();
	}

}
