package iguanaman.iguanatweakstconstruct;

import tconstruct.blocks.logic.PatternChestLogic;
import tconstruct.blocks.logic.StencilTableLogic;
import tconstruct.blocks.logic.ToolStationLogic;
import tconstruct.client.gui.PartCrafterGui;
import tconstruct.client.gui.PatternChestGui;
import tconstruct.client.gui.StencilTableGui;
import tconstruct.common.TProxyCommon;
import tconstruct.library.blocks.InventoryLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	
        public void registerRenderers() {
        }
        
        public void registerSounds() {
        }

        @Override
        public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z)
        {
        	if (ID == TProxyCommon.toolStationID || ID == TProxyCommon.partBuilderID || ID == TProxyCommon.patternChestID
        			|| ID == TProxyCommon.toolForgeID || ID == TProxyCommon.stencilTableID)
        	{
	            TileEntity tile = world.getBlockTileEntity(x, y, z);
	            if (tile != null && tile instanceof InventoryLogic)
	            {
	                return ((InventoryLogic) tile).getGuiContainer(player.inventory, world, x, y, z);
	            }
        	}
			
	        return null;
		}
        
		@Override
		public Object getClientGuiElement(int ID, EntityPlayer player,
				World world, int x, int y, int z) {
			// TODO Auto-generated method stub
			return null;
		}
}