package daljin.kmp;

public class Main2 {

  public static void main(String[] args) {

    String T = "AABAABBBAABAABAABAA";
    String P = "AABAA";

    System.out.println("text:\t" + T);
    System.out.println("pattern:\t" + P);

    getKmp(T, P);

  }

  private static int[] getLps(String pattern) {

    char[] p = pattern.toCharArray();

    int[] lps = new int[pattern.length()];

    lps[0] = 0;
    int i = 1;
    int len = 0;

    final int m = p.length;

    while (i < m) {
      if (p[i] == p[len]) {
        len++;
        lps[i] = len;
        i++;
      } else {
        if (len != 0) {
          len = lps[len - 1];
        } else {
          lps[i] = 0;
          i++;
        }
      }
    }
    return lps;
  }

  private static void getKmp(String text, String pattern) {

    int[] lps = getLps(pattern);

    char[] t = text.toCharArray();
    char[] p = pattern.toCharArray();

    int i = 0;
    int j = 0;

    while (i < t.length) {
      if (t[i] == p[j]) {
        i++;
        j++;

        if (j == p.length) {
          System.out.println("index:\t" + (i - pattern.length()));
          j = lps[j - 1];
        }
      } else if (j != 0) {
        j = lps[j - 1];
      } else {
        i++;
      }
    }
  }
}
