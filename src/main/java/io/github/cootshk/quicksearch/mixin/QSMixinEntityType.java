package io.github.cootshk.quicksearch.mixin;

import io.github.cootshk.quicksearch.impl.Searchable;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityType.class)
public class QSMixinEntityType implements Searchable {
    @Override
    @Shadow
    @Final
    public native Text getName();
}
