package daljin.programmers;

import java.util.LinkedList;
import java.util.Queue;

class Solution {

  private static class Node {

    int x;
    int y;
    int d;
    int[][] g;

    public Node(
        int x,
        int y,
        int d,
        int[][] g
    ) {
      this.x = x;
      this.y = y;
      this.d = d;
      this.g = g;
    }

    public int getX() {
      return this.x;
    }

    public void setX(int x) {
      this.x = x;
    }

    public int getY() {
      return this.y;
    }

    public void setY(int y) {
      this.y = y;
    }

    public int getD() {
      return this.d;
    }

    public void setD(int d) {
      this.d = d;
    }

    public int[][] getG() {
      return this.g;
    }

    public void setG(int[][] g) {
      this.g = g;
    }
  }


  public final static int[][] RAILS = {
      {0, 0, 0, 0}, // 장애물
      {0, 1, 0, 1},
      {1, 0, 1, 0},
      {1, 1, 1, 1},
      {1, 0, 0, 1},
      {1, 1, 0, 0},
      {0, 1, 1, 0},
      {0, 0, 1, 1}
  };

  public int solution(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;

    Queue<Node> queue = new LinkedList<>();
    queue.add(new Node(1, 0, 1, copyGrid(n, m, grid)));

    while (!queue.isEmpty()) {
      Node nextNode = queue.remove();
      Node[] nodes = createNextNodes(n, m, nextNode);

      for (Node node : nodes) {
        queue.add(node);
      }
    }

    int answer = 0;
    return answer;
  }

  private int[][] copyGrid(int n, int m, int[][] grid) {
    int[][] g = new int[n][m];

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].lennth; j++) {
        g[i][j] = grid[i][j];
      }
    }

    return g;
  }

  private Node[] createNextNodes(int n, int m, Node node) {
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (node.getG()[i][j] == 0) {
          node.setY(i);
          node.setX(j);
          node.setD(node.getD() + 1);

          int[][] g = node.getG();

          for (int k = 0; k < RAILS.length; k++) {
            if (
                isPuttable(rail, j, i, n, m) &&
                    (x != 0 && validLeft(rail, g[i][j - 1])) &&
                    (y != 0 && validTop(rail, g[i - 1][j])) &&
                    (x != n - 1 && validRight(rail, g[i][j + 1])) &&
                    (y != n - 1 && validBottom(rail, g[i + 1][j]))
            ) {
              g[y][x] = k + 1;
            }
          }

          return new Node[0];
        }
      }
    }

    //할 수 있는게 없다면 폐기대상
    return null;
  }

  private boolean isPuttable(int[] rail, int x, int y, int n, int m) {
    if (x == 0 && rail[3] == 1) {
      return false;
    }

    if (y == 0 && rail[0] == 1) {
      return false;
    }

    if (x == n - 1 && rail[1] == 1) {
      return false;
    }

    if (y == m - 1 && rail[2] == 1) {
      return false;
    }

    return true;
  }

  private boolean validRight(int[] target, int[] right) {
    return target != null && right != null && target[1] != 0 && right[3] != 0;
  }

  private boolean validLeft(int[] target, int[] left) {
    return target != null && left != null && target[3] != 0 && left[1] != 0;
  }

  private boolean validTop(int[] target, int[] top) {
    return target != null && top != null && target[0] != 0 && top[2] != 0;
  }

  private boolean validDown(int[] target, int[] bottom) {
    return target != null && bottom != null && target[2] != 0 && bottom[0] != 0;
  }
}