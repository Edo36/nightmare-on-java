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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.niklasnson.nightmare.*;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {

  private GameMain game;
  private World world;

  private Player player;

  private OrthographicCamera camera;
  private Box2DDebugRenderer renderer;
  private Viewport viewport;

  private static ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
  private static ArrayList<Tile> gameMap = new ArrayList<Tile>();

   public GameScreen (GameMain game) {
    this.game = game;
    this.world = WorldUtils.createWorld();
    this.renderer = new Box2DDebugRenderer();
    setupCamera();
    initialize();
  }

  private void setupCamera () {
    camera = new OrthographicCamera(Constants.width, Constants.height);
    camera.position.set(Constants.width/2f, Constants.height/2, 0f);
    viewport = new StretchViewport(Constants.width, Constants.height, camera);
    renderer = new Box2DDebugRenderer();
    camera.update();
  }

  private void initialize () {
    player = new Player(world, Constants.width/2, (Constants.height - 80)/2);
    player.setAction(3);
    gameMap.add(new Tile(world, Constants.width/2 , Constants.height / 2));
  }

  void update(float delta) {
    // move camera
    // check bounds
    // count score
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    update(delta);

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    game.getBatch().begin();
    game.getBatch().draw(Assets.background, 0,0, Constants.width, Constants.height);

    // Draw the map
    Iterator<Tile> it = gameMap.iterator();
    while (it.hasNext()) {
      it.next().draw(game.getBatch());
    }

    player.draw(game.getBatch());

    game.getBatch().end();

    if (Constants.dev_mode) {
      renderer.render(world, camera.combined);
    }

    player.updatePlayer();
    world.step(Gdx.graphics.getDeltaTime(), 6, 2);
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
