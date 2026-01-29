package daljin.knapsack.baekjoon12865;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String line = br.readLine();
    String[] spLine = line.trim().split(" ");

    int n = Integer.parseInt(spLine[0]);
    int k = Integer.parseInt(spLine[1]);

    int[] weights = new int[n];
    int[] values = new int[n];

    for (int i = 0; i < n; i++) {
      String weightAndValue = br.readLine();
      String[] spWeightAndValue = weightAndValue.trim().split(" ");
      weights[i] = Integer.parseInt(spWeightAndValue[0]);
      values[i] = Integer.parseInt(spWeightAndValue[1]);
    }

    int[] dp = new int[k + 1];

    for (int i = 0; i < n; i++) {
      int w = weights[i];
      int v = values[i];

      for (int j = k; j >= w; j--) {
        dp[j] = Math.max(dp[j - w] + v, dp[j]);
      }
    }

    System.out.print(dp[k]);
  }

}
