package daljin.programmers.baseball;

import java.util.*;
import java.util.function.*;

class Solution {

  public int solution(int n, Function<Integer, String> submit) {

    Set<Integer> nums = new TreeSet<>();
    for (int i = 1; i < 10; i++) {
      nums.add(i);
    }
    Set<Integer> candidates = getCandidates(nums, new HashSet<>(), new ArrayList<>(), 0);
    int[] sb1 = transform(submit.apply(1234));

    if (sb1[0] + sb1[1] != 4) {
      for (int c : candidates) {

      }

      int[] sb2 = transform(submit.apply(5678));
      if (sb1[0] + sb1[1] + sb2[0] + sb2[1] != 4) {
        return 0;
      }
    }

    return 0;
  }


  private int[] round(int v1, int v2) {
    int[] vs1 = getValues(v1);
    int[] vs2 = getValues(v2);

    int strike = 0;
    int ball = 0;

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (i == j) {
          if (vs1[i] == vs2[j]) {
            strike++;
          }
        } else {
          if (vs1[i] == vs2[j]) {
            ball++;
          }
        }
      }
    }

    return new int[]{strike, ball};
  }


  private Set<Integer> getCandidates(Set<Integer> nums, Set<Integer> used, List<Integer> selected, int depth) {
    if (depth == 4) {
      Set<Integer> set = new TreeSet<>();
      set.add(getValue(selected));
      return set;
    }

    Set<Integer> acc = new TreeSet<>();
    for (int num : nums) {
      if (used.contains(num)) {
        continue;
      }

      used.add(num);
      selected.add(num);
      acc.addAll(getCandidates(nums, used, selected, depth + 1));
      selected.remove(selected.size() - 1);
      used.remove(num);
    }

    return acc;
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

  public int getValue(List<Integer> list) {
    return list.get(0) * 1000 +
        list.get(1) * 100 +
        list.get(2) * 10 +
        list.get(3);
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

  public int[] transform(String round) {
    int[] arr = new int[2];
    String[] sp = round.split(" ");
    arr[0] = Integer.parseInt(sp[0].substring(0, sp[0].length() - 1));
    arr[1] = Integer.parseInt(sp[1].substring(0, sp[1].length() - 1));
    return arr;
  }
}