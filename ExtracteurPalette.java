import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.*;

public class ExtracteurPalette {

    public static Palette extraire(BufferedImage image, int taillePalette, NormeCouleurs norme, double distanceMinimale) {
        Map<Integer, Integer> histogramme = new HashMap<>();

        // Création de l'histogramme
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y) & 0xffffff; // Ignorer alpha
                histogramme.put(rgb, histogramme.getOrDefault(rgb, 0) + 1);
            }
        }

        // Tri des couleurs par fréquence d'apparition (décroissant)
        List<Map.Entry<Integer, Integer>> listeCouleurs = new ArrayList<>(histogramme.entrySet());
        listeCouleurs.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Sélection des couleurs avec distance minimale (pour éviter les doublons perceptuels)
        List<Color> couleursChoisies = new ArrayList<>();
        
        for (Map.Entry<Integer, Integer> entree : listeCouleurs) {
            if (couleursChoisies.size() >= taillePalette) break;

            int cInt = entree.getKey();
            Color candidat = new Color(cInt);
            boolean estAssezDifferent = true;

            // Vérifier la distance avec les couleurs déjà sélectionnées
            for (Color existante : couleursChoisies) {
                if (norme.distanceCouleur(candidat, existante) < distanceMinimale) {
                    estAssezDifferent = false;
                    break;
                }
            }

            if (estAssezDifferent) {
                couleursChoisies.add(candidat);
            }
        }

        return new Palette(couleursChoisies.toArray(new Color[0]), norme);
    }
}