package iguanaman.iguanatweakstconstruct.harvestlevels.gui;

import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import tconstruct.tools.gui.ToolForgeGui;
import tconstruct.tools.logic.ToolForgeLogic;

public class ToolForgeGUI extends ToolForgeGui {

    public ToolForgeGUI(InventoryPlayer inventoryplayer, ToolForgeLogic stationlogic, World world, int x, int y, int z) {
        super(inventoryplayer, stationlogic, world, x, y, z);
    }

    @Override
    protected String getHarvestLevelName(int num) {
        return HarvestLevels.getHarvestLevelName(num);
    }
}
