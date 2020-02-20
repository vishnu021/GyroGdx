package com.zkncpt.gdx.gyrogdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GyroGdxScreen extends Stage implements Screen, Constants {

	GyroGdxGame app;
	Group group;
	Vector2 currentAcceleration;
	Vector2 toAcceleration;
	Vector2 lastAcceleration;
	int orientation;
	float accelerationTreshold = 0.25f;
	Image satellite;
	Image background;
	Image astronaut;

	public GyroGdxScreen(GyroGdxGame app) {
		super(new StretchViewport(GAME_WIDTH, GAME_HEIGHT));
		this.app = app;
		group = new Group();
		this.addActor(group);
		group.setSize(GAME_HEIGHT, GAME_HEIGHT);

		addLayers();
	}

	private void addLayers() {
		Texture texture = new Texture(Gdx.files.internal("Space_Sprites/Layer3.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		background = new Image(texture);
		background.setScale(0.5f);
		background.setPosition(BACKGROUND_X, BACKGROUND_Y);
		group.addActor(background);

		satellite = new Image(new Texture(Gdx.files.internal("Space_Sprites/Layer2.png")));
		satellite.setSize(SATELLITE_SIZE, SATELLITE_SIZE);
		satellite.setPosition(SATELLITE_X, GAME_HEIGHT - 1.5f * SATELLITE_SIZE);
		group.addActor(satellite);

		astronaut = new Image(new Texture(Gdx.files.internal("Space_Sprites/Layer1.png")));
		astronaut.setSize(ASTRONAUT_SIZE, ASTRONAUT_SIZE);
		astronaut.setPosition((GAME_WIDTH - ASTRONAUT_SIZE) / 2, ASTRONAUT_Y);
		group.addActor(astronaut);

		initialiseVectors();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		currentAcceleration.x = Gdx.input.getAccelerometerX();
		currentAcceleration.y = Gdx.input.getAccelerometerY();

		if ((Math.abs(currentAcceleration.dst(lastAcceleration)) > accelerationTreshold)) {
			orientation = Gdx.input.getRotation();
			switch (orientation) {
			case 90:
				toAcceleration.x = -currentAcceleration.y;
				toAcceleration.y = currentAcceleration.x;
				break;
			case 270:
				toAcceleration.x = currentAcceleration.y;
				toAcceleration.y = -currentAcceleration.x;
				break;
			default:
				toAcceleration.x = currentAcceleration.x;
				toAcceleration.y = currentAcceleration.y;
			}
		}
		lastAcceleration.lerp(toAcceleration, delta * 10);

		satellite.setPosition(SATELLITE_X + lastAcceleration.x * 1 * 3 * 5, satellite.getY());
		background.setPosition(BACKGROUND_X + lastAcceleration.x * -1 * 7 * 5, background.getY());

		orientation = Gdx.input.getRotation();

		act(Gdx.graphics.getDeltaTime());
		draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		super.dispose();
	}

	private void initialiseVectors() {
		currentAcceleration = new Vector2(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY());
		lastAcceleration = new Vector2(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY());
		toAcceleration = new Vector2(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY());

	}

}
