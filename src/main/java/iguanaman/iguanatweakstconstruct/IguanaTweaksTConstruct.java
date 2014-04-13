package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.commands.IguanaCommandLevelUpTool;
import iguanaman.iguanatweakstconstruct.commands.IguanaCommandToolXP;
import iguanaman.iguanatweakstconstruct.util.IguanaEventHandler;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

import org.modstats.ModstatInfo;
import org.modstats.Modstats;

import tconstruct.common.TContent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid="IguanaTweaksTConstruct", name="Iguana Tweaks for Tinker's Construct", version="1.6.X-1q",
dependencies = "required-after:TConstruct;after:*")
@NetworkMod(clientSideRequired=true, serverSideRequired=true)
@ModstatInfo(prefix="igtweakstc")
public class IguanaTweaksTConstruct {

	// The instance of your mod that Forge uses.
	@Instance("IguanaTweaksTConstruct")
	public static IguanaTweaksTConstruct instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="iguanaman.iguanatweakstconstruct.ClientProxy", serverSide="iguanaman.iguanatweakstconstruct.CommonProxy")
	public static CommonProxy proxy;


	public static List<Item> toolParts = null;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		proxy.registerSounds();
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);

		IguanaConfig.init(event.getSuggestedConfigurationFile());

		toolParts = Arrays.asList (
				TContent.toolRod, TContent.pickaxeHead, TContent.shovelHead, TContent.hatchetHead,
				TContent.binding, TContent.toughBinding, TContent.toughRod, TContent.largePlate,
				TContent.swordBlade, TContent.wideGuard, TContent.handGuard, TContent.crossbar,
				TContent.knifeBlade, TContent.fullGuard, TContent.frypanHead, TContent.signHead,
				TContent.chiselHead, TContent.scytheBlade, TContent.broadAxeHead, TContent.excavatorHead,
				TContent.largeSwordBlade, TContent.hammerHead, TContent.bowstring, TContent.fletching,
				TContent.arrowhead );

		IguanaBlocks.init();
		IguanaItems.init();
	}


	@EventHandler
	public void load(FMLInitializationEvent event) {
		IguanaLog.log("Registering with modstats");
		Modstats.instance().getReporter().registerMod(this);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.registerRenderers();

		MaterialTweaks.init();
		ModifierTweaks.init();
		VariousTweaks.init();
		RemoveVanillaTools.init();
		HarvestLevelTweaks.init();

		IguanaLog.log("Starting event handler");
		MinecraftForge.EVENT_BUS.register(new IguanaEventHandler());
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