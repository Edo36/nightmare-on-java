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

package com.niklasnson.nightmare.Object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.niklasnson.nightmare.Assets;
import com.niklasnson.nightmare.Constants;



public class Player extends Sprite{

  protected enum Action {
    IDLE, JUMP, RUN, WALK
  }

  private World             world;
  private Body              body;
  private Action            action;

  private TextureAtlas      playerAtlas;
  private Array<Sprite>     playerIdle = new Array<Sprite>();
  private Array<Sprite>     playerJump = new Array<Sprite>();
  private Array<Sprite>     playerRun = new Array<Sprite>();
  private Array<Sprite>     playerWalk = new Array<Sprite>();

  private int               animationFrame = 0;
  private int               counter = 0;

  /**
   * Default constructor
   * @param world
   * @param x
   * @param y
   */
  public Player (World world, float x, float y) {
    this.world = world;

    this.action = Action.IDLE;

    setSize(
        Constants.player_width,
        Constants.player_height);

    setPosition(x, y);

    initializeAnimations();

    createBody();
  }

  /**
   * Create the animation vectors
   */
  void initializeAnimations () {
    TextureAtlas spriteSheet = new TextureAtlas("player.txt");

    for (int i = 1; i <= 16; i++) { playerIdle.add(spriteSheet.createSprite("Idle (" + i + ")")); }
    for (int i = 1; i <= 30; i++) { playerJump.add(spriteSheet.createSprite("Jump (" + i + ")")); }
    for (int i = 1; i <= 20; i++) { playerRun.add(spriteSheet.createSprite("Run (" + i + ")")); }
    for (int i = 1; i <= 20; i++) { playerWalk.add(spriteSheet.createSprite("Walk (" + i + ")")); }

    //spriteSheet.dispose();
  }

  /**
   * Create a body for player
   */
  void createBody () {
    BodyDef bodyDef = new BodyDef();

    bodyDef.type = BodyDef.BodyType.DynamicBody;

    bodyDef.position.set(
        getX() / Constants.ppm,
        getY() / Constants.ppm
    );

    body = world.createBody(bodyDef);

    body.setFixedRotation(true);

    PolygonShape shape = new PolygonShape();

    shape.setAsBox(
        (getWidth() / 2f) / Constants.ppm,
        (getHeight() / 2f) / Constants.ppm
    );

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.friction = 1f;
    fixtureDef.density = 0f;
    fixtureDef.filter.categoryBits = Constants.filterPlayer;
    fixtureDef.filter.maskBits = Constants.filterDefault;

    Fixture fixture = body.createFixture(fixtureDef);
    fixture.setUserData("Player");

    shape.dispose();
  }

  /**
   * Draw player on screen
   * @param spriteBatch
   */
  public void draw (SpriteBatch spriteBatch) {

    float playerX = this.getX() - this.getWidth() / 2;
    float playerY = this.getY() - this.getHeight() / 2;
    float playerH = this.getHeight();
    float playerW = this.getWidth();

    if (action == Action.IDLE) {
      spriteBatch.draw(playerIdle.get(animationFrame), playerX, playerY);
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 16) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.JUMP) {
      spriteBatch.draw(playerJump.get(animationFrame), playerX, playerY,playerW, playerH);
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 30) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.RUN) {
      spriteBatch.draw(playerRun.get(animationFrame), playerX, playerY,playerW, playerH);
      spriteBatch.draw(Assets.playerAnimations.get(75 + animationFrame), playerX, playerY, playerW, playerH);
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 20) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.WALK) {
      spriteBatch.draw(playerWalk.get(animationFrame), playerX, playerY,playerW, playerH);
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 20) {
          animationFrame = 0;
        }
      }
    }
  }

  /**
   * Update the player
   */
  public void updatePlayer () {
    if (body.getLinearVelocity().x > 0) {
      setPosition(body.getPosition().x * Constants.ppm, body.getPosition().y * Constants.ppm);
    } else if (body.getLinearVelocity().x < 0) {
      setPosition((body.getPosition().x) * Constants.ppm,
          body.getPosition().y * Constants.ppm);
    }
  }

  /**
   * Move the player
   * @param x
   */
  public void movePlayer (float x) {
    if (x < 0 && !this.isFlipX()) {
      this.flip(true, false);
    } else if (x > 0 && this.isFlipX()) {
      this.flip(true, false);
    }
    body.setLinearVelocity(x, body.getLinearVelocity().y);
  }

  /**
   * Set action for player
   * @param value
   */
  public void setAction (int value) {
    if (value == 0)
      action = Action.IDLE;

    if (value == 1)
      action = Action.JUMP;

    if (value == 2)
      action = Action.RUN;

    if (value == 3)
      action = Action.WALK;
  }

  /**
   * Get action for player
   * @return
   */
  public Action getAction () {
    return this.action;
  }

}
