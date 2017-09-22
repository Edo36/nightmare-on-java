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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.niklasnson.nightmare.*;


import java.util.ArrayList;
import java.util.Iterator;

public class MainMenuScreen implements Screen {
  private GameMain game;

  private World world;
  private Body ground;
  private BitmapFont font;
  private Player player;

  private OrthographicCamera camera;
  private Box2DDebugRenderer renderer;

  private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();


  private static final int VIEWPORT_WIDTH = 20;
  private static final int VIEWPORT_HEIGHT = 13;

  public MainMenuScreen (GameMain game) {
    this.game = game;
    world = WorldUtils.createWorld();
    ground = WorldUtils.createGround(world);
    renderer = new Box2DDebugRenderer();
    font = new BitmapFont();
    player = new Player(world, Constants.width/2, (Constants.height - 80)/2);
    player.setAction(3);

    int posY = 180;
    // lets try with a list of enemies
    for (int i = 0; i < 3; i++)
    {
      enemyList.add(Enemy.createEnemy(Enemy.EnemyType.FemaleZombie, world, posY, 100));
      posY = posY + 35;
      enemyList.add(Enemy.createEnemy(Enemy.EnemyType.MaleZombie, world, posY, 100));
      posY = posY + 35;
    }

    enemyList.get(0).setAction(3);
    enemyList.get(1).setAction(3);
    enemyList.get(2).setAction(3);
    enemyList.get(3).setAction(3);
    enemyList.get(4).setAction(3);
    enemyList.get(5).setAction(3);


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
    //player.updatePlayer();

    game.getBatch().begin();
    game.getBatch().draw(Assets.background, 0,0, Constants.width, Constants.height);

    // Draw press_to_start to screen
    GlyphLayout glyphLayout = new GlyphLayout();
    glyphLayout.setText(font, Constants.press_to_start);
    float w = glyphLayout.width;
    font.draw(game.getBatch(), Constants.press_to_start, (Constants.width - w)/2, (Constants.height-360)/2);

    // Draw player on screen
    player.draw(game.getBatch());

    // Draw the enemys on screen
    Iterator<Enemy> it = enemyList.iterator();
    while (it.hasNext()) {
      it.next().draw(game.getBatch());
    }

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
    font.dispose();
    world.dispose();
    game.dispose();
  }
}
