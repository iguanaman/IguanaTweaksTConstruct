package iguanaman.iguanatweakstconstruct;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import iguanaman.iguanatweakstconstruct.claybuckets.IguanaItems;
import iguanaman.iguanatweakstconstruct.harvestlevels.IguanaHarvestLevelTweaks;
import iguanaman.iguanatweakstconstruct.leveling.IguanaToolLeveling;
import iguanaman.iguanatweakstconstruct.leveling.commands.IguanaCommandLevelUpTool;
import iguanaman.iguanatweakstconstruct.leveling.commands.IguanaCommandToolXP;
import iguanaman.iguanatweakstconstruct.leveling.commands.debug;
import iguanaman.iguanatweakstconstruct.mobheads.IguanaMobHeads;
import iguanaman.iguanatweakstconstruct.proxy.CommonProxy;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.replacing.IguanaToolPartReplacing;
import iguanaman.iguanatweakstconstruct.tweaks.IguanaTweaks;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import iguanaman.iguanatweakstconstruct.util.Log;
import mantle.pulsar.config.ForgeCFG;
import mantle.pulsar.control.PulseManager;
import mantle.pulsar.pulse.PulseMeta;
import net.minecraft.item.Item;

import java.util.List;

// inofficial todo list:
// todo: check out what mining-maxlvl i wont for emerald/diamond modifier.
// todo: check if it's possible to remove pickaxe harvestability from regular picks... or make them stone mininglevel
// todo: find a solution to stone-head-tools not having xp even when head is replaced (maybe like old iguana, disallow stone tools?)
// todo: add randomly generated weapons with random bonuses(!!!!) to dungeon loot :D:D:D

// todo: Unknown blocks get incorrect mining level. (just check if they're higher than the places where i added additional harvest levels and add the proper ammount to match up with the old mining level)
// todo: add hardcoded ExtraTic mining levels

// todo: add batman-hat (enderman-head without jaw and eyes)
// todo: add enderman-jaw helmet as a very rare enderman drop :D
// todo: bucket hattt
// todo: cracked clay bucket? randomly obtainable when transporting lava? :D

@Mod(modid= Reference.MOD_ID, name= Reference.MOD_NAME, version="${version}",
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
    public static boolean isMobHeadsActive = false;
    public static boolean isTweaksActive = false;
    public static boolean isItemsActive = false;
    public static boolean isPartReplacementActive = false;

	public static List<Item> toolParts = null;

    // TODO: decide wether or not the same cfg as tcon should be used
    // use the PulseManager. This allows us to separate the different parts into independend modules and have stuff together. yay.
    private ForgeCFG pulseCFG = new ForgeCFG("TinkersModules", "Addon: Iguana Tweaks for Tinkers Construct");
    private PulseManager pulsar = new PulseManager(Reference.MOD_ID, pulseCFG);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
        Config config = new Config();
        config.init(event.getSuggestedConfigurationFile());
        // register config as eventhandler to get config changed updates
        FMLCommonHandler.instance().bus().register(config);

        // workaround to know which modules are active.. :I
        isToolLevelingActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_LEVELING, "", false, false));
        isHarvestTweaksActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_HARVESTTWEAKS, "", false, false));
        isMobHeadsActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_MOBHEADS, "", false, false));
        isTweaksActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_TWEAKS, "", false, false));
        isItemsActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_ITEMS, "", false, false));
        isPartReplacementActive = pulseCFG.isModuleEnabled(new PulseMeta(Reference.PULSE_REPLACING, "", false, false));


        // if we don't use our custom harvest levels, we have to adjust what we're using
        if(!isHarvestTweaksActive)
            HarvestLevels.adjustToVanillaLevels();

        pulsar.registerPulse(new IguanaHarvestLevelTweaks());
        pulsar.registerPulse(new IguanaToolLeveling());
        pulsar.registerPulse(new IguanaToolPartReplacing());
        pulsar.registerPulse(new IguanaMobHeads());
        pulsar.registerPulse(new IguanaItems());
        pulsar.registerPulse(new IguanaTweaks());
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