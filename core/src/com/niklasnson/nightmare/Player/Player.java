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

  private int currentFrameInIdle;
  private int currentFrameInWalking;
  private int currentFrameInRunning;
  private int currentFrameInJumping;

  private boolean playerRunning;
  private boolean playerIdle;
  private boolean playerJumping;

  public Player (float x, float y) {
    // Frame counters
    this.currentFrameInIdle = 0;
    this.currentFrameInWalking = 15;
    this.currentFrameInRunning = 36;
    this.currentFrameInJumping = 37;
    // Player is currently
    this.playerIdle = true;
    this.playerRunning = false;
    this.playerJumping = false;
    // Sprite size
    setSize(80, 80);
    // Current position on screen
    setPosition(x, y);
    // Load all sprites
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
    batch.draw(playerAnimations.get(currentFrameInIdle), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    currentFrameInIdle++;
    if (currentFrameInIdle > 15) {
      currentFrameInIdle = 0;
    }
  }

  public void drawWalking (SpriteBatch batch) {
    batch.draw(playerAnimations.get(currentFrameInWalking), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    currentFrameInWalking++;
    if (currentFrameInWalking > 35) {
      currentFrameInWalking = 15;
    }
  }

  public void drawRunning (SpriteBatch batch) {
    batch.draw(playerAnimations.get(currentFrameInRunning), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    currentFrameInRunning++;
    if (currentFrameInRunning > 55) {
      currentFrameInRunning = 36;
    }
  }

  public void drawJumping (SpriteBatch batch) {
    batch.draw(playerAnimations.get(currentFrameInJumping), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    setPosition(this.getX(), this.getY() + 1);
    currentFrameInJumping++;
    if (currentFrameInJumping > 85) {
      currentFrameInJumping = 37;
    }
  }

  public void draw (SpriteBatch batch) {
    if (playerRunning) {
      drawRunning(batch);
    }

    if (playerIdle) {
      drawIdle(batch);
    }

    if (playerJumping) {
      drawJumping(batch);
    }
  }

  public void Run () {
    playerRunning = true;
    playerIdle = false;
    playerJumping = false;
  }

  public void Idle () {
    playerIdle = true;
    playerRunning = false;
    playerJumping = false;
  }

  public void Jump () {
    playerJumping = true;
    playerIdle = false;
    playerRunning = false;
  }

}
