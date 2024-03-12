package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.Village.VillageSansChefException;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Village village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		Gaulois asterix = new Gaulois("Astérix", 8);
		try {
			village.afficherVillageois();
		} catch (VillageSansChefException e) {
			e.printStackTrace();
		}
		
		System.out.println(village.installerVendeur(asterix, "fleurs", 20));
		Etal etal = new Etal();
		try {
		System.out.println(etal.acheterProduit(-1, asterix));
		}catch(IllegalArgumentException | IllegalStateException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Fin du test");

	}

}
