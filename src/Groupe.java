import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implantation d'un groupe de Boule(s) Ã  l'aide d'un HashSet 
 * Ã  utiliser tel quel sans modifier
 * 
 * @author Guy Lapalme
 */
public class Groupe implements Iterable<Boule>{
	private Set<Boule> groupe;
	
	/**
	 * CrÃ©er un nouveau groupe
	 */
	Groupe(){
		groupe = new HashSet<Boule>();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return groupe.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 * 
	 * Ceci permet d'itÃ©rer sur les boules d'un groupe 
	 *      for(Boule b:groupe){...}
	 */
	public Iterator<Boule> iterator(){
		return groupe.iterator();
	}
	
	/**
	 * Retourne le nombre de boules dans le groupe
	 *
	 */
	public int size(){
		return groupe.size();
	}
	
	/**
	 * VÃ©rifie si un groupe est vide ou non
	 */
	public boolean isEmpty(){
		return groupe.isEmpty();
	}
	
	/**
	 * Ajoute une boule Ã  l'ensemble, sans effet si elle y est dÃ©jÃ 
	 * @param b : la boule Ã  ajouter au groupe
	 */
	public void add(Boule b){
		groupe.add(b);
	}
	
	/**
	 * VÃ©rifie si une boule est dans le groupe ou non
	 * @param b
	 * @return true si la boule est dÃ©jÃ  dans le groupe
	 */
	public boolean contains(Boule b){
		return groupe.contains(b);
	}
	
	/**
	 * Vider le groupe de ses Ã©lÃ©ments
	 */
	public void clear(){
		groupe.clear();
	}
}
