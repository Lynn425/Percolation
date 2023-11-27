import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF ds;
    private WeightedQuickUnionUF ds2;
    private int open;
    private int n;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[N][N];
        ds = new WeightedQuickUnionUF(N * N + 2);
        ds2 = new WeightedQuickUnionUF(N * N + 2);
        open = 0;
        n = N;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
    }

    private int xyTo1D(int r, int c) {
        return (r * n + c + 1);
    }

    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;
        open++;
        if (row == 0) {
            ds.union(0, col + 1);
            ds2.union(0, col + 1);
        }
        if (row == n - 1) {
            ds2.union(n * n + 1, xyTo1D(row, col));
        }
        if (n != 1) {
            connectLR(row, col);
            connectUD(row, col);
        }
    }

    public void connectLR(int row, int col) {
        if (col == 0) {
            if (grid[row][1]) {
                ds.union(xyTo1D(row, 0), xyTo1D(row, 1));
                ds2.union(xyTo1D(row, 0), xyTo1D(row, 1));
            }
        } else if (col == n - 1) {
            if (grid[row][n - 2]) {
                ds.union(xyTo1D(row, n - 1), xyTo1D(row, n - 2));
                ds2.union(xyTo1D(row, n - 1), xyTo1D(row, n - 2));
            }
        } else {
            if (grid[row][col - 1]) {
                ds.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                ds2.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (grid[row][col + 1]) {
                ds.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                ds2.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
    }

    public void connectUD(int row, int col) {
        if (row == 0) {
            if (grid[1][col]) {
                ds.union(xyTo1D(0, col), xyTo1D(1, col));
                ds2.union(xyTo1D(0, col), xyTo1D(1, col));
            }
        } else if (row == n - 1) {
            if (grid[n - 2][col]) {
                ds.union(xyTo1D(n - 1, col), xyTo1D(n - 2, col));
                ds2.union(xyTo1D(n - 1, col), xyTo1D(n - 2, col));
            }
        } else {
            if (grid[row - 1][col]) {
                ds.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                ds2.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (grid[row + 1][col]) {
                ds.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                ds2.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return ds.connected(xyTo1D(row, col), 0);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return open;
    }

    public boolean percolates() {
        return ds2.connected(n * n + 1, 0);
    }
}
