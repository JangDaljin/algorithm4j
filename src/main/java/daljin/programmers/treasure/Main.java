package daljin.programmers.treasure;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Main {


  private final static int[][] depth = {
      {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
      {1, 1, 1, 1, 1},
      {2, 100, 1, 100, 3, 100, 1},
      {2, 100, 1, 100, 3, 100, 1},
      {3, 2, 1, 2, 3, 2, 1, 2},
      {1, 1000, 1, 1, 1, 10, 15, 1}
  };

  private final static int[] money = {55, 3, 200, 200, 8, 1002};

  private final static int[] result = {3, 5, 6, 5, 5, 2};


  public static void main(String[] args) {

    Solution sol = new Solution();

    AtomicInteger acc = new AtomicInteger();
    for (int i = 0; i < depth.length; i++) {
      final int cur = i;

      Function<Integer, Integer> excavate = col -> {
        acc.set(acc.get() + depth[cur][col - 1]);
        if (result[cur] == col) {
          return 0;
        } else if (result[cur] < col) {
          return -1;
        } else {
          return 1;
        }
      };

      int r = sol.solution(depth[i], money[i], excavate);

      if (r != result[cur]) {
        System.out.println("#" + (i + 1) + " Fail. 결과 다름");
        continue;
      }

      if (acc.get() > money[cur]) {
        System.out.println("#" + (i + 1) + " Fail. 비용 초과");
        continue;
      }

      System.out.println("#" + (i + 1) + " Success");
    }
  }

}
