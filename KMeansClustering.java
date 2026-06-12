import java.util.ArrayList;
import java.util.List;

public class KMeansClustering implements AlgorithmeClustering{
    private int[][] centroides= new int[0][0];

    public void setCentroides(int[][] centroides) {
        this.centroides = centroides;
    }

    public int[][] getCentroides() {
        return centroides;
    }
    
    public int[] executer(int[][] t2d, int k){
        int[][] centroides = new int[k][3];
        for (int i = 0; i < k; i++) {
        centroides[i] = t2d[(int) (Math.random() * t2d.length)].clone();
        }
        boolean fini = false;
        int[] clusters = new int[t2d.length];
        while(!fini){
            List<List<int[]>> groupes = new ArrayList<>();
            for(int i=0;i<k;i++){
                groupes.add(new ArrayList<>());
            }
            fini=true;
            for(int i=0;i<t2d.length;i++){
                int[] donnee = t2d[i];
                int indexCentroide = indiceCentroidePlusProche(donnee, centroides);

                if(clusters[i] != indexCentroide){
                    fini = false;
                    clusters[i] = indexCentroide;
                }
                groupes.get(indexCentroide).add(donnee);
            }
            if (!fini){
                for(int i=0;i<k;i++) centroides[i] = barycentre(groupes.get(i), 3);
            }
        }
        setCentroides(centroides);
        return clusters;
    }

    private int indiceCentroidePlusProche(int[] donnee, int[][] centroides) {
        int indiceMin = 0;
        int distanceMin = Integer.MAX_VALUE;
        
        for (int i = 0; i < centroides.length; i++) {
            int distance = calculerDistance(donnee, centroides[i]);
            if (distance < distanceMin) {
                distanceMin = distance;
                indiceMin = i;
            }
        }
        return indiceMin;
    }

    private int[] barycentre(List<int[]> objetsDuCluster, int nbCaracteristiques) {
        int[] nouveauCentre = new int[nbCaracteristiques];
        int nbObjets = objetsDuCluster.size();

        if (nbObjets == 0) return nouveauCentre;
        for (int[] objet : objetsDuCluster) {
            for (int i = 0; i < nbCaracteristiques; i++) {
                nouveauCentre[i] += objet[i];
            }
        }

        for (int i = 0; i < nbCaracteristiques; i++) {
            nouveauCentre[i] /= nbObjets;
        }
        return nouveauCentre;
    }

    private int calculerDistance(int[] p1, int[] p2) {
        int somme = 0;
        for (int i = 0; i < p1.length; i++) {
            somme += Math.pow(p1[i] - p2[i], 2);
        }
        return (int) Math.sqrt(somme);
    }
}
