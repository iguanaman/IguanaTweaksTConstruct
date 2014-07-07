package iguanaman.iguanatweakstconstruct.blocks;

import tconstruct.tools.blocks.ToolStationBlock;
import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import net.minecraft.block.material.Material;

public class IguanaToolStationBlock extends ToolStationBlock {

	public IguanaToolStationBlock(Material material) {
		super(material);
	}

	@Override
	public Object getModInstance ()
	{
		return IguanaTweaksTConstruct.instance;
	}

}
