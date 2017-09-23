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

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Tile extends Sprite {
  protected enum Type {
    Tile1, Tile2, Tile3, Tile4, Tile5, Tile6, Tile7, Tile8,
    Tile9, Tile10, Tile11, Tile12, Tile13, Tile14, Tile15,
    Tile16
  }

  private World world;
  private Body body;
  private Type type;

  public Tile (World world, float x, float y) {
    this.world = world;
    this.type = Type.Tile1;
    setSize(Constants.tile_width, Constants.tile_height);
    setPosition(x,y);
    createBody();
  }

  void createBody() {
    BodyDef bodyDef = new BodyDef();

    bodyDef.type = BodyDef.BodyType.StaticBody;
    bodyDef.position.set(getX() / Constants.ppm, getY() / Constants.ppm);

    body = world.createBody(bodyDef);
    body.setFixedRotation(true);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox((getWidth() / 2f - 20f) / Constants.ppm,
        (getHeight() / 2f) / Constants.ppm);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 0f;
    fixtureDef.friction = 2f;
    //fixtureDef.filter.categoryBits = GameInfo.PLAYER;
    //fixtureDef.filter.maskBits = GameInfo.DEFAULT | GameInfo.COLLECTABLE;

    Fixture fixture = body.createFixture(fixtureDef);
    fixture.setUserData("Tile");

    shape.dispose();
  }

  public void draw (SpriteBatch spriteBatch) {
    System.out.println("foo");
    if (type == Type.Tile1)
      System.out.println("bar");
      spriteBatch.draw(Assets.staticTiles.get(15), this.getX(), this.getY(), this.getWidth(), this.getHeight());
  }
}
