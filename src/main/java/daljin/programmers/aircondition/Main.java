package daljin.programmers.aircondition;

public class Main {


  private static final int[] temperature = {
      28, -10, 11, 11
  };
  private static final int[] t1 = {
      18, -5, 8, 8
  };
  private static final int[] t2 = {
      26, 5, 10, 10
  };
  private static final int[] a = {
      10, 5, 10, 10
  };
  private static final int[] b = {
      8, 1, 1, 100
  };
  private static final int[][] onboard = {
      {0, 0, 1, 1, 1, 1, 1},
      {0, 0, 0, 0, 0, 1, 0},
      {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
      {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1}
  };
  private static final int[] result = {
      40, 25, 20, 60
  };

  public static void main(String[] args) {
    Solution s = new Solution();
    for (int i = 0; i < 4; i++) {
      int r = s.solution(
          temperature[i],
          t1[i],
          t2[i],
          a[i],
          b[i],
          onboard[i]
      );
      if (r == result[i]) {
        System.out.println("#" + (i + 1) + " 번째 통과");
      } else {
        System.out.println("#" + (i + 1) + " 번째 실패 " + "[" + "기대값: " + result[i] + "/결과: " + r + "]");
      }
    }
  }
}
