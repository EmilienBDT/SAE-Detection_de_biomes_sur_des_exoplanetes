public class OutilCouleur {
    public static int[] getTabColor(int c) {
        int blue = c & 0xff;
        int green = (c & 0xff00) >> 8;
        int red = (c & 0xff0000) >> 16;
        return new int[]{red, green, blue};
    }

    public static int getIntColor(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }
}