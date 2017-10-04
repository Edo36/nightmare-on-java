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

  private float               accumulator;

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

    initializePlayer();
  }

  /**
   * Initialize the camera
   */
  private void initializeCamera () {
    camera = new OrthographicCamera();

    camera.setToOrtho(
        false,
        Constants.SCREEN_WIDTH,
        Constants.SCREEN_HEIGHT);

    camera.position.set(Constants.V_WIDTH/2f,Constants.V_HEIGHT/2f, 0);

    viewport = new StretchViewport(
        Constants.V_WIDTH,
        Constants.V_HEIGHT, camera);

    renderer = new Box2DDebugRenderer();

    camera.update();
  }

  /**
   * Create a player on the stage
   */
  private void initializePlayer () {
    player = new Player(
        world,
        32*7,
        64+(Constants.player_height/2)
    );

    player.setCurrentState(0);
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
   *
   * Move camera to a new position on screen
   * @param delta
   */
  void moveCamera (float delta) {
    Body body = player.getBody();
    camera.position.set(body.getPosition().x, Constants.SCREEN_HEIGHT/2f, 0);
  }

  /**
   * Move player on screen if any key is pressed
   * @param delta
   */
  void movePlayer (float delta) {
     if (Gdx.input.isKeyPressed(Constants.KEY_LEFT)) {
       if (player.getX() > 150) {
         player.setCurrentState(3);
         player.setCurrentDirection(2);
         player.movePlayer(-200f, 0);
       }
     } else if (Gdx.input.isKeyPressed(Constants.KEY_RIGHT)) {
       player.setCurrentState(3);
       player.setCurrentDirection(3);
       player.movePlayer(200f, 0);
     } else if (Gdx.input.isKeyPressed(Constants.KEY_JUMP)) {
       player.movePlayer( 50f, 100f);
     } else {
       player.setCurrentState(0);
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

    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    game.getBatch().setProjectionMatrix(camera.combined);
    game.getBatch().begin();

    map.drawBackgroundLayer();

    map.drawForegroundLayer();

    player.draw(game.getBatch());

    game.getBatch().end();

    if (Constants.DEV_MODE) {
      renderer.render(
          world,
          camera.combined
      );
    }

    player.updatePlayer();

    camera.update();

    map.updateCamera();

    world.step(
        Gdx.graphics.getDeltaTime(),
        6,
        2
    );

    update(delta);
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
