import java.awt.Color;

public class NormeEuclidienne implements NormeCouleurs {
    @Override
    public double distanceCouleur(Color c1, Color c2) {
        double deltaR = c1.getRed() - c2.getRed();
        double deltaG = c1.getGreen() - c2.getGreen();
        double deltaB = c1.getBlue() - c2.getBlue();
        return Math.sqrt(deltaR*deltaR + deltaG*deltaG + deltaB*deltaB);
    }
}