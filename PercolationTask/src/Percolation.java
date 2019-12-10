import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {

    private boolean grid[][];
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int n;
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        else {
            this.n = n;
            weightedQuickUnionUF = new WeightedQuickUnionUF(this.n*this.n);
            numberOfOpenSites = 0;
            grid = new boolean[this.n][this.n];
            for (int i = 0; i < this.n; i++)
                for (int j = 0; j < this.n; j++)
                    grid[i][j] = false;

        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > this.n || col < 0 || col > this.n)
            throw new IllegalArgumentException();
        else {
            this.grid[row][col] = true;
            numberOfOpenSites++;

            if (row > 0 && grid[row-1][col]) {
                weightedQuickUnionUF.union(row*n+col, (row-1)*n+col);
            }
            if (col > 0 && grid[row][col-1]) {
                weightedQuickUnionUF.union(row*n+col, row*n+(col-1));
            }
            if (row < this.n - 1 && grid[row+1][col]) {
                weightedQuickUnionUF.union(row*n+col, (row+1)*n+col);
            }
            if (col < this.n - 1 && grid[row][col+1]) {
                weightedQuickUnionUF.union(row*n+col, row*n+(col+1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > this.n || col < 0 || col > this.n)
            throw new IllegalArgumentException();
        else {
            return this.grid[row][col];
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        for (int i = 0; i < n-1; i++) {
            if (weightedQuickUnionUF.connected(row*n+col, i)) {
                return  true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < n; i++) {
            if (isFull(n-1, i)) {
                return true;
            }
        }
        return false;
    }

    private static void showGrid(Percolation percolation, int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <  N; j++) {
                StdOut.print(percolation.isOpen(i, j));
                StdOut.print(' ');
            }
            StdOut.print('\n');
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        int N = 7;
        Percolation percolation = new Percolation(N);
        StdOut.print("Initialized grid\n");
        showGrid(percolation, N);


        StdOut.print("Opened (0,4):\n");
        percolation.open(0,4);
        showGrid(percolation, N);

        StdOut.print("Opened (1,4):\n");
        percolation.open(1,4);
        showGrid(percolation, N);

        StdOut.print("Opened (2,4):\n");
        percolation.open(2,4);
        showGrid(percolation, N);

        StdOut.print("Opened (4,4):\n");
        percolation.open(4,4);
        showGrid(percolation, N);


        StdOut.print("IsFull (2,4):\n");
        StdOut.print(percolation.isFull(2, 4));
        StdOut.print('\n');

        StdOut.print("IsFull (4,4):\n");
        StdOut.print(percolation.isFull(4, 4));
        StdOut.print('\n');

        StdOut.print("Opened (3,4):\n");
        percolation.open(3,4);

        StdOut.print("IsFull (4,4):\n");
        StdOut.print(percolation.isFull(4, 4));
        StdOut.print('\n');


        StdOut.print("Opened (5,4) and (6,4):\n");
        percolation.open(5,4);
        percolation.open(6,4);
        showGrid(percolation, N);

        StdOut.print("Percolates:\n");
        StdOut.print(percolation.percolates());
        StdOut.print('\n');
    }
}
