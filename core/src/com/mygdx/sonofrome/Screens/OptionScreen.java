package com.mygdx.sonofrome.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sonofrome.SonOfRome;
import com.mygdx.sonofrome.Tools.Constants;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class OptionScreen implements Screen {
    public static OptionScreen instance = null;
    private TextureAtlas atlas;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private OrthogonalTiledMapRenderer renderer;
    protected Stage stage;
    protected Skin skin;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private World world;
    private Box2DDebugRenderer b2dr;
    AtomicIntegerFieldUpdater<String> assetManager;
    private Texture texture;
    private TextureRegion region;
    Image girlImg,boyImg;
    TextButton playButton;

    public static int playerType = 2;

    public static String filePath;

    public static OptionScreen getInstance(){
        if(instance == null){
            instance = new OptionScreen();
        }
        return instance;
    }

    private OptionScreen(){

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        if(playerType == 1){
            filePath = "pack/pack.pack";
        }else{
            filePath = "packGirl/pack.pack";
        }
        playButton = new TextButton("Main Menu", skin);
        boyImg = new Image(new Texture("pack//boy.png"));
        girlImg = new Image(new Texture("packGirl//girl.png"));
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.V_WIDTH,Constants.V_HEIGHT , new OrthographicCamera());

        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()*3, 0);

        stage = new Stage(gamePort, SonOfRome.getInstance().batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

//        public static final int V_WIDTH = 640;
//        public static final int V_HEIGHT = 384;

        //Create buttons

        girlImg.setSize(48, 48);
        girlImg.setPosition(362,200);
        girlImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                filePath = "packGirl/pack.pack";
                playerType = 2;
                changeImage();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });


        boyImg.setPosition(192,200);
        boyImg.setSize(48, 48);
        boyImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                filePath = "pack/pack.pack";
                playerType = 1;
                changeImage();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });


        playButton.setPosition(360,50);
        playButton.setSize(100,50);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(MainMenuScreen.getInstance());
                Gdx.input.setInputProcessor(MainMenuScreen.getInstance().stage);
            }
        });

        changeImage();
        stage.addActor(girlImg);
        stage.addActor(boyImg);
        stage.addActor(playButton);
    }

    public void changeImage() {
        if(playerType == 1){
            boyImg.setDrawable(new SpriteDrawable(new Sprite(new Texture("pack//boySelected.png"))));
            girlImg.setDrawable(new SpriteDrawable(new Sprite(new Texture("packGirl//girl.png"))));
        }else{
            boyImg.setDrawable(new SpriteDrawable(new Sprite(new Texture("pack//boy.png"))));
            girlImg.setDrawable(new SpriteDrawable(new Sprite(new Texture("packGirl//girlSelected.png"))));
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .18f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        texture = new Texture(Gdx.files.internal("Tileset.png"));
        region = new TextureRegion(texture, 20, 20, 250, 250);

        SonOfRome.getInstance().batch.setProjectionMatrix(gamecam.combined);
        SonOfRome.getInstance().batch.begin();
        SonOfRome.getInstance().batch.end();
        SonOfRome.getInstance().batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
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
    }
}
