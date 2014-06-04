/* Classe Jeu.java
 * 
 * Par Guillaume Lahaie
 * LAHG04077703
 * 
 * Cette classe creer l'interface graphique pour le jeu Bubble Pop.
 * La plupart des methodes se retrouvent a l'interieur de la classe
 * interne Boules qui gere le jeu.
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

public class Jeu extends JPanel {
	
	protected static JFrame frame;
	protected static JLabel total;
	protected static JLabel bubblePop;
	protected static int nombreColonne = 12;
	protected static int nombreBoule = 10;
	protected static int diam = 40;
	protected static JPanel panelBas;
	
	static class Boules extends JPanel implements MouseMotionListener, MouseListener {
		//Classe interne de Jeu. Elle contient le JPanel ou on retrouve les boules
		// pour le jeu, ainsi que la gestion des evenements pour que le jeu se 
		//deroule bien.
		
		protected int numOfCol;
		protected int numOfBoule;
		protected int diam;
		protected int totalPt;
		protected int tempPt;
		private Groupe temp;		//Groupe qui stocke les voisins de meme couleur
		protected Vector<Colonnes> colonnes;
		private Groupe controle;	//Groupe pour verifier s'il reste des mouvements possibles		
		protected boolean click1;	//Verifie s'il s'agit d'un premier ou deuxieme click de souris
				
		Boules(int numOfCol,int numOfBoule, int diam) {
			this.numOfCol = numOfCol;
			this.numOfBoule = numOfBoule;
			this.diam = diam;
			addMouseMotionListener(this);
			addMouseListener(this);
			setSize(new Dimension(numOfCol*diam+5, numOfBoule*diam+5));
			//create objects
			creerObjets();
			creerPartie();
		}
		
		public String toString() {
			return "Jeu de boules ayant " + numOfCol + " colonnes et " + numOfBoule + " boules par colonne";
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
				//Affiche un fond blanc
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0,0, numOfCol*diam+2, numOfBoule*diam+2);					
				//Afficher les boules
				for(int i = 0; i < colonnes.size(); i++) {
					colonnes.get(i).displayColonne(g2d);
				}
				g2d.setColor(Color.black);
				g2d.drawRect(0,0, numOfCol*diam+2, numOfBoule*diam+2);
		}
		
		public void creerObjets() {
			temp = new Groupe();
			colonnes = new Vector<Colonnes>();
			controle = new Groupe();
			totalPt = 0;
			tempPt = 0;
		}
		
		public void creerPartie() {
			int x = 0;
			for(int i = 0; i < numOfCol; i++) {
				colonnes.add(new Colonnes(i, numOfBoule, diam, x));
				x += diam;
			}
			//Verifie si la partie a des mouvements possibles
			if(!moreMoves(controle)) {
				displayMsg(totalPt, colonnes);
			}

		}
		
		public void mouseMoved(MouseEvent arg0) {
		
			boolean noBoule = true;
			tempPt = 0;	
			
			int x = arg0.getX();
			int y = arg0.getY();
			
			//posx et posy a une valeur impossible dans le jeu
			int posx = -1, posy = -1;
						
			//Trouver la boule ou la souris est situe, si elle existe
			for (int i = 0; i < colonnes.size(); i++) {
				for (int j = 0; j < colonnes.get(i).grandeur(); j++) {
					if (colonnes.get(i).get(j).checkInside(x, y)) {
						posx = colonnes.get(i).posx;
						posy = colonnes.get(i).get(j).posy;
						noBoule = false;
					}
				}
			}				
			if(click1) {
			//Il y a eu un clic de souris, un groupe est choisi, ne rien faire
			} else {
				if(noBoule) {
					//S'assurer que chaque boule a un arriere-plan blanc
					//et remettre les pts temporaire a 0
					tempPt = 0;
					bubblePop.setText("BubblePops: 0 (" + tempPt + ")");
					resetBG();
				} else{
					//Verifier si la boule choisi a des voisins de meme couleur
					//si oui, changer leur arriere-plan a gris et mettre a jour
					//les points possibles
					resetBG();
					temp.clear();
					
					findNeighbours(posx, posy, temp);
					if (temp.size() > 1) {
						changeBG(temp, Color.gray);
						tempPt = (temp.size()*(temp.size()-1));
						bubblePop.setText("BubblePops: " + temp.size() + " (" + tempPt + ")");
					} else {
						bubblePop.setText("BubblePops: 0 (" + tempPt + ")");
					}			
				}
				repaint();
			}			
		}
		
		public void findNeighbours(int posx, int posy, Groupe bs) {			
			//Mettre la boule dans le Groupe
			bs.add(colonnes.get(posx).get(posy));
				
			//Verifier les quatres voisins possibles
			checkNeighbour(posx-1, posy, colonnes.get(posx).get(posy), bs);
			checkNeighbour(posx+1, posy, colonnes.get(posx).get(posy), bs);
			checkNeighbour(posx, posy-1, colonnes.get(posx).get(posy), bs);
			checkNeighbour(posx, posy+1, colonnes.get(posx).get(posy), bs);
		}

		
		public void checkNeighbour(int posx, int posy, Boule b, Groupe bs) {
			//Verifier si la boule a cette position existe
			//Si oui, verifier la couleur
			//Si c'est la bonne couleur rappeler findNeighbours
			if(posx < 0 || posx >= colonnes.size()) {
				//do nothing
			} else {
				if (posy < 0 || posy >= colonnes.get(posx).grandeur()) {
					// do nothing
				} else {
					//check if same color
					if(b.compareColor(colonnes.get(posx).get(posy))) {
						//check if ball already inside group
						if(!bs.contains(colonnes.get(posx).get(posy))) {
							findNeighbours(posx, posy, bs);
						}
					}
				}
			}
		}
		public void mouseDragged(MouseEvent e) {}

		public void mouseClicked(MouseEvent arg0) {
			
			int x = arg0.getX();
			int y = arg0.getY();
			
			boolean inside = false;
						
			//S'assurer que le click etait bien a l'interieur d'une boule choisie 
				for(Boule b:temp) {
					if(b.checkInside(x, y)) {
						inside = true;
					}
				}
			
			if(click1 && inside && (temp.size() > 1)) {
				//On efface, met a jour les colonnes et les valeurs
				//et on s'assure que les boules restantes ont 
				//tous un arriere-plan blanc
				//On verifie aussi s'il reste des boules ayant un voisin
				// de meme couleur
				effacerBoules(temp);
				
				colonnes = updateCol(colonnes);
				
				click1 = false;
				totalPt += tempPt;
				total.setText("Points: " + totalPt);
				tempPt = 0;
				bubblePop.setText("BubblePops: 0 (" + tempPt + ")");
				temp.clear();
				resetBG();
				repaint();

				if(!moreMoves(controle)) {
					displayMsg(totalPt, colonnes);
				}

			} else if (click1 && !inside) {
				//On ne veut pas eliminer les boules choisies auparavant
				//On remet le tout a 0
				resetBG();
				temp.clear();
				tempPt = 0;
				bubblePop.setText("BubblePops: 0 (" + tempPt + ")");
				click1=false;
			} else {
				//Premier clic, on change l'arriere-plan au noir si necessaire
				if(temp.size() > 1) {
					click1 = true;
					for(Boule b:temp) {
						b.changeB(Color.BLACK);
					}
				}
			}
			repaint();			
		}

		public void mouseEntered(MouseEvent arg0) {}

		public void mouseExited(MouseEvent arg0) {}

		public void mousePressed(MouseEvent arg0) {}

		public void mouseReleased(MouseEvent arg0) {}
		
		public Vector<Colonnes> updateCol(Vector<Colonnes> vg) {
			//Met a jour les colonnes et les boules dans les colonnes
			Vector<Colonnes> temp = new Vector<Colonnes>();
			for (int i = 0; i < vg.size(); i++) {
				if(!vg.get(i).isEmpty()) {
					temp.add(colonnes.get(i));
					temp.get(temp.size()-1).posx = temp.size()-1;
					temp.get(temp.size()-1).updateBoules();
				}
			}	
			return temp;
		}
		public void resetBG() {
			// Remet la couleur de fond de toutes les boules restantes a blanc
			for(int i = 0; i < colonnes.size(); i++) {
				colonnes.get(i).resetBG();
			}
		}
		
		public void changeBG(Groupe bs, Color c) {
			//Change la couleur de fond de toutes les boules du groupes Bs à la couleur demandee
			for(Boule b:bs) {
				b.changeB(c);
			}
		}
		
		public boolean moreMoves(Groupe bs) {
			//Verifie s'il reste des mouvements possibles dans le jeu
			for(int i = 0; i < colonnes.size(); i++) {
				for (int j = 0; j < colonnes.get(i).grandeur(); j++) {
					bs.clear();
					findNeighbours(i, j, bs);
					if(bs.size() > 1) {
						return true;
					}
				}
			}				
			return false;
		}
		
		public void effacerBoules(Groupe bs) {
			//Efface toutes les boules du groupe dans le jeu
			for(Boule b:bs) {
				colonnes.get(b.posx).effacer(b.posy);
			}
		}
		
	} // fin Boules

	Jeu() {
		setLayout(new BorderLayout());
		panelBas = new JPanel();
		panelBas.setLayout(new FlowLayout());
		
		total = new JLabel("Points: 0");
		bubblePop = new JLabel("Bubble Pop 0 (0)");
		panelBas.add(total);
		panelBas.add(bubblePop);
		
		panelBas.setSize(nombreColonne*diam, 50);
			
		add(panelBas, BorderLayout.SOUTH);
		add(new Boules(nombreColonne, nombreBoule, diam), BorderLayout.CENTER);
		
	}
		
	public static void displayMsg(int totalPt, Vector<Colonnes> vc) {
		//Affiche un JOptionPane lors de la fin du jeu pour demander si l'usager
		//veut jouer de nouveau
		String mess1 = "Bravo, vous avez fait " + totalPt + " points.";
		String mess2 = "Wow! vous avez poppe toutes les boules du jeu et fait " + totalPt + " points!";
		String mess3 = "\n\nVoulez-vous jouer une autre partie?";
		
		String mess = !vc.isEmpty()?mess1+mess3:mess2+mess3;
        int n = JOptionPane.showConfirmDialog(frame, mess, "Test", JOptionPane.YES_NO_OPTION);
        if(n == JOptionPane.YES_OPTION) {        	
        	frame.getContentPane().removeAll();
        	frame.getContentPane().add(new Jeu());
        	//Le nouveau jeu n'apparait pas sans setVisible, repaint ne fait rien
        	frame.setVisible(true);
        } else if (n == JOptionPane.NO_OPTION) {
        	System.exit(0);
        }
	}	
	
	public static void main(String[] args) {
		
		frame = new JFrame("Bubble pop");
		frame.getContentPane().add(new Jeu());
		frame.setVisible(true);
		frame.setSize(nombreColonne*diam+15, nombreBoule*diam+65);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
}