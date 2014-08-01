package iguanaman.iguanatweakstconstruct.mobheads;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import iguanaman.iguanatweakstconstruct.mobheads.blocks.IguanaSkullBlock;
import iguanaman.iguanatweakstconstruct.mobheads.items.IguanaSkull;
import iguanaman.iguanatweakstconstruct.mobheads.proxy.MobHeadCommonProxy;
import iguanaman.iguanatweakstconstruct.mobheads.renderers.RenderPlayerHandler;
import iguanaman.iguanatweakstconstruct.mobheads.tileentities.IguanaSkullTileEntity;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

/**
 * Adds additional MobHeads and controls MobHead dropping.
 */
@Pulse(id = Reference.PULSE_MOBHEADS, description = "Adds additional MobHeads and control over MobHead drops.")
public class IguanaMobHeads {
    public static Item skullItem;
    public static Block skullBlock;

    @SidedProxy(clientSide = "iguanaman.iguanatweakstconstruct.mobheads.proxy.MobHeadClientProxy", serverSide = "iguanaman.iguanatweakstconstruct.mobheads.proxy.MobHeadCommonProxy")
    public static MobHeadCommonProxy proxy;

    @Handler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.initialize();

        skullItem = new IguanaSkull();
        GameRegistry.registerItem(skullItem, "SkullItem");

        skullBlock = new IguanaSkullBlock().setHardness(1.0F).setStepSound(Block.soundTypeSand).setBlockName("skullItem").setBlockTextureName("skullItem");
        GameRegistry.registerBlock(skullBlock, "SkullBlock");
        GameRegistry.registerTileEntity(IguanaSkullTileEntity.class, "SkullEntity");
    }

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new RenderPlayerHandler());
    }
}
