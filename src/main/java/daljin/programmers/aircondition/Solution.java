package daljin.programmers.aircondition;

import java.util.Arrays;

class Solution {

  private int[][] dp = new int[1001][51];

  public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {

    temperature += 10;
    t1 += 10;
    t2 += 10;

    dp = new int[onboard.length][51];
    for (int[] r : dp) {
      Arrays.fill(r, Integer.MAX_VALUE);
    }
    dfs(temperature, t1, t2, a, b, onboard, 0, temperature, 0);

    int answer = Integer.MAX_VALUE;
    for (int i = 0; i < 51; i++) {
      answer = Math.min(answer, dp[onboard.length - 1][i]);
    }
    return answer;
  }


  private void dfs(int out, int t1, int t2, int a, int b, int[] onboard, int m, int in, int acc) {

    if (in < 0 || in >= 51) {
      return;
    }

    if (m == onboard.length) {
      return;
    }

    if (onboard[m] == 1 && (in < t1 || in > t2)) {
      return;
    }

    if (dp[m][in] != Integer.MAX_VALUE && dp[m][in] <= acc) {
      return;
    }

    dp[m][in] = acc;

    //에어컨 종료
    if (in < out) {
      dfs(out, t1, t2, a, b, onboard, m + 1, in + 1, acc);
    } else if (in > out) {
      dfs(out, t1, t2, a, b, onboard, m + 1, in - 1, acc);
    } else {
      dfs(out, t1, t2, a, b, onboard, m + 1, in, acc);
    }

    //에어컨 동작 + 희망온도
    dfs(out, t1, t2, a, b, onboard, m + 1, in, acc + b);

    //에어컨 동작 + 희망온도
    if (in > 0) {
      dfs(out, t1, t2, a, b, onboard, m + 1, in - 1, acc + a);
    }

    if (in < 50) {
      dfs(out, t1, t2, a, b, onboard, m + 1, in + 1, acc + a);
    }
  }

}