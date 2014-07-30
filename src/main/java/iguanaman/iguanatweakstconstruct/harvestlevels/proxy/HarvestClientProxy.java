package iguanaman.iguanatweakstconstruct.harvestlevels.proxy;

import iguanaman.iguanatweakstconstruct.harvestlevels.gui.ToolForgeGUI;
import iguanaman.iguanatweakstconstruct.harvestlevels.gui.ToolStationGUI;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tconstruct.common.TProxyCommon;
import tconstruct.tools.ToolProxyCommon;
import tconstruct.tools.logic.ToolForgeLogic;
import tconstruct.tools.logic.ToolStationLogic;

public class HarvestClientProxy extends HarvestCommonProxy {
    @Override
    public void initialize() {
        Log.info("Replacing GUIs");
        TProxyCommon.registerClientGuiHandler(ToolProxyCommon.toolStationID, this);
        TProxyCommon.registerClientGuiHandler(ToolProxyCommon.toolForgeID, this);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == ToolProxyCommon.toolStationID)
            return new ToolStationGUI(player.inventory, (ToolStationLogic) world.getTileEntity(x, y, z), world, x, y, z);
        if(ID == ToolProxyCommon.toolForgeID)
            return new ToolForgeGUI(player.inventory, (ToolForgeLogic) world.getTileEntity(x, y, z), world, x, y, z);
        return null;
    }
}
