package net.mz.rb.subdiffusion;

public class Matrix {
    private int widthHalf, heightHalf;
    private int[][] matrixBody;

    Matrix(int w, int h) {
        widthHalf = w;
        heightHalf = h;
        matrixBody = new int[2 * w + 1][2 * h + 1];
    }

    public void resetBeautifulMatrix() {
        for (int i = 0; i < 2 * widthHalf + 1; i++) {
            for (int j = 0; j < 2 * heightHalf + 1; j++) {
                matrixBody[i][j] = 0;
            }
        }
    }

    public void upgradeBeautifulMatrix(Point[] points) {
        this.resetBeautifulMatrix();
        for (Point point: points){
            matrixBody[point.getX() + widthHalf][heightHalf - point.getY()]++;
        }
    }

    public int[][] getMatrixBody() {
        return matrixBody;
    }
}
