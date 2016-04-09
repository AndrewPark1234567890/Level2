import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
	Timer timer;
	int x = 0;
	ArrayList<GameObject> objects;
	ArrayList<GameObject> enemys;
	BufferedImage i;
	BufferedImage i2;
	BufferedImage i3;
	BufferedImage i4;
	BufferedImage i5;
	GameObject player;
	GameObject enemy;
	GameObject enemy2;
	GameObject backg;
	int spearLoc;
	Random rand = new Random();
	boolean lose = false;

	GamePanel() {
		objects = new ArrayList<GameObject>();
		enemys = new ArrayList<GameObject>();
		try {
			i = ImageIO.read(this.getClass().getResourceAsStream("Knight2.png"));
			i2 = ImageIO.read(this.getClass().getResourceAsStream("Smorc.png"));
			i3 = ImageIO.read(this.getClass().getResourceAsStream("wall.jpg"));
			i4 = ImageIO.read(this.getClass().getResourceAsStream("Knight2-Right.png"));
			i5 = ImageIO.read(this.getClass().getResourceAsStream("RightAttack.png"));
		} catch (Exception ex) {
		}
		player = new Playa(500, 500, 300, 300, i);
		((Playa) player).loadBoth(i, i4);
		enemy = new Enemy(x, 500, 300, 300, i2);
		enemy2 = new Enemy2(x + 2500, 500, 300, 300, i5);
		backg = new GameObject(0, 0, 2000, 1000, i3);
		objects.add(backg);
		objects.add(player);
		objects.add(enemy);
		objects.add(enemy2);
		for (int i = 0; i < 10; i++) {
			objects.add(new Enemy(x - 300, 500, 300, 300, i2));
			objects.add(new Enemy2(x + 2200, 500, 300, 300, i5));
		}
		timer = new Timer(1000 / 60, this);
		timer.start();
	}

	public void paintComponent(Graphics gra) {

		for (GameObject go : objects) {
			go.paint(gra);
			// System.out.println(lose);
			if (lose == true) {
				gra.setColor(Color.BLACK);
				gra.setFont(new Font(Font.SANS_SERIF, 150, 150));
				gra.drawString("Game Over. You Lose.", 200, 200);
			}
			//System.out.println(lose + "2");
		}
	}

	void update() {
		addEnemies();
		for (int i = 0; i < objects.size(); i++) {
			GameObject obj = objects.get(i);

			if (obj.id == 1) {

				if (player.checkKill(obj.getCboxWin())) {
					// System.out.println("Bye");
					objects.remove(obj);
					((Enemy) obj).dead = true;
				}

				if (player.checkColide(obj.getCbox()) && ((Enemy) obj).dead == false) {
					// System.out.println("hi");
					lose = true;
					//System.out.println(lose + "1");
				}
			}
			if (obj.id == 2) {

				if (player.checkKill(obj.getcboxWin())) {
					// System.out.println("Goodbye");
					objects.remove(obj);
					((Enemy2) obj).dead = true;
				}

				if (player.checkColide(obj.getCbox()) && ((Enemy2) obj).dead == false) {
					// System.out.println("Hello");
					lose = true;

				}
			}

		}
		for (GameObject go : objects) {
			go.update();
		}

	}

	void addEnemies() {
		System.out.println(rand);
		if (rand.nextInt(500) == 10) {
			Enemy e = new Enemy(-200, 500, 300, 300, i2);
			objects.add(e);
			rand = new Random();
		}
		if (rand.nextInt(500) == 90) {
			Enemy2 e2 = new Enemy2(2200, 500, 300, 300, i5);
			objects.add(e2);
			rand = new Random();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		update();
		repaint();

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		player.keyTyped(e);
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		player.keyPressed(e);

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		player.keyReleased(e);
	}
}