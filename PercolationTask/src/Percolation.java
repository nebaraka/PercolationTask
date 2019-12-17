import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {

    private boolean grid[][];
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int n;
    private int numberOfOpenSites;
    private int[] topRadices;
    private int[] botRadices;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        else {
            this.n = n;
            weightedQuickUnionUF = new WeightedQuickUnionUF(this.n*this.n);
            topRadices = new int[n];
            botRadices = new int[n];
            numberOfOpenSites = 0;
            grid = new boolean[this.n][this.n];
            for (int i = 0; i < this.n; i++) {
                for (int j = 0; j < this.n; j++)
                    grid[i][j] = false;
                topRadices[i] = i;
                botRadices[i] = n*(n-1)+i;
            }

        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int stRow = row - 1, stCol = col - 1;
        if (stRow < 0 || stRow >= this.n || stCol < 0 || stCol >= this.n)
            throw new IllegalArgumentException();
        else {
            if (!isOpen(row, col)) {
                numberOfOpenSites++;
            }
            this.grid[stRow][stCol] = true;

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
        if (!isOpen(row, col)) {
            return false;
        }
        int stRow = row - 1, stCol = col - 1;
        if (stRow < 0 || stRow >= this.n || stCol < 0 || stCol >= this.n)
            throw new IllegalArgumentException();

        int rootP = weightedQuickUnionUF.find(stRow*(n) + stCol);
        /*for (int i = 0; i < n; i++) {
            if (isOpen(1,i+1)) topRadices[i] = weightedQuickUnionUF.find(i);
        }*/
        boolean isPrevOpen = false;
        for (int i = 0; i < n; i++) {
            if (isOpen(1,i+1)) {
                if (isPrevOpen) { continue; }
                topRadices[i] = weightedQuickUnionUF.find(i);
                if (rootP == topRadices[i]) {
                    return true;
                } else {
                    isPrevOpen = true;
                }
            } else {
                isPrevOpen = false;
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
        boolean isPrevOpenTop = false;
        for (int i = 0; i < n; i++) {
            if (isOpen(1,i+1)) {
                if (isPrevOpenTop) {
                    topRadices[i] = topRadices[i-1];
                    continue;
                }
                if ((n > 1 && !grid[1][i])) continue;
                topRadices[i] = weightedQuickUnionUF.find(i);
                isPrevOpenTop = true;
            }
            else
                isPrevOpenTop = false;
            //if (isOpen(n,i+1)) botRadices[i] = weightedQuickUnionUF.find(n*(n-1)+i);
        }
        boolean isPrevOpenBot = false;
        for (int i = 0; i < n; i++) {
            if (isOpen(n, i+1)) {
                if (isPrevOpenBot || (n > 1 && !grid[n-2][i])) { continue; }
                botRadices[i] = weightedQuickUnionUF.find(n*(n-1)+i);
                for (int j = 0; j < n; j++) {
                    if (isOpen(1,j+1)) {
                        if (botRadices[i] == topRadices[j])
                            return true;
                    }
                }
                isPrevOpenBot = true;
            } else {
                isPrevOpenBot = false;
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
        int N = 7;
        Percolation percolation = new Percolation(N);
        /*StdOut.print("Initialized grid\n");
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

        StdOut.print("IsFull (4,5):\n");
        StdOut.print(percolation.isFull(5, 6));
        StdOut.print('\n');

        StdOut.print("Opened (4,5):\n");
        percolation.open(5,6);

        StdOut.print("Opened (5,5) and (6,5):\n");
        percolation.open(6,6);
        percolation.open(7,6);

        percolation.open(7,4);
        percolation.open(6,4);
        percolation.open(6,3);
        percolation.open(6,2);
        percolation.open(7,2);*/

        percolation.open(1,1);
        percolation.open(2,1);
        percolation.open(3,1);
        percolation.open(4,1);

        percolation.open(4,2);
        percolation.open(4,3);

        percolation.open(3,3);
        percolation.open(2,3);

        percolation.open(2,4);
        percolation.open(2,5);

        percolation.open(3,5);
        percolation.open(4,5);
        percolation.open(5,5);

        percolation.open(5,6);

        percolation.open(5,7);
        percolation.open(6,7);
        percolation.open(7,7);

        StdOut.print("IsFull bottom:\n");
        StdOut.print(percolation.isFull(7, 7));
        StdOut.print('\n');

        showGrid(percolation, N);



        StdOut.print("Percolates2:\n");
        StdOut.print(percolation.percolates());
        StdOut.print('\n');
    }
}
