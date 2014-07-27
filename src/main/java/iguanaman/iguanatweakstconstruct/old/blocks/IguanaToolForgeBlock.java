package iguanaman.iguanatweakstconstruct.old.blocks;

import tconstruct.tools.blocks.ToolForgeBlock;
import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import net.minecraft.block.material.Material;


public class IguanaToolForgeBlock extends ToolForgeBlock {

	public IguanaToolForgeBlock(Material material) {
		super(material);
	}

	@Override
	public Object getModInstance ()
	{
		return IguanaTweaksTConstruct.instance;
	}

}
