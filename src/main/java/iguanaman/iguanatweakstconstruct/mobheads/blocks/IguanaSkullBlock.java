package iguanaman.iguanatweakstconstruct.mobheads.blocks;

import iguanaman.iguanatweakstconstruct.mobheads.tileentities.IguanaSkullTileEntity;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class IguanaSkullBlock extends BlockSkull {

	public IguanaSkullBlock() {
		super();

        this.setHardness(1.0F);
        this.setStepSound(Block.soundTypeSand);
        this.setBlockName(Reference.block("skull"));
        this.setBlockTextureName("skullItem");
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new IguanaSkullTileEntity();
	}

}
