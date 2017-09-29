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

public class Constants {

  /**
   * Gravity in world
   */
  public static final float GRAVITY = -9.8f;

  /**
   * World timers
    */
  public static final float TIME_STEP = 1 / 300f;
  public static final int POSITION_ITERATIONS = 3;
  public static final int VELOCITY_ITERATIONS = 8;

  /**
   * Height and width of window
   */
  public static final int SCREEN_WIDTH = 800;
  public static final int SCREEN_HEIGHT = 480;

  /**
   * Pixel per meter
   */
  public static final int PPM = 1;

  /**
   * Background image properties
   */
  public static final int BACKGROUND_IMAGE_WIDTH = 800;

  public static final short filterDefault = 1;
  public static final short filterPlayer = 2;

  public static final int female_zombie_width = 80;
  public static final int female_zombie_height = 80;

  public static final int player_width = 44;
  public static final int player_height = 48;

  public static final int tile_width = 32;
  public static final int tile_height = 32;

  public static final int female_zombie_phase = 15;
  public static final int player_phase = 4;


  /**
   * Strings used in game
   */
  public static final String title = "My nightmare, it all stems from childhood";
  public static final String press_to_start = "INSERT COIN TO CONTINUE";
  public static final String game_over = "GAME OVER";

  /**
   * Logs and debug prints.
   */
  public static final boolean DEV_MODE = true;
}
