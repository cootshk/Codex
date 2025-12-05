package io.github.cootshk.quicksearch.ui

import io.github.cootshk.quicksearch.NextHandler
import io.github.cootshk.quicksearch.QuickSearch
import io.github.cootshk.quicksearch.api.QuickSearchHandler
import io.github.cootshk.quicksearch.client.QuickSearchClient
import io.github.cootshk.quicksearch.impl.Searchable
import io.github.cootshk.quicksearch.math.QuickSearchMathHandler
import io.github.cootshk.quicksearch.mixin.IMixinCollapsibleContainer
import io.github.cootshk.quicksearch.util.Colors
import io.github.cootshk.quicksearch.util.History
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
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.events.GuiEventListener
//? if >1.21 {
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.CharacterEvent
//?}
import net.minecraft.network.chat.Component as Text
import net.minecraft.resources.ResourceLocation
import org.lwjgl.glfw.GLFW

class QuickSearchScreen : BaseUIModelScreen<FlowLayout>(
    FlowLayout::class.java,
    DataSource.asset(
        //? >1.21 {
        ResourceLocation.parse("quicksearch:searchbar"))) {
        //?} else {
        /*ResourceLocation("quicksearch:searchbar"))) {
        *///?}

    private var logger = QuickSearch.logger

    private lateinit var coloredBox: BoxComponent
    private lateinit var textBoxComponent: TextBoxComponent
    private lateinit var resultsContainer: CollapsibleContainer
    private lateinit var resultsBox: FlowLayout
    private lateinit var resultsArea: ScrollContainer<*>
    private lateinit var mathContainer: CollapsibleContainer
    private lateinit var mathBox: FlowLayout
    private lateinit var mathResult: LabelComponent

    override fun build(rootComponent: FlowLayout) {
        textBoxComponent = rootComponent.childById(TextBoxComponent::class.java, "searchBox")!!
        textBoxComponent.isVisible = true
        textBoxComponent.active = true
        textBoxComponent.isFocused = true
        textBoxComponent.isEditable = true
        this.setInitialFocus(textBoxComponent)
        coloredBox = rootComponent.childById(BoxComponent::class.java, "coloredBox")!!
        resultsContainer = rootComponent.childById(CollapsibleContainer::class.java, "resultsContainer")!!
        resultsBox = rootComponent.childById(FlowLayout::class.java, "resultsBox")!!
        resultsArea = rootComponent.childById(ScrollContainer::class.java, "resultsArea")!!
        mathContainer = rootComponent.childById(CollapsibleContainer::class.java, "mathContainer")!!
        mathBox = rootComponent.childById(FlowLayout::class.java, "mathBox")!!
        mathResult = rootComponent.childById(LabelComponent::class.java, "mathResult")!!
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

    //? if >1.21 {
    override fun setInitialFocus() {
        super.setInitialFocus(this.textBoxComponent)
    }
    //?}

     override fun setInitialFocus(focus: GuiEventListener) {
        super.setInitialFocus(this.textBoxComponent)
    }

    //? if >1.21 {
    override fun keyPressed(input: KeyEvent): Boolean {
        val keyCode = input.input()
    //?} else {
    /*override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
    *///?}
//        return super.keyPressed(keyCode, scanCode, modifiers)
        return when (keyCode) {
            //? if >1.21 {
            GLFW.GLFW_KEY_ESCAPE -> closeScreen(input)
            //?} else {
             /*GLFW.GLFW_KEY_ESCAPE -> closeScreen(keyCode, scanCode, modifiers)
            *///?}
            // TODO: implement selection highlighting
            GLFW.GLFW_KEY_UP -> history(true)
            GLFW.GLFW_KEY_DOWN -> history(false)
            GLFW.GLFW_KEY_ENTER -> handleSelection()
            //? if >1.21 {
             else -> handleInput(textBoxComponent.keyPressed(input) || super.keyPressed(input))
            //?} else {
             /*else -> handleInput(textBoxComponent.keyPressed(keyCode, scanCode, modifiers) || super.keyPressed(keyCode, scanCode, modifiers))
            *///?}
        }
    }

    private var index: Int = 0

    // up: true
    // down: false
    private fun history(direction: Boolean): Boolean {
        if (direction) {
            index++
        } else {
            index--
            if (index < 0) {
                index = 0
            }
        }
        val history = History.loadHistory()
        if (history.isEmpty()) {
            index = 0
        }
        if (index == 0) {
            textBoxComponent.value = ""
            return false
        }
        if (index > history.size) {
            index = history.size
        }
        textBoxComponent.value = history[index - 1]

        return true
    }

     //? if >1.21 {
     private fun closeScreen(input: KeyEvent): Boolean {
     //?} else {
      /*private fun closeScreen(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
     *///?}
        // The input is pretty much guaranteed to be ESC.
        History.saveQuery(textBoxComponent.value.trim())
        //? if >1.21 {
        return super.keyPressed(input)
        //?} else {
         /*return super.keyPressed(keyCode, scanCode, modifiers)
        *///?}
    }

    @Suppress("SameReturnValue", "UNREACHABLE_CODE")
    private fun handleSelection(): Boolean {
        val text: String = textBoxComponent.value.trim()
        if (text.isEmpty()) {
            return true
        }
        History.saveQuery(text)
        if (text.startsWith('=')) {
            // Math result, enter to copy
            // handled specially here to avoid getting a Searchable object
            val result: String = QuickSearchMathHandler.evaluate(text.substring(1).trim())
            Minecraft.getInstance().keyboardHandler.clipboard = result
        } else {
            val result: Searchable = getTopSearchResults(text, 1).keys.first()
            for (handler in QuickSearchHandler.handlers.values) {
                if (handler.matches(result)) {
                    try {
                        handler.handler(result)
                    } catch (_: NextHandler) {
                        continue
                    }
                    break
                }
            }
        }

        return true
    }

    //? if >1.21 {
    override fun charTyped(input: CharacterEvent): Boolean {
        return handleInput(textBoxComponent.charTyped(input) || super.charTyped(input))
    }

    override fun keyReleased(keyInput: KeyEvent): Boolean {
        return handleInput(textBoxComponent.keyReleased(keyInput) || super.keyReleased(keyInput))
    }
    //?} else {
    /*override fun charTyped(chr: Char, keyCode: Int): Boolean {
        return handleInput(textBoxComponent.charTyped(chr, keyCode) || super.charTyped(chr, keyCode))
    }
    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        return handleInput(textBoxComponent.keyReleased(keyCode, scanCode, modifiers) || super.keyReleased(keyCode, scanCode, modifiers))
    }
    *///?}

    // Answer
    private fun handleInput(`_`: Boolean): Boolean {
        val text = textBoxComponent.value
        if (text.isNullOrBlank() || text.isEmpty()) {
            hideBoxes()
            return `_`
        }
        if (text.startsWith('=')) {
            showAnswerBox()
            val result: String = QuickSearchMathHandler.evaluate(text.substring(1).trim())
            mathResult.text(Text.nullToEmpty(result))
        } else {
            showSearchBox()
            // fill the search box
            resultsBox.clearChildren()
            for ((index: Searchable, child: Component) in getTopSearchResults(text.trim(), 4)) {
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
    @Suppress("SameParameterValue")
    private fun getTopSearchResults(text: String, num: Int): Map<Searchable, Component> {
        if (text.length < QuickSearchClient.config.requiredLetters()) return emptyMap()
        val items: Map<String, Searchable> = RegistryLookup.all
        val results = FuzzySearch.extractTop(text, items.keys, num)
        if (results.isEmpty()) return emptyMap()
        val targetScore: Int = (QuickSearchClient.config.requiredSearchScore() * 100).toInt()
        return results.filter { result ->
            result.score >= targetScore
        }.associate { result ->
            items[result.string]!! to SearchResult(items[result.string]!!)
        }
    }
}
