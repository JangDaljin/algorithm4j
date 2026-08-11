package daljin.programmers;

import java.util.HashMap;
import java.util.Map;

public class Main {

  public static void main(String[] args) {
    Solution solution = new Solution();

    Map<int[][], Integer> gridMap = new HashMap<>();

    gridMap.put(
        new int[][]{
            new int[]{1, 0, -1},
            new int[]{0, 0, 7},
            new int[]{0, 0, 2}
        },
        2
    );
    gridMap.put(
        new int[][]{
            new int[]{1, 0, 0, 0, 0, -1, -1},
            new int[]{-1, 0, 0, 1, 0, 0, 1}
        },
        2
    );
    gridMap.put(
        new int[][]{
            new int[]{1, 0, 0, 0, 0},
            new int[]{0, 0, 3, 0, 2},
            new int[]{0, 0, 0, 0, 2}
        },
        4
    );

    gridMap.put(
        new int[][]{
            new int[]{1, 0, 0, 0},
            new int[]{0, 0, 0, 0},
            new int[]{0, 0, 0, 0},
            new int[]{0, 0, 0, 0},
            new int[]{0, 0, 0, 1}
        },
        644
    );
    gridMap.put(
        new int[][]{

            new int[]{1, 7},
            new int[]{0, 2}
        },
        1
    );
    gridMap.put(
        new int[][]{
            new int[]{1, -1, 0, 0},
            new int[]{-1, 0, 0, 0},
            new int[]{0, 0, 0, -1},
            new int[]{0, 0, -1, 1}
        },
        0
    );

    int[][][] grids = gridMap.keySet().toArray(new int[0][0][0]);
    for (int i = 0; i < grids.length; i++) {
      if (gridMap.get(grids[i]) == solution.solution(grids[i])) {
        System.out.print("#" + i + 1 + " 번째 통과");
      }
    }
  }
}
