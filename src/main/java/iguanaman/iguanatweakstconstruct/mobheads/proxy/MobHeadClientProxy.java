package iguanaman.iguanatweakstconstruct.mobheads.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import iguanaman.iguanatweakstconstruct.mobheads.renderers.IguanaTileEntitySkullRenderer;
import iguanaman.iguanatweakstconstruct.mobheads.tileentities.IguanaSkullTileEntity;

public class MobHeadClientProxy extends MobHeadCommonProxy {
    @Override
    public void initialize() {
        ClientRegistry.bindTileEntitySpecialRenderer(IguanaSkullTileEntity.class, IguanaTileEntitySkullRenderer.renderer);
    }
}
