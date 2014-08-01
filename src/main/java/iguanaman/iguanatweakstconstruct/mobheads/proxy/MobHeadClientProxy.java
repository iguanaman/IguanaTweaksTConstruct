package iguanaman.iguanatweakstconstruct.mobheads.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import iguanaman.iguanatweakstconstruct.claybuckets.IguanaItems;
import iguanaman.iguanatweakstconstruct.mobheads.renderers.IguanaTileEntitySkullRenderer;
import iguanaman.iguanatweakstconstruct.mobheads.tileentities.IguanaSkullTileEntity;
import net.minecraft.item.ItemStack;

public class MobHeadClientProxy extends MobHeadCommonProxy {
    @Override
    public void initialize() {
        ClientRegistry.bindTileEntitySpecialRenderer(IguanaSkullTileEntity.class, IguanaTileEntitySkullRenderer.renderer);
    }

    public void postInit() {
        // hide secret stuff ;)
        if(IguanaTweaksTConstruct.isItemsActive)
        {
            codechicken.nei.api.API.hideItem(new ItemStack(IguanaItems.wearableBuckets, 1, 0));
            codechicken.nei.api.API.hideItem(new ItemStack(IguanaItems.wearableBuckets, 1, 1));
        }
    }
}
