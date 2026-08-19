package daljin.programmers.baseball;

import java.sql.Array;
import java.util.*;
import java.util.function.*;

class Solution {

  public int solution(int n, Function<Integer, String> submit) {

    int[] values = new int[4];
    int idx = 0;
    //모든 숫자 동일하게 확인
    for (int i = 1; i < 10; i++) {
      String round = submit.apply(fill(i));
      int[] sb = extract(round);
      int s = sb[0];
      int b = sb[1];

      if (s + b == 4) {
        values[idx++] = i;
      }
      //미리 채워졌으면 종료
      if (idx == 4) {
        break;
      }
    }
    if (idx != 4) {
      for (int i = 0; i < 4 - idx; i++) {
        values[idx++] = 0;
      }
    }

    int[] selected = new int[4];
    for (int i = 0; i < 4; i++) {
      selected[i] = -1;
    }
    boolean[] used = new boolean[4];

    List<Integer> list = new ArrayList<>();
    selected[0] = 1;
    used[0] = true;

    getConfusion(values, used, selected, 0, list);

    return 0;
  }

  private void getConfusion(int[] values, boolean[] used, int[] selected, int depth, List<Integer> acc) {

    if (values.length == depth) {
      acc.add(getValue(selected));
      return;
    }

    if (selected[depth] != -1) {
      getConfusion(values, used, selected, depth + 1, acc);
      return;
    }

    for (int i = 0; i < values.length; i++) {
      if (used[i]) {
        continue;
      }

      selected[depth] = values[i];
      used[i] = true;
      getConfusion(values, used, selected, depth + 1, acc);
      used[i] = false;
      selected[depth] = -1;
    }
  }

  public int fill(int v) {
    int r = 0;
    for (int i = 0; i < 4; i++) {
      r += v * (int) Math.pow(10, i);
    }
    return r;
  }

  public int getValue(int[] values) {
    return values[0] * 1000 +
        values[1] * 100 +
        values[2] * 10 +
        values[3];
  }

  public int[] getValues(int value) {
    int v = value;
    int[] values = new int[4];
    for (int i = 3; i >= 0; i--) {
      values[i] = v % 10;
      v /= 10;
    }
    return values;
  }

  public void swap(List<Integer> t, int i, int j) {
    int temp = t.get(i);
    t.set(i, t.get(j));
    t.set(j, temp);
  }

  public int[] extract(String round) {
    int[] arr = new int[2];
    String[] sp = round.split(" ");
    arr[0] = Integer.parseInt(sp[0].substring(0, sp[0].length() - 1));
    arr[1] = Integer.parseInt(sp[1].substring(0, sp[1].length() - 1));
    return arr;
  }
}