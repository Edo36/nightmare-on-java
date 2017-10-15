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

package com.niklasnson.nightmare.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.niklasnson.nightmare.Constants;
import com.niklasnson.nightmare.GameMain;
import com.niklasnson.nightmare.Hud.MainMenuButtons;

public class MainMenu implements Screen {

  private GameMain            game;
  private OrthographicCamera  camera;
  private Viewport            viewport;
  private Texture             bg;

  private MainMenuButtons     btns;

  public MainMenu (GameMain game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    camera.position.set(Constants.SCREEN_WIDTH/2f, Constants.SCREEN_HEIGHT/2f,0);

    viewport = new StretchViewport(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, camera);

    bg = new Texture("Backgrounds/Background 0.png");

    btns = new MainMenuButtons(game);
  }


  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    game.getBatch().begin();

    game.getBatch().draw(bg, 0, 0);

    game.getBatch().end();

    game.getBatch().setProjectionMatrix(btns.getStage().getCamera().combined);

    btns.getStage().draw();


  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    bg.dispose();
    btns.getStage().dispose();
  }
}
