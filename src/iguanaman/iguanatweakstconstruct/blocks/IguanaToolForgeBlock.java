package iguanaman.iguanatweakstconstruct.blocks;

import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tconstruct.blocks.ToolForgeBlock;
import tconstruct.blocks.logic.ToolForgeLogic;

public class IguanaToolForgeBlock extends ToolForgeBlock {

	public IguanaToolForgeBlock(int id, Material material) {
		super(id, material);
		// TODO Auto-generated constructor stub
	}

    @Override
    public Object getModInstance ()
    {
        return IguanaTweaksTConstruct.instance;
    }

}
