import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double thresholds[];
    private boolean initialized;
    private int trials;
    private double localMean;
    private double localStddev;

    /*Percolation percolation;
    int lastOpenRow;
    int lastOpenCol;*/

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        thresholds = new double[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(n);
            int cellsOpened = 0;
            do {
                boolean isRandomOk = false;
                int row = 0, col = 0;
                do {
                    row = StdRandom.uniform(n) + 1;
                    col = StdRandom.uniform(n) + 1;

                    if (!percolation.isOpen(row, col)) {
                        isRandomOk = true;
                    }
                } while (!isRandomOk);
                percolation.open(row, col);
                /*lastOpenRow = row;
                lastOpenCol = col;*/
                cellsOpened++;
            } while (!percolation.percolates());
            thresholds[i] = (double) cellsOpened/(n*n);
        }
        initialized = true;
        localMean = StdStats.mean(thresholds);
        localStddev = StdStats.stddev(thresholds);
    }

    // sample mean of percolation threshold
    public double mean() {
        if (!initialized)
            return 0.0;
        else {
            return localMean;
        }
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (!initialized)
            return 0.0;
        else
            return  localStddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return  (localMean - 1.96*localStddev/Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return  (localMean + 1.96*localStddev/Math.sqrt(this.trials));
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /*public void showThresholds() {
        for (int i = 0; i < thresholds.length; i++) {
            StdOut.print("Thresholds[" + i + "] = " + thresholds[i]);
            StdOut.print('\n');
        }
    }

    public void showGrid() {
        percolation.showGrid();
    }

    public void showLastOpen() {
        StdOut.print("[" + lastOpenRow + ", " + lastOpenCol + "]\n");
    }*/

    // test client (see below)
    public static void main(String[] args) {
        /*PercolationStats ps = new PercolationStats(10, 50);

        ps.showThresholds();

        StdOut.print("Mean = " + ps.mean());
        StdOut.print('\n');

        StdOut.print("Stddev = " + ps.stddev());
        StdOut.print('\n');

        StdOut.print("Intervals = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
        StdOut.print('\n');

        ps.showGrid();
        ps.showLastOpen();*/
    }
}

