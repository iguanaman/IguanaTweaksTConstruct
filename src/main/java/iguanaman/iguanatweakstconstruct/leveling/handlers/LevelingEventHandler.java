package iguanaman.iguanatweakstconstruct.leveling.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iguanaman.iguanatweakstconstruct.leveling.LevelingLogic;
import iguanaman.iguanatweakstconstruct.leveling.LevelingTooltips;
import iguanaman.iguanatweakstconstruct.leveling.RandomBonuses;
import iguanaman.iguanatweakstconstruct.reference.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import tconstruct.items.tools.Battleaxe;
import tconstruct.items.tools.Hammer;
import tconstruct.items.tools.Pickaxe;
import tconstruct.items.tools.Shortbow;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.event.ToolCraftEvent;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.Weapon;
import tconstruct.tools.TinkerTools;

public class LevelingEventHandler {
    @SubscribeEvent
    public void onHurt (LivingHurtEvent event)
    {
        // only player caused damage
        if (!(event.source.damageType.equals("player") || event.source.damageType.equals("arrow")))
            return;

        // only players
        if (!(event.source.getEntity() instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.source.getEntity();
        // but no fake players
        if(player instanceof FakePlayer)
            return;

        ItemStack stack = player.getCurrentEquippedItem();
        if (stack == null || !stack.hasTagCompound())
            return;

        // is a weapon?
        if (stack.getItem() instanceof Weapon || stack.getItem() instanceof Battleaxe || stack.getItem() instanceof Shortbow && event.source.damageType.equals("arrow")) {
            long xp = Math.round(event.ammount);
            if (event.entityLiving instanceof EntityAnimal) xp = Math.round(event.ammount / 4f);

            if (xp > 0)
            {
                LevelingLogic.addXP(stack, player, xp);

                NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");

                // bonus chance for luck if hitting passive mob
                if(event.entityLiving instanceof EntityAnimal)
                    RandomBonuses.addModifierExtraWeight(RandomBonuses.Modifier.LAPIS, (int)xp+5, tags);
                // otherwise damage chance
                else
                    RandomBonuses.addModifierExtraWeight(RandomBonuses.Modifier.ATTACK, (int)xp, tags);

                // spiders also increase bane chance
                if(event.entityLiving instanceof EntitySpider)
                    RandomBonuses.addModifierExtraWeight(RandomBonuses.Modifier.BANE, (int)xp, tags);
                // blazes give fiery chance (yes, blizz gives fiery :P)
                else if(event.entityLiving instanceof EntityBlaze)
                    RandomBonuses.addModifierExtraWeight(RandomBonuses.Modifier.BLAZE, (int)xp, tags);
                // zombie pigman gives lifesteal
                else if(event.entityLiving instanceof EntityPigZombie)
                    RandomBonuses.addModifierExtraWeight(RandomBonuses.Modifier.LIFESTEAL, (int)xp, tags);
                // zombie gives smite
                else if(event.entityLiving instanceof EntityZombie)
                    RandomBonuses.addModifierExtraWeight(RandomBonuses.Modifier.SMITE, (int)xp, tags);
                // wither skeleton gives lifesteal
                else if(event.entityLiving instanceof EntitySkeleton) {
                    if (((EntitySkeleton) event.entityLiving).getSkeletonType() != 0)
                        RandomBonuses.addModifierExtraWeight(RandomBonuses.Modifier.LIFESTEAL, (int)xp+2, tags);
                }
                // enderman gives beheading
                else if(event.entityLiving instanceof EntityEnderman)
                    RandomBonuses.addModifierExtraWeight(RandomBonuses.Modifier.BEHEADING, (int)xp+3, tags);

                // knocking back enemies with spriting gives knockback chance
                if(player.isSprinting())
                    RandomBonuses.addModifierExtraWeight(RandomBonuses.Modifier.KNOCKBACK, (int)xp+2, tags);
            }
        }
    }

    @SubscribeEvent
    public void onUseHoe(UseHoeEvent event) {
        EntityPlayer player = event.entityPlayer;
        // no fake players
        if(player instanceof FakePlayer) return;
        ItemStack stack = event.current;
        if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof ToolCore)
            LevelingLogic.addXP(stack, player, 1L);
    }

    @SubscribeEvent
    public void onCraftTool (ToolCraftEvent.NormalTool event) {
        // arrows don't get levels
        if(event.tool == TinkerTools.arrow)
            return;

        NBTTagCompound toolTag = event.toolTag.getCompoundTag("InfiTool");
        int head = toolTag.getInteger("Head");
        int handle = toolTag.getInteger("Handle");
        int accessory = toolTag.getInteger("Accessory");
        int extra = toolTag.hasKey("Extra") ? toolTag.getInteger("Extra") : -1;

        // add tags for tool leveling
        LevelingLogic.addLevelingTags(toolTag, event.tool);



        if (Config.toolLeveling && Config.toolLevelingExtraModifiers)
            toolTag.setInteger("Modifiers", Math.max(toolTag.getInteger("Modifiers") - 3, 0));

        /* what is this? o_O
        if (event.tool == TinkerTools.hammer || event.tool == TinkerTools.excavator || event.tool == TinkerTools.lumberaxe)
        {
            List<String> replaceTags = new ArrayList<String>(Arrays.asList(
                    "MiningSpeed", "MiningSpeed2", "MiningSpeedHandle", "MiningSpeedExtra"
            ));

            for (String replaceTag : replaceTags)
                if (toolTag.hasKey(replaceTag)) toolTag.setInteger(replaceTag, Math.round(toolTag.getInteger(replaceTag) / 2f));

            event = event;
        }
        */
    }



    // Display XP of held tool in debug (F3) if config is set
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (Config.toolLeveling && Config.showDebugXP)
        {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayer player = mc.thePlayer;
            if (!player.isDead && mc.gameSettings.showDebugInfo) {
                ItemStack equipped = player.getCurrentEquippedItem();

                if (equipped != null && equipped.getItem() != null && equipped.getItem() instanceof ToolCore)
                {
                    NBTTagCompound tags = equipped.getTagCompound().getCompoundTag("InfiTool");

                    int level = tags.getInteger("ToolLevel");
                    int hLevel = tags.hasKey("HarvestLevel") ? hLevel = tags.getInteger("HarvestLevel") : -1;

                    event.left.add("");

                    if (Config.showTooltipXP)
                    {
                        if (level <= 5)
                            event.left.add(LevelingTooltips.getXpToolTip(equipped, null));

                        if (Config.levelingPickaxeBoost)
                            if (hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() && hLevel < TConstructRegistry.getMaterial("Manyullyn").harvestLevel()
                                    && !tags.hasKey("HarvestLevelModified")
                                    && (equipped.getItem() instanceof Pickaxe || equipped.getItem() instanceof Hammer))
                                event.left.add(LevelingTooltips.getBoostXpToolTip(equipped, null));
                    }
                }
            }
        }
    }
}
