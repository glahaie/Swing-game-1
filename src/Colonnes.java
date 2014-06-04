/* Classe Colonnes.java
 * 
 * Par Guillaume Lahaie
 * LAHG04077703
 * 
 * Cette classe est utilisée par la class Jeu pour creer
 * le jeu Bubble Pop. Elle contient une colonne du jeu.
 * 
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Colonnes extends JPanel{
	
	protected Vector<Boule> colonne = new Vector<Boule>();
	protected int posx, x, diam;
	protected int numOfBoule; //Nombre de boules dans la colonne au depart
	
	public Colonnes(int posx, int numOfBoule, int diam, int x) {		
		this.posx = posx;
		this.x = x;
		this.diam = diam;
		this.numOfBoule = numOfBoule;
		createRandomColonne();
	}

	public String toString() {
		
		return "Colonne position " + posx + "avec " + colonne.size() + " boules restantes";
	}
	
	public boolean isEmpty() {
		
		return colonne.isEmpty();
	}
	
	public void createRandomColonne() {
		//Creer un colonne en choisissant les couleurs des boules au hasard		
		int choix;
		Color c;
		
		for (int i = 0; i < numOfBoule; i ++) {
			//Choisir un couleur au hasard
			choix = (int)(Math.random() *5);
			switch(choix) {
			case 0:		c = Color.red;
						break;
			case 1:		c = Color.yellow;
						break;
			case 2:		c = Color.blue;
						break;
			case 3:		c = Color.magenta;
						break;
			default:	c = Color.green;
						break;
			} 
			colonne.add(new Boule(c, Color.white, x, (numOfBoule-i-1)*diam, diam, this.posx, i));
		}
	}
	
	public void displayColonne(Graphics2D g2d) {
		for(int i = 0; i < colonne.size(); i++) {
			colonne.get(i).displayBoule(g2d);
		}
		
	}
	
	public Boule get(int i) {
		return colonne.get(i);
	}
	
	public int grandeur() {
		return colonne.size();
	}
	
	public void effacer(int posy) {
		//Efface la boule en position posy et met a jour les positions posx et posy de chaque boule
		colonne.remove(posy);
		//update boules pos
		for(int i = 0; i < colonne.size(); i++) {
			colonne.get(i).setXandY(posx, i, numOfBoule);
		}
	}
	
	public void resetBG () {
		//Remet l'arriere-plan a blanc pour toutes les boules de la colonne
		for (int i = 0; i < colonne.size(); i++) {
			colonne.get(i).changeB(Color.WHITE);
		}
	}
	
	public void updateBoules() {
		//Met a jour les valeurs posx et poy de toutes les boules dans la colonne
		for(int i = 0; i < colonne.size(); i++) {
			colonne.get(i).setXandY(posx, i, numOfBoule);
		}
	}
}  //Fin Colonnes