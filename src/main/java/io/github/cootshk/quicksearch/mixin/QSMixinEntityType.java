package io.github.cootshk.quicksearch.mixin;

import io.github.cootshk.quicksearch.impl.Searchable;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityType.class)
public class QSMixinEntityType implements Searchable {
    @Override
    @Unique
    public Text quickSearch$getName() {
        EntityType<?> entityType = (EntityType<?>) (Object) this;
        return entityType.getName();
    }
}