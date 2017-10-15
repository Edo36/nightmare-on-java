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

package com.niklasnson.nightmare.Hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.niklasnson.nightmare.Constants;
import com.niklasnson.nightmare.GameMain;
import com.niklasnson.nightmare.Screen.GameScreen;

public class MainMenuButtons {

  private GameMain      game;
  private Stage         stage;
  private Viewport      viewport;

  private ImageButton   startBtn;
  private ImageButton   optionsBtn;
  private ImageButton   exitBtn;

  public MainMenuButtons(GameMain game) {
    this.game = game;

    viewport = new FitViewport(
        Constants.SCREEN_WIDTH,
        Constants.SCREEN_HEIGHT, new OrthographicCamera()
    );

    stage = new Stage(viewport, game.getBatch());

    Gdx.input.setInputProcessor(stage);

    createAndPositionButtons();

    addAllListeners();

    stage.addActor(startBtn);
    stage.addActor(optionsBtn);
    stage.addActor(exitBtn);

  }

  void createAndPositionButtons () {

    startBtn = new ImageButton(new SpriteDrawable(new Sprite(
        new Texture("Buttons/Start Game.png"))));

    startBtn.setPosition(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2,
        Align.center);


    optionsBtn = new ImageButton(new SpriteDrawable(new Sprite(
        new Texture("Buttons/Options.png"))));

    optionsBtn.setPosition(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2 - 50,
        Align.center);

    exitBtn = new ImageButton(new SpriteDrawable(new Sprite(
        new Texture("Buttons/Exit.png"))));

    exitBtn.setPosition(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2 - 100,
        Align.center);

  }

  void addAllListeners () {
    startBtn.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.setScreen(new GameScreen(game));
      }
    });
  }

  public Stage getStage() {
    return stage;
  }
}
