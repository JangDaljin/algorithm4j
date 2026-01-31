package daljin.knapsack.baekjoon2629;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  public static void main(String[] args) throws IOException {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int N = Integer.parseInt(br.readLine());
    int[] weights = new int[N];
    int weightSum = 0;

    String[] weightStrings = br.readLine().trim().split(" ");
    for (int i = 0; i < weights.length; i++) {
      weights[i] = Integer.parseInt(weightStrings[i]);
      weightSum += weights[i];
    }

    int M = Integer.parseInt(br.readLine());
    int[] beads = new int[M];

    String[] beadStrings = br.readLine().trim().split(" ");
    for (int i = 0; i < M; i++) {
      beads[i] = Integer.parseInt(beadStrings[i]);
    }

    boolean[] dp = new boolean[weightSum * 2 + 1];
    dp[weightSum] = true;

    boolean[] next;
    int w;

    for (int i = 0; i < N; i++) {
      w = weights[i];
      next = new boolean[weightSum * 2 + 1];
      for (int j = 0; j < weightSum * 2 + 1; j++) {
        next[j] = dp[j];
      }

      for (int j = 0; j < weightSum * 2 + 1; j++) {

        if (!dp[j]) {
          continue;
        }

        if (j + w < weightSum * 2 + 1) {
          next[j + w] = true;
        }

        if (j - w >= 0) {
          next[j - w] = true;
        }
      }

      dp = next;
    }

    String[] results = new String[M];
    for (int i = 0; i < M; i++) {
      if (beads[i] > weightSum) {
        results[i] = "N";
        continue;
      }

      if (dp[weightSum + beads[i]]) {
        results[i] = "Y";
      } else {
        results[i] = "N";
      }
    }

    System.out.print(String.join(" ", results));
  }
}
