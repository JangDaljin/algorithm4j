package daljin.programmers.inspection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {

  private final static int EMPTY = 0;
  private final static int WALL = 1;
  private final static int ELEVATOR = 8;
  private final static int PANEL = 2;

  private int answer = 0;

  public int solution(int h, String[] grid, int[][] panels, int[][] seqs) {

    int r = grid.length;
    int c = grid[0].length();

    int[][][] map = new int[h][r][c];
    for (int f = 0; f < h; f++) {
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[i].length(); j++) {
          if (grid[i].charAt(j) == '.') {
            map[f][i][j] = EMPTY;
          } else if (grid[i].charAt(j) == '#') {
            map[f][i][j] = WALL;
          } else if (grid[i].charAt(j) == '@') {
            map[f][i][j] = ELEVATOR;
          }
        }
      }
    }

    for (int[] panel : panels) {
      map[panel[0] - 1][panel[1] - 1][panel[2] - 1] = PANEL;
    }

    List<List<Integer>> dag = new ArrayList<>();
    for (int i = 0; i < panels.length; i++) {
      dag.add(new ArrayList<>());
    }
    int[] indegree = new int[panels.length];

    for (int[] seq : seqs) {
      dag.get(seq[0] - 1).add(seq[1] - 1);
      indegree[seq[1] - 1]++;
    }

    Queue<int[]> queue = new LinkedList<>();
    queue.add(panels[0]);

    int depth = -1;
    int current = panels[0];
    int[] next;
    while (!queue.isEmpty()) {
      next = queue.remove();

      //없으면 다음 depth추가
      if (queue.isEmpty()) {
        depth++;
        for (int i = 0; i < indegree.length; i++) {
          if (indegree[i] == depth) {
            queue.add(panels[i]);
          }
        }
      }
    }

    return 0;
  }
}
