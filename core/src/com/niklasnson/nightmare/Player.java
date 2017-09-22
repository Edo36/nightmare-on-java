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

package com.niklasnson.nightmare;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Sprite{

  protected enum Action {
    DEAD, IDLE, JUMP, RUN, WALK
  }

  private World world;
  private Body body;

  private Action action;
  private int animationFrame = 0;

  private int counter = 0;

   public Player (World world, float x, float y) {
    this.world = world;
    this.action = Action.IDLE;
    setSize(Constants.player_width, Constants.player_height);
    setPosition(x, y);
    //playerPhysics();
  }

  void playerPhysics() {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(getX(), getY());

    body = world.createBody(bodyDef);
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(getWidth() / 2, getHeight() /2 );

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 1;

    Fixture fixture = body.createFixture(fixtureDef);

    shape.dispose();
  }

  public void draw(SpriteBatch spriteBatch) {
    if (action == Action.DEAD) {
      spriteBatch.draw(Assets.playerAnimations.get(0 + animationFrame), this.getX(), this.getY(), this.getWidth(), this.getHeight());
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 30) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.IDLE) {
      spriteBatch.draw(Assets.playerAnimations.get(30 + animationFrame), this.getX(), this.getY(), this.getWidth(), this.getHeight());
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 16) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.JUMP) {
      spriteBatch.draw(Assets.playerAnimations.get(45 + animationFrame), this.getX(), this.getY(), this.getWidth(), this.getHeight());
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 30) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.RUN) {
      spriteBatch.draw(Assets.playerAnimations.get(75 + animationFrame), this.getX(), this.getY(), this.getWidth(), this.getHeight());
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 20) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.WALK) {
      spriteBatch.draw(Assets.playerAnimations.get(95 + animationFrame), this.getX(), this.getY(), this.getWidth(), this.getHeight());
      counter++;
      if (counter % Constants.player_phase == 0) {
        animationFrame++;
        if (animationFrame == 20) {
          animationFrame = 0;
        }
      }
    }

  }

  public void updatePlayer() {
    this.setPosition(body.getPosition().x, body.getPosition().y);
  }

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

  public Action getAction () {
     return this.action;
  }

}
