package io.github.jsnimda.inventoryprofiles.gui.inject

import io.github.jsnimda.common.vanilla.Vanilla
import io.github.jsnimda.common.vanilla.VanillaUtil
import io.github.jsnimda.common.vanilla.alias.ContainerScreen

object ContainerScreenHandler {
  var currentWidget: SortingButtonContainer? = null
  fun getContainerInjector(screen: ContainerScreen<*>): InjectWidget {
    return InjectWidget().apply {
      addWidget(SortingButtonContainer(screen).also { currentWidget = it })
    }
  }

  var renderedThisFrame = false
  fun renderWidget() {
    renderedThisFrame = true
    val currentWidget = currentWidget
    currentWidget ?: return
    if (currentWidget.screen == Vanilla.screen()) {
      currentWidget.postRender(VanillaUtil.mouseX(), VanillaUtil.mouseY(), VanillaUtil.lastFrameDuration())
    }
  }

  fun postRender() {
    if (!renderedThisFrame) renderWidget()
  }

  fun preScreenRender() {
    renderedThisFrame = false
  }

  fun preRenderTooltip() {
    if (!renderedThisFrame) renderWidget()
  }
}