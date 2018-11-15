package com.mygdx.sonofrome.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sonofrome.Screens.PlayScreen;
import com.mygdx.sonofrome.SonOfRome;

public class Player extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion playerStand;

    public Player(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("boy"));
        this.world = world;
        definePlayer();
        playerStand = new TextureRegion(getTexture(), 32, 195,32,32 );
        setBounds(0,0,32/SonOfRome.PPM,32/SonOfRome.PPM);
        setRegion(playerStand);


    }

    public void update(float dt){
        setPosition(b2body.getPosition().x-getWidth()/2, b2body.getPosition().y-getHeight()/2);
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ SonOfRome.PPM,1010/ SonOfRome.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15/ SonOfRome.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}