package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.commands.IguanaCommandLevelUpTool;
import iguanaman.iguanatweakstconstruct.commands.IguanaCommandToolXP;
import iguanaman.iguanatweakstconstruct.util.IguanaEventHandler;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
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
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid="IguanaTweaksTConstruct", name="Iguana Tweaks for Tinker's Construct", version="1.6.X-1p",
dependencies = "required-after:TConstruct;after:*")
public class IguanaTweaksTConstruct {

	// The instance of your mod that Forge uses.
	@Instance("IguanaTweaksTConstruct")
	public static IguanaTweaksTConstruct instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="iguanaman.iguanatweakstconstruct.ClientProxy", serverSide="iguanaman.iguanatweakstconstruct.CommonProxy")
	public static CommonProxy proxy;

public static Logger ITconTweaksLog = Logger.getLogger("IguanaTweaksTConstruct");
	public static List<Item> toolParts = null;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		proxy.registerSounds();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

		IguanaConfig.init(event.getSuggestedConfigurationFile());

		toolParts = Arrays.asList (
				TinkerTools.toolRod, TinkerTools.pickaxeHead, TinkerTools.shovelHead, TinkerTools.hatchetHead,
				TinkerTools.binding, TinkerTools.toughBinding, TinkerTools.toughRod, TinkerTools.largePlate,
				TinkerTools.swordBlade, TinkerTools.wideGuard, TinkerTools.handGuard, TinkerTools.crossbar,
				TinkerTools.knifeBlade, TinkerTools.fullGuard, TinkerTools.frypanHead, TinkerTools.signHead,
				TinkerTools.chiselHead, TinkerTools.scytheBlade, TinkerTools.broadAxeHead, TinkerTools.excavatorHead,
				TinkerTools.largeSwordBlade, TinkerTools.hammerHead, TinkerTools.bowstring, TinkerTools.fletching,
				TinkerTools.arrowhead );

		IguanaBlocks.init();
		IguanaItems.init();
	}


	@EventHandler
	public void load(FMLInitializationEvent event) {
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