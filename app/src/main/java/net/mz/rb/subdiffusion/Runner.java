package net.mz.rb.subdiffusion;

public class Runner extends Thread {
    private int K, borderTop, borderLeft;
    private Point[] points;

    Runner(int k, int bTop, int bLeft) {
        K = k;
        borderTop = bTop;
        borderLeft = bLeft;
        points = new Point[K];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(borderTop, borderLeft);
        }
    }

    void getSelfRightPoints() {
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(borderTop, borderLeft);
        }
    }

    public void run(Matrix matrix) {
        while (true) {
            int i = 0;
            for (Point point : points) {
                point.walk();
                if (point.isEnded()) i++;
            }
            matrix.upgradeBeautifulMatrix(this.giveMassiveOfPoints());
            if (i == points.length) return;
        }
    }

    public Point[] giveMassiveOfPoints() {
        return points;
    }

    public void AlternateRun() {
        for (Point point : points) {
            point.walk();
        }
    }
}
