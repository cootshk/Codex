package io.github.cootshk.quicksearch.mixin;

import io.github.cootshk.quicksearch.impl.Searchable;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public class QSMixinItem implements Searchable {
    @Override
    @Unique
    public String quickSearch$getSearchString() {
        Item item = (Item) (Object) this;
        return item.getName().getString();
    }
}
