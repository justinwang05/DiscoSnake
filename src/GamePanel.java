import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

	public static final int SCREEN_WIDTH = 900;
	public static final int SCREEN_HEIGHT = 900;
	public static final int UNIT_SIZE = 30;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT / UNIT_SIZE);
	static final int DELAY = 75;

	ArrayList<Fruit> fruitsList = new ArrayList<>();

	Snake snake = new Snake(5, "BLUE");

	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];

	char direction = 'R';
	
	boolean showGridlines = false;
	boolean running = false;
	Timer timer;
	Timer dialogueTimer;
	Random random;

	String gameOverReason = "";

	Image imageBomb = (new ImageIcon("bomb.png")).getImage().getScaledInstance(UNIT_SIZE, UNIT_SIZE, Image.SCALE_DEFAULT);
	Image imageFire = (new ImageIcon("fire.png")).getImage().getScaledInstance(UNIT_SIZE, UNIT_SIZE, Image.SCALE_DEFAULT);

	GamePanel() {
		fruitsList.add(new Fruit(0));
		fruitsList.add(new Fruit(1));
		fruitsList.add(new Fruit(2));
		fruitsList.add(new Fruit(3));

		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		for(Fruit f : fruitsList){
			f.newFruitLocation();
		}

		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		if (running) {
			// gridlines
			if(showGridlines){
				for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
					g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
					g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
				}
			}

			
			// draws the fruits
			for (Fruit f : fruitsList){
				g.setColor(f.getColor());
				g.fillOval(f.getX(), f.getY(), UNIT_SIZE, UNIT_SIZE);
			}

			//draw the snake
			for (int i = 0; i < snake.getBodyParts(); i++) {
				if (i == 0) {
					g.setColor(snake.getHeadColor());
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					if(i % 2 == 0){
						g.setColor(snake.getBodyColor1());
					}else{
						g.setColor(snake.getBodyColor2());
					}

					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}

			//Score at top
			g.setColor(Color.WHITE);
			g.setFont(new Font("Comic Sans MS", 0, 35));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + snake.getFruitsEaten(), (SCREEN_WIDTH - metrics.stringWidth("Score: " + snake.getFruitsEaten())) / 2, g.getFont().getSize());

			g.setColor(fruitsList.get(snake.getColorInt()).getColor());
			g.drawString(snake.getColorName(), (SCREEN_WIDTH - metrics.stringWidth(snake.getColorName())) / 2, g.getFont().getSize() * 2);


		} else {
			gameOver(g, gameOverReason);
		}
	}

	public void move() {
		for (int i = snake.getBodyParts(); i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		switch (direction) {
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
		}
	}

	public void checkFruit() {
		for(Fruit f : fruitsList){
			//Collision with a fruit
			if(f.sameLocation(x[0], y[0])){
				//Relocates the fruit
				f.newFruitLocation();

				//Checks matching color
				if(f.sameColor(snake.getColorName())){
					snake.addFruitsEaten(1);
					snake.addBodyParts(1);
					snake.setRandomColor();


				}else{
					snake.addFruitsEaten(-1);
				}


			}

			
		}
		/* 
		if (x[0] == appleX && y[0] == appleY) {
			bodyParts+=5;
			applesEaten++;

			newApple();
		}
		*/
	}

	public void checkCollisions() {
		// checks for head collides with body.
		for (int i = snake.getBodyParts(); i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
				gameOverReason = "You collided with yourself!";
			}
		}

		// checks if head touches left border
		if (x[0] < 0) {
			running = false;
			gameOverReason = "You hit the left wall!";
		}

		// checks if head touches right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
			gameOverReason = "You hit the right wall!";
		}

		// checks if head touches top border
		if (y[0] < 0) {
			running = false;
			gameOverReason = "You hit the top wall!";
		}

		// checks if head touches bottom border
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
			gameOverReason = "You hit the bottom wall!";
		}

		if (!running) {
			timer.stop();
		}
	}

	public void gameOver(Graphics g, String reason) {
		//Score text
		g.setColor(Color.RED);
		g.setFont(new Font("Comic Sans MS", 0, 25));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + snake.getFruitsEaten(), (SCREEN_WIDTH - metrics.stringWidth("Score: " + snake.getFruitsEaten())) / 2, g.getFont().getSize());

		// Game Over text
		g.setColor(Color.RED);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 75));
		metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_WIDTH / 2);

		g.setFont(new Font("Comic Sans MS", Font.BOLD, 55));
		metrics = getFontMetrics(g.getFont());
		g.drawString(reason, (SCREEN_WIDTH - metrics.stringWidth(reason)) / 2, SCREEN_WIDTH / 2 + 85);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (running) {
			move();
			checkFruit();
			checkCollisions();
		
		}

		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (direction != 'R') {
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (direction != 'L') {
						direction = 'R';
					}
					break;

				case KeyEvent.VK_DOWN:
					if (direction != 'U') {
						direction = 'D';
					}
					break;

				case KeyEvent.VK_UP:
					if (direction != 'D') {
						direction = 'U';
					}
					break;
			}
		}
	}
}
