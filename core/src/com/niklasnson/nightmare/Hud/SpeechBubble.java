/*
 * MIT License
 *
 * Copyright (c) [2017] [Niklas Nilsson]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.niklasnson.nightmare.Hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.niklasnson.nightmare.GameMain;

public class SpeechBubble {

  private GameMain      game;
  private Stage         stage;
  private Viewport      viewport;
  private String        textString;
  private BitmapFont    font = new BitmapFont();
  private float         posX;
  private float         posY;

  public SpeechBubble (GameMain game, Viewport viewport, String text, float posX, float posY) {
    this.game = game;
    this.viewport = viewport;

    this.stage = new Stage(viewport, game.getBatch());
    this.textString = text;
    this.posX = posX;
    this.posY = posY;
  }

  public void render(float delta) {
    game.getBatch().begin();
    final GlyphLayout layout = new GlyphLayout(font, textString);
    font.draw(game.getBatch(), layout, posX - (layout.width/2), posY);
    game.getBatch().end();
  }

}
