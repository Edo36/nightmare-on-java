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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.niklasnson.nightmare.Constants;

public class Player extends Sprite{

  public enum State {
    IDLE, JUMPING, RUNNING, WALKING, FALLING, DYING, BRAKING
  }

  public enum Direction {
    LEFT, RIGHT, UP, DOWN
  }

  private World             world;
  private Body              body;
  private State             currentState;
  private Direction         currentDirection;
  private float             stateTime;
  private TextureAtlas      spriteSheet;

  private final float       normalForce = 20.0f * 10;
  private final float       normalSpeedMax = 6.0f * 10;

  private final float       fastForce = 36.0f * 10;
  private final float       fastSpeedMax = 12.0f * 10;

  private float             keyPressedTime;

  private boolean           facingRight;

  private boolean           isDead;
  private boolean           isLevelCompleted;

  private boolean           grounded;
  private boolean           die;

  private boolean           smallJump;
  private boolean           bigJump;
  private boolean           jump;
  private boolean           brake;

  private Animation         waitingAnimation;
  private Animation         walkingAnimation;
  private Animation         jumpingAnimation;
  private Animation         runningAnimation;

  /**
   * Default constructor
   * @param world world
   * @param x x
   * @param y y
   */
  public Player (World world, float x, float y) {
    this.world = world;

    this.currentState = State.IDLE;
    this.smallJump = false;

    setSize(
        Constants.PLAYER_HEIGHT, Constants.PLAYER_WIDTH);

    setPosition(x * Constants.PPM, y * Constants.PPM);

    initializeAnimations();

    createBody();

    stateTime = 0f;
    keyPressedTime = 99.0f;
    facingRight = true;
    grounded = true;
    smallJump = false;
    bigJump = true;
    jump = false;
    die = false;
  }

  /**
   * Create the animation vectors
   */
  void initializeAnimations () {
    spriteSheet = new TextureAtlas("player.atlas");

    Array<TextureRegion> keyFrames = new Array<TextureRegion>();
    for (int i = 1; i <= 16; i++) { keyFrames.add(new TextureRegion(spriteSheet.findRegion("Idle (" + i + ")"))); }
    waitingAnimation = new Animation(0.1f, keyFrames);
    keyFrames.clear();

    for (int i = 1; i <= 20; i++) { keyFrames.add(new TextureRegion(spriteSheet.findRegion("Walk (" + i + ")"))); }
    walkingAnimation = new Animation(0.1f, keyFrames);
    keyFrames.clear();

    for (int i = 1; i <= 30; i++) { keyFrames.add(new TextureRegion(spriteSheet.findRegion("Jump (" + i + ")"))); }
    jumpingAnimation = new Animation(0.1f, keyFrames);
    keyFrames.clear();

    for (int i = 1; i <= 20; i++) { keyFrames.add(new TextureRegion(spriteSheet.findRegion("Run (" + i + ")"))); }
    runningAnimation = new Animation(0.1f, keyFrames);
    keyFrames.clear();

    Gdx.app.log("[Player]", "animations loaded");
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
    shape.setAsBox(Constants.PLAYER_WIDTH/2, Constants.PLAYER_HEIGHT/2);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 0f;
    fixtureDef.friction = 2f;
    //fixtureDef.filter.categoryBits = Constants.filterPlayer;
    //fixtureDef.filter.maskBits = Constants.filterDefault;

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

    float playerX = getX();
    float playerY = getY()-(getHeight()/2f);

    // default frame if not moving!
    currentFrame = (TextureRegion) waitingAnimation.getKeyFrame(stateTime, true);

    if (currentState == State.WALKING) {
      currentFrame = (TextureRegion) walkingAnimation.getKeyFrame(stateTime, true);

      if (currentFrame.isFlipX() && currentDirection == Direction.RIGHT) {
        currentFrame.flip(true, false);
      } else if (!currentFrame.isFlipX() && currentDirection == Direction.LEFT) {
        currentFrame.flip(true, false);
      }

    } else if (currentState == State.JUMPING) {
      currentFrame = (TextureRegion) jumpingAnimation.getKeyFrame(stateTime, true);
    } else {
      currentFrame = (TextureRegion) waitingAnimation.getKeyFrame(stateTime, true);
    }
    spriteBatch.draw(currentFrame, playerX, playerY);

  }

  /**
   *
   */
  public void inputHandler (float delta) {
    float maxSpeed = normalSpeedMax;
    float force = normalForce;

    if (!isDead && !isLevelCompleted) {
      keyPressedTime += delta;
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
      System.out.println("X: " + getX() + " Y:" + getY() + "   " + "bodyX: " + getBody().getPosition().x + " bodyY: " + getBody().getPosition().y);
    }

    // Accelerate
    if (Gdx.input.isKeyPressed(Constants.KEY_ACCELERATE) && grounded) {
      maxSpeed = fastSpeedMax;
      force = fastForce;
    }

    // Left
    if (Gdx.input.isKeyPressed(Constants.KEY_LEFT) && body.getLinearVelocity().x > -maxSpeed) {
      body.applyForceToCenter(new Vector2(-force, 0.0f), true);
      currentDirection = Direction.LEFT;
      if (body.getLinearVelocity().x > normalSpeedMax || (currentState == State.BRAKING && body.getLinearVelocity().x > 0)) {
        brake = true;
      }
    }

    // Right
    if (Gdx.input.isKeyPressed(Constants.KEY_RIGHT) && body.getLinearVelocity().x < maxSpeed) {
      body.applyForceToCenter(new Vector2(force, 0.0f), true);
      currentDirection = Direction.RIGHT;
      if (body.getLinearVelocity().x < -normalSpeedMax || (currentState == State.BRAKING && body.getLinearVelocity().x < 0)) {
        brake = true;
      }
    }

    // Jump
    if ((Gdx.input.isKeyJustPressed(Constants.KEY_JUMP) || Gdx.input.isKeyJustPressed(Constants.ALT_JUMP)) && grounded) {
      body.applyLinearImpulse(new Vector2(0.0f, 27.0f * 10), body.getWorldCenter(), true);
      smallJump = true;
      grounded = false;
      keyPressedTime = 0;
    }

    if ((Gdx.input.isKeyPressed(Constants.KEY_JUMP) || Gdx.input.isKeyPressed(Constants.ALT_JUMP)) && currentState == State.JUMPING) {
      if (keyPressedTime > 0.1f && keyPressedTime < 0.15f) {
        body.applyLinearImpulse(new Vector2(0.0f, 5.0f * 10), body.getWorldCenter(), true);
        bigJump = true;
        keyPressedTime = 99.0f;
      }
    }
  }

  /**
   * Update player this is called from the render function in GameScreen class,
   * this should handle all states on the user until the next cycle.
   * @param delta
   */
  public void updatePlayer(float delta) {
    State previousState = currentState;

    if (body.getPosition().y < -2.0f) {
      die = true;
    }

    if (die) {
      if (!isDead) {
        currentState = State.DYING;
        System.out.println("Player is now dead");
      }
    }
    else if (!grounded) {
      if (smallJump) {
        currentState = State.JUMPING;
      } else {
        currentState = State.FALLING;
      }
    } else  {
      if (currentState == State.JUMPING) {
        smallJump = false;
        currentState = State.IDLE;
        // this might not be the perfect way to get user from bouncing. but it works!
        body.applyLinearImpulse(new Vector2(0.0f, -27.0f * 10), body.getWorldCenter(), true);
      } else if (body.getLinearVelocity().x !=0) {
        currentState = State.WALKING;
      } else {
        currentState = State.IDLE;
      }
      if (brake) {
        System.out.println("Braking!");
        currentState = State.BRAKING;
        brake = false;
      }
    }

    switch (currentState) {
      case WALKING:
        break;
      case JUMPING:
        break;
      case IDLE:
        break;
    }

    // limit players movement
    if (body.getPosition().x < 64f) { // dont move out of screen (left)
      body.setTransform(64f, body.getPosition().y, 0);
      body.setLinearVelocity(0, body.getLinearVelocity().y);
    } else if (body.getPosition().y > 350f) { // dont move out of screen (top)
      body.setTransform(body.getPosition().x, 350f, 0);
      body.setLinearVelocity(body.getLinearVelocity().x, 0);
    }

    setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y);
  }


  /**
   * Get action for player
   * @return action
   */
  public Body getBody () { return  this.body; }

  public void setCurrentState(State currentState) {
    this.currentState = currentState;
  }

  public State getCurrentState () {
    return this.currentState;
  }

  public boolean isSmallJump() {
    return smallJump;
  }

  public void setSmallJump(boolean smallJump) {
    this.smallJump = smallJump;
  }

  public boolean isGrounded() {
    return grounded;
  }

  public void setGrounded(boolean grounded) {
    this.grounded = grounded;
  }

}
