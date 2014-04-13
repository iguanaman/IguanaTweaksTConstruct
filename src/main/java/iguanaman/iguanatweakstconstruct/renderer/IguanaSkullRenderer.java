package iguanaman.iguanatweakstconstruct.renderer;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class IguanaSkullRenderer extends TileEntitySkullRenderer {

	public IguanaSkullRenderer() {}

	@Override
	public void func_82393_a(float par1, float par2, float par3, int par4, float par5, int par6, String par7Str)
	{
		ModelSkeletonHead modelskeletonhead = new ModelSkeletonHead(0, 0, 64, 32);

		switch (par6)
		{
		case 0:
		default:
			bindTexture(new ResourceLocation("textures/entity/skeleton/skeleton.png"));
			break;
		case 1:
			bindTexture(new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
			break;
		case 2:
			bindTexture(new ResourceLocation("textures/entity/zombie/zombie.png"));
			modelskeletonhead = new ModelSkeletonHead(0, 0, 64, 64);
			break;
		case 3:
			ResourceLocation resourcelocation = AbstractClientPlayer.locationStevePng;

			if (par7Str != null && par7Str.length() > 0)
			{
				resourcelocation = AbstractClientPlayer.getLocationSkull(par7Str);
				AbstractClientPlayer.getDownloadImageSkin(resourcelocation, par7Str);
			}

			bindTexture(resourcelocation);
			break;
		case 4:
			bindTexture(new ResourceLocation("textures/entity/creeper/creeper.png"));
			break;
		case 5:
			bindTexture(new ResourceLocation("textures/entity/enderman/enderman.png"));
			break;
		case 6:
			bindTexture(new ResourceLocation("textures/entity/zombie_pigman.png"));
			break;
		case 7:
			bindTexture(new ResourceLocation("textures/entity/blaze.png"));
			break;
		}

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);

		if (par4 != 1)
			switch (par4)
			{
			case 2:
				GL11.glTranslatef(par1 + 0.5F, par2 + 0.25F, par3 + 0.74F);
				break;
			case 3:
				GL11.glTranslatef(par1 + 0.5F, par2 + 0.25F, par3 + 0.26F);
				par5 = 180.0F;
				break;
			case 4:
				GL11.glTranslatef(par1 + 0.74F, par2 + 0.25F, par3 + 0.5F);
				par5 = 270.0F;
				break;
			case 5:
			default:
				GL11.glTranslatef(par1 + 0.26F, par2 + 0.25F, par3 + 0.5F);
				par5 = 90.0F;
			}
		else
			GL11.glTranslatef(par1 + 0.5F, par2, par3 + 0.5F);

		float f4 = 0.0625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		modelskeletonhead.render((Entity)null, 0.0F, 0.0F, 0.0F, par5, 0.0F, f4);
		GL11.glPopMatrix();
	}

}
