package io.github.cootshk.codex.ui

import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.TextBoxComponent
import io.wispforest.owo.ui.container.FlowLayout
import net.minecraft.client.gui.Element
import net.minecraft.client.input.CharInput
import net.minecraft.client.input.KeyInput
import net.minecraft.util.Identifier

class CodexQuickSearchScreen : BaseUIModelScreen<FlowLayout>(
    FlowLayout::class.java,
    DataSource.asset(
        Identifier.of("codex:codex_ui_model"))) {

    private lateinit var textBoxComponent: TextBoxComponent;

    override fun build(rootComponent: FlowLayout) {
        textBoxComponent = rootComponent.childById(TextBoxComponent::class.java, "searchBox")
        textBoxComponent.isVisible = true
        textBoxComponent.active = true
        textBoxComponent.isFocused = true
        textBoxComponent.setEditable(true) // minecraft makes .editable private D:
        this.setInitialFocus(textBoxComponent)
    }

    override fun setInitialFocus() {
        super.setInitialFocus(this.textBoxComponent)
    }

    override fun setInitialFocus(element: Element) {
        super.setInitialFocus(this.textBoxComponent)
    }

    override fun keyPressed(input: KeyInput): Boolean {
        if (input.isEscape) return super.keyPressed(input)
        return textBoxComponent.keyPressed(input) || textBoxComponent.keyPressed(input)
    }

    override fun charTyped(input: CharInput): Boolean {
        return textBoxComponent.charTyped(input) || super.charTyped(input)
    }

    override fun keyReleased(keyInput: KeyInput): Boolean {
        return textBoxComponent.keyReleased(keyInput) || super.keyReleased(keyInput)
    }
}
