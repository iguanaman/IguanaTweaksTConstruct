package iguanaman.iguanatweakstconstruct.blocks;

import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import net.minecraft.block.material.Material;
import tconstruct.blocks.ToolForgeBlock;

public class IguanaToolForgeBlock extends ToolForgeBlock {

	public IguanaToolForgeBlock(int id, Material material) {
		super(id, material);
	}

	@Override
	public Object getModInstance ()
	{
		return IguanaTweaksTConstruct.instance;
	}

}
