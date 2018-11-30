package com.mygdx.sonofrome.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.sonofrome.Sprites.Ground;
import com.mygdx.sonofrome.Sprites.InteractiveTileObject;

public class WorldContactListener  implements ContactListener {

    private boolean rightContactWithGround;
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if(fixA.getUserData() != null && fixA.getUserData().equals("rightSensor") ){
            if(fixB.getUserData() != null && fixB.getUserData().equals("Tree") ){
                rightContactWithGround = true;
                System.out.println("FixA"+fixB.getClass());
                if(InteractiveTileObject.class.isAssignableFrom(fixB.getUserData().getClass())) {
                    System.out.println("FixA enter");
                    ((InteractiveTileObject) fixB.getUserData()).lovit();
                }
            }
        }
        if(fixB.getUserData() != null && fixB.getUserData().equals("rightSensor") ){
            if(fixA.getUserData() != null && fixA.getUserData().equals("Tree") ){
                rightContactWithGround = true;
                System.out.println("FixB"+(InteractiveTileObject) fixB.getUserData()+InteractiveTileObject.class.isAssignableFrom(fixB.getUserData().getClass()));
                System.out.println("FixA"+InteractiveTileObject.class.isAssignableFrom(fixA.getUserData().getClass()));
                if(InteractiveTileObject.class.isAssignableFrom(fixA.getUserData().getClass())) {
                    System.out.println("FixB enter");
                    ((InteractiveTileObject) fixA.getUserData()).lovit();
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if(fixA.getUserData() != null && fixA.getUserData().equals("rightSensor") ){
            if(fixB.getUserData() != null && fixB.getUserData().equals("ground") ){
                rightContactWithGround = false;
            }
        }
        if(fixB.getUserData() != null && fixB.getUserData().equals("rightSensor") ){
            if(fixA.getUserData() != null && fixA.getUserData().equals("ground") ){
                rightContactWithGround = false;
            }
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
    public boolean isRightContactWithGround() {
        return rightContactWithGround;
    }

}
