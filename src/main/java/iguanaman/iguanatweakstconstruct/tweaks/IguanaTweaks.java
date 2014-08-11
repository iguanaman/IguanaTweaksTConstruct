package iguanaman.iguanatweakstconstruct.tweaks;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.tweaks.handlers.*;
import iguanaman.iguanatweakstconstruct.tweaks.modifiers.ModFluxExpensive;
import iguanaman.iguanatweakstconstruct.tweaks.modifiers.ModLimitedToolRepair;
import iguanaman.iguanatweakstconstruct.util.Log;
import iguanaman.iguanatweakstconstruct.util.RecipeRemover;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.util.IPattern;
import tconstruct.modifiers.tools.ModFlux;
import tconstruct.modifiers.tools.ModToolRepair;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tconstruct.world.TinkerWorld;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Various Tweaks for Tinkers Construct and Vanilla Minecraft. See Config.
 */

@Pulse(id = Reference.PULSE_TWEAKS, description = "Various Tweaks for vanilla Minecraft and Tinker's Construct. See Config.")
public class IguanaTweaks {
    public static Set<Item> toolWhitelist = new HashSet<Item>();

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        // flint recipes n stuff
        flintTweaks();

        if(Config.castsBurnMaterial)
            castCreatingConsumesPart();

        if(Config.allowPartReuse)
            reusableToolParts();

        // no stone tools for you
        if(Config.disableStoneTools) {
            Log.info("Disabling tinkers stone tools");
            MinecraftForge.EVENT_BUS.register(new StoneToolHandler());
            ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Items.stone_axe));
            ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Items.stone_pickaxe));
        }

        // because diamond pickaxe is hax
        if(Config.nerfVanillaTools) {
            // init whitelist
            findToolsFromConfig();

            Log.info("Sticks and stones may break my bones, but your pickaxes and axes will break no blocks.");
            MinecraftForge.EVENT_BUS.register(new VanillaToolNerfHandler());

            // replace vanilla tools with tinker tools in bonus chests
            ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Items.wooden_pickaxe));
            ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Items.wooden_axe));
            ItemStack starterPick = ToolBuilder.instance.buildTool(new ItemStack(TinkerTools.pickaxeHead, 1, 0), new ItemStack(TinkerTools.toolRod, 1, 0), new ItemStack(TinkerTools.binding, 1, 0), "Starter Pickaxe");
            ItemStack starterAxe = ToolBuilder.instance.buildTool(new ItemStack(TinkerTools.hatchetHead, 1, 0), new ItemStack(TinkerTools.toolRod, 1, 0), null, "Starter Hatchet");
            if(starterPick != null)
                ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(starterPick, 1, 1, 5));
            if(starterAxe != null)
                ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(starterAxe, 1, 1, 5));

            // same with stone tools if not disabled
            if(!Config.disableStoneTools)
            {
                ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Items.stone_axe));
                ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Items.stone_pickaxe));
                ItemStack stonePick = ToolBuilder.instance.buildTool(new ItemStack(TinkerTools.pickaxeHead, 1, 1), new ItemStack(TinkerTools.toolRod, 1, 0), new ItemStack(TinkerTools.binding, 1, 0), "");
                ItemStack stoneAxe = ToolBuilder.instance.buildTool(new ItemStack(TinkerTools.hatchetHead, 1, 1), new ItemStack(TinkerTools.toolRod, 1, 0), null, "");
                if(stonePick != null)
                    ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(stonePick, 1, 1, 5));
                if(stoneAxe != null)
                    ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(stoneAxe, 1, 1, 5));
            }
        }

        // no hoes for you
        if(Config.nerfVanillaHoes) {
            Log.info("Vanilla hoe? More like vanilla go!");
            MinecraftForge.EVENT_BUS.register(new VanillaHoeNerfHandler());
        }

        if(Config.nerfVanillaSwords) {
            Log.info("Replacing swords with pasta");
            MinecraftForge.EVENT_BUS.register(new VanillaSwordNerfHandler());
        }

        if(Config.nerfVanillaBows) {
            Log.info("Sabotaging bows");
            MinecraftForge.EVENT_BUS.register(new VanillaBowNerfHandler());
        }

        // stonetorches
        if(Config.removeStoneTorchRecipe)
        {
            Log.info("Removing stone torch recipe");
            RecipeRemover.removeAnyRecipeFor(Item.getItemFromBlock(TinkerWorld.stoneTorch));
        }

        // silky jewel nerfs
        if(Config.moreExpensiveSilkyCloth)
        {
            Log.info("Making Silky Cloth more expensive");
            RecipeRemover.removeAnyRecipe(new ItemStack(TinkerTools.materials, 1, 25));
            String[] patSurround = { "###", "#m#", "###" };
            GameRegistry.addRecipe(new ItemStack(TinkerTools.materials, 1, 25), patSurround, 'm', new ItemStack(TinkerTools.materials, 1, 14), '#', new ItemStack(Items.string));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TinkerTools.materials, 1, 25), patSurround, 'm', "ingotGold", '#', new ItemStack(Items.string)));
        }
        if(Config.moreExpensiveSilkyJewel)
        {
            Log.info("Making Silky Jewel more expensive");
            RecipeRemover.removeAnyRecipe(new ItemStack(TinkerTools.materials, 1, 26));
            GameRegistry.addRecipe(new ItemStack(TinkerTools.materials, 1, 26), " c ", "cec", " c ", 'c', new ItemStack(TinkerTools.materials, 1, 25), 'e', new ItemStack(Item.getItemFromBlock(Blocks.emerald_block)));
        }

        if(Config.moreModifiersForFlux)
            exchangeFluxModifier();

        if(Config.maxToolRepairs > -1)
            limitToolRepair();

        // has to be added after exchanging the repair modifier, to obtain the correct cache
        if(Config.easyToolRepair)
            GameRegistry.addRecipe(new RepairCraftingRecipe());
    }

    private void flintTweaks()
    {
        if(Config.removeFlintDrop) {
            Log.info("Removing Flint drops from Gravel");
            MinecraftForge.EVENT_BUS.register(new FlintHandler());
        }

        if(Config.addFlintRecipe) {
            Log.info("Adding shapeless Flint recipe from " + Config.recipeGravelPerFlint + " Gravel");
            // create recipe
            Object[] recipe = new ItemStack[Config.recipeGravelPerFlint];
            for(int i = 0; i < Config.recipeGravelPerFlint; i++)
                recipe[i] = new ItemStack(Blocks.gravel);

            // add recipe
            GameRegistry.addShapelessRecipe(new ItemStack(Items.flint), recipe);
        }
    }

    private void castCreatingConsumesPart()
    {
        Log.info("Modifying cast creation to consume toolpart");
        try {
            Field consume = CastingRecipe.class.getDeclaredField("consumeCast");
            consume.setAccessible(true);

            for(CastingRecipe recipe : TConstructRegistry.getTableCasting().getCastingRecipes())
                if(recipe.getResult().getItem() == TinkerSmeltery.metalPattern)
                    consume.set(recipe, true);
        } catch (NoSuchFieldException e) {
            Log.error("Couldn't find field to modify");
            Log.error(e);
        } catch (IllegalAccessException e) {
            Log.error("Couldn't modify casting pattern");
            Log.error(e);
        }
    }

    private void reusableToolParts() {
        Log.info("Registering reusable tool parts");
        // the material IDs of non-metal parts
        //int[] nonMetals = { 0, 1, 3, 4, 5, 6, 7, 8, 9, 17, 31 };
        for (Map.Entry<List, ItemStack> entry : TConstructRegistry.patternPartMapping.entrySet()) {
            Item pattern = (Item) entry.getKey().get(0); // the pattern
            Integer meta = (Integer) entry.getKey().get(1); // metadata of the pattern
            Integer matID = (Integer) entry.getKey().get(2); // Material-ID of the material needed to craft
            ItemStack toolPart = (ItemStack) entry.getValue(); // the itemstack created

            // get pattern cost
            int cost = ((IPattern)pattern).getPatternCost(new ItemStack(pattern, 1, meta)); // the cost is 0.5*2
            if(cost <= 0)
                continue;

            PatternBuilder.instance.registerMaterial(toolPart, cost, TConstructRegistry.getMaterial(matID).materialName);
        }
    }

    private void exchangeFluxModifier()
    {

        List<ItemModifier> mods = ModifyBuilder.instance.itemModifiers;
        for(ListIterator<ItemModifier> iter = mods.listIterator(); iter.hasNext();)
        {
            ItemModifier mod = iter.next();
            // flux mod
            if(mod instanceof ModFlux) {
                iter.set(new ModFluxExpensive(((ModFlux) mod).batteries));
                Log.trace("Replaced Flux Modifier to make it more expensive");
            }
        }
    }

    private void limitToolRepair()
    {

        List<ItemModifier> mods = ModifyBuilder.instance.itemModifiers;
        for(ListIterator<ItemModifier> iter = mods.listIterator(); iter.hasNext();)
        {
            ItemModifier mod = iter.next();
            // flux mod
            if(mod instanceof ModToolRepair) {
                iter.set(new ModLimitedToolRepair());
                Log.trace("Replaced Tool Repair Modifier to limit the maximum amount of repairs");
            }
        }
    }

    private static void findToolsFromConfig()
    {
        Log.info("Setting up whitelist for allowed tools");
        // cycle through config entries
        for(String identifier : Config.allowedTools) {
            // look them up in the registry (we're in postInit. everything should be registered)
            Object o = Item.itemRegistry.getObject(identifier);
            // if we found it, add it.
            if(o != null)
                toolWhitelist.add((Item)o);
        }

        // mod-wide enabling
        for(Object identifier : Item.itemRegistry.getKeys())
        {
            String mod = identifier.toString().split(":")[0]; // should always be non-null... I think
            if(Config.allowedModTools.contains(mod))
            {
                // get the item
                Object item = Item.itemRegistry.getObject(identifier);
                if(item instanceof ItemTool || item instanceof ItemHoe || item instanceof ItemSword || item instanceof ItemBow)
                    toolWhitelist.add((Item)item);
            }
        }
    }
}
