package io.github.cootshk.quicksearch.mixin;

import io.github.cootshk.quicksearch.api.SearchableType;
import io.github.cootshk.quicksearch.impl.Searchable;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public class QSMixinItem implements Searchable {
    @Shadow(prefix = "QSMixinItem$")
    @Final
    public native Text QSMixinItem$getName();

    @Unique
    public String quickSearch$getIdentifier() {
        return Registries.ITEM.getEntry((Item)(Object)this).toString();
    }

    @Unique
    public Text quickSearch$getName() {
        return QSMixinItem$getName();
    }
    @Unique
    public SearchableType quickSearch$getSearchableType() {
        return SearchableType.ITEM;
    }
}
