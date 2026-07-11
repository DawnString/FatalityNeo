package cn.dawnstring.fatality.mixin;

import cn.dawnstring.fatality.core.combat.ArmorHandler;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntityArmor
{
    @Inject(method = "getDamageAfterArmorAbsorb", at = @At("HEAD"), cancellable = true)
    private void onArmorCalc(DamageSource source, float damage, CallbackInfoReturnable<Float> cir)
    {
        if (!((LivingEntity)(Object)this instanceof Player player))
            return;
        if (source.is(DamageTypeTags.BYPASSES_ARMOR))
            return;
        cir.setReturnValue(ArmorHandler.apply(damage, player, source));
    }
}
