package daljin.programmers.treasrue;

import java.sql.Array;
import java.util.function.*;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

  private int[] depth;
  private int money;
  private Function<Integer, Integer> excavate;

  public int solution(int[] depth, int money, Function<Integer, Integer> excavate) {
    this.depth = depth;
    this.money = money;
    this.excavate = excavate;

    List<List<Integer>> result = new ArrayList<>();
    combinations(
        Arrays.stream(depth)
            .boxed()
            .collect(Collectors.toCollection(ArrayList::new)),
        new ArrayList<>(),
        0, result);

    return 0;
  }


  public void combinations(List<Integer> selectable, List<Integer> accDepth, int accMoney, List<List<Integer>> result) {

    int s;
    for (int i = 0; i < selectable.size(); i++) {
      s = selectable.get(i);

      int nextAccMoney = accMoney + s;
      if (nextAccMoney > money) {
        continue;
      }

      accDepth.add(s);

      //현재
      result.add(accDepth);

      //오른쪽
      List<Integer> right = new ArrayList<>();
      for (int j = i + 1; j < selectable.size(); j++) {
        right.add(selectable.get(j));
      }
      combinations(right, new ArrayList<>(accDepth), accMoney, result);

      //왼쪽
      List<Integer> left = new ArrayList<>();
      for (int j = 0; j < i - 1; j++) {
        left.add(selectable.get(j));
      }
      combinations(left, new ArrayList<>(accDepth), accMoney, result);
    }
  }

}
