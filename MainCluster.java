import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainCluster {
    public static void main(String[] args) {
        try {
            new File("output").mkdirs();

            Map<String, Color> biomesReference = new HashMap<>();
            biomesReference.put("Tundra", new Color(71, 70, 61));
            biomesReference.put("Taiga", new Color(43, 50, 35));
            biomesReference.put("Foret_temperee", new Color(59, 66, 43));
            biomesReference.put("Foret_tropicale", new Color(46, 64, 34));
            biomesReference.put("Savane", new Color(84, 106, 70));
            biomesReference.put("Prairie", new Color(104, 95, 82));
            biomesReference.put("Desert", new Color(152, 140, 120));
            biomesReference.put("Glacier", new Color(200, 200, 200));
            biomesReference.put("Eau_peu_profonde", new Color(49, 83, 100));
            biomesReference.put("Eau_profonde", new Color(12, 31, 47));

            System.out.println("Chargement de la carte...");
            BufferedImage carte = ManipulateurImage.chargerImage("input/Planete 1.jpg");

            System.out.println("Extraction des données pixels...");
            int[][] pixels = GestionnaireBiome.extrairePixels(carte);

            System.out.println("Exécution de l'algorithme K-Means...");
            int nombreDeClusters = 5; // Vous pouvez ajuster ce paramètre
            KMeansClustering kmeans = new KMeansClustering();
            int[] affectations = kmeans.executer(pixels, nombreDeClusters);
            
            int[][] centroides = kmeans.getCentroides();

            System.out.println("Étiquetage des biomes...");
            NormeCouleurs norme = new NormeEuclidienne();
            Map<Integer, String> etiquettes = GestionnaireBiome.etiqueterClusters(centroides, biomesReference, norme);

            // 6. Génération des images de sortie
            System.out.println("Générations des rendus visuels...");
            BufferedImage fondClair = GestionnaireBiome.creerImageFondClair(carte, 75.0);

            for (int i = 0; i < nombreDeClusters; i++) {
                String nomBiome = etiquettes.get(i);
                System.out.println("Génération de l'image pour le biome : " + nomBiome);
                
                BufferedImage renduBiome = GestionnaireBiome.isolerBiome(carte, fondClair, affectations, i);
                
                ManipulateurImage.sauverImage(renduBiome, "PNG", "output/" + nomBiome + "_cluster_" + i + ".png");
            }

            System.out.println("Traitement terminé avec succès !");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}