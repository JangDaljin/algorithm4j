package daljin.programmers.inspection;

public class Main {

  public final static int[] h = {
      3, 1, 4
  };
  public final static String[][] grid = {
      {".#.##..", ".#..##.", ".......", "##.###.", ".@.#...", "...#..."},
      {"@......", ".######", ".......", "######.", ".......", ".####..", "....#.."},
      {"........#", "........#", "@.......#", ".#.#....#", "........#", "#........", "#.#..####", "..#..####", ".....####"}
  };
  public final static int[][][] panels = {
      {{2, 3, 4}, {2, 5, 6}, {1, 1, 1}, {3, 6, 3}},
      {{1, 7, 4}, {1, 3, 5}, {1, 1, 3}},
      {{2, 9, 1}, {2, 1, 8}, {1, 1, 3}, {3, 3, 2}, {1, 2, 8}}
  };
  public final static int[][][] seqs = {
      {{3, 2}, {1, 2}, {4, 1}, {4, 3}},
      {{1, 3}, {3, 2}},
      {{1, 2}, {2, 3}, {3, 4}, {4, 5}}
  };
  public final static int[] result = {
      36, 31, 47
  };

  public static void main(String[] args) {
    Solution solution = new Solution();

    for (int i = 0; i < 3; i++) {
      int r = solution.solution(h[i], grid[i], panels[i], seqs[i]);
      if (r == result[i]) {
        System.out.println("#" + (i + 1) + " 번째 통과");
      } else {
        System.out.println("#" + (i + 1) + " 번째 실패 " + "[" + "기대값: " + result[i] + "/결과: " + r + "]");
      }
    }

  }
}
