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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.niklasnson.nightmare.Assets;
import com.niklasnson.nightmare.Constants;

public class FemaleZombie extends Enemy {

  protected enum Action {
    ATTACK, DEAD, IDLE, WALK
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
  public FemaleZombie (World world, float x, float y) {
    this.world = world;
    this.action = Action.IDLE;
    setSize(Constants.female_zombie_width, Constants.female_zombie_height);
    setPosition(x, y);
  }

  /**
   * Draw zombie on screen
   * @param spriteBatch
   */
  public void draw(SpriteBatch spriteBatch) {
    if (action == Action.ATTACK) {
      spriteBatch.draw(Assets.femaleZombieAnimations.get(0 + animationFrame), this.getX(), this.getY(), this.getWidth(), this.getHeight());
      counter++;
      if (counter % Constants.female_zombie_phase == 0) {
        animationFrame++;
        if (animationFrame == 8) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.DEAD) {
      spriteBatch.draw(Assets.femaleZombieAnimations.get(7 + animationFrame), this.getX(), this.getY(), this.getWidth(), this.getHeight());
      counter++;
      if (counter % Constants.female_zombie_phase == 0) {
        animationFrame++;
        if (animationFrame == 12) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.IDLE) {
      spriteBatch.draw(Assets.femaleZombieAnimations.get(20 + animationFrame), this.getX(), this.getY(), this.getWidth(), this.getHeight());
      counter++;
      if (counter % Constants.female_zombie_phase == 0) {
        animationFrame++;
        if (animationFrame == 15) {
          animationFrame = 0;
        }
      }
    }

    if (action == Action.WALK) {
      spriteBatch.draw(Assets.femaleZombieAnimations.get(35 + animationFrame), this.getX(), this.getY(), this.getWidth(), this.getHeight());
      counter++;
      if (counter % Constants.female_zombie_phase == 0) {
        animationFrame++;
        if (animationFrame == 10) {
          animationFrame = 0;
        }
      }
    }
  }

  /**
   * Set action on zombie
   * @param value
   */
  public void setAction (int value) {
    if (value == 0)
      action = Action.ATTACK;
    if (value == 1)
      action = Action.DEAD;
    if (value == 2)
      action = Action.IDLE;
    if (value == 3)
      action = Action.WALK;
  }

}
