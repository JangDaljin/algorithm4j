package daljin.kmp.baekjoon1786;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    String text = reader.readLine();
    String pattern = reader.readLine();

    int[] pi = new int[pattern.length()];
    pi[0] = 0;
    char now;
    char before;
    int j;
    for (int i = 1; i < pattern.length(); i++) {
      now = pattern.charAt(i);
      before = pattern.charAt(pi[i - 1]);

      if (now == before) {
        pi[i] = pi[i - 1] + 1;
      } else {
        j = pi[i - 1];
        while (true) {
          if (j == 0) {
            pi[i] = 0;
            break;
          }
          j = pi[j - 1];
          before = pattern.charAt(j);
          if (now == before) {
            pi[i] = j + 1;
            break;
          }
        }
      }
    }

    int count = 0;
    List<Integer> indices = new ArrayList<>();

    int n = 0;
    int m = 0;
    while (n < text.length()) {
      if (text.charAt(n) == pattern.charAt(m)) {
        m++;
        n++;

        if (m == pattern.length()) {
          count++;
          indices.add(n - m + 1);
          m = pi[m - 1];
        }
        continue;
      }

      if (m == 0) {
        n++;
        continue;
      }

      m = pi[m - 1];
    }

    System.out.println(count);
    System.out.print(
        String.join(" ", indices.stream().map(String::valueOf).toArray(String[]::new)));
  }
}