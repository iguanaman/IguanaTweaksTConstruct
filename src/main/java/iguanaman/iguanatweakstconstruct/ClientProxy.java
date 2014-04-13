package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.blocks.IguanaTileEntitySkull;
import iguanaman.iguanatweakstconstruct.gui.IguanaPartCrafterGui;
import iguanaman.iguanatweakstconstruct.gui.IguanaToolForgeGui;
import iguanaman.iguanatweakstconstruct.gui.IguanaToolStationGui;
import iguanaman.iguanatweakstconstruct.renderer.IguanaSkullRenderer;
import iguanaman.iguanatweakstconstruct.util.IguanaClientEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.blocks.logic.PartBuilderLogic;
import tconstruct.blocks.logic.PatternChestLogic;
import tconstruct.blocks.logic.StencilTableLogic;
import tconstruct.blocks.logic.ToolForgeLogic;
import tconstruct.blocks.logic.ToolStationLogic;
import tconstruct.client.gui.PatternChestGui;
import tconstruct.client.gui.StencilTableGui;
import tconstruct.common.TProxyCommon;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(IguanaTileEntitySkull.class, new IguanaSkullRenderer());
	}

	@Override
	public void registerSounds() {
		MinecraftForge.EVENT_BUS.register(new IguanaClientEventHandler());
	}

	@Override
	public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == TProxyCommon.toolStationID)
			return new IguanaToolStationGui(player.inventory, (ToolStationLogic) world.getBlockTileEntity(x, y, z), world, x, y, z);
		if (ID == TProxyCommon.partBuilderID)
			return new IguanaPartCrafterGui(player.inventory, (PartBuilderLogic) world.getBlockTileEntity(x, y, z), world, x, y, z);
		if (ID == TProxyCommon.patternChestID)
			return new PatternChestGui(player.inventory, (PatternChestLogic) world.getBlockTileEntity(x, y, z), world, x, y, z);
		if (ID == TProxyCommon.toolForgeID)
			return new IguanaToolForgeGui(player.inventory, (ToolForgeLogic) world.getBlockTileEntity(x, y, z), world, x, y, z);
		if (ID == TProxyCommon.stencilTableID)
			return new StencilTableGui(player.inventory, (StencilTableLogic) world.getBlockTileEntity(x, y, z), world, x, y, z);

		return null;
	}
}