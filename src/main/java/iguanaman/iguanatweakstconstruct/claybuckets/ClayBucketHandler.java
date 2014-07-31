package iguanaman.iguanatweakstconstruct.claybuckets;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mantle.world.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.smeltery.blocks.LiquidMetalFinite;

public class ClayBucketHandler {
    // filling buckets, same behaviour as regular buckets from TConstruct, but with clay buckets
    @SubscribeEvent
    public void bucketFill (FillBucketEvent evt)
    {
        if (evt.current.getItem() == IguanaItems.clayBucketFired && evt.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            int hitX = evt.target.blockX;
            int hitY = evt.target.blockY;
            int hitZ = evt.target.blockZ;

            if (evt.entityPlayer != null && !evt.entityPlayer.canPlayerEdit(hitX, hitY, hitZ, evt.target.sideHit, evt.current))
            {
                return;
            }

            Block bID = evt.world.getBlock(hitX, hitY, hitZ);
            for (int id = 0; id < TinkerSmeltery.fluidBlocks.length; id++)
            {
                if (bID == TinkerSmeltery.fluidBlocks[id])
                {
                    if (evt.entityPlayer.capabilities.isCreativeMode)
                    {
                        WorldHelper.setBlockToAir(evt.world, hitX, hitY, hitZ);
                    }
                    else
                    {
                        if (TinkerSmeltery.fluidBlocks[id] instanceof LiquidMetalFinite)
                        {
                            WorldHelper.setBlockToAir(evt.world, hitX, hitY, hitZ);
                        }
                        else
                        {
                            WorldHelper.setBlockToAir(evt.world, hitX, hitY, hitZ);
                        }

                        evt.setResult(Event.Result.ALLOW);
                        evt.result = new ItemStack(IguanaItems.clayBucketsTinkers, 1, id);
                    }
                }
            }
        }
    }
}
