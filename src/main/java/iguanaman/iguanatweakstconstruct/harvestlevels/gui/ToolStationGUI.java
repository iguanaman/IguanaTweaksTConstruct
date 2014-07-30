package iguanaman.iguanatweakstconstruct.harvestlevels.gui;

import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import tconstruct.tools.gui.ToolStationGui;
import tconstruct.tools.logic.ToolStationLogic;

public class ToolStationGUI extends ToolStationGui {

    public ToolStationGUI(InventoryPlayer inventoryplayer, ToolStationLogic stationlogic, World world, int x, int y, int z) {
        super(inventoryplayer, stationlogic, world, x, y, z);
    }

    @Override
    protected String getHarvestLevelName(int num) {
        return HarvestLevels.getHarvestLevelName(num);
    }
}
