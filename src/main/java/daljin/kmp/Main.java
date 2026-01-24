package daljin.kmp;

public class Main {

  static void main(String[] args) {

    String text = "ABC ABCDAB ABCDABCDABDE";
    String pattern = "ABCDABD";

    int[] failure = new int[pattern.length()];
    failure[0] = -1;

    for (int i = 1; i < pattern.length(); i++) {
      char now = pattern.charAt(i);
      char before = pattern.charAt(failure[i - 1] + 1);

      if(before == now) {
        failure[i] = failure[i - 1] + 1;
      } else {
        failure[i] = -1;
      }
    }


    int i = 0;
    int j = 0;
    while(i < text.length() && j < pattern.length()) {
      if(text.charAt(i) == pattern.charAt(j)) {
        j++;
      } else {
        if( j != 0 ) {
          j = failure[j - 1] + 1;
          continue;
        }
      }
      i++;
    }

    if( j == pattern.length()) {
      System.out.println("Pattern found at index: " + (i - pattern.length()));
    } else  {
      System.out.println("Pattern not found");
    }
  }


}