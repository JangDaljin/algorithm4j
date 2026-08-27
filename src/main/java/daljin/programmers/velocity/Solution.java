package daljin.programmers.velocity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Solution {

  public static String getStrPos(int[] pos) {
    return "x" + pos[0] + "y" + pos[1];
  }

  public int[] solution(int[][] city, int[][] road) {

    Map<String, Vertex> map = getMap(city, road);

    int[] answer = route(city, map);
    return answer;
  }

  public Map<String, Vertex> getMap(int[][] city, int[][] road) {
    Map<String, Vertex> map = new HashMap<>();
    for (int i = 0; i < road.length; i++) {
      Map<String, Vertex> vMap = new HashMap<>();

      //city
      for (int j = 0; j < city.length; j++) {
        if ((road[i][0] <= city[j][0] && city[j][0] <= road[i][2]) &&
            (road[i][1] <= city[j][1] && city[j][1] <= road[i][3])) {
          Vertex v = new Vertex(city[j][0], city[j][1], Integer.MAX_VALUE);
          vMap.compute(v.getKey(), (k, it) -> {
            if (it == null) {
              return v;
            }
            it.limit = Math.min(it.limit, v.limit);
            return it;
          });
        }
      }

      //끝점
      Vertex v1 = new Vertex(road[i][0], road[i][1], Integer.MAX_VALUE);
      Vertex v2 = new Vertex(road[i][2], road[i][3], Integer.MAX_VALUE);
      vMap.compute(v1.getKey(), (k, it) -> {
        if (it == null) {
          return v1;
        }
        it.limit = Math.min(it.limit, v1.limit);
        return it;
      });
      vMap.compute(v2.getKey(), (k, it) -> {
        if (it == null) {
          return v2;
        }
        it.limit = Math.min(it.limit, v2.limit);
        return it;
      });

      //road
      for (int j = 0; j < road.length; j++) {
        if (i == j) {
          continue;
        }

        if (
          //가로형
            road[i][1] == road[i][3] &&
                //대상 세로형
                road[j][0] == road[j][2] &&
                //교차점 확인
                road[i][0] <= road[j][0] && road[j][2] <= road[i][2] &&
                road[j][1] <= road[i][1] && road[i][3] <= road[j][3]) {
          Vertex v = new Vertex(road[j][0], road[i][1], Integer.MAX_VALUE);
          vMap.compute(v.getKey(), (k, it) -> {
            if (it == null) {
              return v;
            }
            it.limit = Math.min(it.limit, v.limit);
            return it;
          });
        } else if (
          //세로형
            road[i][0] == road[i][2] &&
                //대상 가로형
                road[j][1] == road[j][3] &&
                //교차점 확인
                road[i][1] <= road[j][1] && road[j][3] <= road[i][3] &&
                road[j][0] <= road[i][0] && road[i][2] <= road[j][2]) {
          Vertex v = new Vertex(road[i][0], road[j][1], Integer.MAX_VALUE);
          vMap.compute(v.getKey(), (k, it) -> {
            if (it == null) {
              return v;
            }
            it.limit = Math.min(it.limit, v.limit);
            return it;
          });
        }
      }

      //중앙
      Vertex v = new Vertex((road[i][0] + road[i][2]) / 2, (road[i][1] + road[i][3]) / 2, road[i][4] == 0 ? Integer.MAX_VALUE : road[i][4]);
      vMap.compute(v.getKey(), (k, it) -> {
        if (it == null) {
          return v;
        }
        it.limit = Math.min(it.limit, v.limit);
        return it;
      });

      //정렬
      List<Vertex> vs = new ArrayList<>();
      for (Vertex tv : vMap.values()) {
        vs.add(map.getOrDefault(tv.getKey(), tv));
      }

      vs.sort(Comparator.comparing((Vertex it) -> it.x).thenComparing(it -> it.y));

      Vertex cur;
      Vertex before;
      Vertex after;
      for (int vsi = 0; vsi < vs.size(); vsi++) {
        cur = vs.get(vsi);

        before = null;
        if (vsi != 0) {
          before = vs.get(vsi - 1);
        }
        if (before != null) {
          cur.link(before);
        }

        after = null;
        if (vsi != vs.size() - 1) {
          after = vs.get(vsi + 1);
        }
        if (after != null) {
          cur.link(after);
        }
      }

      for (Vertex c : vs) {
        map.compute(c.getKey(), (k, cv) -> {
          if (cv == null) {
            return c;
          }
          cv.limit = Math.min(cv.limit, c.limit);
          for (Vertex lc : c.links.values()) {
            cv.link(lc);
          }
          return cv;
        });
      }
    }
    return map;
  }

  public int[] route(int[][] city, Map<String, Vertex> map) {

    Map<String, Integer> maxVelocity = new HashMap<>();
    for (String k : map.keySet()) {
      maxVelocity.put(k, 0);
    }

    Queue<Item> q = new LinkedList<>();
    q.add(new Item(map.get(getStrPos(city[0])), map.get(getStrPos(city[0])).limit));
    while (!q.isEmpty()) {
      Item item = q.poll();

      int nextMaxVelocity;
      int nextVelocity;
      for (Vertex nextVertex : item.v.links.values()) {
        nextMaxVelocity = maxVelocity.get(nextVertex.getKey());
        nextVelocity = Math.min(item.minLimit, nextVertex.limit);

        if (Math.max(nextMaxVelocity, nextVelocity) == nextMaxVelocity) {
          continue;
        }

        maxVelocity.put(nextVertex.getKey(), nextVelocity);
        q.add(new Item(nextVertex, nextVelocity));
      }
    }

    int[][] targets = Arrays.copyOfRange(city, 1, city.length);
    int[] result = new int[targets.length];
    for (int i = 0; i < targets.length; i++) {
      int velocity = maxVelocity.get(getStrPos(targets[i]));
      if (velocity == Integer.MAX_VALUE) {
        result[i] = 0;
      } else {
        result[i] = velocity;
      }
    }

    return result;
  }

  public static class Item {

    Vertex v;
    int minLimit;

    public Item(Vertex v, int minLimit) {
      this.v = v;
      this.minLimit = minLimit;
    }
  }

  public static class Vertex {

    public int x;
    public int y;
    public int limit;
    public Map<String, Vertex> links = new HashMap<>();

    public Vertex(int x, int y, int limit) {
      this.x = x;
      this.y = y;
      this.limit = limit;
    }

    public void link(Vertex v) {
      links.put(v.getKey(), v);
    }

    public String getKey() {
      return getStrPos(new int[]{x, y});
    }
  }
}
