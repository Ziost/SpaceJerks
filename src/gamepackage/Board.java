package gamepackage;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;


public class Board extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	public static ArrayList<Aliens> aliensList = new ArrayList<>();
	private Aliens al;
	Player player;
	Random rand = new Random();
	
	public Board(){		
		setBackground(Color.DARK_GRAY);
		player = new Player();		
	}
	public void born(){		
		al = new Aliens(rand.nextInt(360) + 2 ,0);
		aliensList.add(al);
	}
	
	public void draw(Graphics g){		
		for (int i = 0; i < aliensList.size(); i++) {
			Aliens a = aliensList.get(i);
			boolean draw = true;
			if(a.isVisible()){			
				//if(!player.readyToFire){
					if(player.bullets.size() > 0){
						for (int j = 0; j < player.bullets.size(); j++) {
							if(a.getBounds().intersects(player.bullets.get(j))){
								System.out.println("Collide");
								a.setVisible(false);
								//player.readyToFire = true;
								player.bullets.remove(j);
								draw = false;
							}
						}						
					}
					if (player.getBounds().intersects(a.getBounds())) {
						System.out.println("GAME OVER");
						System.exit(0);
					}
				//}
				if (draw) {
					g.drawImage(a.getImage(),a.getX(),a.getY(),this);
				}
			}
			else {
				aliensList.remove(i);
			}
		}
	}
	
	@Override
	public void run() {
		try {		
			born();
			while(true){
				Aliens last = aliensList.get(aliensList.size()-1);
				if(last.getY() > 50){
					born();
				}
				for (int i = 0; i < aliensList.size(); i++) {	
					Aliens a = aliensList.get(i);
					if(a.isVisible()){
						a.move();
					}
					else {
						aliensList.remove(i);
					}
				}
				Thread.sleep(7);
			}
		} catch (Exception e) {
			System.err.println("Exception " + e.getMessage());
		}
	}
}