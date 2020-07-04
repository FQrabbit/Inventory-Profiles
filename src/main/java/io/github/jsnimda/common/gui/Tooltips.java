package io.github.jsnimda.common.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mojang.blaze3d.platform.GlStateManager;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringRenderable;

public class Tooltips {

  private static final Tooltips INSTANCE = new Tooltips();
  public static Tooltips getInstance() {
    return INSTANCE;
  }

  public static class Tooltip extends DrawableHelper {
    public List<String> strings;
    public int mouseX;
    public int mouseY;
    public Tooltip(String string, int mouseX, int mouseY) {
      this.strings = Arrays.asList(string.split("\n"));
      this.mouseX = mouseX;
      this.mouseY = mouseY;
    }
    public Tooltip(List<String> strings, int mouseX, int mouseY) {
      this.strings = strings;
      this.mouseX = mouseX;
      this.mouseY = mouseY;
    }
    public void render(MatrixStack matrices) {
      renderTooltip(matrices, false);
      // MinecraftClient.getInstance().currentScreen.renderTooltip(strings, mouseX, mouseY);
    }

    private void renderTooltip(MatrixStack matrices, boolean firstLineGap) { // ref: Screen.renderTooltip
      List<String> list = strings;
      int width = MinecraftClient.getInstance().currentScreen.width;
      int height = MinecraftClient.getInstance().currentScreen.height;
      TextRenderer font = MinecraftClient.getInstance().textRenderer;
      ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
      if (!list.isEmpty()) {
        RenderSystem.disableRescaleNormal();
        DiffuseLighting.disable();
        RenderSystem.disableLighting();
        GlStateManager.disableDepthTest();
        int maxStringWidth = 0;
        Iterator<String> var5 = list.iterator();

        while(var5.hasNext()) {
           String string = var5.next();
           int l = font.getWidth(string);
           if (l > maxStringWidth) {
              maxStringWidth = l;
           }
        }

        int p = 8;
        if (list.size() > 1) {
           p += (firstLineGap ? 2 : 0) + (list.size() - 1) * 10;
        }

        int textX = mouseX + 4;
        int textY = mouseY - p - 6;
        if (textX + maxStringWidth + 4 + 5 > width) {
           textX -= 7 + maxStringWidth;
        }
        if (textX - 4 - 5 < 0) {
          textX = width - (maxStringWidth + 4 + 5);
        }

        if (textY + p + 6 > height) {
          textY = height - p - 6;
        }
        if (textY - 6 < 0) {
            textY = 6;
        }

        this.setZOffset(300);
        itemRenderer.zOffset = 300.0F;
        int COLOR_BG = 0xF0100010;
        this.fillGradient(matrices, textX - 3, textY - 4, textX + maxStringWidth + 3, textY - 3, COLOR_BG, COLOR_BG);
        this.fillGradient(matrices, textX - 3, textY + p + 3, textX + maxStringWidth + 3, textY + p + 4, COLOR_BG, COLOR_BG);
        this.fillGradient(matrices, textX - 3, textY - 3, textX + maxStringWidth + 3, textY + p + 3, COLOR_BG, COLOR_BG);
        this.fillGradient(matrices, textX - 4, textY - 3, textX - 3, textY + p + 3, COLOR_BG, COLOR_BG);
        this.fillGradient(matrices, textX + maxStringWidth + 3, textY - 3, textX + maxStringWidth + 4, textY + p + 3, COLOR_BG, COLOR_BG);
        int COLOR_OUTLINE_TOP = 0x505000FF;
        int COLOR_OUTLINE_BOTTOM = 0x5028007F;
        this.fillGradient(matrices, textX - 3, textY - 3 + 1, textX - 3 + 1, textY + p + 3 - 1, COLOR_OUTLINE_TOP, COLOR_OUTLINE_BOTTOM);
        this.fillGradient(matrices, textX + maxStringWidth + 2, textY - 3 + 1, textX + maxStringWidth + 3, textY + p + 3 - 1, COLOR_OUTLINE_TOP, COLOR_OUTLINE_BOTTOM);
        this.fillGradient(matrices, textX - 3, textY - 3, textX + maxStringWidth + 3, textY - 3 + 1, COLOR_OUTLINE_TOP, COLOR_OUTLINE_TOP);
        this.fillGradient(matrices, textX - 3, textY + p + 2, textX + maxStringWidth + 3, textY + p + 3, COLOR_OUTLINE_BOTTOM, COLOR_OUTLINE_BOTTOM);

        for(int t = 0; t < list.size(); ++t) {
           String string2 = (String)list.get(t);
           font.drawWithShadow(matrices, string2, (float)textX, (float)textY, -1);
           if (t == 0) {
              textY += (firstLineGap ? 2 : 0);
           }

           textY += 10;
        }

        this.setZOffset(0);
        itemRenderer.zOffset = 0.0F;
        RenderSystem.enableLighting();
        GlStateManager.enableDepthTest();
        DiffuseLighting.enable();
        RenderSystem.enableRescaleNormal();
      }
    }
  }

  public List<Tooltip> tooltips = new ArrayList<>();

  public void addTooltip(String string, int mouseX, int mouseY) {
    this.addTooltip(Arrays.asList(string.split("\n")), mouseX, mouseY);
  }
  public void addTooltip(List<String> strings, int x, int y) {
    tooltips.add(new Tooltip(strings, x, y));
  }
  public void renderAll(MatrixStack matrices) {
    tooltips.forEach(x -> x.render(matrices));
    tooltips.clear();
  }

  /**
   * @deprecated Ideally, {@link Tooltip Tooltip} should use {@link net.minecraft.text.Text Text} or {@link StringRenderable}.
   */
  @Deprecated
  private static List<String> renderableListToStrings(List<StringRenderable> renderables) {
    return renderables.stream().map(StringRenderable::getString).collect(Collectors.toList());
  }

  public void addTooltip(String string, int mouseX, int mouseY, int maxWidth) {
    this.addTooltip(renderableListToStrings(MinecraftClient.getInstance().textRenderer.wrapLines(StringRenderable.plain(string), maxWidth)), mouseX, mouseY);
  }

  public void addTooltip(String string, int mouseX, int mouseY, Function<Integer, Integer> maxWidthProvider) {
    this.addTooltip(renderableListToStrings(MinecraftClient.getInstance().textRenderer.wrapLines(StringRenderable.plain(string), maxWidthProvider.apply(
      MinecraftClient.getInstance().currentScreen.width
                                                                                                                               ))), mouseX, mouseY);
  }

}