package cn.dawnstring.fatality.mixin;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RangedAttribute.class)
public class MixinAttribute
{
    @Inject(method = "getMaxValue", at = @At("HEAD"), cancellable = true)
    private void removeMaxValue(CallbackInfoReturnable<Double> cir)
    {
        cir.setReturnValue(Double.MAX_VALUE);
    }
}
