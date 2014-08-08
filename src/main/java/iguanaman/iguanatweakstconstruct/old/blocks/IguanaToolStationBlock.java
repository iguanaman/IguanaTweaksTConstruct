package iguanaman.iguanatweakstconstruct.old.blocks;

import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import net.minecraft.block.material.Material;
import tconstruct.tools.blocks.ToolStationBlock;

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
