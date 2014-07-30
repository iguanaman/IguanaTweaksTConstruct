package iguanaman.iguanatweakstconstruct;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import iguanaman.iguanatweakstconstruct.harvestlevels.IguanaHarvestLevelTweaks;
import iguanaman.iguanatweakstconstruct.leveling.IguanaToolLeveling;
import iguanaman.iguanatweakstconstruct.leveling.commands.IguanaCommandLevelUpTool;
import iguanaman.iguanatweakstconstruct.leveling.commands.IguanaCommandToolXP;
import iguanaman.iguanatweakstconstruct.leveling.commands.debug;
import iguanaman.iguanatweakstconstruct.proxy.CommonProxy;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.replacing.IguanaToolPartReplacing;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import iguanaman.iguanatweakstconstruct.util.Log;
import mantle.pulsar.config.ForgeCFG;
import mantle.pulsar.control.PulseManager;
import mantle.pulsar.pulse.PulseMeta;
import net.minecraft.item.Item;

import java.util.List;

// inofficial todo list:
// todo: find a solution to stone-head-tools not having xp even when head is replaced (maybe like old iguana, disallow stone tools?)
// todo: PR a ToolModifyEvent in ModifyBuilder to Tinkers to update XP when a modifier that modifies XP needed is applied.
// todo: refactor adding modifiers on levelup from that long chaotic function + normal modifiers ;o

@Mod(modid= Reference.MOD_ID, name= Reference.MOD_NAME, version="1.7.X-1p",
dependencies = "required-after:" + Reference.TCON_MOD_ID + ";after:*")
public class IguanaTweaksTConstruct {

	// The instance of your mod that Forge uses.
	@Instance(Reference.MOD_ID)
	public static IguanaTweaksTConstruct instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide= Reference.PROXY_CLIENT_CLASS, serverSide= Reference.PROXY_SERVER_CLASS)
	public static CommonProxy proxy;

    public static boolean isToolLevelingActive = false;
    public static boolean isHarvestTweaksActive = false;

	public static List<Item> toolParts = null;

    // TODO: decide wether or not the same cfg as tcon should be used
    // use the PulseManager. This allows us to separate the different parts into independend modules and have stuff together. yay.
    private ForgeCFG pulseCFG = new ForgeCFG("TinkersModules", "Addon: Iguana Tweaks for Tinkers Construct");
    private PulseManager pulsar = new PulseManager(Reference.MOD_ID, pulseCFG);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Config.init(event.getSuggestedConfigurationFile());
        // workaround to know which modules are active.. :I
        PulseMeta meta = new PulseMeta(Reference.PULSE_LEVELING, "", false, false);
        isToolLevelingActive = pulseCFG.isModuleEnabled(meta);
        meta = new PulseMeta(Reference.PULSE_HARVESTTWEAKS, "", false, false);
        isHarvestTweaksActive = pulseCFG.isModuleEnabled(meta);

		/*toolParts = Arrays.asList (
				TinkerTools.toolRod, TinkerTools.pickaxeHead, TinkerTools.shovelHead, TinkerTools.hatchetHead,
				TinkerTools.binding, TinkerTools.toughBinding, TinkerTools.toughRod, TinkerTools.largePlate,
				TinkerTools.swordBlade, TinkerTools.wideGuard, TinkerTools.handGuard, TinkerTools.crossbar,
				TinkerTools.knifeBlade, TinkerTools.fullGuard, TinkerTools.frypanHead, TinkerTools.signHead,
				TinkerTools.chiselHead, TinkerTools.scytheBlade, TinkerTools.broadAxeHead, TinkerTools.excavatorHead,
				TinkerTools.largeSwordBlade, TinkerTools.hammerHead, TinkerTools.bowstring, TinkerTools.fletching,
				TinkerTools.arrowhead );
*/

        // if we don't use our custom harvest levels, we have to adjust what we're using
        if(!isHarvestTweaksActive)
            HarvestLevels.adjustToVanillaLevels();

        pulsar.registerPulse(new IguanaHarvestLevelTweaks());
        pulsar.registerPulse(new IguanaToolLeveling());
        pulsar.registerPulse(new IguanaToolPartReplacing());
        pulsar.preInit(event);
	}


	@EventHandler
	public void load(FMLInitializationEvent event) {
        pulsar.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
        pulsar.postInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
        // TODO: change this to a proper isModuleLoaded or something in Pulsar 0.4+ (when released/implemented)
		if (isToolLevelingActive)
		{
            Log.debug("Adding command: leveluptool");
            event.registerServerCommand(new IguanaCommandLevelUpTool());
            Log.debug("Adding command: toolxp");
            event.registerServerCommand(new IguanaCommandToolXP());
            event.registerServerCommand(new debug());
		}
	}


    // backwards compatibility
    public static String getHarvestLevelName (int num)
    {
        return HarvestLevels.getHarvestLevelName(num);
    }
}