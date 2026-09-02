package daljin.programmers.treasure;

import java.util.Arrays;
import java.util.function.*;

public class Solution {

  private int[] depth;
  private int money;
  private Function<Integer, Integer> excavate;

  private int[][] dp;
  private int[][] choice;

  public int solution(int[] depth, int money, Function<Integer, Integer> excavate) {
    this.depth = depth;
    this.money = money;
    this.excavate = excavate;

    dp = new int[depth.length][depth.length];
    for (int[] arr : dp) {
      Arrays.fill(arr, -1);
    }
    choice = new int[depth.length][depth.length];
    for (int[] arr : choice) {
      Arrays.fill(arr, -1);
    }

    calDP(0, depth.length - 1);

    int l = 0;
    int r = depth.length - 1;
    while (true) {
      int k = choice[l][r];
      int res = excavate.apply(k + 1);
      switch (res) {
        case 0: {
          return k + 1;
        }
        case -1: {
          r = k - 1;
          break;
        }
        case 1: {
          l = k + 1;
          break;
        }
      }
    }
  }

  public int calDP(int l, int r) {
    if (l > r) {
      return 0;
    }

    if (dp[l][r] != -1) {
      return dp[l][r];
    }

    int best = Integer.MAX_VALUE;
    int bestK = 0;
    for (int k = l; k < r + 1; k++) {
      int cost = depth[k] + Math.max(calDP(l, k - 1), calDP(k + 1, r));

      if (best > cost) {
        best = cost;
        bestK = k;
      }
    }
    dp[l][r] = best;
    choice[l][r] = bestK;

    return dp[l][r];
  }
}
