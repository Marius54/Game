package com.mygdx.sonofrome.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sonofrome.Scenes.Hud;
import com.mygdx.sonofrome.SonOfRome;
import com.mygdx.sonofrome.Sprites.Ground;
import com.mygdx.sonofrome.Sprites.InteractiveTileObject;
import com.mygdx.sonofrome.Sprites.Player;
import com.mygdx.sonofrome.Tools.B2WorldCreator;
import com.mygdx.sonofrome.Tools.Constants;
import com.mygdx.sonofrome.Tools.WorldContactListener;

import java.awt.event.InputMethodEvent;

public class PlayScreen implements Screen {

    private SonOfRome game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Player player;

    private WorldContactListener contact;

    public PlayScreen(SonOfRome game){

        this.game = game;
        atlas = new TextureAtlas("pack/pack.pack");

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.V_WIDTH / Constants.PPM,Constants.V_HEIGHT / Constants.PPM,gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ Constants.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()*3, 0);

        world = new World(new Vector2(0,-32), true);
        b2dr = new Box2DDebugRenderer();
//        b2dr.setDrawBodies(false);

        new B2WorldCreator(world, map);

        player = new Player(world, this, hud);

        contact = new WorldContactListener();

        world.setContactListener(contact);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void handleInput(float dt){
        float velX = 0, velY = 0;
        if(hud.isUpPressed() && player.b2body.getLinearVelocity().y == 0 ) {
            velY = 20.0f ;
        } else if(hud.isRightPressed() && player.b2body.getPosition().x < 46) {
            velX = 2.0f;
        } else if(hud.isLeftPressed() && player.b2body.getPosition().x > 4) {
            velX = -2.0f;
        }
        if(hud.isActionPressed() && hud.isRightPressed() && contact.isRightContactWithGround()) {
            System.out.println("Contact + Right");
        }
        player.b2body.setLinearVelocity(velX, velY);
    }

    public Hud getHud(){
        return hud;
    }

    public void update(float delta){
        handleInput(delta);

        world.step(1/60f,6,2);

        player.update(delta);

        gamecam.position.x = player.b2body.getPosition().x;
        if( gamecam.position.y - player.b2body.getPosition().y > 1 || player.b2body.getPosition().y - gamecam.position.y  > 1)
            gamecam.position.y = player.b2body.getPosition().y;

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
