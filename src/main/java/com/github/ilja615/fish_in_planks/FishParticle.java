package com.github.ilja615.fish_in_planks;

import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ReuseableStream;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class FishParticle extends SpriteTexturedParticle
{
    private int numberBouncesDone = 0;
    private int ageAtGround = 0;
    private double previousMotionY = 0.0d;
    private boolean collidedY;
    private final float rotSpeed;

    protected FishParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        super(world, x, y, z, (float) motionX, (float) motionY, (float) motionZ);
        this.motionX = motionX + (Math.random() * 2.0d - 1.0d) * 0.1d;
        this.motionY = motionY + (Math.random() * 2.0d - 1.0d) * 0.1d;
        this.motionZ = motionZ + (Math.random() * 2.0d - 1.0d) * 0.1d;
        this.particleScale += 0.1f;
        this.maxAge = 2000;
        this.rotSpeed = ((float)Math.random() - 0.5F) * 0.1F;
        this.particleAngle = (float)Math.random() * ((float)Math.PI * 2F);
    }


    @Override
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else if (this.collidedY) {
            if (this.ageAtGround++ >= 30) {
                this.setExpired();
            }
        } else {
            this.prevParticleAngle = this.particleAngle;
            this.particleAngle += (float)Math.PI * this.rotSpeed * 2.0F;
            if (this.onGround) {
                this.prevParticleAngle = this.particleAngle = 0.0F;
            }
            this.motionY -= 0.04;
            this.move(this.motionX, this.motionY, this.motionZ);
        }
    }

    @Override
    public void move(double x, double y, double z)
    {
        if (!this.collidedY)
        {
            double d0 = x;
            double d1 = y;
            double d2 = z;
            if (this.canCollide && (x != 0.0D || y != 0.0D || z != 0.0D))
            {
                Vector3d vector3d = Entity.collideBoundingBoxHeuristically((Entity)null, new Vector3d(x, y, z), this.getBoundingBox(), this.world, ISelectionContext.dummy(), new ReuseableStream<>(Stream.empty()));
                x = vector3d.x;
                y = vector3d.y;
                z = vector3d.z;
            }

            if (x != 0.0D || y != 0.0D || z != 0.0D)
            {
                this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
                this.resetPositionToBB();
            }

            if (Math.abs(d1) >= (double)1.0E-5F && Math.abs(y) < (double)1.0E-5F)
            {
                if (this.numberBouncesDone > 3 || Math.abs(this.motionY) < 0.2f)
                    this.collidedY = true;
                else
                {
                    this.motionY = Math.abs(this.previousMotionY) * 0.4d + (rand.nextDouble() * 0.2d);
                    this.motionX = (Math.random() * 2.0d - 1.0d) * 0.1d;
                    this.motionZ = (Math.random() * 2.0d - 1.0d) * 0.1d;
                    this.numberBouncesDone++;
                }
            }

            this.onGround = d1 != y && d1 < 0.0D;
            if (d0 != x)
            {
                this.motionX = 0.0D;
            }

            if (d2 != z)
            {
                this.motionZ = 0.0D;
            }

            this.previousMotionY = this.motionY;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FishParticle fishParticle = new FishParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            fishParticle.selectSpriteRandomly(this.spriteSet);
            return fishParticle;
        }
    }
}
