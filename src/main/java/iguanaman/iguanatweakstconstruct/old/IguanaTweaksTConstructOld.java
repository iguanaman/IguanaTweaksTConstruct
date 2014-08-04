package iguanaman.iguanatweakstconstruct.old;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import iguanaman.iguanatweakstconstruct.debug.DebugCommand;
import iguanaman.iguanatweakstconstruct.leveling.IguanaToolLeveling;
import iguanaman.iguanatweakstconstruct.leveling.commands.IguanaCommandLevelUpTool;
import iguanaman.iguanatweakstconstruct.leveling.commands.IguanaCommandToolXP;
import iguanaman.iguanatweakstconstruct.proxy.CommonProxy;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.util.Log;
import mantle.pulsar.config.ForgeCFG;
import mantle.pulsar.control.PulseManager;
import mantle.pulsar.pulse.PulseMeta;
import net.minecraft.item.Item;
import tconstruct.tools.TinkerTools;

import java.util.Arrays;
import java.util.List;

//@Mod(modid= IguanaReference.MOD_ID, name=IguanaReference.MOD_NAME, version="1.6.X-1p",
//dependencies = "required-after:" + IguanaReference.TCON_MOD_ID + ";after:*")
public class IguanaTweaksTConstructOld {

	// The instance of your mod that Forge uses.
	//@Instance(Reference.MOD_ID)
	public static IguanaTweaksTConstructOld instance;

	// Says where the client and server 'proxy' code is loaded.
	//@SidedProxy(clientSide= Reference.PROXY_CLIENT_CLASS, serverSide= Reference.PROXY_SERVER_CLASS)
	public static CommonProxy proxy;

	public static List<Item> toolParts = null;

    // TODO: decide wether or not the same cfg as tcon should be used
    // use the PulseManager. This allows us to separate the different parts into independend modules and have stuff together. yay.
    private ForgeCFG pulseCFG = new ForgeCFG("TinkersModules", "Addon: Iguana Tweaks for Tinkers Construct");
    private PulseManager pulsar = new PulseManager(Reference.MOD_ID, pulseCFG);

//	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		//proxy.registerSounds();
		//NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

        IguanaConfig.init(event.getSuggestedConfigurationFile());

		toolParts = Arrays.asList (
				TinkerTools.toolRod, TinkerTools.pickaxeHead, TinkerTools.shovelHead, TinkerTools.hatchetHead,
				TinkerTools.binding, TinkerTools.toughBinding, TinkerTools.toughRod, TinkerTools.largePlate,
				TinkerTools.swordBlade, TinkerTools.wideGuard, TinkerTools.handGuard, TinkerTools.crossbar,
				TinkerTools.knifeBlade, TinkerTools.fullGuard, TinkerTools.frypanHead, TinkerTools.signHead,
				TinkerTools.chiselHead, TinkerTools.scytheBlade, TinkerTools.broadAxeHead, TinkerTools.excavatorHead,
				TinkerTools.largeSwordBlade, TinkerTools.hammerHead, TinkerTools.bowstring, TinkerTools.fletching,
				TinkerTools.arrowhead );

		//IguanaBlocks.init();
		//IguanaItems.init();

        pulsar.registerPulse(new IguanaToolLeveling());
        pulsar.preInit(event);
	}


//	@EventHandler
	public void load(FMLInitializationEvent event) {
        pulsar.init(event);
	}

//	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//proxy.registerRenderers();

		//MaterialTweaks.init();
		//ModifierTweaks.init();
		//VariousTweaks.init();
		//RemoveVanillaTools.init();
		// TODO: need to re-implement harvest level tweaks
		//HarvestLevelTweaks.init();

		Log.info("Starting event handlers");
		//MinecraftForge.EVENT_BUS.register(new IguanaEventHandler());

        pulsar.postInit(event);
	}

//	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
        // TODO: change this to a proper isModuleLoaded or something in Pulsar 0.4+ (when released/implemented)
        PulseMeta meta = new PulseMeta(Reference.PULSE_LEVELING, "", false, false);
		if (pulseCFG.isModuleEnabled(meta))
		{
            Log.debug("Adding command: leveluptool");
            event.registerServerCommand(new IguanaCommandLevelUpTool());
            Log.debug("Adding command: toolxp");
            event.registerServerCommand(new IguanaCommandToolXP());
            event.registerServerCommand(new DebugCommand());
		}
	}


	public static String getHarvestLevelName (int num)
	{
		if (Config.pickaxeBoostRequired && num > 1) --num;
		switch (num)
		{
		case 0: return "\u00a77Stone";
		case 1: return "\u00a76Copper";
		case 2: return "\u00a74Iron";
		case 3: return "\u00a7fTin";
		case 4: return "\u00a7bDiamond";
		case 5: return "\u00a7cArdite";
		case 6: return "\u00a79Cobalt";
		case 7: return "\u00a75Manyullyn";
		default: return "\u00a7k\u00a7k\u00a7k\u00a7k\u00a7k\u00a7k";
		}
	}

}