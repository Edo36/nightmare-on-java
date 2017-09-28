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
  // Game screen settings
  public static final int width = 800;
  public static final int height = 480;

  public static final int ppm = 100;

  public static final int female_zombie_width = 80;
  public static final int female_zombie_height = 80;

  public static final int male_zombie_width = 80;
  public static final int male_zombie_height = 80;

  public static final int pump_king_width = 80;
  public static final int pump_king_height = 80;

  public static final int player_width = 70;
  public static final int player_height = 70;

  public static final int tile_width = 32;
  public static final int tile_height = 32;

  public static final int female_zombie_phase = 15;
  public static final int male_zombie_phase = 15;
  public static final int pump_king_phase = 10;
  public static final int player_phase = 4;

  public static final float ground_x = 0;
  public static final float ground_y = 0;
  public static final float ground_width = (float)width;
  public static final float ground_height = 0.5f;
  public static final float ground_density = 0f;

  public static final String title = "My nightmare, it all stems from childhood";
  public static final String press_to_start = "INSERT COIN TO CONTINUE";
  public static final String game_over = "GAME OVER";

  public static final boolean dev_mode = true;
}
