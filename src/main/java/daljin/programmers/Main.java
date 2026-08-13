package daljin.programmers;

public class Main {

  public static void main(String[] args) {
    Solution solution = new Solution();

    int[][][] grids = {
        {
            {1, 0, -1},
            {0, 0, 7},
            {0, 0, 2}
        },
        {
            {1, 0, 0, 0, 0, -1, -1},
            {-1, 0, 0, 1, 0, 0, 1}
        },
        {
            {1, 0, 0, 0, 0},
            {0, 0, 3, 0, 2},
            {0, 0, 0, 0, 2}
        },
        {
            {1, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 1},
        },
        {
            {1, 7},
            {0, 2}
        },
        {
            {1, -1, 0, 0},
            {-1, 0, 0, 0},
            {0, 0, 0, -1},
            {0, 0, -1, 1}
        }
    };

    int[] result = {
        2, 2, 4, 644, 1, 0
    };

    for (int i = 0; i < grids.length; i++) {
      int r = solution.solution(grids[i]);
      if (r == result[i]) {
        System.out.println("#" + (i + 1) + " 번째 통과");
      } else {
        System.out.println("#" + (i + 1) + " 번째 실패 " + "[" + "기대값: " + result[i] + "/결과: " + r + "]");
      }
    }
  }
}
