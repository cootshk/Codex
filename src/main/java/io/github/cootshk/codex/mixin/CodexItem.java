package io.github.cootshk.codex.mixin;

import io.github.cootshk.codex.impl.CodexSearchable;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public class CodexItem implements CodexSearchable {
    @Override
    @Unique
    public String codex$getSearchString() {
        Item item = (Item) (Object) this;
        return item.getName().getString();
    }
}
