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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class FemaleZombie extends Enemy {

  private World world;
  private Body body;

  private int animationAttackStart = 0;
  private int animationDeadStart = 0;
  private int animationIdleStart = 0;
  private int animationWalkStart = 0;

  private int animationAttackLength = 8;
  private int animationDeadLenght = 12;
  private int animationIdleLength = 15;
  private int animationWalkLength = 10;

  private int animationCurrentFrame = 0;
  private int animationSpeed = 0;

  public FemaleZombie (World world, float x, float y) {
    this.world = world;
    setSize(Constants.female_zombie_width, Constants.female_zombie_height);
    setPosition(x, y);
  }

  @Override
  public void draw(SpriteBatch batch) {
    batch.draw(Assets.femaleZombieAnimations.get(0 + animationCurrentFrame), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    animationSpeed++;
    if (animationSpeed % Constants.female_zombie_phase == 0) {
      animationCurrentFrame++;
    }
    if (animationCurrentFrame > 7) {
      animationCurrentFrame = 0;
    }
  }

}
