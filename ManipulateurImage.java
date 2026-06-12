import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ManipulateurImage {

    public static BufferedImage chargerImage(String chemin) throws IOException {
        return ImageIO.read(new File(chemin));
    }

    public static void sauverImage(BufferedImage image, String format, String chemin) throws IOException {
        ImageIO.write(image, format, new File(chemin));
    }

    public static BufferedImage appliquerNiveauGris(BufferedImage source) {
        BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                int[] rgb = OutilCouleur.getTabColor(source.getRGB(x, y));
                int gris = (rgb[0] + rgb[1] + rgb[2]) / 3;
                dest.setRGB(x, y, OutilCouleur.getIntColor(gris, gris, gris));
            }
        }
        return dest;
    }

    public static BufferedImage isolerRouge(BufferedImage source) {
        BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                int color = source.getRGB(x, y);
                dest.setRGB(x, y, color & 0xff0000); // Garde uniquement le rouge
            }
        }
        return dest;
    }

    public static BufferedImage isolerVertBleu(BufferedImage source) {
        BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                int color = source.getRGB(x, y);
                dest.setRGB(x, y, color & 0x00ffff); // Garde vert (00ff00) et bleu (0000ff)
            }
        }
        return dest;
    }

    public static BufferedImage appliquerPalette(BufferedImage source, Palette palette) {
        BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                int[] rgb = OutilCouleur.getTabColor(source.getRGB(x, y));
                Color couleurPixel = new Color(rgb[0], rgb[1], rgb[2]);
                Color plusProche = palette.getPlusProche(couleurPixel);
                dest.setRGB(x, y, plusProche.getRGB());
            }
        }
        return dest;
    }

    public static BufferedImage copierImage(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();
        BufferedImage copie = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                copie.setRGB(x, y, source.getRGB(x, y));
            }
        }
        return copie;
    }

    // filtre gaussiens
    public static BufferedImage FiltreConvolution(BufferedImage source, int[][] matrice, int somme) {

        BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        
        int tailleMatrice = matrice.length;
        int offset = tailleMatrice / 2;

        // parcours de l'image
        for (int x = offset; x < source.getWidth() - offset; x++) {
            for (int y = offset; y < source.getHeight() - offset; y++) {
                
                int r = 0, g = 0, b = 0;
                
               // parcours de la matrice
                for (int i = -offset; i <= offset; i++) {
                    for (int j = -offset; j <= offset; j++) {
                        
                        int rgb = source.getRGB(x + i, y + j);
                        
                        int pixelR = (rgb >> 16) & 0xFF;
                        int pixelG = (rgb >> 8) & 0xFF;
                        int pixelB = rgb & 0xFF;
                        
                        // Récupération du coefficient
                        int coef = matrice[i + offset][j + offset];
                        
                        r += pixelR * coef;
                        g += pixelG * coef;
                        b += pixelB * coef;
                    }
                }
                
                
                r = r / somme;
                g = g / somme;
                b = b / somme;
                
                r = Math.min(Math.max(r, 0), 255);
                g = Math.min(Math.max(g, 0), 255);
                b = Math.min(Math.max(b, 0), 255);
                
                int newRgb = (255 << 24) | (r << 16) | (g << 8) | b;
                
                dest.setRGB(x, y, newRgb);
            }
        }

        return dest;
    }
}
