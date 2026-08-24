package daljin.programmers.baseball;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

class Solution {


  public int solution(int n, Function<Integer, String> submit) {

    //9자리수 모든 수 생성
    Set<Integer> nums = new TreeSet<>();
    for (int i = 1; i < 10; i++) {
      nums.add(i);
    }
    Set<Integer> candidates = getCandidates(nums, new HashSet<>(), new ArrayList<>(), 0);

    //1234제출
    int[] sb1 = transform(submit.apply(1234));
    if (sb1[0] + sb1[1] != 4) {
      candidates = candidates.stream().filter(it -> {
            int[] sb2 = round(1234, it);
            return sb1[0] == sb2[0] && sb1[1] == sb2[1];
          })
          .collect(Collectors.toCollection(
              TreeSet::new));

      //5678제출
      int[] sb2 = transform(submit.apply(5678));
      candidates = candidates.stream().filter(it -> {
        int[] sb3 = round(5678, it);
        return sb2[0] == sb3[0] && sb2[1] == sb3[1];
      }).collect(Collectors.toCollection(TreeSet::new));

      //9를 사용해야 하는지 여부
      boolean need9 = sb1[0] + sb1[1] + sb2[0] + sb2[1] != 4;
      candidates = candidates.stream().filter(it -> {
        int c = it;
        while (c != 0) {
          if (c % 10 == 9) {
            return need9;
          }
          c /= 10;
        }
        return true;
      }).collect(Collectors.toCollection(TreeSet::new));
    }

    return tracking(n, submit, candidates);
  }

  private int tracking(int n, Function<Integer, String> submit, Set<Integer> candidates) {
    if (n < 0) {
      return 0;
    }

    int c = selectQuestion(candidates);
    candidates.remove(c);

    if (candidates.isEmpty()) {
      return c;
    }

    int[] result = transform(submit.apply(c));
    if (result[0] == 4) {
      return c;
    }

    Set<Integer> nextCandidates = candidates.stream().filter(
        it -> {
          int[] sb = round(c, it);
          return sb[0] == result[0] && sb[1] == result[1];
        }
    ).collect(Collectors.toCollection(TreeSet::new));

    return tracking(n - 1, submit, nextCandidates);
  }

  private int selectQuestion(Set<Integer> candidates) {
    int bestQuestion = 0;
    int minValue = Integer.MAX_VALUE;
    List<Integer> questions = new ArrayList<>(candidates);
    for (int c : questions) {
      int worstValue = getWorstest(c, candidates);

      if (minValue > worstValue) {
        minValue = worstValue;
        bestQuestion = c;
      }
    }

    return bestQuestion;
  }

  private int getWorstest(int question, Set<Integer> candidates) {
    int maxValue = 0;
    int[][] acc = new int[5][5];
    for (int c : candidates) {
      int[] sb = round(question, c);
      acc[sb[0]][sb[1]] += 1;
      if (acc[sb[0]][sb[1]] > maxValue) {
        maxValue = acc[sb[0]][sb[1]];
      }
    }
    return maxValue;
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