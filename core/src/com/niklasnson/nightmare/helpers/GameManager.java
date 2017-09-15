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

package com.niklasnson.nightmare.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameManager {

  private static GameManager ourInstance = new GameManager();

  public GameData gameData;
  private Json json = new Json();
  private FileHandle fileHandle = Gdx.files.local("bin/GameData.json");
  private GameManager() {}

  public void initializeGameData() {
    if (!fileHandle.exists()) {
      gameData = new GameData();

      gameData.setHighscore(0);
      gameData.setMusicOn(false);

      saveData();
    } else {
      loadData();
    }
  }

  public void saveData() {
    if (gameData != null) {
      fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)),
          false);
    }
  }

  public void loadData() {
    gameData = json.fromJson(GameData.class,
        Base64Coder.decodeString(fileHandle.readString()));
  }
    public static GameManager getInstance() {
    return ourInstance;
  }
}
