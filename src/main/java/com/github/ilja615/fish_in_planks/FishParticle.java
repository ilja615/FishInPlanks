package com.github.ilja615.fish_in_planks;

import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class FishParticle extends TextureSheetParticle
{
    private int numberBouncesDone = 0;
    private int ageAtGround = 0;
    private double previousMotionY = 0.0d;
    private boolean collidedY;
    private final float rotSpeed;

    protected FishParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        super(world, x, y, z, (float) motionX, (float) motionY, (float) motionZ);
        this.xd = motionX + (Math.random() * 2.0d - 1.0d) * 0.1d;
        this.yd = motionY + (Math.random() * 2.0d - 1.0d) * 0.1d;
        this.zd = motionZ + (Math.random() * 2.0d - 1.0d) * 0.1d;
        this.quadSize += 0.1f;
        this.lifetime = 2000;
        this.rotSpeed = ((float)Math.random() - 0.5F) * 0.1F;
        this.roll = (float)Math.random() * ((float)Math.PI * 2F);
    }


    @Override
    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick()
    {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else if (this.collidedY) {
            if (this.ageAtGround++ >= 30) {
                this.remove();
            }
        } else {
            this.oRoll = this.roll;
            this.roll += (float)Math.PI * this.rotSpeed * 2.0F;
            if (this.onGround) {
                this.oRoll = this.roll = 0.0F;
            }
            this.yd -= 0.04;
            this.move(this.xd, this.yd, this.zd);
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
            if (this.hasPhysics && (x != 0.0D || y != 0.0D || z != 0.0D))
            {
                Vec3 vector3d = Entity.collideBoundingBox((Entity)null, new Vec3(x, y, z), this.getBoundingBox(), this.level, List.of());
                x = vector3d.x;
                y = vector3d.y;
                z = vector3d.z;
            }

            if (x != 0.0D || y != 0.0D || z != 0.0D)
            {
                this.setBoundingBox(this.getBoundingBox().move(x, y, z));
                this.setLocationFromBoundingbox();
            }

            if (Math.abs(d1) >= (double)1.0E-5F && Math.abs(y) < (double)1.0E-5F)
            {
                if (this.numberBouncesDone > 3 || Math.abs(this.yd) < 0.2f)
                    this.collidedY = true;
                else
                {
                    this.yd = Math.abs(this.previousMotionY) * 0.4d + (random.nextDouble() * 0.2d);
                    this.xd = (Math.random() * 2.0d - 1.0d) * 0.1d;
                    this.zd = (Math.random() * 2.0d - 1.0d) * 0.1d;
                    this.numberBouncesDone++;
                }
            }

            this.onGround = d1 != y && d1 < 0.0D;
            if (d0 != x)
            {
                this.xd = 0.0D;
            }

            if (d2 != z)
            {
                this.zd = 0.0D;
            }

            this.previousMotionY = this.yd;
        }
    }

    public static class Factory implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FishParticle fishParticle = new FishParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            fishParticle.pickSprite(this.spriteSet);
            return fishParticle;
        }
    }
}
