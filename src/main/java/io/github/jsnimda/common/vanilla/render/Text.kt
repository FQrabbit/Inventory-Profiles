package io.github.jsnimda.common.vanilla.render

import com.mojang.blaze3d.matrix.MatrixStack
import io.github.jsnimda.common.math2d.Rectangle
import io.github.jsnimda.common.vanilla.Vanilla
import io.github.jsnimda.common.vanilla.alias.LiteralText

var rMatrixStack = MatrixStack()

fun rMeasureText(string: String): Int =
  Vanilla.textRenderer().getStringWidth(string) // getStringWidth() = getWidth()

fun rDrawText(string: String, x: Int, y: Int, color: Int, shadow: Boolean = true) {
  if (shadow) {
//    Vanilla.textRenderer().drawStringWithShadow(rMatrixStack, string, x.toFloat(), y.toFloat(), color) // drawWithShadow() = drawStringWithShadow()
    Vanilla.textRenderer().func_238405_a_(rMatrixStack, string, x.toFloat(), y.toFloat(), color) // drawWithShadow() = drawStringWithShadow()
  } else {
//    Vanilla.textRenderer().drawString(rMatrixStack, string, x.toFloat(), y.toFloat(), color) // draw() = drawString()
    Vanilla.textRenderer().func_238421_b_(rMatrixStack, string, x.toFloat(), y.toFloat(), color) // draw() = drawString()
  }
}

fun rDrawCenteredText(string: String, x: Int, y: Int, color: Int, shadow: Boolean = true) {
  rDrawText(string, x - rMeasureText(string) / 2, y, color, shadow)
}

fun rDrawCenteredText(string: String, bounds: Rectangle, color: Int, shadow: Boolean = true) { // text height = 8
  val (x, y, width, height) = bounds
  rDrawText(string, x + (width - rMeasureText(string)) / 2, y + (height - 8) / 2, color, shadow)
}

fun rDrawText(
  string: String, bounds: Rectangle,
  horizontalAlign: Int, verticalAlign: Int,
  color: Int, shadow: Boolean = true
) {

}

fun rWrapText(string: String, maxWidth: Int): String =
//  Vanilla.textRenderer().trimStringToWidth(string, maxWidth) // wrapStringToWidth() = trimStringToWidth()
  Vanilla.textRenderer().func_238412_a_(string, maxWidth) // wrapStringToWidth() = trimStringToWidth()
  // wrapStringToWidth() = wrapLines() // trimToWidth() is not!!!!!!!!!!
  Vanilla.textRenderer().wrapLines(LiteralText(string), maxWidth).joinToString("\n") { it.string }

