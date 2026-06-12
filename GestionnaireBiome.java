import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.HashMap;

public class GestionnaireBiome {

    public static int[][] extrairePixels(BufferedImage image) {
        int[][] pixels = new int[image.getWidth() * image.getHeight()][3];
        
        for(int y=0;y<image.getWidth();y++){
            for(int x=0;x<image.getHeight();x++){
                int[] rgb = OutilCouleur.getTabColor(image.getRGB(x, y));
                pixels[y*image.getWidth()+x] = rgb;
            }
        }
        return pixels;
    }

    public static Map<Integer, String> etiqueterClusters(int[][] centroides, Map<String, Color> biomesReference, NormeCouleurs norme) {
        Map<Integer, String> etiquettes = new HashMap<>();

        for(int i = 0; i < centroides.length; i++){
            int[] centroide = centroides[i];
            Color couleurCentroide = new Color(centroide[0], centroide[1], centroide[2]);
            double distMin = Double.MAX_VALUE;
            String biomeLePlusProche = "";

            for(Map.Entry<String, Color> entree : biomesReference.entrySet()){
                String nomBiome = entree.getKey();
                Color couleurBiome = entree.getValue();
                
                double dist = norme.distanceCouleur(couleurCentroide, couleurBiome);
                if(dist < distMin) {
                    distMin = dist;
                    biomeLePlusProche = nomBiome;
                }
            }
            etiquettes.put(i, biomeLePlusProche);
        }
        return etiquettes;
    }

    public static BufferedImage creerImageFondClair(BufferedImage source, double pourcentage) {
        BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        
        for(int x=0;x<source.getWidth();x++){
            for(int y=0;y<source.getHeight();y++){
                int[] rgb = OutilCouleur.getTabColor(source.getRGB(x, y));
                for(int i=0;i<rgb.length;i++){
                    rgb[i] = (int) Math.round(rgb[i] + (pourcentage / 100.0) * (255 - rgb[i]));
                }
                dest.setRGB(x, y, OutilCouleur.getIntColor(rgb[0], rgb[1], rgb[2]));
            }
        }
        return dest;
    }

    public static BufferedImage isolerBiome(BufferedImage imageOriginale, BufferedImage fondClair, int[] affectations, int indexClusterCible) {
        int width = imageOriginale.getWidth();
        int height = imageOriginale.getHeight();
        BufferedImage resultat = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int index = y * width + x;
                
                if(affectations[index] == indexClusterCible) {
                    resultat.setRGB(x, y, imageOriginale.getRGB(x, y));
                } else {
                    resultat.setRGB(x, y, fondClair.getRGB(x, y));
                }
            }
        }
        return resultat;
    }
}