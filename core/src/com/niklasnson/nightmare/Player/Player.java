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

package com.niklasnson.nightmare.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Player extends Sprite{

  private ArrayList<Texture> playerAnimations = new ArrayList<Texture>();
  private float x;
  private float y;

  private int posIdle;
  private int posWalking;
  private int posRunning;
  private int posJumping;

  private boolean isRunning;
  private boolean isIdle;
  private boolean isJumping;

  public Player(String fileName, float x, float y) {
    super(new Texture("Player/" + fileName + ".png"));
    setSize(80, 80);
    setPosition(x-getWidth() / 2, y -getHeight() / 2);
  }

  public Player (float x, float y) {
    this.posIdle = 0;
    this.posWalking = 15;
    this.posRunning = 36;
    this.posJumping = 37;
    this.isIdle = true;
    this.isRunning = false;
    this.isJumping = false;
    setSize(80, 80);
    setPosition(x, y);
    initializeAnimations();
  }

  private void initializeAnimations () {
    for (int i=1; i <= 16; i++) {
      playerAnimations.add(new Texture("Player/Idle (" + i + ").png"));
    }

    for (int i=1; i <= 20; i++) {
      playerAnimations.add(new Texture("Player/Walk (" + i + ").png"));
    }

    for (int i=1; i <= 20; i++) {
      playerAnimations.add(new Texture("Player/Run (" + i + ").png"));
    }

    for (int i=1; i <= 30; i++) {
      playerAnimations.add(new Texture("Player/Jump (" + i + ").png"));
    }
  }

  public void drawIdle (SpriteBatch batch) {
    batch.draw(playerAnimations.get(posIdle), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    posIdle++;
    if (posIdle > 15) {
      posIdle = 0;
    }
  }

  public void drawWalking (SpriteBatch batch) {
    batch.draw(playerAnimations.get(posWalking), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    posWalking++;
    if (posWalking > 35) {
      posWalking = 15;
    }
  }

  public void drawRunning (SpriteBatch batch) {
    batch.draw(playerAnimations.get(posRunning), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    posRunning++;
    if (posRunning > 55) {
      posRunning = 36;
    }
  }

  public void drawJumping (SpriteBatch batch) {
    batch.draw(playerAnimations.get(posJumping), this.getX(), this.getY(), this.getWidth(), this.getHeight());
  }

  public void draw (SpriteBatch batch) {
    if (isRunning) {
      drawRunning(batch);
    }

    if (isIdle) {
      drawIdle(batch);
    }
  }

  public void Run () {
    isRunning = true;
    isIdle = false;
    isJumping = false;
  }

  public void Idle () {
    isIdle = true;
    isRunning = false;
    isJumping = false;
  }

  public void Jump () {
    isJumping = true;
    isIdle = false;
    isRunning = false;
  }

}
