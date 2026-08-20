package daljin.programmers.baseball;


import java.util.concurrent.atomic.AtomicInteger;

public class Main {

  private static final int[] n = {
      3024,
      3024,
      33
  };

  private static final int[] result = {
      1357,
      3986,
      7685
  };


  public static void main(String[] args) {

    Solution sol = new Solution();

    for (int i = 0; i < 3; i++) {
      int r = result[i];

      int[] rs = splitInteger(r);

      AtomicInteger called = new AtomicInteger();

      int res = sol.solution(n[i], value -> {
        int v = value;
        int[] vs = splitInteger(v);

        int st = 0;
        int ba = 0;

        for (int a = 0; a < 4; a++) {
          for (int b = 0; b < 4; b++) {
            if (rs[a] == vs[b]) {
              if (a == b) {
                st++;
              } else {
                ba++;
              }
            }
          }
        }

        called.getAndIncrement();
        return st + "S" + " " + ba + "B";
      });

      if (called.get() <= n[i] && r == res) {
        System.out.println("#" + (i + 1) + " Success [" + called + "/" + n[i] + "]");
      } else {
        System.out.println("#" + (i + 1) + " Fail [target: " + res + " result: " + r + "][" + called + "/" + n[i] + "]");
      }
    }
  }

  private static int[] splitInteger(int value) {
    int v = value;
    int len = 4;
    int[] r = new int[len];
    while (len > 0) {
      int cur = v % 10;
      r[len - 1] = cur;
      v /= 10;
      len--;
    }
    return r;
  }
}
