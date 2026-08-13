package daljin.programmers.inspection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {

  private final static int EMPTY = 0;
  private final static int WALL = 1;
  private final static int ELEVATOR = 8;
  private final static int PANEL = 2;

  private final static int[][] DIRECTIONS = {
      {-1, 0},
      {0, 1},
      {1, 0},
      {0, -1}
  };
  private int answer = Integer.MAX_VALUE;

  public int solution(int h, String[] grid, int[][] panels, int[][] seqs) {
    answer = Integer.MAX_VALUE;

    int r = grid.length;
    int c = grid[0].length();

    int[] elevator = new int[2];
    int[][][] map = new int[h][r][c];
    for (int f = 0; f < h; f++) {
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[i].length(); j++) {
          if (grid[i].charAt(j) == '.') {
            map[f][i][j] = EMPTY;
          } else if (grid[i].charAt(j) == '#') {
            map[f][i][j] = WALL;
          } else if (grid[i].charAt(j) == '@') {
            map[f][i][j] = ELEVATOR;
            elevator[0] = i;
            elevator[1] = j;
          }
        }
      }
    }

    for (int[] panel : panels) {
      panel[0] -= 1;
      panel[1] -= 1;
      panel[2] -= 1;
      map[panel[0]][panel[1]][panel[2]] = PANEL;
    }

    List<List<Integer>> dag = new ArrayList<>();
    for (int i = 0; i < panels.length; i++) {
      dag.add(new ArrayList<>());
    }
    int[] indegree = new int[panels.length];

    for (int[] seq : seqs) {
      dag.get(seq[0] - 1).add(seq[1] - 1);
      indegree[seq[1] - 1]++;
    }

    //패널에서 엘레베이터 계산
    int[] eldist = new int[panels.length];
    for (int i = 0; i < panels.length; i++) {
      eldist[i] = bfs(map[panels[i][0]], r, c, new int[]{panels[i][1], panels[i][2]}, elevator);
    }

    //패널간 거리 계산
    int[][] dist = new int[panels.length][panels.length];
    for (int i = 0; i < panels.length; i++) {
      for (int j = 0; j < panels.length; j++) {
        //자기자신
        if (i == j) {
          dist[i][j] = 0;
          continue;
        }

        //동일 거리 복사
        if (dist[j][i] != 0) {
          dist[i][j] = dist[j][i];
          continue;
        }

        //층수가 다를 때
        if (panels[i][0] != panels[j][0]) {
          int a = eldist[i];
          int b = eldist[j];
          int d = Math.abs(panels[i][0] - panels[j][0]);
          dist[i][j] = a + b + d;
        }

        //층수가 같을 때
        else {
          dist[i][j] = bfs(map[panels[i][0]], r, c, new int[]{panels[i][1], panels[i][2]}, new int[]{panels[j][1], panels[j][2]});
        }
      }
    }

    dfs(0, dist, dag, indegree, new int[panels.length], 0);

    return answer;
  }

  private int bfs(int[][] map, int r, int c, int[] src, int[] des) {
    Queue<int[]> queue = new LinkedList<>();
    int[][] visited = new int[r][c];
    int cnt = -1;

    int[] cur;
    int y;
    int x;
    int acc;
    int nx;
    int ny;

    visited[src[0]][src[1]] = 1;
    queue.add(new int[]{src[0], src[1], 0});

    while (!queue.isEmpty()) {
      cur = queue.remove();

      y = cur[0];
      x = cur[1];
      acc = cur[2];

      if (des[0] == y && des[1] == x) {
        cnt = acc;
        break;
      }

      for (int[] d : DIRECTIONS) {
        ny = y + d[0];
        nx = x + d[1];
        if (ny >= 0
            && nx >= 0
            && ny <= r - 1
            && nx <= c - 1
            && map[ny][nx] != WALL
            && visited[ny][nx] != 1) {
          visited[ny][nx] = 1;
          queue.add(new int[]{ny, nx, acc + 1});
        }
      }
    }
    return cnt;
  }

  private void dfs(int cur, int[][] dist, List<List<Integer>> dag, int[] indegree, int[] visited, int acc) {

    List<Integer> curInds = new ArrayList<>();
    for (int i = 0; i < indegree.length; i++) {
      if (indegree[i] == 0 && visited[i] == 0) {
        curInds.add(i);
      }
    }

    if (curInds.isEmpty()) {
      answer = Math.min(answer, acc);
    }

    for (int curInd : curInds) {
      List<Integer> nexts = dag.get(curInd);

      visited[curInd]++;
      for (int n : nexts) {
        indegree[n]--;
      }

      dfs(curInd, dist, dag, indegree, visited, acc + dist[cur][curInd]);
      for (int n : nexts) {
        indegree[n]++;
      }
      visited[curInd]--;
    }
  }
}
