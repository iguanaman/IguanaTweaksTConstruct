package iguanaman.iguanatweakstconstruct.proxy;

import iguanaman.iguanatweakstconstruct.blocks.IguanaTileEntitySkull;
import iguanaman.iguanatweakstconstruct.gui.IguanaPartCrafterGui;
import iguanaman.iguanatweakstconstruct.gui.IguanaToolForgeGui;
import iguanaman.iguanatweakstconstruct.gui.IguanaToolStationGui;
import iguanaman.iguanatweakstconstruct.renderer.IguanaSkullRenderer;
import iguanaman.iguanatweakstconstruct.util.IguanaClientEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.tools.ToolProxyCommon;
import tconstruct.tools.logic.PartBuilderLogic;
import tconstruct.tools.logic.PatternChestLogic;
import tconstruct.tools.logic.StencilTableLogic;
import tconstruct.tools.logic.ToolForgeLogic;
import tconstruct.tools.logic.ToolStationLogic;
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
	public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
        /*
		if (ID == ToolProxyCommon.toolStationID)
			return new IguanaToolStationGui(player.inventory, (ToolStationLogic) world.getTileEntity(x, y, z), world, x, y, z);
		if (ID == ToolProxyCommon.partBuilderID)
			return new IguanaPartCrafterGui(player.inventory, (PartBuilderLogic) world.getTileEntity(x, y, z), world, x, y, z);
		if (ID == ToolProxyCommon.patternChestID)
			return new PatternChestGui(player.inventory, (PatternChestLogic) world.getTileEntity(x, y, z), world, x, y, z);
		if (ID == ToolProxyCommon.toolForgeID)
			return new IguanaToolForgeGui(player.inventory, (ToolForgeLogic) world.getTileEntity(x, y, z), world, x, y, z);
		if (ID == ToolProxyCommon.stencilTableID)
			return new StencilTableGui(player.inventory, (StencilTableLogic) world.getTileEntity(x, y, z), world, x, y, z);
*/
		return null;
	}
}