package io.github.cootshk.quicksearch.mixin;

import io.github.cootshk.quicksearch.api.SearchableType;
import io.github.cootshk.quicksearch.impl.Searchable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityType.class)
public class QSMixinEntityType implements Searchable {
    @Unique
    private EntityType<?> self() {
        return (EntityType<?>)(Object)this;
    }

    @Shadow(prefix = "QSMixinEntityType$")
    @Final
    public native Component QSMixinEntityType$getDescription();

    @Unique
    public Component quickSearch$getName() {
        return QSMixinEntityType$getDescription();
    }
    @Unique
    public String quickSearch$getIdentifier() {
        return BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(self()).toString();
    }
    @Unique
    public SearchableType quickSearch$getSearchableType() {
        return SearchableType.ENTITY;
    }
}
