package iguanaman.iguanatweakstconstruct.mobheads.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelEnderManHead extends ModelBase {

    public ModelRenderer endermanHead;
    public ModelRenderer endermanJaw;

    public ModelEnderManHead()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.endermanHead = new ModelRenderer(this, 0, 0);
        this.endermanHead.addBox(-4.0F, -10.0F, -4.0F, 8, 8, 8, 0.0F);
        this.endermanHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.endermanJaw = new ModelRenderer(this, 0, 16);
        this.endermanJaw.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.3F);
        this.endermanJaw.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
    {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.endermanHead.render(p_78088_7_);
        this.endermanJaw.render(p_78088_7_);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
    {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        this.endermanHead.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
        this.endermanHead.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);

        this.endermanJaw.rotateAngleX = this.endermanHead.rotateAngleX;
        this.endermanJaw.rotateAngleY = this.endermanHead.rotateAngleY;
    }
}
