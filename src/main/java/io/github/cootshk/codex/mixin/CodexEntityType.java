package io.github.cootshk.codex.mixin;

import io.github.cootshk.codex.impl.CodexSearchable;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityType.class)
public class CodexEntityType implements CodexSearchable {
    @Override
    @Unique
    public String codex$getSearchString() {
        EntityType<?> entityType = (EntityType<?>) (Object) this;
        return entityType.getName().getString();
    }
}
