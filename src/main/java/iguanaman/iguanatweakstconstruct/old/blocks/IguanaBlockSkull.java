package iguanaman.iguanatweakstconstruct.old.blocks;

import net.minecraft.block.BlockSkull;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class IguanaBlockSkull extends BlockSkull {

	public IguanaBlockSkull() {
		super();
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new IguanaTileEntitySkull();
	}

}
