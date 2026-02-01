package daljin.knapsack.baekjoon1106;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  public static void main(String[] args) throws IOException {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] CN = br.readLine().trim().split(" ");

    int C = Integer.parseInt(CN[0]);
    int N = Integer.parseInt(CN[1]);

    int[] costs = new int[N];
    int[] humans = new int[N];

    String[] CH;
    for (int i = 0; i < N; i++) {
      CH = br.readLine().trim().split(" ");
      costs[i] = Integer.parseInt(CH[0]);
      humans[i] = Integer.parseInt(CH[1]);
    }

    int[] dp = new int[1000 * 100 + 1];
    for (int i = 1; i < dp.length; i++) {
      for (int j = 0; j < N; j++) {
        if (i < costs[j]) {
          continue;
        }
        dp[i] = Math.max(dp[i - costs[j]] + humans[j], dp[i]);
      }

      if (dp[i] >= C) {
        System.out.print(i);
        return;
      }
    }
  }
}
