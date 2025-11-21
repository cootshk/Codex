package io.github.cootshk.quicksearch.ui

import io.github.cootshk.quicksearch.QuickSearchLogger
import io.github.cootshk.quicksearch.impl.Searchable
import io.github.cootshk.quicksearch.math.QuickSearchMathHandler
import io.github.cootshk.quicksearch.mixin.IMixinCollapsibleContainer
import io.github.cootshk.quicksearch.util.Colors
import io.github.cootshk.quicksearch.util.RegistryLookup
import io.wispforest.owo.ui.base.BaseUIModelScreen
import io.wispforest.owo.ui.component.BoxComponent
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.component.TextBoxComponent
import io.wispforest.owo.ui.container.CollapsibleContainer
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.container.ScrollContainer
import io.wispforest.owo.ui.core.Component
import io.wispforest.owo.ui.core.Insets
import io.wispforest.owo.ui.core.Surface
import me.xdrop.fuzzywuzzy.FuzzySearch
import net.minecraft.client.gui.Element
import net.minecraft.client.input.CharInput
import net.minecraft.client.input.KeyInput
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.lwjgl.glfw.GLFW

class QuickSearchScreen : BaseUIModelScreen<FlowLayout>(
    FlowLayout::class.java,
    DataSource.asset(
        Identifier.of("quicksearch:searchbar"))) {

    private var logger = QuickSearchLogger.getLogger()

    private lateinit var coloredBox: BoxComponent
    private lateinit var textBoxComponent: TextBoxComponent
    private lateinit var resultsContainer: CollapsibleContainer
    private lateinit var resultsBox: FlowLayout
    private lateinit var resultsArea: ScrollContainer<*>
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
        resultsContainer = rootComponent.childById(CollapsibleContainer::class.java, "resultsContainer")
        resultsBox = rootComponent.childById(FlowLayout::class.java, "resultsBox")
        resultsArea = rootComponent.childById(ScrollContainer::class.java, "resultsArea")
        mathContainer = rootComponent.childById(CollapsibleContainer::class.java, "mathContainer")
        mathBox = rootComponent.childById(FlowLayout::class.java, "mathBox")
        mathResult = rootComponent.childById(LabelComponent::class.java, "mathResult")
        hideBoxes()
        hideCollapsibleContainerButton(mathContainer)
        hideCollapsibleContainerButton(resultsContainer)
    }

    private fun hideCollapsibleContainerButton(c: CollapsibleContainer) {
        val container = c as IMixinCollapsibleContainer
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
        return when (input.keycode) {
            GLFW.GLFW_KEY_ESCAPE -> super.keyPressed(input)
            // TODO: implement selection highlighting
            GLFW.GLFW_KEY_UP -> true
            GLFW.GLFW_KEY_DOWN -> true
            else -> handleInput(textBoxComponent.keyPressed(input) || super.keyPressed(input))
        }
    }

    override fun charTyped(input: CharInput): Boolean {
        return handleInput(textBoxComponent.charTyped(input) || super.charTyped(input))
    }

    override fun keyReleased(keyInput: KeyInput): Boolean {
        return handleInput(textBoxComponent.keyReleased(keyInput) || super.keyReleased(keyInput))
    }

    // Answer
    private fun handleInput(`_`: Boolean): Boolean {
        val text = textBoxComponent.text
        if (text.isNullOrBlank() || text.isEmpty()) {
            hideBoxes()
            return `_`
        }
        if (text.startsWith('=')) {
            showAnswerBox()
            val result: String = QuickSearchMathHandler.evaluate(text.substring(1).trim())
            mathResult.text(Text.of(result))
        } else {
            showSearchBox()
            // fill the search box
            resultsBox.clearChildren()
            for (child in getTopSearchResults(text.trim(), 4)) {
                resultsBox.child(child)
            }
        }
        return `_`
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
    private fun getTopSearchResults(text: String, num: Int): Array<Component> {
        if (textBoxComponent.text.isNullOrEmpty()) return arrayOf()
        val items: Map<String, Searchable> = RegistryLookup.all
        val results = FuzzySearch.extractTop(text, items.keys, num)
        if (results.isEmpty()) return arrayOf()
        return results.map { result ->
            SearchResult(items[result.string]!!)
        }.toTypedArray()
    }
}
