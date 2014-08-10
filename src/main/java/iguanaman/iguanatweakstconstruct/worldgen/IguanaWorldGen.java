package iguanaman.iguanatweakstconstruct.worldgen;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraftforge.common.ChestGenHooks;

@Pulse(id = Reference.PULSE_WORLDGEN, description = "This module takes care of additional awesome dungeon loot.")
public class IguanaWorldGen {
    @Handler
    public void init(FMLInitializationEvent event)
    {
        // add some a starting weapon to bonus chests!
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new RandomWeaponChestContent(1,1, 10, 0,0, HarvestLevels._1_flint));

        // awesome dungeon loot!
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new RandomWeaponChestContent(0,1, 2, 1,2, HarvestLevels._5_diamond));
    }
}
