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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Assets {
  public static Texture background;

  public static Music music;

  public static ArrayList<Texture> femaleZombieAnimations = new ArrayList<Texture>();
  public static ArrayList<Texture> maleZombieAnimations = new ArrayList<Texture>();
  public static ArrayList<Texture> pumpKingAnimations = new ArrayList<Texture>();
  public static ArrayList<Texture> playerAnimations = new ArrayList<Texture>();

  public static ArrayList<Texture> staticTiles = new ArrayList<Texture>();
  public static ArrayList<Texture> staticObjects = new ArrayList<Texture>();

  public static Texture loadTexture (String file) {
    return new Texture(Gdx.files.internal(file));
  }

  public static void initialize () {
    background = loadTexture("Backgrounds/Background 1.png");

    music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/music.mp3"));
    music.setLooping(true);
    music.setVolume(0.1f);
    //music.play();

    loadAnimations();
  }

  public static void loadAnimations () {
    for (int i = 1; i <= 30; i++) { playerAnimations.add(loadTexture("Player/Dead (" + i + ").png")); }
    for (int i = 1; i <= 16; i++) { playerAnimations.add(loadTexture("Player/Idle (" + i + ").png")); }
    for (int i = 1; i <= 30; i++) { playerAnimations.add(loadTexture("Player/Jump (" + i + ").png")); }
    for (int i = 1; i <= 20; i++) { playerAnimations.add(loadTexture("Player/Run (" + i + ").png")); }
    for (int i = 1; i <= 20; i++) { playerAnimations.add(loadTexture("Player/Walk (" + i + ").png")); }

    for (int i = 1; i <= 8; i++) { femaleZombieAnimations.add(loadTexture("FemaleZombie/Attack (" + i + ").png")); }
    for (int i = 1; i <= 12; i++) { femaleZombieAnimations.add(loadTexture("FemaleZombie/Dead (" + i + ").png")); }
    for (int i = 1; i <= 15; i++) { femaleZombieAnimations.add(loadTexture("FemaleZombie/Idle (" + i + ").png")); }
    for (int i = 1; i <= 10; i++) { femaleZombieAnimations.add(loadTexture("FemaleZombie/Walk (" + i + ").png")); }

    for (int i = 1; i <= 8; i++) { maleZombieAnimations.add(loadTexture("MaleZombie/Attack (" + i + ").png")); }
    for (int i = 1; i <= 12; i++) { maleZombieAnimations.add(loadTexture("MaleZombie/Dead (" + i + ").png")); }
    for (int i = 1; i <= 15; i++) { maleZombieAnimations.add(loadTexture("MaleZombie/Idle (" + i + ").png")); }
    for (int i = 1; i <= 10; i++) { maleZombieAnimations.add(loadTexture("MaleZombie/Walk (" + i + ").png")); }

    for (int i = 1; i <= 10; i++) { pumpKingAnimations.add(loadTexture("PumpKing/Dead (" + i + ").png")); }
    for (int i = 1; i <= 10; i++) { pumpKingAnimations.add(loadTexture("PumpKing/Idle (" + i + ").png")); }
    for (int i = 1; i <= 10; i++) { pumpKingAnimations.add(loadTexture("PumpKing/Jump (" + i + ").png")); }
    for (int i = 1; i <= 8; i++) { pumpKingAnimations.add(loadTexture("PumpKing/Run (" + i + ").png")); }
    for (int i = 1; i <= 10; i++) { pumpKingAnimations.add(loadTexture("PumpKing/Slide (" + i + ").png")); }
    for (int i = 1; i <= 10; i++) { pumpKingAnimations.add(loadTexture("PumpKing/Walk (" + i + ").png")); }
  }
}
