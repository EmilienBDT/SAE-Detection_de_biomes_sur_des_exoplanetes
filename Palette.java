import java.awt.Color;

public class Palette {
    private Color[] couleurs;
    private NormeCouleurs norme;

    public Palette(Color[] couleurs, NormeCouleurs norme) {
        this.couleurs = couleurs;
        this.norme = norme;
    }

    public Color getPlusProche(Color c) {
        Color plusProche = couleurs[0];
        double minDistance = norme.distanceCouleur(c, plusProche);

        for (int i = 1; i < couleurs.length; i++) {
            double distance = norme.distanceCouleur(c, couleurs[i]);
            if (distance < minDistance) {
                minDistance = distance;
                plusProche = couleurs[i];
            }
        }
        return plusProche;
    }

    public Color[] getCouleurs() {
        return couleurs;
    }
}