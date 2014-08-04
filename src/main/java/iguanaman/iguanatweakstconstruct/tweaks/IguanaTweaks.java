package iguanaman.iguanatweakstconstruct.tweaks;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.tweaks.handlers.FlintHandler;
import iguanaman.iguanatweakstconstruct.tweaks.handlers.StoneToolHandler;
import iguanaman.iguanatweakstconstruct.tweaks.handlers.VanillaToolNerfHandler;
import iguanaman.iguanatweakstconstruct.util.Log;
import iguanaman.iguanatweakstconstruct.util.RecipeRemover;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.world.TinkerWorld;

import java.lang.reflect.Field;

/**
 * Various Tweaks for Tinkers Construct and Vanilla Minecraft. See Config.
 */

@Pulse(id = Reference.PULSE_TWEAKS, description = "Various Tweaks for vanilla Minecraft and Tinker's Construct. See Config.")
public class IguanaTweaks {

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        // flint recipes n stuff
        flintTweaks();

        if(Config.easyToolRepair)
            GameRegistry.addRecipe(new RepairCraftingRecipe());

        if(Config.castsBurnMaterial)
            castCreatingConsumesPart();

        // no stone tools for you
        if(Config.disableStoneTools)
            MinecraftForge.EVENT_BUS.register(new StoneToolHandler());

        // because diamond pickaxe is hax
        if(Config.nerfVanillaTools)
            MinecraftForge.EVENT_BUS.register(new VanillaToolNerfHandler());

        // stonetorches
        if(Config.removeStoneTorchRecipe)
        {
            Log.info("Removing stone torch recipe");
            RecipeRemover.removeAnyRecipeFor(Item.getItemFromBlock(TinkerWorld.stoneTorch));
        }
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
}
