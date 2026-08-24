package daljin.programmers.velocity;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Solution {

  public int[] solution(int[][] city, int[][] road) {

    List<Vertex> vs = new ArrayList<>();
    List<Edge> es = new ArrayList<>();

    int[] answer = new int[city.length - 1];
    return answer;
  }

  public void setGraph(int[][] city, int[][] road, List<Vertex> vs, List<Edge> es) {

    for (int i = 0; i < road.length; i++) {

      List<Vertex> tvs = new ArrayList<>();

      //city
      for (int j = 0; j < city.length; j++) {
        if ((road[i][0] <= city[j][0] && road[i][2] >= city[j][0]) ||
            (road[i][1] <= city[j][1] && road[i][3] >= city[j][1])) {
          tvs.add(new Vertex(city[j][0], city[j][1]));
        }
      }

      //road
      for (int j = 0; j < road.length; j++) {
        if (i == j) {
          continue;
        }

        if((road[i][0] != road[j][0]))

      }
    }
  }

  public class Vertex {

    public int x;
    public int y;

    public Vertex(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  public class Edge {

    public int v1;
    public int v2;
    public int limit;

    public Edge(int v1, int v2, int limit) {
      this.v1 = v1;
      this.v2 = v2;
      this.limit = limit;
    }
  }


}
