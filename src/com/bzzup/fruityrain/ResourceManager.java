package com.bzzup.fruityrain;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
import org.andengine.engine.options.MusicOptions;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.font.BitmapFont;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.physics.box2d.FixtureDef;

public class ResourceManager {
	private static ResourceManager instance;

	public static void initializeResourceManager(Main activityReference) {
		if (instance == null) {
			instance = new ResourceManager(activityReference);
		}
	}

	public static synchronized ResourceManager getInstance() {
		return instance;
	}

	private static final int FONT_SIZE = 48;

	// Application reference
	private Main activityReference;

	// Resources
	public Music menuMusic;
	public Music fighter_shot;

	// Fonts
	public BitmapFont splashBitmapFont;

	// Textures
	public BitmapTextureAtlas splashBitmapTextureAtlas;

	// Menu stuff
	public BitmapTextureAtlas mMenuTexture;
	public ITextureRegion mMenuPlayTextureRegion;
	public ITextureRegion mMenuQuitTextureRegion;

	// Fonts
	public Font mDroidFont;
	public Font mPlokFont;
	public Font mNeverwinterNightsFont;
	public Font mUnrealTournamenFont;
	public Font mKingdomOfHeartsFont;

	// AutoParallax stuff for menu
	public BitmapTextureAtlas mAutoParallaxBackgroundTexture;

	public ITextureRegion mParallaxLayerBack;
	public ITextureRegion mParallaxLayerMid;
	public ITextureRegion mParallaxLayerFront;

	// game scene stuff
	public BitmapTextureAtlas mGameBitmapTextureAtlas;
	public RepeatingSpriteBackground mStoneBackground;

	// game entity sprites
	public BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	public TiledTextureRegion mChopperJoeTextureRegion;
	public TiledTextureRegion mChopperHellTextureRegion;

	// analog controller stuff
	public BitmapTextureAtlas mOnScreenControlTexture;
	public ITextureRegion mOnScreenControlBaseTextureRegion;
	public ITextureRegion mOnScreenControlKnobTextureRegion;

	//OLD TEXTURES
	public BitmapTextureAtlas mTextureAtlas;
	public TiledTextureRegion mCircleFaceTextureRegion;
	public TiledTextureRegion baloonPlayer;
	public TiledTextureRegion baloonEnemy;
	public TiledTextureRegion bullet_fighter;
	public TiledTextureRegion enemy_simple;
	public TextureRegion ship_fighter;
	
	//Physics staff
	public FixtureDef FIXTURE_DEF_SHIP;
	public FixtureDef FIXTURE_DEF_BULLET;
	public FixtureDef FIXTURE_DEF_ENEMY;
	public FixtureDef FIXTURE_DEF_WALL;
	
	final short CATEGORY_SHIP = 0x0001;  // 0000000000000001 in binary
	final short CATEGORY_BULLET = 0x0002; // 0000000000000010 in binary
	final short CATEGORY_ENEMY = 0x0004; // 0000000000000100 in binary
	final short CATEGORY_WALL = 0x0008; // 0000000000000100 in binary
	
	// constructor
	private ResourceManager(Main activityReference) {
		this.activityReference = activityReference;

		loadFonts();
		loadTextures();
		loadMusic();
		loadPhysics();
		
	}

	
	private void loadPhysics() {
		final short MASK_SHIP = CATEGORY_ENEMY + CATEGORY_WALL + CATEGORY_SHIP; 
		final short MASK_BULLET =  CATEGORY_ENEMY + CATEGORY_WALL;
		final short MASK_ENEMY = CATEGORY_BULLET + CATEGORY_SHIP + CATEGORY_WALL; 
		final short MASK_WALL = -1; 
		
		FIXTURE_DEF_SHIP = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		FIXTURE_DEF_SHIP.filter.categoryBits = CATEGORY_SHIP;
		FIXTURE_DEF_SHIP.filter.maskBits = MASK_SHIP;
		
		FIXTURE_DEF_BULLET = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		FIXTURE_DEF_BULLET.filter.categoryBits = CATEGORY_BULLET;
		FIXTURE_DEF_BULLET.filter.maskBits = MASK_BULLET;
		
		FIXTURE_DEF_ENEMY = PhysicsFactory.createFixtureDef(1, 1, 0.5f);
		FIXTURE_DEF_ENEMY.filter.categoryBits = CATEGORY_ENEMY;
		FIXTURE_DEF_ENEMY.filter.maskBits = MASK_ENEMY;
		
		FIXTURE_DEF_WALL = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		FIXTURE_DEF_WALL.filter.categoryBits = CATEGORY_WALL;
		FIXTURE_DEF_WALL.filter.maskBits = MASK_WALL;
	}
	
	private void loadFonts() {

		final ITexture droidFontTexture = new BitmapTextureAtlas(activityReference.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		final ITexture kingdomOfHeartsFontTexture = new BitmapTextureAtlas(activityReference.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		final ITexture neverwinterNightsFontTexture = new BitmapTextureAtlas(activityReference.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		final ITexture plokFontTexture = new BitmapTextureAtlas(activityReference.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		final ITexture unrealTournamentFontTexture = new BitmapTextureAtlas(activityReference.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

		FontFactory.setAssetBasePath("font/");
		this.mDroidFont = FontFactory.createFromAsset(activityReference.getFontManager(), droidFontTexture, activityReference.getAssets(), "Droid.ttf", FONT_SIZE, true, android.graphics.Color.WHITE);
		this.mDroidFont.load();

		this.mKingdomOfHeartsFont = FontFactory.createFromAsset(activityReference.getFontManager(), kingdomOfHeartsFontTexture, activityReference.getAssets(), "KingdomOfHearts.ttf", FONT_SIZE + 20,
				true, android.graphics.Color.WHITE);
		this.mKingdomOfHeartsFont.load();

		this.mNeverwinterNightsFont = FontFactory.createFromAsset(activityReference.getFontManager(), neverwinterNightsFontTexture, activityReference.getAssets(), "NeverwinterNights.ttf", FONT_SIZE,
				true, android.graphics.Color.WHITE);
		this.mNeverwinterNightsFont.load();

		this.mPlokFont = FontFactory.createFromAsset(activityReference.getFontManager(), plokFontTexture, activityReference.getAssets(), "Plok.ttf", FONT_SIZE, true, android.graphics.Color.WHITE);
		this.mPlokFont.load();

		this.mUnrealTournamenFont = FontFactory.createFromAsset(activityReference.getFontManager(), unrealTournamentFontTexture, activityReference.getAssets(), "UnrealTournament.ttf", FONT_SIZE,
				true, android.graphics.Color.WHITE);
		this.mUnrealTournamenFont.load();

	}

	private void loadTextures() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		splashBitmapTextureAtlas = new BitmapTextureAtlas(activityReference.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		splashBitmapTextureAtlas.load();

		// Menu stuff
		this.mMenuTexture = new BitmapTextureAtlas(activityReference.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		this.mMenuPlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, activityReference, "menu_play.png", 0, 0);
		this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, activityReference, "menu_quit.png", 0, 50);
		this.mMenuTexture.load();

		// Bitmap Fonts for splash screen...
		this.splashBitmapFont = new BitmapFont(activityReference.getTextureManager(), activityReference.getAssets(), "font/BitmapFont.fnt");
		this.splashBitmapFont.load();

		// Auto parallax background stuff
		this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(activityReference.getTextureManager(), 1024, 1024);
		this.mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, activityReference, "parallax_background_layer_front.png", 0, 0);
		this.mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, activityReference, "para_back2.png", 0, 188);
		this.mParallaxLayerMid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, activityReference, "parallax_background_layer_mid.png", 0, 669);
		this.mAutoParallaxBackgroundTexture.load();

		// game scene background stuff
//		mGameBitmapTextureAtlas = new BitmapTextureAtlas(activityReference.getTextureManager(), 128, 128);
//		mStoneBackground = new RepeatingSpriteBackground(EngineOptionsManager.getInstance().CAMERA_WIDTH, EngineOptionsManager.getInstance().CAMERA_HEIGHT, activityReference.getTextureManager(),
//				AssetBitmapTextureAtlasSource.create(activityReference.getAssets(), "gfx/stone1.png"), activityReference.getVertexBufferObjectManager());
//		mGameBitmapTextureAtlas.load();

		// game entities
//		this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(activityReference.getTextureManager(), 512, 256, TextureOptions.NEAREST);
//		this.mChopperJoeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, activityReference, "helicopter_joe.png", 2, 2);
//		this.mChopperHellTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, activityReference, "helicopter_hell.png", 2, 2);
//
//		try {
//			this.mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
//			this.mBitmapTextureAtlas.load();
//		} catch (TextureAtlasBuilderException e) {
//			Debug.e(e);
//		}

		// analog controller stuff
//		this.mOnScreenControlTexture = new BitmapTextureAtlas(activityReference.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
//		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, activityReference, "onscreen_control_base.png", 0, 0);
//		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, activityReference, "onscreen_control_knob.png", 128, 0);
//		this.mOnScreenControlTexture.load();
		
		// OLD TEXTURES
		mTextureAtlas = new BitmapTextureAtlas(activityReference.getTextureManager(), 64, 256, TextureOptions.BILINEAR);
		mCircleFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTextureAtlas, activityReference, "face_circle_tiled.png", 0, 32, 2, 1);
		baloonEnemy = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTextureAtlas, activityReference, "baloon_enemy.png", 0, 64, 2, 1);
		baloonPlayer = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTextureAtlas, activityReference, "baloon_player.png", 0, 96, 2, 1);
		bullet_fighter = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTextureAtlas, activityReference, "bullet_fighter_white.png", 0, 128, 1, 1);
		enemy_simple = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTextureAtlas, activityReference, "bullet_fighter_white.png", 0, 160, 1, 1);
//		ship_fighter = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, activityReference, "fighter_ship", 0, 220);
		
		mTextureAtlas.load();
	}

	// access the application
	public Main getActivityReference() {
		return this.activityReference;
	}

	private boolean loadMusic() {

		// Music
		MusicFactory.setAssetBasePath("mfx/");
		try {
			menuMusic = MusicFactory.createMusicFromAsset(this.activityReference.getEngine().getMusicManager(), activityReference, "wagner_the_ride_of_the_valkyries.ogg");
			fighter_shot = MusicFactory.createMusicFromAsset(this.activityReference.getEngine().getMusicManager(), activityReference, "fighter_shot_single.ogg");
			fighter_shot.setLooping(false);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void playMenuMusic() {
		if (!menuMusic.isPlaying()) {
			menuMusic.play();
		}
	}

	public void stopMenuMusic() {
		if (menuMusic.isPlaying()) {
			menuMusic.pause();
		}
	}
	
	public void playFighterShot() {
		if (!fighter_shot.isPlaying()) {
			fighter_shot.play();
		}
	}
}
