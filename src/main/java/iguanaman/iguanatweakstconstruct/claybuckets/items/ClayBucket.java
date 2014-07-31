package iguanaman.iguanatweakstconstruct.claybuckets.items;

import iguanaman.iguanatweakstconstruct.old.IguanaItems;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class ClayBucket extends ItemBucket
{
    public ClayBucket(Block contents)
    {
        super(contents);

        this.setUnlocalizedName(Reference.MOD_ID + ".clayBucketFired");
        this.setUnlocalizedName(Reference.resource("clayBucketFired"));
        this.setContainerItem(iguanaman.iguanatweakstconstruct.claybuckets.IguanaItems.clayBucketFired);
    }
}
