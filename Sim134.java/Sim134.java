import java.util.Random;
import java.awt.Color;

public class Sim134 {
    private int[][] grid;
    private int size;
    private Picture pic;

    public Sim134(int size) {
        this.size = size;
        grid = new int[size][size];
        int scale = 10;
        pic = new Picture(size * scale, size * scale);
        initializeGrid();
    }

    private void initializeGrid() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = random.nextBoolean() ? 1 : 0;
            }
        }

    }

    private void updateGrid() {
        int[][] newGrid = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);

                if (grid[i][j] == 1) { // alive cell
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newGrid[i][j] = 0; // cell dies
                    } else {
                        newGrid[i][j] = 1; // cell lives
                    }
                } else { // dead cell
                    if (liveNeighbors == 3) {
                        newGrid[i][j] = 1; // Cell birth
                    }

                }

            }
        }

        grid = newGrid; // update grid for next iteration
    }

    private int countLiveNeighbors(int x, int y) {
        int liveCount = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue; // skip self
                int newX = (x + i + size) % size;
                int newY = (y + j + size) % size;

                liveCount += grid[newX][newY];
            }
        }

        return liveCount;
    }

    public void startSimulation() {
        while (true) {
            updateGrid();
            displayGrid();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayGrid() {
        int scale = 10;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Color color = (grid[i][j] == 1) ? Color.BLACK : Color.WHITE;

                for (int p = 0; p < scale; p++) {
                    for (int q = 0; q < scale; q++) {
                        int x = i * scale + p;
                        int y = j * scale + q;
                        pic.set(x, y, color);
                    }
                }
            }
        }
        pic.show(); // Display grid
    }

    public static void main(String[] args) {
        Sim134 trial = new Sim134(80);
        trial.startSimulation();
    }
}