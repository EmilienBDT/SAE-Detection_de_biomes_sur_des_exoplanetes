import java.awt.Color;

public class NormeRedmean implements NormeCouleurs {
    @Override
    public double distanceCouleur(Color c1, Color c2) {
        double rBar = (c1.getRed() + c2.getRed()) / 2.0;
        double deltaR = c1.getRed() - c2.getRed();
        double deltaG = c1.getGreen() - c2.getGreen();
        double deltaB = c1.getBlue() - c2.getBlue();

        double term1 = (2.0 + (rBar / 256.0)) * Math.pow(deltaR, 2);
        double term2 = 4.0 * Math.pow(deltaG, 2);
        double term3 = (2.0 + ((255.0 - rBar) / 256.0)) * Math.pow(deltaB, 2);

        return Math.sqrt(term1 + term2 + term3);
    }
}