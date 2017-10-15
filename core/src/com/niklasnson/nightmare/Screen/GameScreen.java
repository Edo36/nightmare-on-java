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
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.niklasnson.nightmare.Constants;
import com.niklasnson.nightmare.GameMain;
import com.niklasnson.nightmare.Hud.SpeechBubble;
import com.niklasnson.nightmare.Object.*;

public class GameScreen implements Screen, ContactListener {

  private GameMain            game;
  private World               world;
  private Map                 map;

  private Player              player;
  private Array<Enemy>        enemies = new Array<Enemy>();

  private OrthographicCamera  camera;

  private Box2DDebugRenderer  renderer;
  private Viewport            viewport;
  private Array<SpeechBubble> bubbles = new Array<SpeechBubble>();

  private float               cameraLeftLimit;
  private float               cameraRightLimit;

  private float               accumulator;

  private int level = 1;

  /**
   * Default constructor
   * @param game
   */
  public GameScreen (GameMain game) {
    this.game = game;

    this.world = WorldUtils.createWorld();
    world.setContactListener(this);

    this.renderer = new Box2DDebugRenderer();

    initializeCamera();

    initializeLevel(level, camera, game.getBatch());

    initializePlayer();
    initializeEnemies();

    bubbles.add(new SpeechBubble(game, viewport, "Move player with arrow keys", 32*5, 150f));
    bubbles.add(new SpeechBubble(game, viewport, "Monsters will kill you,", 32*15, 165f));
    bubbles.add(new SpeechBubble(game, viewport, "space makes you jump!", 32*15, 150f));

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

    viewport = new FitViewport(
        Constants.SCREEN_WIDTH,
        Constants.SCREEN_HEIGHT,
        camera
    );

    renderer = new Box2DDebugRenderer();

    camera.update();

  }

  /**
   * Create a player on the stage
   */
  private void initializePlayer () {
    player = new Player(
        world,
        32*5,
        64+(Constants.PLAYER_HEIGHT /2)
    );

    player.setCurrentState(Player.State.IDLE);
  }

  /**
   * this would be better off loaded from the map, but im running out of time!
   */
  private void initializeEnemies () {
    enemies.add(Enemy.createEnemy(Enemy.EnemyType.MaleZombie, world, 32 * 15, 64 + (Constants.MALE_ZOMBIE_HEIGHT/2), (32*15), (32*15)));

    enemies.add(Enemy.createEnemy(Enemy.EnemyType.FemaleZombie, world, 32 * 20, 32*6 + (Constants.FEMALE_ZOMBIE_HEIGHT/2), (32*20), (32*25)));
    enemies.add(Enemy.createEnemy(Enemy.EnemyType.MaleZombie, world, 32 * 27, 32*6 + (Constants.MALE_ZOMBIE_HEIGHT/2), (32*27), (32*32)));

    enemies.add(Enemy.createEnemy(Enemy.EnemyType.FemaleZombie, world, 32 * 45, 64 + (Constants.FEMALE_ZOMBIE_HEIGHT/2), (32*45), (32*50)));

    enemies.add(Enemy.createEnemy(Enemy.EnemyType.FemaleZombie, world, 1153, 86, 1153f, 1401f));
    enemies.add(Enemy.createEnemy(Enemy.EnemyType.MaleZombie, world, 1659, 86,1659f, 1857f));
    enemies.add(Enemy.createEnemy(Enemy.EnemyType.FemaleZombie, world, 2270, 86, 2270f, 2571f));
    
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

     cameraLeftLimit = Constants.V_WIDTH;
     cameraRightLimit = map.getMapWidth() - Constants.V_WIDTH;
  }

  /**
   * Batch update function for delta
   * @param delta
   */
  void update(float delta) {

    delta *= Constants.TIMESCALE;
    float step = Constants.STEP * Constants.TIMESCALE;

    accumulator += delta;
    if (accumulator > step) {
      world.step(step, 8, 3);
      accumulator -= step;
    }

    player.inputHandler(delta);
     //movePlayer(delta);
     moveCamera(delta);
  }

  /**
   *
   * Move camera to a new position on screen
   * @param delta
   */
  void moveCamera (float delta) {
    Body body = player.getBody();

    float targetX = 416f;

    if (body.getPosition().x > 416f) {
      targetX = body.getPosition().x;
    }

    if (body.getPosition().x > 6343f) {
      targetX = 6343f;
    }
    camera.position.set(targetX, camera.viewportHeight / 2f, 0);
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

    for (Enemy enemy : enemies) {
      enemy.draw(game.getBatch());
    }
    game.getBatch().end();

    if (Constants.DEV_MODE) {
      renderer.render(
          world,
          camera.combined
      );
    }

    player.updatePlayer(delta);

    for (Enemy enemy : enemies) {
      enemy.update(delta);
    }

    for (SpeechBubble bubble : bubbles) {
      bubble.render(delta);
    }

    camera.update();
    map.updateCamera();
    update(delta);

    if (player.getCurrentState() != Player.State.DYING) {
      // player is dead! do things!
    }

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

  @Override
  public void beginContact(Contact contact) {
    Fixture body1, body2;

    if(contact.getFixtureA().getUserData() == "Player") {
      body1 = contact.getFixtureA();
      body2 = contact.getFixtureB();
    } else {
      body1 = contact.getFixtureB();
      body2 = contact.getFixtureA();
    }

    if(body1.getUserData() == "Player" && body2.getUserData() == "Block") {
      if (!player.isGrounded()) {
        player.setSmallJump(false);
        player.setGrounded(true);
      }
    }

    if (body1.getUserData() == "Player" && body2.getUserData() == "MaleZombie") {
      playerDied ();
    }

    if (body1.getUserData() == "Player" && body2.getUserData() == "FemaleZombie") {
      playerDied ();
    }
  }

  void playerDied () {

    System.out.println("We should now kill the player!");

  }

  @Override
  public void endContact(Contact contact) {

  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {

  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {

  }

}
