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

package com.niklasnson.nightmare.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Block {

  private TextureAtlas          textureAtlas;
  private Sprite                sprite;

  public Block () {
    this.textureAtlas = new TextureAtlas("Map/tiles.atlas");
  }

  /**
   * Create a body on the tile/block
   * @param world
   * @param x
   * @param y
   */
  public void createBody (World world, int x, int y) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.StaticBody;

    int posX = (x > 0) ? x*32 : x;  // calculate the width and hight of the block.
    int posY = (y > 0) ? y*32 : y;

    Gdx.app.log("[Block]", "createBody \t( x:" + posX + "\t y:" + posY + " )");

    bodyDef.position.set(
        posX,
        posY
    );

    Body body = world.createBody(bodyDef);

    PolygonShape shape = new PolygonShape();

    Vector2 pos = new Vector2(16, 16);

    shape.setAsBox(
        16,
        16,
        pos,
        0
    );

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;

    Fixture fixture = body.createFixture(fixtureDef);
    fixture.setUserData("Block");

    shape.dispose();
  }
}
