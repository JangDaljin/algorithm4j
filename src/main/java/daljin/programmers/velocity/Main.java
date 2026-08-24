package daljin.programmers.velocity;

public class Main {

  private static final int[][][] city = {
      {{-1, 3}, {7, 3}, {1, -1}, {-2, 6}},
      {{3, 5}, {3, 3}, {2, 1}, {9, 1}, {7, -1}}
  };

  private static final int[][][] road = {
      {{-1, 7, 7, 7, 80}, {-3, 3, 9, 3, 45}, {-2, -4, -2, 6, 60}, {1, -4, 1, 8, 50}, {5, 1, 5, 7, 70}},
      {{3, -2, 3, 4, 30}, {5, 1, 9, 1, 29}, {3, 4, 3, 8, 99}, {1, 1, 5, 1, 99}, {7, -3, 7, 5, 99}}
  };

  private static final int[][] result = {
      {70, 50, 0},
      {0, 30, 29, 29}
  };


  public static void main(String[] args) {
    Solution s = new Solution();

    for (int i = 0; i < 2; i++) {
      int[] r = s.solution(city[i], road[i]);

      if (r.length == result[i].length) {
        for (int j = 0; j < r.length; j++) {
          if (r[j] != result[i][j]) {
            System.out.println("#" + (i + 1) + " Fail");
            return;
          }
        }
        System.out.println("#" + (i + 1) + " Success");
      } else {
        System.out.println("#" + (i + 1) + " Fail");
      }
    }
  }
}
