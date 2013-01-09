package com.bzzup.fruityrain;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.KeyEvent;

import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Main extends SimpleBaseGameActivity implements IOnSceneTouchListener {

	public static Main instance;

	final int CAMERA_WIDTH = 480;
	final int CAMERA_HEIGHT = 800;
	public SceneType currentScene = SceneType.SPLASH;
	public BitmapTextureAtlas mTextureAtlas;
	public TiledTextureRegion mCircleFaceTextureRegion;
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
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		RatioResolutionPolicy resolution = new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions eOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, resolution, mCamera);
		return eOptions;
	}

	// @Override
	// public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
	// throws Exception {
	// this.mCurrentScene = new Scene();
	// this.mCurrentScene.setBackground(new Background(0.09804f, 0.7274f,
	// 0.8f));
	// // Text title = new Text(0, 0, mFont, "Splash!",
	// getVertexBufferObjectManager());
	// title.setPosition(-title.getWidth(), CAMERA_HEIGHT / 2);
	// mCurrentScene.attachChild(title);

	// title.registerEntityModifier(new MoveXModifier(2, title.getX(),
	// CAMERA_WIDTH / 2));
	// setCurrentScene(new SplashScreen());
	// setCurrentScene(new PhysicsScene());
	// mScene.setBackground(new Background(Color.BLACK));
	// mWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH),
	// false);
	// mScene.setOnSceneTouchListener(this);
	//
	// final VertexBufferObjectManager vertexBufferObjectManager =
	// this.getVertexBufferObjectManager();
	// final Rectangle ground = new Rectangle(0, CAMERA_HEIGHT - 5,
	// CAMERA_WIDTH, 5, vertexBufferObjectManager);
	// final Rectangle roof = new Rectangle(0, 0, CAMERA_WIDTH, 5,
	// vertexBufferObjectManager);
	// final Rectangle left = new Rectangle(0, 0, 5, CAMERA_HEIGHT,
	// vertexBufferObjectManager);
	// final Rectangle right = new Rectangle(CAMERA_WIDTH - 5, 0, 5,
	// CAMERA_HEIGHT, vertexBufferObjectManager);
	// final Rectangle shelf = new Rectangle(300, 200, 100, 2,
	// vertexBufferObjectManager);
	//
	// final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0,
	// 0.5f, 0.5f);
	// PhysicsFactory.createBoxBody(this.mWorld, ground, BodyType.StaticBody,
	// wallFixtureDef);
	// PhysicsFactory.createBoxBody(this.mWorld, roof, BodyType.StaticBody,
	// wallFixtureDef);
	// PhysicsFactory.createBoxBody(this.mWorld, left, BodyType.StaticBody,
	// wallFixtureDef);
	// PhysicsFactory.createBoxBody(this.mWorld, right, BodyType.StaticBody,
	// wallFixtureDef);
	// PhysicsFactory.createBoxBody(this.mWorld, shelf, BodyType.StaticBody,
	// wallFixtureDef);
	//
	// this.mScene.attachChild(ground);
	// this.mScene.attachChild(roof);
	// this.mScene.attachChild(left);
	// this.mScene.attachChild(right);
	//
	//
	//
	// this.mScene.registerUpdateHandler(this.mWorld);
	//
	// pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	// }

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

	private void addFace(final float pX, final float pY) {
		// //this.mFaceCount++;
		// //Debug.d("Faces: " + this.mFaceCount);
		//
		// final AnimatedSprite face;
		// final Body body;
		//
		// face = new AnimatedSprite(pX, pY, mCircleFaceTextureRegion,
		// this.getVertexBufferObjectManager());
		// body = PhysicsFactory.createCircleBody(mWorld, face,
		// BodyType.DynamicBody, FIXTURE_DEF);
		//
		// // if(this.mFaceCount % 4 == 0) {
		// // face = new AnimatedSprite(pX, pY, this.mBoxFaceTextureRegion,
		// this.getVertexBufferObjectManager());
		// // body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, face,
		// BodyType.DynamicBody, FIXTURE_DEF);
		// // } else if (this.mFaceCount % 4 == 1) {
		// // face = new AnimatedSprite(pX, pY, this.mCircleFaceTextureRegion,
		// this.getVertexBufferObjectManager());
		// // body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, face,
		// BodyType.DynamicBody, FIXTURE_DEF);
		// // } else if (this.mFaceCount % 4 == 2) {
		// // face = new AnimatedSprite(pX, pY, this.mTriangleFaceTextureRegion,
		// this.getVertexBufferObjectManager());
		// // body = MainActivity.createTriangleBody(this.mPhysicsWorld, face,
		// BodyType.DynamicBody, FIXTURE_DEF);
		// // } else {
		// // face = new AnimatedSprite(pX, pY, this.mHexagonFaceTextureRegion,
		// this.getVertexBufferObjectManager());
		// // body = MainActivity.createHexagonBody(this.mPhysicsWorld, face,
		// BodyType.DynamicBody, FIXTURE_DEF);
		// // }
		//
		// face.animate(200);
		//
		// this.mScene.attachChild(face);
		// this.mWorld.registerPhysicsConnector(new PhysicsConnector(face, body,
		// true, true));
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
