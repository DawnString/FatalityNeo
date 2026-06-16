package cn.dawnstring.fatality.mixin;

import cn.dawnstring.fatality.core.combat.DamageHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class MixinLivingEntityHurt
{
    @ModifyVariable(method = "hurt", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float onHurt(float amount, DamageSource source)
    {
        LivingEntity target = (LivingEntity) (Object) this;

        if (target.level().isClientSide())
            return amount;

        if (!(source.getEntity() instanceof Player attacker))
            return amount;

        float custom = DamageHandler.apply(attacker, target, source, amount);

        return custom >= 0 ? custom : amount;
    }
}
