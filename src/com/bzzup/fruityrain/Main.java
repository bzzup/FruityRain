package com.bzzup.fruityrain;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Typeface;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Main extends SimpleBaseGameActivity implements IOnSceneTouchListener {

	public static Main instance;

	public int CAMERA_WIDTH;
	public int CAMERA_HEIGHT;
	public SceneType currentScene = SceneType.SPLASH;
	public BitmapTextureAtlas mTextureAtlas;
	public TiledTextureRegion mCircleFaceTextureRegion;
	public TiledTextureRegion baloonPlayer;
	public TiledTextureRegion baloonEnemy;
	private PhysicsWorld mWorld;
	private Scene mScene;
	public Scene mCurrentScene;
	public Font mFont;
	public Camera mCamera;
	public int test;
	Handler handler;
	
	private final int HANDLER_DROP = 1; // sec
	private int dropSpeed = 1; // sec

	public static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);

	public static Main getInstance() {
		return instance;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		instance = this;
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		CAMERA_WIDTH = displayMetrics.widthPixels;
		CAMERA_HEIGHT = displayMetrics.heightPixels;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		RatioResolutionPolicy resolution = new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions eOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, resolution, mCamera);
		return eOptions;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if (mCurrentScene.getClass() == GameScene.class) {
				this.setCurrentScene(new MainMenu());
			}
		}
		return false;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// if(this.mWorld != null) {
		// if(pSceneTouchEvent.isActionDown()) {
		// this.addFace(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
		// return true;
		// }
		// }
		return false;
	}

	public void setCurrentScene(Scene mScene) {
		mCurrentScene = mScene;
		this.getEngine().setScene(mCurrentScene);
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		mTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 64, 128, TextureOptions.BILINEAR);
		mCircleFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTextureAtlas, this, "face_circle_tiled.png", 0, 32, 2, 1);
		baloonEnemy = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTextureAtlas, this, "baloon_enemy.png", 0, 64, 2, 1);
		baloonPlayer = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTextureAtlas, this, "baloon_player.png", 0, 96, 2, 1);
		mTextureAtlas.load();

		mFont = FontFactory.create(getFontManager(), getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		mFont.load();
	}

	@Override
	protected Scene onCreateScene() {
		setCurrentScene(new SplashScreen());
		return mCurrentScene;
	}
	
	
	
}
