package iguanaman.iguanatweakstconstruct.mobheads.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import iguanaman.iguanatweakstconstruct.mobheads.IguanaMobHeads;
import iguanaman.iguanatweakstconstruct.mobheads.renderers.IguanaTileEntitySkullRenderer;
import iguanaman.iguanatweakstconstruct.mobheads.tileentities.IguanaSkullTileEntity;
import net.minecraft.item.ItemStack;

public class MobHeadClientProxy extends MobHeadCommonProxy {
    @Override
    public void initialize() {
        ClientRegistry.bindTileEntitySpecialRenderer(IguanaSkullTileEntity.class, IguanaTileEntitySkullRenderer.renderer);
    }

    public void postInit() {
        codechicken.nei.api.API.hideItem(new ItemStack(IguanaMobHeads.wearables, 1, 0));
        codechicken.nei.api.API.hideItem(new ItemStack(IguanaMobHeads.wearables, 1, 1));
        codechicken.nei.api.API.hideItem(new ItemStack(IguanaMobHeads.wearables, 1, 2));
        codechicken.nei.api.API.hideItem(new ItemStack(IguanaMobHeads.wearables, 1, 3));
    }
}
