package iguanaman.iguanatweakstconstruct.mobheads.blocks;

import iguanaman.iguanatweakstconstruct.mobheads.IguanaMobHeads;
import iguanaman.iguanatweakstconstruct.mobheads.tileentities.IguanaSkullTileEntity;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class IguanaSkullBlock extends BlockSkull {

	public IguanaSkullBlock() {
		super();

        this.setHardness(1.0F);
        this.setStepSound(Block.soundTypePiston);
        this.setBlockName(Reference.prefix("skull"));
        this.setBlockTextureName("skull");
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new IguanaSkullTileEntity();
	}

    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return IguanaMobHeads.skullItem;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return IguanaMobHeads.skullItem;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int noidea, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        if(world.getTileEntity(x,y,z) == null)
            return ret;

        ret.add(new ItemStack(IguanaMobHeads.skullItem, 1, this.getDamageValue(world, x,y,z)));
        return ret;
    }
}
