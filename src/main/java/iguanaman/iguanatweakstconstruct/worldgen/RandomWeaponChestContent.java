package iguanaman.iguanatweakstconstruct.worldgen;

import iguanaman.iguanatweakstconstruct.leveling.RandomBonuses;
import iguanaman.iguanatweakstconstruct.util.Log;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import tconstruct.items.tools.Battleaxe;
import tconstruct.items.tools.BowBase;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.crafting.ToolRecipe;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.library.tools.Weapon;
import tconstruct.library.util.IToolPart;
import tconstruct.tools.TinkerTools;

import java.util.*;

public class RandomWeaponChestContent extends WeightedRandomChestContent {
    private int minModifiers;
    private int maxModifiers;
    private int maxQuality;
    private List<Integer> materialIDs;

    private List<ToolRecipe> weapons;

    public RandomWeaponChestContent(int minCount, int maxCount, int weight, int minModifiers, int maxModifiers, int maxQuality) {
        super(TinkerTools.broadsword, 0, minCount, maxCount, weight);

        this.minModifiers = minModifiers;
        this.maxModifiers = maxModifiers;
        this.maxQuality = maxQuality;

        // determine all available weapons
        weapons = new ArrayList<ToolRecipe>();
        for(ToolRecipe recipe : ToolBuilder.instance.combos) {
            ToolCore type = recipe.getType();
            if (type instanceof Weapon || type instanceof Battleaxe) //  || type instanceof BowBase) //todo: re-enable bow once IToolPart PR is merged
                weapons.add(recipe);
        }
    }

    @Override
    protected ItemStack[] generateChestContent(Random random, IInventory newInventory) {
        int count = this.theMinimumChanceToGenerateItem + (random.nextInt(this.theMaximumChanceToGenerateItem - this.theMinimumChanceToGenerateItem + 1));
        ItemStack[] ret = new ItemStack[count];

        while(count > 0) {
            // determine type
            ToolRecipe recipe = weapons.get(random.nextInt(weapons.size()));
            ToolCore type = recipe.getType();

            if(materialIDs == null)
                prepareMaterials();;

            ItemStack weapon = null;
            // try to build the weapon
            do {
                // get components
                ItemStack[] parts = new ItemStack[] {null,null,null,null};

                Item[] items = new Item[4];
                items[0] = type.getHeadItem();
                items[1] = type.getHandleItem();
                items[2] = type.getAccessoryItem();
                items[3] = type.getExtraItem();

                for(int i = 0; i < 4; i++)
                {
                    if(items[i] == null)
                        continue;

                    // get a material
                    Integer matId = materialIDs.get(random.nextInt(materialIDs.size()));
                    do {
                        parts[i] = new ItemStack(items[i], 1, matId);
                    } while(((IToolPart)items[i]).getMaterialID(parts[i]) == -1);
                }
                // build the tool
                weapon = ToolBuilder.instance.buildTool(parts[0], parts[1], parts[2], parts[3], "");
            } while(weapon == null);

            int modCount = minModifiers + (random.nextInt(maxModifiers - minModifiers + 1));
            while(modCount-- > 0)
                RandomBonuses.tryModifying(null, weapon);

            count--;
            ret[count] = weapon;
        }



        return ret;
    }

    // this is done the first time a weapon generates, so we catch all materials
    private void prepareMaterials()
    {
        materialIDs = new ArrayList<Integer>(); // converted to list for indexed access for random

        for(Map.Entry<Integer, ToolMaterial> entry : TConstructRegistry.toolMaterials.entrySet())
            if(entry.getValue().harvestLevel() <= maxQuality)
                materialIDs.add(entry.getKey());
    }
}
