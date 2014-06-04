/* Classe Boule.java
 * 
 * Par Guillaume Lahaie
 * LAHG04077703
 * 
 * Cette classe est utilisee par la classe Jeu pour creer le jeu
 * Bubble Pop. Elle contient les informations concernant une boule
 * du jeu.
 */

import java.awt.*;

public class Boule {
	
	protected Color background, cercle;
	protected int x, y,diam, posx, posy;
	
	public Boule(Color cercle, Color background, int x, int y, int diam, int posx, int posy) {
		this.cercle = cercle;
		this.background = background;
		this.x = x+1;
		this.y = y+1;
		this.diam = diam;
		this.posx = posx;
		this.posy = posy;
	}
		
	public String toString() {
		
		return "Boule de couleur " + cercle + ", de position " + x + ", " + y + " et de rayon " + diam/2; 
	}

	public void displayBoule(Graphics2D g2d) {
		g2d.setColor(background);
		g2d.fillRect(x, y, diam, diam);

		//Maintenant en 3D!		
		g2d.setColor(cercle);
		int centreX = x + diam/2;
		int centreY = y + diam/2;
		int rayon = (diam-2)/2;
		for(int r = rayon; r > 0; r--) {
			float degrade = 1-((float)r/(2*rayon));
			g2d.setColor(new Color((int)(cercle.getRed()*degrade),
					               (int)(cercle.getGreen()*degrade),
					               (int)(cercle.getBlue()*degrade)));
			g2d.fillOval(centreX-r, centreY-r, 2*r, 2*r);
			
		}
		
	}
	
	public boolean checkInside(int x, int y) {
		//Verifie si les coordonnees x et y sont a l'interieur du cercle
		int centerX = this.x+(diam/2);
		int centerY = this.y+(diam/2);
		int distance = (centerX-x)*(centerX-x) + (centerY-y)*(centerY-y);
		return distance <= ((diam-2)*(diam-2)/4);
	}

	public void changeB(Color c) {
		this.background = c;		
	}
	
	public void changeColor(Color c) {
		this.cercle=c;
	}
	
	public boolean compareColor(Boule other) {
		return this.cercle == other.cercle;
	}
	
	public void setXandY(int i, int j, int num) {
		//Met a jour les valeurs de position de la boule
		this.x = i*diam+1;
		this.y = (num-j-1) *diam+1;
		this.posx = i;
		this.posy = j;
	}	
} //Fin Boule