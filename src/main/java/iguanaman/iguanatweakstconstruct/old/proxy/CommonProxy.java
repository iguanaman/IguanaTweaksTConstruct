package iguanaman.iguanatweakstconstruct.old.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import mantle.blocks.abstracts.InventoryLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tconstruct.tools.ToolProxyCommon;

public class CommonProxy implements IGuiHandler
{

	public void registerRenderers() {}

	public void registerSounds() {}

	@Override
	public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == ToolProxyCommon.toolStationID || ID == ToolProxyCommon.partBuilderID || ID == ToolProxyCommon.patternChestID
				|| ID == ToolProxyCommon.toolForgeID || ID == ToolProxyCommon.stencilTableID)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && tile instanceof InventoryLogic)
				return ((InventoryLogic) tile).getGuiContainer(player.inventory, world, x, y, z);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player,
			World world, int x, int y, int z) {
		return null;
	}
}