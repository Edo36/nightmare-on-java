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
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.niklasnson.nightmare.Constants;

public class Player extends Sprite{

  protected enum State {
    IDLE, JUMP, RUN, WALK
  }

  protected enum Direction {
    LEFT, RIGHT, UP, DOWN
  }

  private World             world;
  private Body              body;
  private State             currentState;
  private Direction         currentDirection;
  private float             stateTime;
  private Animation         animation;
  private TextureAtlas      spriteSheet;
  private Array<Sprite>     playerIdle = new Array<Sprite>();
  private Array<Sprite>     playerJump = new Array<Sprite>();
  private Array<Sprite>     playerRun = new Array<Sprite>();
  private Array<Sprite>     playerWalk = new Array<Sprite>();

  private TextureRegion[]   idleTexture;
  private TextureRegion[]   walkingTexture;

  private Animation         idleAnimation;
  private Animation         walkingAnimation;

  private int               animationFrame = 0;
  private int               counter = 0;

  private boolean           playerWalking = false;
  private boolean           playerJumping = false;

  /**
   * Default constructor
   * @param world world
   * @param x x
   * @param y y
   */
  public Player (World world, float x, float y) {
    this.world = world;

    this.currentState = State.IDLE;

    setSize(
        Constants.player_width,
        Constants.player_height);

    setPosition(x, y);

    initializeAnimations();

    createBody();

    stateTime = 0f;
  }

  /**
   * Create the animation vectors
   */
  void initializeAnimations () {
    spriteSheet = new TextureAtlas("player.atlas");

    Array<TextureRegion> keyFrames = new Array<TextureRegion>();
    for (int i = 1; i <= 16; i++) { keyFrames.add(new TextureRegion(spriteSheet.findRegion("Idle (" + i + ")"))); }
    idleAnimation = new Animation(0.1f, keyFrames);
    keyFrames.clear();

    for (int i = 1; i <= 20; i++) { keyFrames.add(new TextureRegion(spriteSheet.findRegion("Walk (" + i + ")"))); }
    walkingAnimation = new Animation(0.1f, keyFrames);
    keyFrames.clear();



    for (int i = 1; i <= 16; i++) { playerIdle.add(spriteSheet.createSprite("Idle (" + i + ")")); }

    for (int i = 1; i <= 30; i++) { playerJump.add(spriteSheet.createSprite("Jump (" + i + ")")); }

    for (int i = 1; i <= 20; i++) { playerRun.add(spriteSheet.createSprite("Run (" + i + ")")); }

    for (int i = 1; i <= 20; i++) { playerWalk.add(spriteSheet.createSprite("Walk (" + i + ")")); }

    Gdx.app.log("[Player]", "animations loaded");
  }

  /**
   * Create a body for player
   */
  void createBody () {
    BodyDef bodyDef = new BodyDef();

    bodyDef.type = BodyDef.BodyType.DynamicBody;

    bodyDef.position.set(
        getX() / Constants.PPM,
        getY() / Constants.PPM
    );

    System.out.println("getWidth()  :" + getWidth());
    System.out.println("getHeight() :" + getHeight());

    body = world.createBody(bodyDef);

    body.setFixedRotation(true);

    PolygonShape shape = new PolygonShape();

    shape.setAsBox(
        getWidth()/2f,
        getHeight()/2f
    );

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.friction = 500f;
    fixtureDef.restitution = 1f;
    fixtureDef.filter.categoryBits = Constants.filterPlayer;
    fixtureDef.filter.maskBits = Constants.filterDefault;

    Fixture fixture = body.createFixture(fixtureDef);
    fixture.setUserData("Player");

    shape.dispose();
  }

  /**
   * Draw player animation on screen
   * @param spriteBatch spriteBatch
   */
  public void draw (SpriteBatch spriteBatch) {

    stateTime += Gdx.graphics.getDeltaTime();
    TextureRegion currentFrame;

    float playerX = getX()+getWidth()/2f -40f;
    float playerY = getY()-getWidth()/2f -4f;

    currentFrame = (TextureRegion) idleAnimation.getKeyFrame(stateTime, true);

    if (currentState == State.WALK) {
      currentFrame = (TextureRegion) walkingAnimation.getKeyFrame(stateTime, true);

      if (currentFrame.isFlipX() && currentDirection == Direction.RIGHT) {
        currentFrame.flip(true, false);
      } else if (!currentFrame.isFlipX() && currentDirection == Direction.LEFT) {
        currentFrame.flip(true, false);
      }

    } else if (currentState == State.JUMP) {

    } else {
      currentFrame = (TextureRegion) idleAnimation.getKeyFrame(stateTime, true);
    }
    spriteBatch.draw(currentFrame, playerX, playerY);

  }

  /**
   * Update the player, this is called to set the position of the body.
   */
  public void updatePlayer () {
    if (body.getLinearVelocity().x > 0) {
      setPosition(body.getPosition().x, body.getPosition().y);
    } else if (body.getLinearVelocity().x < 0) {
      setPosition(body.getPosition().x,
          body.getPosition().y);
    }
  }

  /**
   * Move the player, this is
   * @param x x
   */
  public void movePlayer (float x, float y) {
    if (x < 0 && !this.isFlipX()) {
      this.flip(true, false);
    } else if (x > 0 && this.isFlipX()) {
      this.flip(true, false);
    }
    body.setLinearVelocity(x, body.getLinearVelocity().y);
  }

  /**
   * Set action for player
   * @param value value
   */
  public void setCurrentState (int value) {
    if (value == 0)
      currentState = State.IDLE;
    if (value == 1)
      currentState = State.JUMP;
    if (value == 2)
      currentState = State.RUN;
    if (value == 3)
      currentState = State.WALK;
  }

  /**
   * Get action for player
   * @return action
   */
  public State getCurrentState() {
    return this.currentState;
  }

  public Direction getCurrentDirection() { return this.currentDirection; }

  public void setCurrentDirection (int value) {
    if (value == 0)
      currentDirection = Direction.DOWN;
    if (value == 1)
      currentDirection = Direction.UP;
    if (value == 2)
      currentDirection = Direction.LEFT;
    if (value == 3)
      currentDirection = Direction.RIGHT;
  }

  public Body getBody () { return  this.body; }

}
