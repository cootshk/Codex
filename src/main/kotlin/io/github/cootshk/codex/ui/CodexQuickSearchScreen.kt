package io.github.cootshk.codex.ui

import io.github.cootshk.codex.mixin.IMixinCollapsibleContainer
import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.TextBoxComponent
import io.wispforest.owo.ui.container.CollapsibleContainer
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.Surface
import net.minecraft.client.gui.Element
import net.minecraft.client.input.CharInput
import net.minecraft.client.input.KeyInput
import net.minecraft.util.Identifier

class CodexQuickSearchScreen : BaseUIModelScreen<FlowLayout>(
    FlowLayout::class.java,
    DataSource.asset(
        Identifier.of("codex:codex_ui_model"))) {

    private lateinit var textBoxComponent: TextBoxComponent
    private lateinit var mathContainer: CollapsibleContainer
    private lateinit var resultsContainer: CollapsibleContainer
    private lateinit var resultsBox: FlowLayout
    private lateinit var mathBox: FlowLayout

    override fun build(rootComponent: FlowLayout) {
        textBoxComponent = rootComponent.childById(TextBoxComponent::class.java, "searchBox")
        textBoxComponent.isVisible = true
        textBoxComponent.active = true
        textBoxComponent.isFocused = true
        textBoxComponent.setEditable(true) // minecraft makes .editable private D:
        this.setInitialFocus(textBoxComponent)
        resultsBox = rootComponent.childById(FlowLayout::class.java, "resultsBox")
        resultsContainer = rootComponent.childById(CollapsibleContainer::class.java, "resultsContainer")
        mathContainer = rootComponent.childById(CollapsibleContainer::class.java, "mathContainer")
        mathBox = rootComponent.childById(FlowLayout::class.java, "mathBox")
        hideBoxes()
        (mathContainer as IMixinCollapsibleContainer).contentLayout.surface(Surface.BLANK)
        (resultsContainer as IMixinCollapsibleContainer).contentLayout.surface(Surface.BLANK)
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
        } else {
            showSearchBox()
        }
    }

    private fun showAnswerBox() {
        if (resultsContainer.expanded())
            resultsContainer.toggleExpansion()
        if (!mathContainer.expanded())
            mathContainer.toggleExpansion()
    }
    private fun showSearchBox() {
        if (!resultsContainer.expanded())
            resultsContainer.toggleExpansion()
        if (mathContainer.expanded())
            mathContainer.toggleExpansion()
    }
    private fun hideBoxes() {
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
