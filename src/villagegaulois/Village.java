package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	public Village(String nom, int nbVillageoisMaximum,int nbEtalMarche) {
		marche = new Marche(nbEtalMarche);
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
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

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException("Le village ne peut exister sans chef");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	//Methode String installerVendeur:  installe un vendeur dans le marche puis il affiche.
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int indiceEtalLibre = marche.trouverEtalLibre();
		marche.utiliserEtal(indiceEtalLibre,vendeur,produit,nbProduit);
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit +
				" " + produit + ".\n" + "Le vendeur " + vendeur.getNom() +
				" vend des " + produit + " à l'étal n°" + (indiceEtalLibre+1) + ".\n ");
		return chaine.toString();
		
	}
	
	//Methode String rechercherVendeursProduit:  retourne l’étal sur lequel s’est 
	//installé le vendeur passé en paramètre d’entrée ou null s’il n’y en a pas.
	
	public String rechercherVendeursProduit(String produit) {
		
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsAvecProduit = marche.trouverEtals(produit);

		int i = 0;
		
		if(etalsAvecProduit.length == 1){
			Gaulois gaulois = etalsAvecProduit[0].getVendeur();
			chaine.append("Seul le vendeur " + gaulois.getNom()  + " propose des " + produit + " au marché \n");
		}else if(etalsAvecProduit.length > 1) {
			chaine.append("Les vendeurs qui proposent des fleurs sont :" + "\n");
			while (i < etalsAvecProduit.length) {
				Gaulois gauloisAvecProduit = etalsAvecProduit[i].getVendeur();
				chaine.append("- " + gauloisAvecProduit.getNom() + "\n");
	
				i++;
	
			}
		}else {
			chaine.append("Il n'y a pas de vendeur qui propose des " +  produit + " au marché.\n");
		}
		return chaine.toString();
		
	}
	

	//Methode Etal rechercherEtal:  retourne l’étal sur lequel s’est 
	//installé le vendeur passé en paramètre d’entrée ou null s’il n’y en a pas.
	
	 public Etal rechercherEtal(Gaulois vendeur) {
		 return marche.trouverVendeur(vendeur);
	 }
	 
	//Methode String partirVendeur:  on enleve un vendeur du tableau des etals et il affiche 
	 // qu'il quitte le marche
	 
	 
	 public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etal = marche.trouverVendeur(vendeur);
		chaine.append("\n" + etal.libererEtal());
		return chaine.toString();		 
	 }
	 
	//Methode String afficherMarche(): On affiche le marche du villlage
	 
	 public String afficherMarche() {
		 StringBuilder chaine = new StringBuilder();
		 chaine.append("Le marché du village " + nom
				 + " possède plusieurs étals :\n");
		 chaine.toString();
		 return marche.afficherMarcher();
	 }	 
	 
	//Classe interne Marche
	
	private static class Marche{ 
		
		private Etal[] etals;
		private int nbEtal;
		
		//Constructeur Marche
		
		private Marche(int nbEtal){ 
			
			this.nbEtal = nbEtal;
			this.etals = new Etal[nbEtal];
			for (int i = 0; i < nbEtal; i++) {
				etals[i] = new Etal();
			}
			
		}
		
		//Methode void utiliserEtal:  permet à un gaulois de s’installer à un étal.
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			if (indiceEtal >= 0 && indiceEtal < nbEtal) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}

		}

		
		//Methode int trouverEtalLibre:  permet de trouver un étal
		// non occupé dans le tableau etals. S’il n’y a pas d’étal disponible, la méthode
		// retourne -1.
		
		private int trouverEtalLibre() {
			for (int i = 0; i < nbEtal; i++) {
				if (!etals[i].isEtalOccupe() ) {
					return i;
				}
			}
			return -1;
		}
		
		
		//Methode int trouverEtalLibre:  retourne un tableau contenant 
		//tous les étals où l’on vend un produit.

		
		private Etal[] trouverEtals(String produit) {
			
			int nbEtalAvecProduit = 0;
			for (int i = 0; i < nbEtal; i++) {
				if (etals[i].isEtalOccupe() && (etals[i].contientProduit(produit))) {
						nbEtalAvecProduit++;	
				}
			}
			
			Etal[] etalAvecProduit = new Etal[nbEtalAvecProduit];
			int indiceEtal = 0;
			for (int i = 0; i < nbEtal; i++) {
				if(etals[i].isEtalOccupe() && (etals[i].contientProduit(produit))) {
						etalAvecProduit[indiceEtal] = etals[i];
						indiceEtal++;
					
				}
			}
			
			return etalAvecProduit;
			
		}
		
		//Methode Etal trouverVendeur:  retourne l’étal sur lequel s’est 
		//installé le vendeur passé en paramètre d’entrée ou null s’il n’y en a pas.
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < nbEtal; i++) {

				if (etals[i].getVendeur()==(gaulois)) {

					return etals[i];

				}

			}

			return null;

		}

		
		//Methode void  afficherMarche: une chaîne de caractères
		//contenant l’affichage de l’ensemble des étals occupés du marché. S’il reste
		//des étals vide la chaîne de retour se terminera par : ”Il reste " +
		//nbEtalVide + " étals non utilisés dans le marché.\n”.
		
		private String afficherMarcher() {
			
			int nbEtalVide = 0;

			StringBuilder chaine = new StringBuilder();

			for (int i = 0; i < nbEtal; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
					nbEtalVide = nbEtal - i;
				}

			}
			chaine.append("Il reste " + nbEtalVide + " �tals non utilis�s dans le march�");
			return chaine.toString();
		}
     }
	
	
	
	public class VillageSansChefException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public VillageSansChefException() {
		}

		public VillageSansChefException(String message) {
			super(message);
		}

		public VillageSansChefException(Throwable cause) {
			super(cause);
		}

		public VillageSansChefException(String message, Throwable cause) {
			super(message, cause);
		}

	}
	
}