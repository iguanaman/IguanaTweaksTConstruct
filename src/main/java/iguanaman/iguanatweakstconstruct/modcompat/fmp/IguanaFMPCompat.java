package iguanaman.iguanatweakstconstruct.modcompat.fmp;

import codechicken.lib.config.ConfigFile;
import codechicken.lib.config.ConfigTag;
import codechicken.lib.config.ConfigTagParent;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.ItemSaw;
import codechicken.microblock.MicroMaterialRegistry$;
import codechicken.microblock.Saw;
import codechicken.microblock.handler.MicroblockProxy;
import codechicken.microblock.handler.MicroblockProxy$;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.handler.MultipartProxy;
import codechicken.multipart.handler.MultipartProxy$;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import iguanaman.iguanatweakstconstruct.harvestlevels.HarvestLevelTweaks;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.tweaks.IguanaTweaks;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import iguanaman.iguanatweakstconstruct.util.Log;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tconstruct.world.TinkerWorld;

import java.lang.reflect.Field;

@Pulse(id = Reference.PULSE_COMPAT_FMP, description = "Makes Saw cut stuff again", pulsesRequired = Reference.PULSE_HARVESTTWEAKS, modsRequired = "ForgeMultipart")
public class IguanaFMPCompat {
    @SidedProxy(clientSide = "iguanaman.iguanatweakstconstruct.modcompat.fmp.ClientFMPProxy", serverSide = "iguanaman.iguanatweakstconstruct.modcompat.fmp.CommonFMPProxy")
    public static CommonFMPProxy proxy;

    public static Item arditeSaw;
    public static Item cobaltSaw;
    public static Item manyullynSaw;

    private Item createSaw(ToolMaterial mat)
    {
        String name = String.format("saw%s", mat.name());
        // proper default value...
        MultipartProxy.config().getTag(name).useBraces().getTag("durability").getIntValue(mat.durability()*5);
        // create the saw
        Item saw = MicroblockProxy.createSaw(MultipartProxy.config(), name, mat.harvestLevel());
        saw.setUnlocalizedName(Reference.prefix(name));

        return saw;
    }

    @Handler
    public void init(FMLInitializationEvent event)
    {
        Log.info("Making TConstruct blocks multipart compatible");

        //make Tconstruct blocks multipartable!
        if(TinkerWorld.metalBlock != null) {
            // metal blocks
            for (int i = 0; i < 11; i++)
                FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", new ItemStack(TinkerWorld.metalBlock, 1, i));
        }

        if(TinkerSmeltery.smeltery != null) {
            // smeltery bricks
            for (int i = 2; i < 12; i++) {
                if (i == 3)
                    continue;
                FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", new ItemStack(TinkerSmeltery.smeltery, 1, i));
                FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", new ItemStack(TinkerSmeltery.smelteryNether, 1, i));
            }

            // brownstone
            for (int i = 0; i < 7; i++)
                FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", new ItemStack(TinkerSmeltery.speedBlock, 1, i));
        }

        if(TinkerTools.multiBrick != null) {
            // chisel bricks
            for (int i = 0; i < 14; i++)
                FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", new ItemStack(TinkerTools.multiBrick, 1, i));
            for (int i = 0; i < 16; i++)
                FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", new ItemStack(TinkerTools.multiBrickFancy, 1, i));
        }
    }

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        if(IguanaTweaksTConstruct.pulsar.isPulseLoaded("Debug"))
            MinecraftForge.EVENT_BUS.register(new SawStrengthHandler());

        Log.info("Adapting and adding FMP saws");
        // change existing saws
        for(Object o : Item.itemRegistry)
        {
            // is it a saw?
            if(!(o instanceof Saw))
                continue;

            if(o == arditeSaw || o == cobaltSaw || o == manyullynSaw)
                continue;

            Item item = (Item) o;

            // FMP saw
            if(o instanceof ItemSaw)
            {
                try {
                    Field hlvlField = ItemSaw.class.getDeclaredField("harvestLevel");
                    hlvlField.setAccessible(true);
                    Integer old = (Integer)hlvlField.get(o);
                    Integer hlvl = HarvestLevelTweaks.getUpdatedHarvestLevel(old);

                    // update the value
                    hlvlField.set(o, hlvl);
                    if (Config.logMiningLevelChanges)
                        Log.info(String.format("Changed Cutting Strength for %s from %d to %d", item.getUnlocalizedName(), old, hlvl));
                } catch(Exception e)
                {
                    // something failed :(
                    Log.error(String.format("Couldn't update FMP saw %s", item.getUnlocalizedName()));
                }
            }
        }

        // create our own saws (have to do this this late, since the harvestlevel changes and overrides have to be applied first)
        arditeSaw = createSaw(TConstructRegistry.getMaterial("Ardite"));
        cobaltSaw = createSaw(TConstructRegistry.getMaterial("Cobalt"));
        manyullynSaw = createSaw(TConstructRegistry.getMaterial("Manyullyn"));

        // and add recipes for them
        String[] recipe = { "srr", "sbr"};

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(arditeSaw), recipe, 's', Items.stick, 'r', "rodStone", 'b', new ItemStack(TinkerTools.toolRod, 1, 11)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cobaltSaw), recipe, 's', Items.stick, 'r', "rodStone", 'b', new ItemStack(TinkerTools.toolRod, 1, 10)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(manyullynSaw), recipe, 's', Items.stick, 'r', "rodStone", 'b', new ItemStack(TinkerTools.toolRod, 1, 12)));

        proxy.updateSawRenderers();
    }
}
