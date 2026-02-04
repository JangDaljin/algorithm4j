package daljin.knapsack.baekjoon1535;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  public static void main(String[] args) throws IOException {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int N = Integer.parseInt(br.readLine().trim());
    int[] healthList = new int[N];
    int[] happinessList = new int[N];

    String[] healthStrings = br.readLine().trim().split(" ");
    for (int i = 0; i < healthList.length; i++) {
      healthList[i] = Integer.parseInt(healthStrings[i]);
    }

    String[] happinessStrings = br.readLine().trim().split(" ");
    for (int i = 0; i < happinessList.length; i++) {
      happinessList[i] = Integer.parseInt(happinessStrings[i]);
    }

    int[] dp = new int[100];
    for (int i = 0; i < N; i++) {
      for (int j = 99; j >= healthList[i]; j--) {
        dp[j] = Math.max(dp[j], dp[j - healthList[i]] + happinessList[i]);
      }
    }

    System.out.print(dp[99]);
  }
}
