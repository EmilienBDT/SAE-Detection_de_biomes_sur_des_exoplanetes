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
}