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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.niklasnson.nightmare.Constants;
import com.niklasnson.nightmare.GameMain;
import com.niklasnson.nightmare.Object.Map;
import com.niklasnson.nightmare.Object.Player;
import com.niklasnson.nightmare.Object.WorldUtils;

public class GameScreen implements Screen {

  private GameMain            game;
  private World               world;
  private Body                ground;
  private Map                 map;

  private Player              player;

  private OrthographicCamera  camera;

  private Box2DDebugRenderer  renderer;
  private Viewport            viewport;

  private int level = 1;

  /**
   * Default constructor
   * @param game
   */
  public GameScreen (GameMain game) {
    this.game = game;

    this.world = WorldUtils.createWorld();

    this.renderer = new Box2DDebugRenderer();

    initializeCamera();

    initializeLevel(level, camera, game.getBatch());

    initializePlayer(10, 10);
  }

  /**
   * Initialize the camera
   */
  private void initializeCamera () {
    camera = new OrthographicCamera(
        Constants.width,
        Constants.height);

    camera.setToOrtho(
        false,
        Constants.width / Constants.ppm,
        Constants.height / Constants.ppm);

    camera.position.set(
        Constants.width/2f,
        Constants.height/2f,
        0);

    viewport = new StretchViewport(
        Constants.width,
        Constants.height, camera);

    renderer = new Box2DDebugRenderer();

    camera.update();
  }

  /**
   * Create a player on the stage
   * @param x position of player X
   * @param y position of player Y
   */
  private void initializePlayer (float x, float y) {
    player = new Player(
        world,

        32,
        64+(Constants.player_height/2)
    );
    player.setAction(1);
  }

  /**
   * Initialize a new level
   * @param level current level as an integer
   * @param camera
   * @param batch
   */
  private void initializeLevel (int level, OrthographicCamera camera, SpriteBatch batch) {
     map = new Map(level, camera, batch);
     map.initalizeTiles(world);
  }

  /**
   * Batch update function for delta
   * @param delta
   */
  void update(float delta) {
     movePlayer(delta);
     moveCamera(delta);
  }

  /**
   * Move camera to a new position on screen
   * @param delta
   */
  void moveCamera (float delta) {
    //camera.translate(15 * delta,0,0);
  }

  /**
   * Move player on screen if any key is pressed
   * @param delta
   */
  void movePlayer (float delta) {
     if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
       player.movePlayer(-2f);
     } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
       player.movePlayer(2f);
     }
  }

  @Override
  public void show() {}

  /**
   * Render function on screen
   * @param delta
   */
  @Override
  public void render(float delta) {
    update(delta);

    Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    game.getBatch().begin();

    map.drawBackgroundLayer();

    map.drawForegroundLayer();

    player.draw(game.getBatch());

    game.getBatch().end();

    renderer.render(
        world,
        camera.combined
    );

    camera.update();

    map.updateCamera();

    player.updatePlayer();

    world.step(
        Gdx.graphics.getDeltaTime(),
        6,
        2
    );
  }

  @Override
  public void resize(int width, int height) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    world.dispose();
    player.getTexture().dispose();
    renderer.dispose();
  }
}
