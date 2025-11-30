package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;


public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche m ;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		m = new Marche(nbEtals);
	}
	
	public class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			for (int i = 0; i < nbEtal; i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit ) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			
		}
		
		public int trouverEtalLibre() {
			for (int i=0; i<etals.length; i++) {
				if(!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
			
		}
		
		public Etal[] trouverEtals(String produit) {
			int cpt = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					cpt++;
				}
			}
			Etal[] etalsVendeur = new Etal[cpt];
			int j = 0;
			for(Etal etal : etals) {
				if (etal.contientProduit(produit)) {
					etalsVendeur[j]=etal;
					j++;
				}
			}
			return etalsVendeur;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(Etal etal : etals) {
				if(etal.getVendeur() == gaulois) {
					return etal;
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			int nbEtalVide = 0;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					sb.append(etals[i].afficherEtal());
					sb.append("\n");
				}else {
					nbEtalVide++;
				}
			}
			if (nbEtalVide>0) {
				sb.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			}
			
			return sb.toString();
		}
		
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		
		chaine.append(vendeur.getNom()).append("cherche un endroit pour vendre ").append(nbProduit).append(produit).append(".\n");
		
		int etalVide = m.trouverEtalLibre();
		
		if (etalVide!=-1) {
			m.utiliserEtal(etalVide, vendeur, produit, nbProduit);
			chaine.append("Le vendeur ").append(vendeur).append(" vend des").append(produit).append(" à l'etal n°").append(etalVide).append(".\n");
		}
		return chaine.toString();
		
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsProduit = m.trouverEtals(produit);
		if (etalsProduit.length==0) {
			chaine.append("Il n'y a pas de vendeur qui propose ").append(produit).append(" au marche.\n");
		}
		else if (etalsProduit.length==1) {
			chaine.append("Seul le vendeur ").append(etalsProduit[0].getVendeur()).append("propose des ").append(produit).append(" au marche.\n");
			
		}
		else {
			chaine.append("Les vendeurs qui proposent des ").append(produit).append(" sont :\n");
			for (int i = 0; i < etalsProduit.length; i++) {
				chaine.append("- ").append(etalsProduit[i].getVendeur()).append("\n");
			}
		}
		
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return m.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = m.trouverVendeur(vendeur);
		
		return etal.libererEtal();
	}
	
	public String afficherMarche() {
		return m.afficherMarche();
	}
	
	
}