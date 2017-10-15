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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.niklasnson.nightmare.Assets;
import com.niklasnson.nightmare.Constants;

public class FemaleZombie extends Enemy {

  protected enum State {
    ATTACKING, IDLE, WALKING
  }

  public enum Direction {
    LEFT, RIGHT
  }

  private World             world;
  private Body              body;
  private TextureAtlas      spriteSheet;

  private State             currentState;
  private Direction         currentDirection;
  private float             stateTime;

  private Animation         attackAnimation;
  private Animation         waitingAnimation;
  private Animation         walkingAnimation;

  private float             leftBound;
  private float             rightBound;

  /**
   * Default constructor
   * @param world
   * @param x
   * @param y
   */
  public FemaleZombie (World world, float x, float y, float leftBound, float rightBound) {

    this.world = world;

    this.currentState = State.WALKING;

    this.leftBound = leftBound;

    this.rightBound = rightBound;

    setSize(Constants.FEMALE_ZOMBIE_WIDTH, Constants.FEMALE_ZOMBIE_HEIGHT);

    setPosition(x * Constants.PPM, y * Constants.PPM);

    initializeAnimations();

    createBody();

    currentDirection = Direction.RIGHT;

    stateTime = 0f;


  }

  void initializeAnimations () {
    spriteSheet = new TextureAtlas("fenemy.atlas");

    Array<TextureRegion> keyFrames = new Array<TextureRegion>();
    for (int i = 1; i <= 15; i++) { keyFrames.add(new TextureRegion(spriteSheet.findRegion("Idle (" + i + ")"))); }
    waitingAnimation = new Animation(0.1f, keyFrames);
    keyFrames.clear();

    for (int i = 1; i <= 10; i++) { keyFrames.add(new TextureRegion(spriteSheet.findRegion("Walk (" + i + ")"))); }
    walkingAnimation = new Animation(0.1f, keyFrames);
    keyFrames.clear();

    for (int i = 1; i <= 8; i++) { keyFrames.add(new TextureRegion(spriteSheet.findRegion("Attack (" + i + ")"))); }
    attackAnimation = new Animation(0.1f, keyFrames);
    keyFrames.clear();

    Gdx.app.log("[FemaleZombie]", "animations loaded");
  }

  /**
   * Create a body for player
   */
  void createBody () {
    BodyDef bodyDef = new BodyDef();

    bodyDef.type = BodyDef.BodyType.DynamicBody;

    bodyDef.position.set(getX() / Constants.PPM, getY() / Constants.PPM);

    body = world.createBody(bodyDef);
    body.setFixedRotation(true);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(Constants.FEMALE_ZOMBIE_WIDTH/2f, Constants.FEMALE_ZOMBIE_HEIGHT/2f); // tweak numbers for sprite layer.

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;

    fixtureDef.density = 0f;
    fixtureDef.friction = 2f;

    Fixture fixture = body.createFixture(fixtureDef);
    fixture.setUserData("FemaleZombie");

    shape.dispose();
  }


  @Override
  public void draw(SpriteBatch spriteBatch) {
    stateTime += Gdx.graphics.getDeltaTime();
    TextureRegion currentFrame;

    float enemyX = getX();
    float enemyY = getY()-(getHeight()/2f);

    currentFrame = (TextureRegion) waitingAnimation.getKeyFrame(stateTime, true);

    if (currentState == State.WALKING) {
      currentFrame = (TextureRegion) walkingAnimation.getKeyFrame(stateTime, true);

      if (currentFrame.isFlipX() && currentDirection == Direction.RIGHT) {
        currentFrame.flip(true, false);
      } else if (!currentFrame.isFlipX() && currentDirection == Direction.LEFT) {
        currentFrame.flip(true, false);
      }
    }
    spriteBatch.draw(currentFrame, enemyX, enemyY);
  }

  @Override
  public void update (float delta) {
    float xforce = 15;
    if (Math.ceil(body.getPosition().x) < rightBound && currentDirection == Direction.RIGHT) {
      body.setLinearVelocity(xforce, body.getLinearVelocity().y);
    } else if (Math.ceil(body.getPosition().x) == rightBound) {
      body.setLinearVelocity(-xforce, body.getLinearVelocity().y);
      currentDirection = Direction.LEFT;
    } else if (Math.ceil(body.getPosition().x) > leftBound && currentDirection == Direction.LEFT) {
      body.setLinearVelocity(-xforce, body.getLinearVelocity().y);
    } else if (Math.ceil(body.getPosition().x) == leftBound) {
      body.setLinearVelocity(xforce, body.getLinearVelocity().y);
      currentDirection = Direction.RIGHT;
    }
    setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y);
  }
  public void setCurrentState(FemaleZombie.State currentState) {
    this.currentState = currentState;
  }

  public FemaleZombie.State getCurrentState () {
    return this.currentState;
  }

}
