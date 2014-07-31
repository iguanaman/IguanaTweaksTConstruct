package iguanaman.iguanatweakstconstruct.claybuckets;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import iguanaman.iguanatweakstconstruct.claybuckets.items.ClayBucket;
import iguanaman.iguanatweakstconstruct.claybuckets.items.ClayBucketMilk;
import iguanaman.iguanatweakstconstruct.claybuckets.items.ClayBucketTinkerLiquids;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Pulse(id = Reference.PULSE_ITEMS, description = "All the Items Iguana Tweaks for TConstruct adds (Clay Buckets,...)")
public class IguanaItems {
    public static Item clayBucketUnfired;
    public static Item clayBucketFired;
    public static Item clayBucketWater;
    public static Item clayBucketLava;
    public static Item clayBucketMilk;
    public static Item clayBucketsTinkers;

    @Handler
    public void preInit(FMLPreInitializationEvent event)
    {
        // unfired clay bucket is a regular item
        clayBucketUnfired = new Item().setUnlocalizedName(Reference.MOD_ID + ".clayBucketUnfired").setTextureName(Reference.resource("clayBucketUnfired")).setMaxStackSize(16).setCreativeTab(CreativeTabs.tabMisc);
        GameRegistry.registerItem(clayBucketUnfired, "clayBucketUnfired");

        clayBucketFired = new ClayBucket(Blocks.air, "clayBucketFired", "clayBucketFired").setMaxStackSize(16);
        clayBucketWater = new ClayBucket(Blocks.flowing_water, "clayBucketWater", "clayBucketWater");
        clayBucketLava = new ClayBucket(Blocks.flowing_lava, "clayBucketLava", "clayBucketLava");
        clayBucketMilk = new ClayBucketMilk();

        clayBucketsTinkers = new ClayBucketTinkerLiquids(null);

        GameRegistry.registerItem(clayBucketFired, "clayBucketFired");
        GameRegistry.registerItem(clayBucketWater, "clayBucketWater");
        GameRegistry.registerItem(clayBucketLava,  "clayBucketLava");
        GameRegistry.registerItem(clayBucketMilk,  "clayBucketMilk");
        GameRegistry.registerItem(clayBucketsTinkers, "clayBucketsTinkers");
    }

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ClayBucketHandler());
    }
}
