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

package com.niklasnson.nightmare.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.niklasnson.nightmare.Assets;
import com.niklasnson.nightmare.Constants;


public class Player extends Sprite{

  protected enum Action {
    DEAD, IDLE, JUMP, RUN, WALK
  }

  private World         world;
  private Body          body;
  private Action        action;

  private int           animationFrame = 0;

  private int           counter = 0;

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

    createBody();
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

    if (action == Action.DEAD) {
      spriteBatch.draw(Assets.playerAnimations.get(0 + animationFrame), playerX, playerY, playerW, playerH);
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 30) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.IDLE) {
      spriteBatch.draw(Assets.playerAnimations.get(30 + animationFrame), playerX, playerY, playerW, playerH);
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 16) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.JUMP) {
      spriteBatch.draw(Assets.playerAnimations.get(45 + animationFrame), playerX, playerY, playerW, playerH);
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 30) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.RUN) {
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
      spriteBatch.draw(Assets.playerAnimations.get(95 + animationFrame), playerX, playerY, playerW, playerH);
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
      action = Action.DEAD;

    if (value == 1)
      action = Action.IDLE;

    if (value == 2)
      action = Action.JUMP;

    if (value == 3)
      action = Action.RUN;

    if (value == 4)
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
