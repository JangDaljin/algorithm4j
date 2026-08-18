package daljin.programmers.baseball;

import java.util.*;
import java.util.function.*;

class Solution {

  public int solution(int n, Function<Integer, String> submit) {

    List<Integer> selectable = new ArrayList<>();

    //모든 숫자 동일하게 확인
    for (int i = 1; i < 10; i++) {
      String res = submit.apply(fill(i));
      if (!res.equals("0S 0B")) {
        selectable.add(i);
      }
      //미리 채워졌으면 종료
      if (selectable.size() == 4) {
        break;
      }
    }
    if (selectable.size() != 4) {
      for (int i = 0; i < 4 - selectable.size(); i++) {
        selectable.add(0);
      }
    }

    int[] arr = selectable.stream().toList().toArray(new Integer[0]);
    Map<Integer, String> map = new HashMap<>();

    return 0;
  }

  public int fill(int v) {
    int r = 0;
    for (int i = 0; i < 4; i++) {
      r += v * (int) Math.pow(10, i);
    }
    return r;
  }

  public void swap(int[] arr, int i, int j) {
    int t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
  }
}