package com.mygdx.sonofrome.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sonofrome.Scenes.Hud;
import com.mygdx.sonofrome.Screens.PlayScreen;
import com.mygdx.sonofrome.Tools.Constants;

public class Player extends Sprite {
    public enum State {RUNNING, STANDING, JUMPING, HITTING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    PlayScreen screen;
    private TextureRegion playerStand;
    private TextureRegion playerJump;
    private Animation playerRun;
    private Animation playerHit;
    private float stateTimer;
    private boolean faceRight;
    private int currentBlock;



    public Player(World world, PlayScreen screen, Hud hud){
        super(screen.getAtlas().findRegion("boy"));
        this.world = world;
        this.screen = screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        faceRight =  true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(getTexture(),i*32,227,32,32));
        }

        playerRun = new Animation(0.1f,frames);

        frames = new Array<TextureRegion>();
        for(int i = 3; i < 6; i++){
            frames.add(new TextureRegion(getTexture(),i*32,227,32,32));
        }

        playerHit = new Animation(0.1f,frames);

        playerJump = new TextureRegion(getTexture(),32,259,32,32);

        definePlayer();
        playerStand = new TextureRegion(getTexture(), 32, 195,32,32 );
        setBounds(0,0,32/Constants.PPM,32/Constants.PPM);
        setRegion(playerStand);


    }

    public void update(float dt){
        setPosition(b2body.getPosition().x-getWidth()/2, b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case RUNNING:
                region = (TextureRegion) playerRun.getKeyFrame(stateTimer, true);
                break;
            case JUMPING:
                region = playerJump;
                break;
            case HITTING:
                region = (TextureRegion) playerHit.getKeyFrame(stateTimer, true);
                break;
            default:
                region = playerStand;
                break;
        }

        if((b2body.getLinearVelocity().x<0 || !faceRight) && !region.isFlipX()){
            region.flip(true,false);
            faceRight = false;
        }else if((b2body.getLinearVelocity().x>0 || faceRight) && region.isFlipX()){
            region.flip(true,false);
            faceRight = true ;
        }

        stateTimer = currentState == previousState ? stateTimer + dt:0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(b2body.getLinearVelocity().x != 0){
            return State.RUNNING;
        }else if(b2body.getLinearVelocity().y > 0) {
            return State.JUMPING;
        }else if(screen.getHud().isActionPressed()) {
            return State.HITTING;
        }else{
            return State.STANDING;
        }
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(524/ Constants.PPM,1010/ Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        CircleShape circle = new CircleShape();
        circle.setRadius(12/Constants.PPM);
        fdef.filter.categoryBits = Constants.BIT_PLAYER;
        fdef.filter.maskBits = Constants.BIT_ENEMY | Constants.BIT_GROUND | Constants.BIT_TREE;

        fdef.shape = circle;
        b2body.createFixture(fdef).setUserData("player");

        PolygonShape sensorShape = new PolygonShape();

        //foot Sensor
        sensorShape.setAsBox((float) (0.1/Constants.PPM), (float) (0.1/Constants.PPM), new Vector2(0,-12/Constants.PPM),0);
        fdef.shape = sensorShape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("downSensor");

        //head Sensor
        sensorShape.setAsBox((float) (0.1/Constants.PPM),(float) (0.1/Constants.PPM), new Vector2(0,12/Constants.PPM),0);
        fdef.shape = sensorShape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("upSensor");

        //right Sensor
        sensorShape.setAsBox((float) (0.1/Constants.PPM),(float) (0.1/Constants.PPM), new Vector2(12/Constants.PPM,0),0);
        fdef.shape = sensorShape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("rightSensor");

        //left Sensor
        sensorShape.setAsBox((float) (0.1/Constants.PPM),(float) (0.1/Constants.PPM), new Vector2(-12/Constants.PPM,0),0);
        fdef.shape = sensorShape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("leftSensor");
    }

    public void setFaceRight(boolean faceRight){
        this.faceRight = faceRight;
    }

    public boolean isFacingRight(){
        return faceRight;
    }

    public int getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(int currentBlock) {
        this.currentBlock = currentBlock;
    }


}
