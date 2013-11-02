package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.commands.IguanaCommandConfig;
import iguanaman.iguanatweakstconstruct.commands.IguanaCommandLevelUpTool;
import iguanaman.iguanatweakstconstruct.commands.IguanaCommandToolXP;
import iguanaman.iguanatweakstconstruct.handlers.IguanaCraftingHandler;
import iguanaman.iguanatweakstconstruct.handlers.IguanaEventHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import tconstruct.TConstruct;
import tconstruct.blocks.ToolForgeBlock;
import tconstruct.blocks.ToolStationBlock;
import tconstruct.blocks.logic.ToolForgeLogic;
import tconstruct.blocks.logic.ToolStationLogic;
import tconstruct.common.TContent;
import tconstruct.items.CraftingItem;
import tconstruct.items.FilledBucket;
import tconstruct.items.MetalPattern;
import tconstruct.items.Pattern;
import tconstruct.items.ToolPart;
import tconstruct.items.blocks.ToolForgeItemBlock;
import tconstruct.items.blocks.ToolStationItemBlock;
import tconstruct.items.tools.*;
import tconstruct.library.ActiveToolMod;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.crafting.ToolRecipe;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.library.tools.ToolMod;
import tconstruct.library.tools.Weapon;
import tconstruct.modifiers.ModAttack;
import tconstruct.modifiers.ModButtertouch;
import tconstruct.modifiers.ModDurability;
import tconstruct.modifiers.ModExtraModifier;
import tconstruct.modifiers.ModInteger;
import tconstruct.modifiers.ModLapis;
import tconstruct.modifiers.ModRedstone;
import tconstruct.modifiers.ModRepair;
import tconstruct.modifiers.TActiveOmniMod;
import tconstruct.util.PHConstruct;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.src.ModLoader;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

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

@Mod(modid="IguanaTweaksTConstruct", name="Iguana Tweaks for Tinker's Construct", version="1.6.X-1l", 
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
            IguanaBlocks.init();
            IguanaItems.init();
            MaterialTweaks.init();
            HarvestLevelTweaks.init();
            ModifierTweaks.init();
            VariousTweaks.init();
            RemoveVanillaTools.init();
        }
     	  
       
        @EventHandler
        public void load(FMLInitializationEvent event) {
        	IguanaLog.log("Registering with modstats");
            Modstats.instance().getReporter().registerMod(this);
            
        	IguanaLog.log("Starting event handler");
            MinecraftForge.EVENT_BUS.register(new IguanaEventHandler());

            GameRegistry.registerCraftingHandler(new IguanaCraftingHandler());
        }
       
        @EventHandler
        public void postInit(FMLPostInitializationEvent event) {
        	proxy.registerRenderers();
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