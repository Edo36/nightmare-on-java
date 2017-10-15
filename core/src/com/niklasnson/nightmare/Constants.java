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

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Constants {

  /**
   * Key mappings
   */

  public static final int KEY_LEFT = Input.Keys.LEFT;
  public static final int KEY_RIGHT = Input.Keys.RIGHT;
  public static final int KEY_JUMP = Input.Keys.SPACE;
  public static final int KEY_ACCELERATE = Input.Keys.X;
  public static final int ALT_JUMP = Input.Keys.C;

  /**
   * Gravity in world
   */
  public static final Vector2 GRAVITY = new Vector2(0.0f, -9.8f * 7);

  /**
   * Height and width of window
   */
  public static final int SCREEN_WIDTH = 800;
  public static final int SCREEN_HEIGHT = 480;

  public static final float V_WIDTH = 20.0f;
  public static final float V_HEIGHT = 15.0f;

  /**
   * Pixel per meter
   */
  public static final int PPM = 32;

  public static final float STEP = 1 / 60.0f;
  public static float TIMESCALE = 1;

  /**
   * Background image properties
   */
  public static final int BACKGROUND_IMAGE_WIDTH = 800;

  public static final int MALE_ZOMBIE_WIDTH = 54;
  public static final int MALE_ZOMBIE_HEIGHT = 60;

  public static final int FEMALE_ZOMBIE_WIDTH = 54;
  public static final int FEMALE_ZOMBIE_HEIGHT = 60;

  public static final int PLAYER_WIDTH = 44;
  public static final int PLAYER_HEIGHT = 44;

  /**
   * Strings used in game
   */
  public static final String title = "My nightmare, it all stems from childhood";
  public static final String press_to_start = "INSERT COIN TO CONTINUE";
  public static final String game_over = "GAME OVER";

  /**
   * Logs and debug prints.
   */
  public static final boolean DEV_MODE = false;
}
