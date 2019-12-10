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
        int stRow = row - 1, stCol = col - 1;
        if (stRow < 0 || stRow >= this.n || stCol < 0 || stCol >= this.n)
            throw new IllegalArgumentException();
        else {
            this.grid[stRow][stCol] = true;
            if (isOpen(row, col)) {
                numberOfOpenSites++;
            }

            if (stRow > 0 && grid[stRow-1][stCol]) {
                weightedQuickUnionUF.union(stRow*n+stCol, (stRow-1)*n+stCol);
            }
            if (stCol > 0 && grid[stRow][stCol-1]) {
                weightedQuickUnionUF.union(stRow*n+stCol, stRow*n+(stCol-1));
            }
            if (stRow < this.n - 1 && grid[stRow+1][stCol]) {
                weightedQuickUnionUF.union(stRow*n+stCol, (stRow+1)*n+stCol);
            }
            if (stCol < this.n - 1 && grid[stRow][stCol+1]) {
                weightedQuickUnionUF.union(stRow*n+stCol, stRow*n+(stCol+1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int stRow = row - 1, stCol = col - 1;
        if (stRow < 0 || stRow >= this.n || stCol < 0 || stCol >= this.n)
            throw new IllegalArgumentException();
        else {
            return this.grid[stRow][stCol];
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int stRow = row - 1, stCol = col - 1;
        if (stRow < 0 || stRow >= this.n || stCol < 0 || stCol >= this.n)
            throw new IllegalArgumentException();
        for (int i = 0; i < n; i++) {
            if (isOpen(row, col) && weightedQuickUnionUF.connected(stRow*n+stCol, i)) {
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
        for (int i = 1; i <= n; i++) {
            if (isFull(n, i)) {
                return true;
            }
        }
        return false;
    }

    private static void showGrid(Percolation percolation, int N) {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <=  N; j++) {
                StdOut.print(percolation.isOpen(i, j));
                StdOut.print(' ');
            }
            StdOut.print('\n');
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        /*int N = 1;
        Percolation percolation = new Percolation(N);
        StdOut.print("Initialized grid\n");
        showGrid(percolation, N);
        StdOut.print("Opened (0,4):\n");
        percolation.open(1,1);
        showGrid(percolation, N);
        StdOut.print(percolation.isFull(1,1) + "\n\n");*/
        /*int N = 7;
        Percolation percolation = new Percolation(N);
        StdOut.print("Initialized grid\n");
        showGrid(percolation, N);


        StdOut.print("Opened (0,4):\n");
        percolation.open(1,5);
        showGrid(percolation, N);

        StdOut.print("Opened (1,4):\n");
        percolation.open(2,5);
        showGrid(percolation, N);

        StdOut.print("Opened (2,4):\n");
        percolation.open(3,5);
        showGrid(percolation, N);

        StdOut.print("Opened (4,4):\n");
        percolation.open(5,5);
        showGrid(percolation, N);


        StdOut.print("IsFull (2,4):\n");
        StdOut.print(percolation.isFull(3, 5));
        StdOut.print('\n');

        StdOut.print("IsFull (4,4):\n");
        StdOut.print(percolation.isFull(5, 5));
        StdOut.print('\n');

        StdOut.print("Opened (3,4):\n");
        percolation.open(4,5);

        StdOut.print("IsFull (4,4):\n");
        StdOut.print(percolation.isFull(5, 5));
        StdOut.print('\n');

        StdOut.print("Percolates1:\n");
        StdOut.print(percolation.percolates());
        StdOut.print('\n');

        StdOut.print("Opened (5,4) and (6,4):\n");
        percolation.open(6,5);
        percolation.open(7,5);
        showGrid(percolation, N);

        StdOut.print("Percolates2:\n");
        StdOut.print(percolation.percolates());
        StdOut.print('\n');*/
    }
}
