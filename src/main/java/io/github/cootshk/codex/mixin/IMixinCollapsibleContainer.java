package io.github.cootshk.codex.mixin;

import io.wispforest.owo.ui.container.CollapsibleContainer;
import io.wispforest.owo.ui.container.FlowLayout;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CollapsibleContainer.class, remap = false)
public interface IMixinCollapsibleContainer {
    @Accessor
    FlowLayout getContentLayout();

    @Accessor
    FlowLayout getTitleLayout();
}
