package com.zkncpt.gdx.gyrogdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import com.badlogic.gdx.backends.android.AndroidWallpaperListener;

import com.badlogic.gdx.Gdx;

public class LiveWallpaper extends AndroidLiveWallpaperService {

    private static final String tag = "LiveWallpaper";

    @Override
    public void onCreateApplication() {
        super.onCreateApplication();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;
		config.useWakelock = true;
        config.useCompass = false;
		config.disableAudio = true;
        config.getTouchEventsForLiveWallpaper = false;
        ApplicationListener listener = new GyroGdxGameListener();
        initialize(listener, config);
    }

    @Override
    public Engine onCreateEngine() {
        return new AndroidWallpaperEngine() {
            @Override
            public void onPause() {
                super.onPause();
            }

            @Override
            public void onDestroy() {
                super.onDestroy();
            }

            @Override
            public void onResume() {
                super.onResume();
            }
        };
    }

    public static class GyroGdxGameListener extends GyroGdxGame
            implements AndroidWallpaperListener, ApplicationListener {

        @Override
        public void offsetChange(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset,
                                 int yPixelOffset) {
            Gdx.app.log(tag,"Offset change");
        }

        @Override
        public void previewStateChange(boolean isPreview) {
            Gdx.app.log(tag,"preview state change");
            if (isPreview) {
                Gdx.app.log(tag,"preview state change true");
                // TODO
            }
        }

        @Override
        public void iconDropped(int x, int y) {
            Gdx.app.log(tag,"icon dropped");
        }
    }
}