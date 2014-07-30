package iguanaman.iguanatweakstconstruct.old;

import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGravel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.PatternBuilder.ItemKey;
import tconstruct.library.crafting.PatternBuilder.MaterialSet;
import tconstruct.library.event.PartBuilderEvent;
import tconstruct.library.event.ToolCraftEvent;
import tconstruct.library.tools.ToolCore;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.smeltery.blocks.LiquidMetalFinite;
import tconstruct.tools.TinkerTools;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class IguanaEventHandler {

	Random random = new Random();

	@SubscribeEvent
	public void onItemExpireEvent(ItemExpireEvent event)
	{
		if (Config.toolsNeverDespawn && event.entity != null && event.entity instanceof EntityItem)
		{
			ItemStack stack = ((EntityItem)event.entity).getEntityItem();
			if (stack.getItem() != null && stack.getItem() instanceof ToolCore)
				event.setResult(Event.Result.DENY);
		}
	}
/*
	@SubscribeEvent
	public void onHurt (LivingHurtEvent event)
	{
		if (event.source.damageType.equals("player") || event.source.damageType.equals("arrow"))
			if (event.source.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) event.source.getEntity();
				ItemStack stack = player.getCurrentEquippedItem();
				if (stack != null && stack.hasTagCompound())
					if (stack.getItem() instanceof Weapon || stack.getItem() instanceof Shortbow && event.source.damageType.equals("arrow"))
					{
						long xp = Math.round(event.ammount);
						if (event.entityLiving instanceof EntityAnimal) xp = Math.round(event.ammount / 4f);

						if (xp > 0) IguanaLevelingLogic.addXP(stack, player, xp);
					}
			}
	}

	@SubscribeEvent
	public void onUseHoe(UseHoeEvent event) {
		EntityPlayer player = event.entityPlayer;
		ItemStack stack = event.current;
		if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof ToolCore)
			IguanaLevelingLogic.addXP(stack, player, 1L);
	}*/

	void addDrops(LivingDropsEvent event, ItemStack dropStack)
	{
		EntityItem entityitem = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, dropStack);
		entityitem.delayBeforeCanPickup = 10;
		event.drops.add(entityitem);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void LivingDrops(LivingDropsEvent event)
	{
		Iterator<EntityItem> i = event.drops.iterator();
		while (i.hasNext()) {
			EntityItem eitem = i.next();

			if (eitem != null)
				if (eitem.getEntityItem() != null)
				{
					ItemStack item = eitem.getEntityItem();
					if (item.getItem() == Items.skull && item.getItemDamage() != 3)
						i.remove();
				}
		}

		if (event.recentlyHit && event.source.damageType.equals("player"))
		{
			int skullID = -1;

			if (event.entityLiving instanceof EntitySkeleton)
				skullID = ((EntitySkeleton)event.entityLiving).getSkeletonType();
			else if (event.entityLiving instanceof EntityPigZombie)
				skullID = 6;
			else if (event.entityLiving instanceof EntityZombie)
				skullID = 2;
			else if (event.entityLiving instanceof EntityCreeper)
				skullID = 4;
			else if (event.entityLiving instanceof EntityEnderman)
				skullID = 5;
			else if (event.entityLiving instanceof EntityBlaze)
				skullID = 7;

			if (skullID != -1)
			{
				EntityPlayer player = (EntityPlayer) event.source.getEntity();
				ItemStack stack = player.getCurrentEquippedItem();
				int beheading = 0;

				if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof ToolCore)
				{
					beheading = stack.getTagCompound().getCompoundTag("InfiTool").getInteger("Beheading");
					if (stack.getItem() == TinkerTools.cleaver)
						beheading += 2;
				}

				if (random.nextInt(100) < beheading * Config.beheadingHeadDropChance + Config.baseHeadDropChance)
					addDrops(event, new ItemStack(Items.skull, 1, skullID));
			}
		}
	}

	@SubscribeEvent
	public void bucketFill (FillBucketEvent evt)
	{
		if (evt.current.getItem() == IguanaItems.clayBucketFired && evt.target.typeOfHit == MovingObjectType.BLOCK)
		{
			int hitX = evt.target.blockX;
			int hitY = evt.target.blockY;
			int hitZ = evt.target.blockZ;

			if (evt.entityPlayer != null && !evt.entityPlayer.canPlayerEdit(hitX, hitY, hitZ, evt.target.sideHit, evt.current))
				return;

			Block block = evt.world.getBlock(hitX, hitY, hitZ);
			for (int id = 0; id < TinkerSmeltery.fluidBlocks.length; id++)
				if (block == TinkerSmeltery.fluidBlocks[id])
					if (evt.entityPlayer.capabilities.isCreativeMode)
						evt.world.setBlockToAir(hitX, hitY, hitZ);
					else
					{
						if (TinkerSmeltery.fluidBlocks[id] instanceof LiquidMetalFinite)
						{
							int quanta = 0;
							for (int posX = -1; posX <= 1; posX++)
								for (int posZ = -1; posZ <= 1; posZ++)
								{
									Block localBlock = evt.world.getBlock(hitX + posX, hitY, hitZ + posZ);
									if (localBlock == block)
										quanta += evt.world.getBlockMetadata(hitX + posX, hitY, hitZ + posZ) + 1;
								}

							if (quanta >= 8)
								while (quanta > 0)
									for (int posX = -1; posX <= 1; posX++)
										for (int posZ = -1; posZ <= 1; posZ++)
										{
											Block localBlock = evt.world.getBlock(hitX + posX, hitY, hitZ + posZ);
											if (localBlock == block)
											{
												quanta -= 1;
												int meta = evt.world.getBlockMetadata(hitX + posX, hitY, hitZ + posZ);
												if (meta > 0)
													evt.world.setBlockMetadataWithNotify(hitX + posX, hitY, hitZ + posZ, meta - 1, 3);
												else
													evt.world.setBlockToAir(hitX + posX, hitY, hitZ + posZ);
											}
										}
						} else
							evt.world.setBlockToAir(hitX, hitY, hitZ);

						evt.setResult(Event.Result.ALLOW);
						evt.result = new ItemStack(IguanaItems.clayBuckets, 1, id);
					}
		}
	}

	@SubscribeEvent
	public void EntityInteract(EntityInteractEvent event)
	{
		if (event != null && event.target != null && event.target instanceof EntityCow)
		{
			ItemStack equipped = event.entityPlayer.getCurrentEquippedItem();
			{
				if (equipped != null && equipped.getItem() == IguanaItems.clayBucketFired)
					if (--equipped.stackSize <= 0)
						event.entityPlayer.setCurrentItemOrArmor(0, new ItemStack(IguanaItems.clayBucketMilk));
					else if (!event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(IguanaItems.clayBucketMilk)))
						event.entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(IguanaItems.clayBucketMilk), false);
			}
		}
	}

	@SubscribeEvent
	public void craftTool (ToolCraftEvent.NormalTool event)
	{
		NBTTagCompound toolTag = event.toolTag.getCompoundTag("InfiTool");
		int head = toolTag.getInteger("Head");
		int handle = toolTag.getInteger("Handle");
		int accessory = toolTag.getInteger("Accessory");
		int extra = toolTag.hasKey("Extra") ? toolTag.getInteger("Extra") : -1;

		if (!IguanaConfig.allowStoneTools && (head == 1 || handle == 1 || event.tool != TinkerTools.arrow && accessory == 1) || extra == 1)
		{
			event.setResult(Event.Result.DENY);
			return;
		}
		else if (IguanaConfig.allowStoneTools)
		{
			if (head == 1)
			{
				int partIndex = IguanaTweaksTConstruct.toolParts.indexOf(event.tool.getHeadItem());
				if (IguanaConfig.restrictedFlintParts.contains(partIndex+1))
				{
					event.setResult(Event.Result.DENY);
					return;
				}
			}

			if (handle == 1)
			{
				int partIndex = IguanaTweaksTConstruct.toolParts.indexOf(event.tool.getHandleItem());
				if (IguanaConfig.restrictedFlintParts.contains(partIndex+1))
				{
					event.setResult(Event.Result.DENY);
					return;
				}
			}

			if (event.tool != TinkerTools.arrow && accessory == 1)
			{
				int partIndex = IguanaTweaksTConstruct.toolParts.indexOf(event.tool.getAccessoryItem());
				if (IguanaConfig.restrictedFlintParts.contains(partIndex+1))
				{
					event.setResult(Event.Result.DENY);
					return;
				}
			}

			if (extra == 1)
			{
				int partIndex = IguanaTweaksTConstruct.toolParts.indexOf(event.tool.getExtraItem());
				if (IguanaConfig.restrictedFlintParts.contains(partIndex+1))
				{
					event.setResult(Event.Result.DENY);
					return;
				}
			}
		}

// Leveling
        /*
		if (event.tool != TinkerTools.arrow)
		{
			// CREATE TOOLTIP LISTS
			List<String> tips = new ArrayList<String>();
			List<String> modifierTips = new ArrayList<String>();

			// MINING LEVEL TOOLTIP
			if (event.tool instanceof Pickaxe || event.tool instanceof Hammer)
			{
				String mLevel = IguanaTweaksTConstruct.getHarvestLevelName(toolTag.getInteger("HarvestLevel"));
				tips.add("Mining Level: " + mLevel);
				modifierTips.add("");
			}

			// TOOL LEVELING DATA + TOOLTIP
			if (IguanaConfig.toolLeveling)
			{
				tips.add(IguanaLevelingLogic.getLevelTooltip(1));
				modifierTips.add("");

				toolTag.setInteger("ToolLevel", 1);

				toolTag.setLong("ToolEXP", 0);
				if (IguanaConfig.showTooltipXP)
				{
					tips.add(IguanaLevelingLogic.getXpToolTip(new ItemStack(event.tool), false, toolTag));
					modifierTips.add("");
				}

				if (IguanaConfig.levelingPickaxeBoost && (event.tool instanceof Pickaxe || event.tool instanceof Hammer))
				{
					toolTag.setLong("HeadEXP", 0);

					int hLevel = toolTag.hasKey("HarvestLevel") ? hLevel = toolTag.getInteger("HarvestLevel") : -1;
					if (hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() && hLevel < TConstructRegistry.getMaterial("Manyullyn").harvestLevel())
					{
						if (IguanaConfig.showTooltipXP)
						{
							tips.add(IguanaLevelingLogic.getXpToolTip(new ItemStack(event.tool), false, toolTag, true));
							modifierTips.add("");
						}

						tips.add("\u00A76Requires boost");
						modifierTips.add("");
					}
				}
			}

			// STORE + REMOVE EXISTING TOOLTIPS
			int tipNum = 0;
			while (true)
			{
				String tip = "Tooltip" + ++tipNum;
				if (toolTag.hasKey(tip))
				{
					tips.add(toolTag.getString(tip));
					modifierTips.add(toolTag.getString("ModifierTip" + tipNum));
					toolTag.removeTag(tip);
					toolTag.removeTag("ModifierTip" + tipNum);
				}
				else break;
			}

			// WRITE TOOLTIPS
			for (int i = 1; i <= tips.size(); ++i)
				if (tips.get(i - 1) != null)
				{
					toolTag.setString("Tooltip" + i, tips.get(i - 1));
					if (modifierTips.get(i - 1) != null)
						toolTag.setString("ModifierTip" + i, modifierTips.get(i - 1));
					else
						toolTag.setString("ModifierTip" + i, "");
				}

			if (IguanaConfig.toolLeveling && IguanaConfig.toolLevelingExtraModifiers)
				toolTag.setInteger("Modifiers", Math.max(toolTag.getInteger("Modifiers") - 3, 0));

			if (event.tool == TinkerTools.hammer || event.tool == TinkerTools.excavator || event.tool == TinkerTools.lumberaxe)
			{
				List<String> replaceTags = new ArrayList<String>(Arrays.asList(
						"MiningSpeed", "MiningSpeed2", "MiningSpeedHandle", "MiningSpeedExtra"
						));

				for (String replaceTag : replaceTags)
					if (toolTag.hasKey(replaceTag)) toolTag.setInteger(replaceTag, Math.round(toolTag.getInteger(replaceTag) / 2f));
			}
		}
*/
	}

	/* Crafting */
	@SubscribeEvent
	public void craftPart (PartBuilderEvent.NormalPart event)
	{
		ItemKey key = PatternBuilder.instance.getItemKey(event.material);
		if (key != null)
		{
			MaterialSet mat = (MaterialSet) PatternBuilder.instance.materialSets.get(key.key);
			if (mat != null)
				if (
						mat.materialID == 0 && IguanaConfig.restrictedWoodParts.contains(event.pattern.getItemDamage()) ||
						mat.materialID == 1 && IguanaConfig.restrictedStoneParts.contains(event.pattern.getItemDamage()) ||
						mat.materialID == 3 && IguanaConfig.restrictedFlintParts.contains(event.pattern.getItemDamage()) ||
						mat.materialID == 4 && IguanaConfig.restrictedCactusParts.contains(event.pattern.getItemDamage()) ||
						mat.materialID == 5 && IguanaConfig.restrictedBoneParts.contains(event.pattern.getItemDamage()) ||
						mat.materialID == 8 && IguanaConfig.restrictedSlimeParts.contains(event.pattern.getItemDamage()) ||
						mat.materialID == 9 && IguanaConfig.restrictedPaperParts.contains(event.pattern.getItemDamage()) ||
						mat.materialID == 17 && IguanaConfig.restrictedSlimeParts.contains(event.pattern.getItemDamage())
						)
				{
					event.setResult(Event.Result.DENY);
					return;
				}
		}
	}

	@SubscribeEvent
	public void onBlockHarvested(HarvestDropsEvent event)
	{
		if (event.block != null)
			if (Config.removeFlintDrop && event.block != null && event.block instanceof BlockGravel)
			{
				boolean addGravel = false;

				Iterator<ItemStack> it = event.drops.iterator();
				while (it.hasNext())
				{
					ItemStack stack = (ItemStack) it.next();
					if (stack != null && stack.getItem() == Items.flint)
					{
						it.remove();
						addGravel = true;
					}
				}

				if (addGravel) event.drops.add(new ItemStack(Blocks.gravel));
			}
	}
/*
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
		if (IguanaConfig.toolLeveling && IguanaConfig.showDebugXP)
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

					if (IguanaConfig.showTooltipXP)
					{
						if (level <= 5)
							event.left.add(IguanaLevelingLogic.getXpToolTip(equipped, true));

						if (IguanaConfig.levelingPickaxeBoost)
							if (hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() && hLevel < TConstructRegistry.getMaterial("Manyullyn").harvestLevel()
							&& !tags.hasKey("HarvestLevelModified")
							&& (equipped.getItem() instanceof Pickaxe || equipped.getItem() instanceof Hammer))
								event.left.add(IguanaLevelingLogic.getXpToolTip(equipped, true, true));
					}
				}
			}
		}
	}*/

}


/* patterns
1	"toolRod"
2	"pickaxeHead"
3	"shovelHead"
4	"hatchetHead"
5	"swordBlade"
6	"wideGuard"
7	"handGuard",
8	"crossbar"
9	"binding"
10	"frypanHead"
11	"signHead"
12	"knifeBlade"
13	"chiselHead"
14	"toughRod"
15	"toughBinding"
16	"heavyPlate"
17	"broadAxeHead"
18	"scytheBlade"
19	"excavatorHead"
20	"largeSwordBlade"
21	"hammerHead"
12	"fullGuard"
23	"bowstring",
24	"fletching"
25	"arrowhead"


    /** Default Material Index
 * 0:  Wood
 * 1:  Stone
 * 2:  Iron
 * 3:  Flint
 * 4:  Cactus
 * 5:  Bone
 * 6:  Obsidian
 * 7:  Netherrack
 * 8:  Green Slime
 * 9:  Paper
 * 10: Cobalt
 * 11: Ardite
 * 12: Manyullyn
 * 13: Copper
 * 14: Bronze
 * 15: Alumite
 * 16: Steel
 * 17: Blue Slime

 */