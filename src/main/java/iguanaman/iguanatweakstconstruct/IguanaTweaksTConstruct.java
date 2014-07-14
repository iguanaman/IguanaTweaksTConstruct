package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.commands.IguanaCommandLevelUpTool;
import iguanaman.iguanatweakstconstruct.commands.IguanaCommandToolXP;
import iguanaman.iguanatweakstconstruct.proxy.CommonProxy;
import iguanaman.iguanatweakstconstruct.reference.IguanaConfig;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import iguanaman.iguanatweakstconstruct.reference.IguanaReference;
import iguanaman.iguanatweakstconstruct.util.IguanaLog;
import mantle.pulsar.config.ForgeCFG;
import mantle.pulsar.control.PulseManager;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.item.Item;
import tconstruct.tools.TinkerTools;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid= IguanaReference.MOD_ID, name=IguanaReference.MOD_NAME, version="1.6.X-1p",
dependencies = "required-after:" + IguanaReference.TCON_MOD_ID + ";after:*")
public class IguanaTweaksTConstruct {

	// The instance of your mod that Forge uses.
	@Instance(IguanaReference.MOD_ID)
	public static IguanaTweaksTConstruct instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide=IguanaReference.PROXY_CLIENT_CLASS, serverSide=IguanaReference.PROXY_SERVER_CLASS)
	public static CommonProxy proxy;

	public static List<Item> toolParts = null;

    // TODO: decide wether or not the same cfg as tcon should be used
    // use the PulseManager. This allows us to separate the different parts into independend modules and have stuff together. yay.
    private PulseManager pulsar = new PulseManager(IguanaReference.MOD_ID, new ForgeCFG("TinkersModules", "Modules added by Iguana Tweaks for Tinkers Construct"));

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		proxy.registerSounds();
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

        pulsar.preInit(event);
	}


	@EventHandler
	public void load(FMLInitializationEvent event) {
        pulsar.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.registerRenderers();

		//MaterialTweaks.init();
		//ModifierTweaks.init();
		//VariousTweaks.init();
		//RemoveVanillaTools.init();
		// TODO: need to re-implement harvest level tweaks
		//HarvestLevelTweaks.init();

		IguanaLog.info("Starting event handler");
		//MinecraftForge.EVENT_BUS.register(new IguanaEventHandler());

        pulsar.postInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		if (IguanaConfig.toolLeveling)
		{
			ICommandManager commandManager = FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager();
			ServerCommandManager serverCommandManager = (ServerCommandManager) commandManager;
			serverCommandManager.registerCommand(new IguanaCommandLevelUpTool());
			serverCommandManager.registerCommand(new IguanaCommandToolXP());
		}
	}


	public static String getHarvestLevelName (int num)
	{
		if (IguanaConfig.pickaxeBoostRequired && num > 1) --num;
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