
import java.awt.image.BufferedImage;
import java.io.File;

public class MainFlou {
    public static void main(String[] args) {
        try {
            // Créer le dossier output s'il n'existe pas
            new File("output").mkdirs();
            new File("output/Planete1").mkdirs();

            // Remplacer par le chemin de l'image
            String inputPath = "input/Planete 1.jpg";
            BufferedImage imageOriginale = ManipulateurImage.chargerImage(inputPath);

            System.out.println("SAE P1. Test flou");

            int[][][] matrices = {
                    { { 1, 2, 1 },
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
                    },
                    {

                            { 0, -1, 0 },
                            { -1, 5, -1 },
                            { 0, -1, 0 } },
                    {

                            { -1, -1, -1 },
                            { -1, 8, -1 },
                            { -1, -1, -1 }
                    }

            };

            int[] somme = { 16, 273, 1003, 1, 1 };

            for (int i = 0; i < somme.length; i++) {
                BufferedImage imageFloue = ManipulateurImage.FiltreConvolution(imageOriginale,
                        matrices[i], somme[i]);
                ManipulateurImage.sauverImage(imageFloue, "PNG", "output/Planete1/imageFloue" + i + ".png");
            }

            System.out.println("Opérations terminées. Vérifiez le dossier 'output'.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}