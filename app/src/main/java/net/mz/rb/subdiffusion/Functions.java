package net.mz.rb.subdiffusion;

public class Functions {

    static double sigma1, sigma2, alpha, koeff, kBig;
    static int difSpeed, points, X, Y, radius, width, height;
    final static int PATTERN_ID = 60000;
    final static int EDIT_ID = 65000;
    final static int DELETE_ID = 70000;

    public static int[] getIntegerRandomMassive(int massiveLength) {
        int[] a = new int[massiveLength];
        for (int i = 0; i < massiveLength - 1; i++) {
            a[i] = ((int) (Math.random() * 1000 + 1));
        }
        return a;
    }

    public static int[][][] getIntegerRandomHyperMatrix(int matrixX, int matrixY, int matrixZ) {
        int[][][] a = new int[matrixX][matrixY][matrixZ];
        for (int i = 0; i < matrixX; i++) {
            for (int j = 0; j < matrixY; j++) {
                for (int g = 0; g < matrixZ; g++) {
                    a[i][j][g] = ((int) (Math.random() * 1000 + 1));
                }
            }
        }
        return a;
    }

    public static int[][] getIntegerRandomMatrix(int matrixX, int matrixY) {
        int[][] a = new int[matrixX][matrixY];
        for (int i = 0; i < matrixX; i++) {
            for (int j = 0; j < matrixY; j++) {
                a[i][j] = ((int) (Math.random() * 1000 + 1));
            }
        }
        return a;
    }

    static int getLengthOfVector() {
        double d = Math.random();
        int a;
        if (d < sigma1) a = 1;
        else if (d < sigma1 + sigma2) a = 2;
        else a = 3;
        return a;
    }

    static int getSomethingOfVector() {
        return (int) (Math.random() * 5) + 1;
    }

    public static double getNextMoment() {
        return Math.pow(kBig, 1 / alpha) * Math.pow(1 - Math.random(), -1 / alpha) - 1;
    }

    public static void calculateKBig() {
        kBig = (2 * Math.PI * alpha * (1 - 2 * sigma1 + 4 * sigma2)) / (koeff * Math.sin(Math.PI * alpha));
    }

    static int expAndRoot(int K) {
        return (int) Math.exp(Math.cbrt(K));
    }

    static int logAndPow(int K) {
        return (int) Math.pow(Math.log(K), 3);
    }

    static int calculateMaxPoints(int wOrH, int r) {
        return wOrH / (r * 2 + 1);
    }

    static boolean willPointWalk() {
        return (Math.random() < alpha * alpha * koeff / 100);
    }
}
