package tanks.game;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import tanks.game.display.Display;
import tanks.game.display.logic.MyTime;
import tanks.game.myinput.MyInput;

public class Game implements Runnable {

	public static final int WIDTH = 1500;
	public static final int HEIGTH = 1000;
	public static final String TITLE = "lol kek";
	public static final int CLEAR_COLOR = 0xff000000;
	public static final int NUM_BUFFER = 3;

	public static final float UPDATE_RATE = 60.0f;
	public static final float UPDATE_INTERVAL = MyTime.SECOND / UPDATE_RATE;
	public static final long IDLE_TIME = 1;

	private boolean runing;
	private Thread gameThread;

	// delete
	private Graphics2D graphics2D;

	// delete

	float speed = 6;
	float x = 350;
	float y = 250;

	MyInput enterButton;

	public Game() {
		runing = false;
		Display.create(WIDTH, HEIGTH, TITLE, CLEAR_COLOR, NUM_BUFFER);
		graphics2D = Display.getGraphics2d();
		enterButton = new MyInput();
		Display.addInputListener(enterButton);
	}

	public synchronized void start() {
		if (runing)
			return;

		runing = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	public synchronized void stop() {
		if (!runing)
			return;
		runing = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}
	}

	boolean enterButton
	private void update() {
		if (enterButton.checkButton(KeyEvent.VK_UP)|(enterButton.checkButton(KeyEvent.VK_W)) {
			y -= speed;
		} else {
           if (enterButton.checkButton(KeyEvent.VK_)) {
        	   y -= speed;
           }
			if (enterButton.checkButton(KeyEvent.VK_DOWN)) {
				y += speed;
			} else {

				if (enterButton.checkButton(KeyEvent.VK_RIGHT)) {
					x += speed;
				}

				if (enterButton.checkButton(KeyEvent.VK_LEFT)) {
					x -= speed;

				}
			}
		}
	}

	private void render() {
		Display.clear();

		graphics2D.setColor(Color.red);
		graphics2D.fillRect((int) x, (int) y, 100, 100);

		Display.swapBuffer();

	}

	public void run() {

		int fps = 0;
		int upd = 0;
		int updLoop = 0;

		long countTime = 0;

		float delta = 0;

		long lastTime = MyTime.get();
		while (runing) {
			long now = MyTime.get();
			long elapseTime = now - lastTime;
			lastTime = now;

			countTime += elapseTime;

			boolean rendering = false;
			delta += (elapseTime / UPDATE_INTERVAL);
			while (delta > 1) {
				update();
				upd++;
				delta--;
				if (rendering) {
					updLoop++;
				} else {
					rendering = true;
				}
			}
			if (rendering) {
				render();
				fps++;
			} else {
				try {
					Thread.sleep(IDLE_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (countTime >= MyTime.SECOND) {
				Display.setTitle(TITLE + "___Fps:" + fps + " | Upd:" + upd
						+ " | UpLoop:" + updLoop);
				upd = 0;
				updLoop = 0;
				fps = 0;
				countTime = 0;
			}
		}
	}

	private void cleanUp() {
		Display.destroyWindow();
	}
}
