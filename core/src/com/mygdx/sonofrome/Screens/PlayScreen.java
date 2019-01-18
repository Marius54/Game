package com.mygdx.sonofrome.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sonofrome.Scenes.Hud;
import com.mygdx.sonofrome.SonOfRome;
import com.mygdx.sonofrome.Sprites.InteractiveTileObject;
import com.mygdx.sonofrome.Sprites.Player;
import com.mygdx.sonofrome.Sprites.StoneBlock;
import com.mygdx.sonofrome.Sprites.WoodBlock;
import com.mygdx.sonofrome.Tools.B2WorldCreator;
import com.mygdx.sonofrome.Tools.Constants;
import com.mygdx.sonofrome.Tools.WorldContactListener;


import java.util.Timer;
import java.util.TimerTask;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

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

    private static PlayScreen instance = null;

    public int dummyInteger = 0;

    public TextureRegion tilereg;
    public Texture tile;

    private RayHandler rayHandler;
    private Light light;

//    private Sound axeSound;

    public static PlayScreen getInstance(){
        if(instance == null){
            instance = new PlayScreen();
        }
        return instance;
    }

    private PlayScreen(){

        this.game = SonOfRome.getInstance();
        atlas = new TextureAtlas(OptionScreen.getInstance().filePath);

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.V_WIDTH / Constants.PPM,Constants.V_HEIGHT / Constants.PPM,gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ Constants.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()*3, 0);

        world = new World(new Vector2(0,-32), true);
        b2dr = new Box2DDebugRenderer();
        b2dr.setDrawBodies(false);

        new B2WorldCreator(world, map);

        player = new Player(world, this, hud);

        contact = new WorldContactListener(this);

        world.setContactListener(contact);

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(1);
        light = new PointLight(rayHandler, 5, Color.WHITE,100/Constants.PPM,0,25*32/Constants.PPM);
        light.attachToBody(player.b2body);
        System.out.print(light.getX()+"  "+ light.getY()+"  "+player.bdef.position.x+"   "+player.bdef.position.y);

//        axeSound = Gdx.audio.newSound(Gdx.files.internal("audio/axeSoundEffect.mp3"));

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                hud.substractFood(5);
                hud.substractWater(5);
            }
        }, 0, 5000);
    }



    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void addCell(int x,int y){
        boolean build = true;
        boolean first = true;
        Array<Body> worldBodies = new Array();
        world.getBodies(worldBodies);
        for(int i = 0; i < worldBodies.size; i++)
        {
            if(worldBodies.get(i).getPosition().x == (x * 32  + 16)/Constants.PPM && worldBodies.get(i).getPosition().y == (y * 32 + 16)/Constants.PPM){
                build = false;
            }
        }

        if(build == true) {
            if (this.player.getCurrentBlock() == 1) {
                MapObject object = new MapObject();
                tile = new Texture(Gdx.files.internal("Map//wood.png"));
                tilereg = new TextureRegion(tile);
                TiledMapTileLayer layer1 = new TiledMapTileLayer(32, 32, 32, 32);
                TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(4);
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(tilereg));
                layer.setCell(x, y, cell);
                map.getLayers().get(1).getObjects().add(object);
                Rectangle rect = new Rectangle(x * 32 , y * 32 , 32, 32);
                new WoodBlock(world,map, rect);
            } else if (player.getCurrentBlock() == 2) {
                MapObject object = new MapObject();
                tile = new Texture(Gdx.files.internal("Map//stoneWall.png"));
                tilereg = new TextureRegion(tile);
                TiledMapTileLayer layer1 = new TiledMapTileLayer(32, 32, 32, 32);
                TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(4);
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(tilereg));
                layer.setCell(x, y, cell);
                map.getLayers().get(1).getObjects().add(object);
                Rectangle rect = new Rectangle(x * 32 , y * 32 , 32, 32);
                new StoneBlock(world,map, rect);
            }
        }
    }

    public void handleInput(float dt){
        float velX = 0, velY = 0;

        if(hud.isBuildPressed() && idle()){
            if(player.isFacingRight() && !contact.isRightContact()){
                addCell((int)(player.getX()* Constants.PPM /32+2),(int)(player.getY()* Constants.PPM /32+1));
            }else if(!player.isFacingRight() && !contact.isLeftContact()){
                addCell((int)(player.getX()* Constants.PPM /32-1),(int)(player.getY()* Constants.PPM /32+1));
            }
        }
        if(hud.isInventoryPressed()){
            openBuildMenu();
        }
        if(hud.isRightPressed()){
            player.setFaceRight(true);
        }
        if(hud.isLeftPressed()){
            player.setFaceRight(false);
        }
        if(hud.isUpPressed() && player.b2body.getLinearVelocity().y == 0 ) {
            velY = 25.0f ;
        } else if(hud.isRightPressed() && player.b2body.getPosition().x < 46) {
            velX = 2.0f;
        } else if(hud.isLeftPressed() && player.b2body.getPosition().x > 4) {
            velX = -2.0f;
        }
        if(hud.isActionPressed() && idle()){
            if(contact.isDownContact() && hud.isDownPressed() ) {
                boolean downTile = playerAction(contact.getDownTile());
                if(downTile){
                    contact.setDownContact(false);
                }
            }else if(contact.isUpContact() && hud.isUpPressed()) {
                boolean upTile = playerAction(contact.getUpTile());
                if(upTile){
                    contact.setUpContact(false);
                }
            }else if(!player.isFacingRight() && (contact.isLeftContact() || (contact.isLeftContact() && hud.isLeftPressed())) && contact.getLeftTile() != null ) {
                boolean leftTile = playerAction(contact.getLeftTile());
                if(leftTile){
                    contact.setLeftContact(false);
                }
            }else if(player.isFacingRight() && (contact.isRightContact() || (contact.isRightContact() && hud.isRightPressed())) && contact.getRightTile() != null) {
                boolean rightTile = playerAction(contact.getRightTile());
                if(rightTile){
                    contact.setRightContact(false);
                }
            }
        }
        player.b2body.setLinearVelocity(velX, velY);

        if((player.b2body.getPosition().x < 5 && player.b2body.getPosition().y < 10) || (player.b2body.getPosition().x > 46 && player.b2body.getPosition().y < 10)){

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(player.b2body.getPosition().y < 9.5){
                hud.decreaseLife();
            }
        }

    }


    public void setCurrentBlock(int type){
        player.setCurrentBlock(type);
    }
    public void openBuildMenu(){

    }

    public boolean idle(){
        if(!hud.isRightPressed() && !hud.isLeftPressed()) {
            return true;
        }
        return false;

    }

    public Hud getHud(){
        return hud;
    }

    public void update(float delta){
        handleInput(delta);

        world.step(1/60f,6,2);

        player.update(delta);
//        gamecam.position.x = RoundTo.RoundToNearest(player.b2body.getPosition().x,.2f);
        gamecam.position.x = (float)Math.round(player.b2body.getPosition().x*50f)/50f;
        if( gamecam.position.y - player.b2body.getPosition().y > 1 || player.b2body.getPosition().y - gamecam.position.y  > 1)
            gamecam.position.y = (int)player.b2body.getPosition().y;

        gamecam.update();

        renderer.setView(gamecam);
//        rayHandler.update();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        rayHandler.updateAndRender();
        renderer.render();


        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        hud.stage.draw();
//        rayHandler.updateAndRender();
//        rayHandler.setCombinedMatrix(gamecam.combined.cpy().scl(Constants.PPM));
//        rayHandler.updateAndRender();

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

    public boolean playerAction(InteractiveTileObject tile){
        boolean destroyed = false;
        try {
            destroyed = tile.playerAction();

        }catch(Exception e){

            }
        if(destroyed) {
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        light.dispose();
    }
}
