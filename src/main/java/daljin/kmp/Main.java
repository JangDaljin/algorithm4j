package daljin.kmp;

public class Main {

  static void main(String[] args) {

    String text = "ABC ABCDAB ABCDABCDABDE";
    String pattern = "ABCDABD";

    int[] failure = new int[pattern.length()];
    failure[0] = -1;

    for (int i = 1; i < pattern.length(); i++) {
      int target = failure[i - 1] + 1;
      char c = pattern.charAt(target);

      if(c == pattern.charAt(i)) {
        failure[i] =
      }
    }
  }


}