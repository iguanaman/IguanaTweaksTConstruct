package iguanaman.iguanatweakstconstruct;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import iguanaman.iguanatweakstconstruct.claybuckets.IguanaItems;
import iguanaman.iguanatweakstconstruct.debug.DebugCommand;
import iguanaman.iguanatweakstconstruct.debug.DumpOredict;
import iguanaman.iguanatweakstconstruct.debug.IguanaDebug;
import iguanaman.iguanatweakstconstruct.harvestlevels.IguanaHarvestLevelTweaks;
import iguanaman.iguanatweakstconstruct.leveling.IguanaToolLeveling;
import iguanaman.iguanatweakstconstruct.leveling.commands.IguanaCommandLevelUpTool;
import iguanaman.iguanatweakstconstruct.leveling.commands.IguanaCommandToolXP;
import iguanaman.iguanatweakstconstruct.mobheads.IguanaMobHeads;
import iguanaman.iguanatweakstconstruct.proxy.CommonProxy;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.replacing.IguanaToolPartReplacing;
import iguanaman.iguanatweakstconstruct.restriction.IguanaPartRestriction;
import iguanaman.iguanatweakstconstruct.tweaks.IguanaTweaks;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import iguanaman.iguanatweakstconstruct.util.Log;
import iguanaman.iguanatweakstconstruct.worldgen.IguanaWorldGen;
import mantle.pulsar.config.ForgeCFG;
import mantle.pulsar.control.PulseManager;
import mantle.pulsar.pulse.PulseMeta;
import net.minecraft.item.Item;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;
import java.util.Random;

@Mod(modid= Reference.MOD_ID, name= Reference.MOD_NAME, version="${version}",
dependencies = "required-after:" + Reference.TCON_MOD_ID + ";after:*")
public class IguanaTweaksTConstruct {

	// The instance of your mod that Forge uses.
	@Instance(Reference.MOD_ID)
	public static IguanaTweaksTConstruct instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide= Reference.PROXY_CLIENT_CLASS, serverSide= Reference.PROXY_SERVER_CLASS)
	public static CommonProxy proxy;

    public static Random random = new Random();

    public static boolean isToolLevelingActive = false;
    public static boolean isHarvestTweaksActive = false;
    public static boolean isMobHeadsActive = false;
    public static boolean isTweaksActive = false;
    public static boolean isItemsActive = false;
    public static boolean isPartReplacementActive = false;

	public static List<Item> toolParts = null;

    // TODO: decide wether or not the same cfg as tcon should be used
    // use the PulseManager. This allows us to separate the different parts into independend modules and have stuff together. yay.
    private ForgeCFG pulseCFG = new ForgeCFG("TinkersModules", "Addon: Iguana Tweaks for Tinkers Construct");
    private PulseManager pulsar = new PulseManager(Reference.MOD_ID, pulseCFG);

    public static boolean modTEDetected = false;
    public static File configPath;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
        File suggestion = event.getSuggestedConfigurationFile();
        configPath = new File(suggestion.getParentFile(), FilenameUtils.removeExtension(suggestion.getName()));
        configPath.mkdirs();

        Config config = new Config();
        config.init(new File(configPath, "main.cfg"));
        // register config as eventhandler to get config changed updates
        FMLCommonHandler.instance().bus().register(config);

        // workaround to know which modules are active.. :I
        isToolLevelingActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_LEVELING, "", false, false));
        isHarvestTweaksActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_HARVESTTWEAKS, "", false, false));
        isMobHeadsActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_MOBHEADS, "", false, false));
        isTweaksActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_TWEAKS, "", false, false));
        isItemsActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_ITEMS, "", false, false));
        isPartReplacementActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_REPLACING, "", false, false));

        modTEDetected = Loader.isModLoaded("ThermalFoundation");

        // if we don't use our custom harvest levels, we have to adjust what we're using
        if(!isHarvestTweaksActive)
            HarvestLevels.adjustToVanillaLevels();

        // order matters here
        pulsar.registerPulse(new IguanaHarvestLevelTweaks());
        pulsar.registerPulse(new IguanaToolLeveling());
        pulsar.registerPulse(new IguanaMobHeads());
        pulsar.registerPulse(new IguanaItems());
        pulsar.registerPulse(new IguanaTweaks());
        pulsar.registerPulse(new IguanaPartRestriction());
        // replacing has to be after tweaks and restrictions, because its tooltips have to be handled last
        pulsar.registerPulse(new IguanaToolPartReplacing());
        pulsar.registerPulse(new IguanaWorldGen());
        pulsar.registerPulse(new IguanaDebug());
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
        Log.debug("Adding command: dumpOredict");
        event.registerServerCommand(new DumpOredict());
		if (isToolLevelingActive)
		{
            Log.debug("Adding command: leveluptool");
            event.registerServerCommand(new IguanaCommandLevelUpTool());
            Log.debug("Adding command: toolxp");
            event.registerServerCommand(new IguanaCommandToolXP());
		}
        if(pulseCFG.isModuleEnabled(new PulseMeta("Debug", "", false, false)))
            event.registerServerCommand(new DebugCommand());
	}


    // backwards compatibility
    public static String getHarvestLevelName (int num)
    {
        return HarvestLevels.getHarvestLevelName(num);
    }
}