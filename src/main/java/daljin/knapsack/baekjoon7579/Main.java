package daljin.knapsack.baekjoon7579;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] NM = br.readLine().split(" ");

    int N = Integer.parseInt(NM[0]);
    int M = Integer.parseInt(NM[1]);

    int[] memories = new int[N];
    int[] costs = new int[N];
    int costSum = 0;

    String[] memoryStrings = br.readLine().split(" ");
    for (int i = 0; i < N; i++) {
      memories[i] = Integer.parseInt(memoryStrings[i]);
    }

    String[] costStrings = br.readLine().split(" ");
    for (int i = 0; i < N; i++) {
      costs[i] = Integer.parseInt(costStrings[i]);
      costSum += costs[i];
    }

    int[] dp = new int[costSum + 1];
    for (int i = 0; i < N; i++) {
      for (int j = costSum; j >= costs[i]; j--) {
        dp[j] = Math.max(dp[j], dp[j - costs[i]] + memories[i]);
      }
    }

    int answer = 10_000;
    for (int i = 0; i <= costSum; i++) {
      if (dp[i] >= M) {
        answer = Math.min(i, answer);
      }
    }

    System.out.print(answer);
  }
}
