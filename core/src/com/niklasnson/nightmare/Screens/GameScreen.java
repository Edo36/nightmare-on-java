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

package com.niklasnson.nightmare.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.niklasnson.nightmare.*;

public class GameScreen implements Screen {

  private GameMain game;
  private World world;
  private OrthographicCamera camera;
  private Box2DDebugRenderer renderer;

  private static final int VIEWPORT_WIDTH = 20;
  private static final int VIEWPORT_HEIGHT = 13;

  private GameState gameState;

  public GameScreen (GameMain game) {
    this.game = game;
    this.world = WorldUtils.createWorld();
    this.renderer = new Box2DDebugRenderer();
    gameState.initialize();
    setupCamera();
  }

  private void setupCamera () {
    camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
    camera.update();
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    game.getBatch().begin();
    game.getBatch().draw(Assets.background, 0,0, Constants.width, Constants.height);
    game.getBatch().end();
    if (Constants.dev_mode) {
      renderer.render(world, camera.combined);
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

  }
}
