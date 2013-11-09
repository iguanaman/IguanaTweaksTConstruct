package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.commands.IguanaCommandConfig;
import iguanaman.iguanatweakstconstruct.commands.IguanaCommandLevelUpTool;
import iguanaman.iguanatweakstconstruct.commands.IguanaCommandToolXP;
import iguanaman.iguanatweakstconstruct.util.IguanaPatternCraftingHandler;
import iguanaman.iguanatweakstconstruct.util.IguanaEventHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;

import org.modstats.ModstatInfo;
import org.modstats.Modstats;

import com.google.common.base.Optional;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="IguanaTweaksTConstruct", name="Iguana Tweaks for Tinker's Construct", version="1.6.X-1n", 
dependencies = "required-after:TConstruct;after:UndergroundBiomes@;after:UndergroundBiomesBlender@;after:GregTech;after:GregTech-Addon;after:IC2@;after:ThermalExpansion@;after:Buildcraft@;after:TEOreGen@")
@NetworkMod(clientSideRequired=true, serverSideRequired=true)
@ModstatInfo(prefix="igtweakstc")
public class IguanaTweaksTConstruct {
	
        // The instance of your mod that Forge uses.
        @Instance("IguanaTweaksTConstruct")
        public static IguanaTweaksTConstruct instance;
       
        // Says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide="iguanaman.iguanatweakstconstruct.ClientProxy", serverSide="iguanaman.iguanatweakstconstruct.CommonProxy")
        public static CommonProxy proxy;
        
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {
            
            proxy.registerSounds();
            NetworkRegistry.instance().registerGuiHandler(instance, proxy);

        	IguanaConfig.init(event.getSuggestedConfigurationFile());
            
        	IguanaLog.log("Starting event handler");
            MinecraftForge.EVENT_BUS.register(new IguanaEventHandler());
            
            IguanaBlocks.init();
            IguanaItems.init();
            MaterialTweaks.init();
            ModifierTweaks.init();
            VariousTweaks.init();
            RemoveVanillaTools.init();
        }
     	  
       
        @EventHandler
        public void load(FMLInitializationEvent event) {
        	IguanaLog.log("Registering with modstats");
            Modstats.instance().getReporter().registerMod(this);

            GameRegistry.registerCraftingHandler(new IguanaPatternCraftingHandler());
        }
       
        @EventHandler
        public void postInit(FMLPostInitializationEvent event) {
        	proxy.registerRenderers();

            HarvestLevelTweaks.init();
        }
        
		@EventHandler
		public void serverStarting(FMLServerStartingEvent event)
		{
			ICommandManager commandManager = ModLoader.getMinecraftServerInstance().getCommandManager();
			ServerCommandManager serverCommandManager = ((ServerCommandManager) commandManager);
			serverCommandManager.registerCommand(new IguanaCommandConfig());
			serverCommandManager.registerCommand(new IguanaCommandLevelUpTool());
			serverCommandManager.registerCommand(new IguanaCommandToolXP());
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