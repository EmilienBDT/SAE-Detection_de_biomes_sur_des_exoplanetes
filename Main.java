import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            // Créer le dossier output s'il n'existe pas
            new File("output").mkdirs();

            // Remplacer par le chemin de l'image
            String inputPath = "input/originale.jpg"; 
            BufferedImage imageOriginale = ManipulateurImage.chargerImage(inputPath);

            System.out.println("1. Test des filtres (Q1)");
            ManipulateurImage.sauverImage(ManipulateurImage.appliquerNiveauGris(imageOriginale), "PNG", "output/gris.png");
            ManipulateurImage.sauverImage(ManipulateurImage.isolerRouge(imageOriginale), "PNG", "output/rouge.png");
            ManipulateurImage.sauverImage(ManipulateurImage.isolerVertBleu(imageOriginale), "PNG", "output/vertbleu.png");

            System.out.println("2. Test de similarité simple (Q2.1)");
            Color[] deuxCouleurs = {Color.GREEN, Color.YELLOW};
            Palette paletteSimple = new Palette(deuxCouleurs, new NormeEuclidienne());
            BufferedImage imageDeuxCouleurs = ManipulateurImage.appliquerPalette(imageOriginale, paletteSimple);
            ManipulateurImage.sauverImage(imageDeuxCouleurs, "PNG", "output/deuxCouleurs.png");

            System.out.println("3. Test extraction de palette (Q3)");
            NormeCouleurs normeRedmean = new NormeRedmean();
            // Demande 10 couleurs, avec une distance perceptuelle de 50 pour assurer la variété
            Palette paletteExtraite = ExtracteurPalette.extraire(imageOriginale, 10, normeRedmean, 50.0);
            BufferedImage imageReduite = ManipulateurImage.appliquerPalette(imageOriginale, paletteExtraite);
            ManipulateurImage.sauverImage(imageReduite, "PNG", "output/imageReduite.png");

            System.out.println("Opérations terminées. Vérifiez le dossier 'output'.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}