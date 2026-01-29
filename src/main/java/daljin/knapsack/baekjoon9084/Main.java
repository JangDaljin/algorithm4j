package daljin.knapsack.baekjoon9084;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

  public static void main(String[] args) throws IOException {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int T = Integer.parseInt(br.readLine());
    int[] results = new int[T];

    int N;
    int[] coins;
    int[] dp;

    for (int i = 0; i < T; i++) {
      //N
      N = Integer.parseInt(br.readLine());

      //coins
      coins = new int[N];
      String[] coinStrings = br.readLine().split(" ");
      for (int j = 0; j < N; j++) {
        coins[j] = Integer.parseInt(coinStrings[j]);
      }

      //M
      int M = Integer.parseInt(br.readLine());

      //dp
      dp = new int[M + 1];
      dp[0] = 1;
      for (int coin : coins) {
        for (int k = coin; k <= M; k++) {
          dp[k] += dp[k - coin];
        }
      }

      results[i] = dp[M];
    }

    //print
    System.out.print(
        String.join("\n",
            IntStream.of(results)
                .mapToObj(v -> String.valueOf(v))
                .collect(Collectors.toList())));
  }
}
