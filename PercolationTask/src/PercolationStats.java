import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double thresholds[];
    private boolean initialized;
    private int trials;

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
                    row = StdRandom.uniform(n);
                    col = StdRandom.uniform(n);

                    if (!percolation.isOpen(row, col)) {
                        isRandomOk = true;
                    }
                } while (!isRandomOk);
                percolation.open(row, col);
                cellsOpened++;
            } while (!percolation.percolates());
            thresholds[i] = (double) cellsOpened/(n*n);
        }
        initialized = true;
    }

    // sample mean of percolation threshold
    public double mean() {
        if (!initialized)
            return 0.0;
        else
            return  StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (!initialized)
            return 0.0;
        else
            return  StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return  (mean() - 1.96*stddev()/Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return  (mean() + 1.96*stddev()/Math.sqrt(this.trials));
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   /* public void showThresholds() {
        for (int i = 0; i < thresholds.length; i++) {
            StdOut.print("Thresholds[" + i + "] = " + thresholds[i]);
            StdOut.print('\n');
        }
    }*/

    // test client (see below)
    public static void main(String[] args) {/*
        PercolationStats ps = new PercolationStats(10, 50);

        ps.showThresholds();

        StdOut.print("Mean = " + ps.mean());
        StdOut.print('\n');

        StdOut.print("Stddev = " + ps.stddev());
        StdOut.print('\n');

        StdOut.print("Intervals = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
        StdOut.print('\n');*/
    }
}

