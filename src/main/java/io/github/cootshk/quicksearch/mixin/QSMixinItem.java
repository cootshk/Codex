package io.github.cootshk.quicksearch.mixin;

import io.github.cootshk.quicksearch.api.SearchableType;
import io.github.cootshk.quicksearch.impl.Searchable;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public class QSMixinItem implements Searchable {
    @Unique
    private Item self() {
        return (Item)(Object)this;
    }

    @Shadow(prefix = "QSMixinItem$")
    @Final
    public native Component QSMixinItem$getName(ItemStack itemStack);

    @Unique
    public String quickSearch$getIdentifier() {
        return BuiltInRegistries.ITEM.wrapAsHolder(self()).toString();
    }

    @Unique
    public Component quickSearch$getName() {
        return QSMixinItem$getName(self().getDefaultInstance());
    }
    @Unique
    public SearchableType quickSearch$getSearchableType() {
        return SearchableType.ITEM;
    }
}
