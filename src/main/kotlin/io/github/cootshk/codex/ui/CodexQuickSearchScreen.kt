package io.github.cootshk.codex.ui

import io.github.cootshk.codex.math.CodexMathHandler
import io.github.cootshk.codex.mixin.IMixinCollapsibleContainer
import io.github.cootshk.codex.util.Colors
import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.BoxComponent
import io.wispforest.owo.ui.component.Components
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.component.TextBoxComponent
import io.wispforest.owo.ui.container.CollapsibleContainer
import io.wispforest.owo.ui.container.Containers
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.Component
import io.wispforest.owo.ui.core.Insets
import io.wispforest.owo.ui.core.Sizing
import io.wispforest.owo.ui.core.Surface
import net.minecraft.client.gui.Element
import net.minecraft.client.input.CharInput
import net.minecraft.client.input.KeyInput
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class CodexQuickSearchScreen : BaseUIModelScreen<FlowLayout>(
    FlowLayout::class.java,
    DataSource.asset(
        Identifier.of("codex:codex_ui_model"))) {

    private lateinit var coloredBox: BoxComponent
    private lateinit var textBoxComponent: TextBoxComponent
    private lateinit var resultsContainer: CollapsibleContainer
    private lateinit var resultsBox: FlowLayout
    private lateinit var mathContainer: CollapsibleContainer
    private lateinit var mathBox: FlowLayout
    private lateinit var mathResult: LabelComponent

    override fun build(rootComponent: FlowLayout) {
        textBoxComponent = rootComponent.childById(TextBoxComponent::class.java, "searchBox")
        textBoxComponent.isVisible = true
        textBoxComponent.active = true
        textBoxComponent.isFocused = true
        textBoxComponent.setEditable(true) // minecraft makes .editable private D:
        this.setInitialFocus(textBoxComponent)
        coloredBox = rootComponent.childById(BoxComponent::class.java, "coloredBox")
        resultsBox = rootComponent.childById(FlowLayout::class.java, "resultsBox")
        resultsContainer = rootComponent.childById(CollapsibleContainer::class.java, "resultsContainer")
        mathContainer = rootComponent.childById(CollapsibleContainer::class.java, "mathContainer")
        mathBox = rootComponent.childById(FlowLayout::class.java, "mathBox")
        mathResult = rootComponent.childById(LabelComponent::class.java, "mathResult")
        hideBoxes()
        hideCollapsibleContainerButton(mathContainer as IMixinCollapsibleContainer)
        hideCollapsibleContainerButton(resultsContainer as IMixinCollapsibleContainer)
    }

    private fun hideCollapsibleContainerButton(container: IMixinCollapsibleContainer) {
        container.titleLayout.clearChildren()
        container.titleLayout.padding(Insets.of(0,0,0,0))
        container.contentLayout.surface(Surface.BLANK)
    }

    override fun setInitialFocus() {
        super.setInitialFocus(this.textBoxComponent)
    }

    override fun setInitialFocus(element: Element) {
        super.setInitialFocus(this.textBoxComponent)
    }

    override fun keyPressed(input: KeyInput): Boolean {
        if (input.isEscape) return super.keyPressed(input)
        val out = textBoxComponent.keyPressed(input) || textBoxComponent.keyPressed(input)
        handleInput()
        return out
    }

    override fun charTyped(input: CharInput): Boolean {
        val out = textBoxComponent.charTyped(input) || super.charTyped(input)
        handleInput()
        return out
    }

    override fun keyReleased(keyInput: KeyInput): Boolean {
        val out = textBoxComponent.keyReleased(keyInput) || super.keyReleased(keyInput)
        handleInput()
        return out
    }

    // Answer
    private fun handleInput() {
        val text = textBoxComponent.text
        if (text.isNullOrBlank() || text.isEmpty()) {
            hideBoxes()
            return
        }
        if (text.startsWith('=')) {
            showAnswerBox()
            val result: String = CodexMathHandler.evaluate(text.substring(1))
            mathResult.text(Text.of(result))
        } else {
            showSearchBox()
        }
    }

    private fun showAnswerBox() {
        coloredBox.color(Colors.ACCENT_MATH)
        if (resultsContainer.expanded())
            resultsContainer.toggleExpansion()
        if (!mathContainer.expanded())
            mathContainer.toggleExpansion()
    }
    private fun showSearchBox() {
        coloredBox.color(Colors.ACCENT_SEARCH)
        if (!resultsContainer.expanded())
            resultsContainer.toggleExpansion()
        if (mathContainer.expanded())
            mathContainer.toggleExpansion()
    }
    private fun hideBoxes() {
        coloredBox.color(Colors.ACCENT_NONE)
        if (resultsContainer.expanded())
            resultsContainer.toggleExpansion()
        if (mathContainer.expanded())
            mathContainer.toggleExpansion()
    }

    // Searching
    private fun getTopSearchResults(num: Int): Array<FlowLayout> {
        TODO("implement")
//        if (textBoxComponent.text.isNullOrEmpty()) return arrayOf()
//        val items: Array<String> = emptyArray()
//        val results = FuzzySearch.extractTop(textBoxComponent.text, items.toList(), num)
//        val out = results.map { result ->
//            generateTemplate(Identifier.of(result.string))
//        }
//        return out.toTypedArray()
    }
}
