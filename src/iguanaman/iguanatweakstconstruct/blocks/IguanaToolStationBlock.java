package iguanaman.iguanatweakstconstruct.blocks;

import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tconstruct.TConstruct;
import tconstruct.blocks.ToolStationBlock;
import tconstruct.blocks.logic.PatternChestLogic;
import tconstruct.blocks.logic.StencilTableLogic;
import tconstruct.blocks.logic.ToolStationLogic;

public class IguanaToolStationBlock extends ToolStationBlock {

	public IguanaToolStationBlock(int id, Material material) {
		super(id, material);
	}

    @Override
    public Object getModInstance ()
    {
        return IguanaTweaksTConstruct.instance;
    }

}
