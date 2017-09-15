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

package com.niklasnson.nightmare.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.niklasnson.nightmare.GameMain;
import com.niklasnson.nightmare.helpers.GameData;
import com.niklasnson.nightmare.helpers.Settings;

public class IntroScene implements Screen {
  private GameMain game;
  private Music music;

  private OrthographicCamera mainCamera;
  private Viewport gameViewport;
  private Texture backgroundImage;



  public IntroScene (GameMain game) {
    this.game = game;

    // Setting up the camera.
    mainCamera = new OrthographicCamera();
    mainCamera.setToOrtho(false, Settings.width, Settings.height);
    mainCamera.position.set(Settings.width / 2f, Settings.height / 2f, 0);
    gameViewport = new StretchViewport(Settings.width, Settings.height, mainCamera);

    // Setting static background.
    backgroundImage = new Texture("Backgrounds/Background 1.png");
  }

  @Override
  public void show() {
    music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/intro-background.mp3"));
    music.setLooping(true);
    //music.play();
  }

  @Override
  public void render(float delta) {
    // Set background colors.
    Gdx.gl.glClearColor(0, 0,0,1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Start the rendering.
    game.getBatch().begin();
    game.getBatch().draw(backgroundImage, 0,0, Settings.width, Settings.height);
    game.getBatch().end();

    // Wait for key press.
    if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
      music.stop();
      game.setScreen(new GameScene(game));
    }

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
    music.dispose();
    backgroundImage.dispose();
  }
}
