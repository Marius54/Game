package com.mygdx.sonofrome.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.mygdx.sonofrome.SonOfRome;
import com.mygdx.sonofrome.Tools.Constants;


import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class GameOverScreen implements Screen {
    public static GameOverScreen instance = null;
    private GestureListener myGestureListener;
    private TextureAtlas atlas;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private OrthogonalTiledMapRenderer renderer;
    public Stage stage;
    protected Skin skin;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Texture texture;
    private TextureRegion region;

    public static GameOverScreen getInstance(){
        if(instance == null){
            instance = new GameOverScreen();
        }
        return instance;
    }

    private GameOverScreen(){
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.V_WIDTH,Constants.V_HEIGHT , new OrthographicCamera());

        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()*3, 0);

        stage = new Stage(gamePort, SonOfRome.getInstance().batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


        //Create buttons
        TextButton playButton = new TextButton("Main Menu", skin);
        playButton.setPosition(190,50);
        playButton.setSize(100,50);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(MainMenuScreen.getInstance());
                Gdx.input.setInputProcessor(MainMenuScreen.getInstance().stage);

            }
        });

        TextButton optionsButton = new TextButton("Game Over", skin);
        optionsButton.setPosition(190,250);
        optionsButton.setSize(220,50);

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.setPosition(310,50);
        exitButton.setSize(100,50);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(playButton);
        stage.addActor(optionsButton);
        stage.addActor(exitButton);
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
