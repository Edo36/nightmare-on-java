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

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.niklasnson.nightmare.Screen.GameScreen;
import com.niklasnson.nightmare.Helper.GameManager;

public class GameMain extends Game {

	private SpriteBatch batch;

	/**
	 *
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();

		Gdx.app.log("[GameMain]", "new SpriteBatch");
		GameManager.getInstance().initializeGameData();

		Assets.initialize();
		Gdx.app.log("[GameMain]", "assets should be loaded");

		setScreen(new GameScreen(this));
	}

	/**
	 *
	 */
	@Override
	public void render () { super.render(); }

	/**
	 *
	 * @return spriteBatch
	 */
  public SpriteBatch getBatch () { return this.batch; }
}
