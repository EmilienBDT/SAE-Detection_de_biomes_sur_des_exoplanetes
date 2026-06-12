import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
        public static void main(String[] args) {
                try {
                        // Créer le dossier output s'il n'existe pas
                        new File("output").mkdirs();

                        // Remplacer par le chemin de l'image
                        String inputPath = "input/Planete 1.jpg";
                        BufferedImage imageOriginale = ManipulateurImage.chargerImage(inputPath);

                        System.out.println("1. Test des filtres (Q1)");
                        ManipulateurImage.sauverImage(ManipulateurImage.appliquerNiveauGris(imageOriginale), "PNG",
                                        "output/gris.png");
                        ManipulateurImage.sauverImage(ManipulateurImage.isolerRouge(imageOriginale), "PNG",
                                        "output/rouge.png");
                        ManipulateurImage.sauverImage(ManipulateurImage.isolerVertBleu(imageOriginale), "PNG",
                                        "output/vertbleu.png");

                        System.out.println("2. Test de similarité simple (Q2.1)");
                        Color[] deuxCouleurs = { Color.GREEN, Color.YELLOW };
                        Palette paletteSimple = new Palette(deuxCouleurs, new NormeEuclidienne());
                        BufferedImage imageDeuxCouleurs = ManipulateurImage.appliquerPalette(imageOriginale,
                                        paletteSimple);
                        ManipulateurImage.sauverImage(imageDeuxCouleurs, "PNG", "output/deuxCouleurs.png");

                        System.out.println("3. Test extraction de palette (Q3)");
                        NormeCouleurs normeRedmean = new NormeRedmean();
                        // Demande 10 couleurs, avec une distance perceptuelle de 50 pour assurer la
                        // variété
                        Palette paletteExtraite = ExtracteurPalette.extraire(imageOriginale, 10, normeRedmean, 50.0);
                        BufferedImage imageReduite = ManipulateurImage.appliquerPalette(imageOriginale,
                                        paletteExtraite);
                        ManipulateurImage.sauverImage(imageReduite, "PNG", "output/imageReduite.png");

                        System.out.println("SAE P1. Test flou");

                        int[][][] matrices = {
                                        { { 1, 2, 1},
                                                        { 2, 4, 2 },
                                                        { 1, 2, 1 } },

                                        { { 1, 4, 7, 4, 1 },
                                                        { 4, 16, 26, 16, 4 },
                                                        { 7, 26, 41, 26, 7 },
                                                        { 4, 16, 26, 16, 4 },
                                                        { 1, 4, 7, 4, 1 }, },

                                        { { 0, 0, 1, 2, 1, 0, 0 },
                                                        { 0, 3, 13, 22, 13, 3, 0 },
                                                        { 1, 13, 59, 97, 59, 13, 1 },
                                                        { 2, 22, 97, 159, 97, 22, 2 },
                                                        { 1, 13, 59, 97, 59, 13, 1 },
                                                        { 0, 3, 13, 22, 13, 3, 0 },
                                                        { 0, 0, 1, 2, 1, 0, 0 },
                                        }
                        };

                        int[] somme = {16, 273, 1003 };

                        for (int i = 0; i < somme.length; i++) {
                                BufferedImage imageFloue = ManipulateurImage.FiltreConvolution(imageOriginale,
                                                matrices[i], somme[i]);
                                ManipulateurImage.sauverImage(imageFloue, "PNG", "output/imageFloue" + i + ".png");
                        }

                        System.out.println("Opérations terminées. Vérifiez le dossier 'output'.");

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}