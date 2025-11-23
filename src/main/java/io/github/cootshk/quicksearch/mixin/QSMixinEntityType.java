package io.github.cootshk.quicksearch.mixin;

import io.github.cootshk.quicksearch.impl.Searchable;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityType.class)
public class QSMixinEntityType implements Searchable {
    @Shadow(prefix = "QSMixinEntityType$")
    @Final
    public native Text QSMixinEntityType$getName();

    @Unique
    public Text quickSearch$getName() {
        return QSMixinEntityType$getName();
    }
}
