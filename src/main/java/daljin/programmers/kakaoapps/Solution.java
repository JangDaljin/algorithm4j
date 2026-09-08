package daljin.programmers.kakaoapps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Solution {
  public static class Delta {
    public int dx;
    public int dy;

    public Delta(int dx, int dy) {
      this.dx = dx;
      this.dy = dy;
    }
  }

  private static final int[][] DIRECTIONS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
  private static final int X1 = 0;
  private static final int Y1 = 1;
  private static final int X2 = 2;
  private static final int Y2 = 3;


  public int[][] solution(int[][] board, int[][] commands) {

    int[][] apps = getApps(board);

    Map<Integer, Delta> map = new TreeMap<>();
    for (int[] command : commands) {
      int appId = command[0];
      int[] direction = DIRECTIONS[command[1] - 1];
      int dx = direction[0];
      int dy = direction[1];
      if (!map.containsKey(appId)) {
        map.put(appId, new Delta(dx, dy));
      } else {
        Delta d = map.get(appId);
        d.dx += dx;
        d.dy += dy;
      }
      move(board, apps, appId, map, dx, dy);

      board = new int[board.length][board[0].length];
      for (int mapAppId = 1; mapAppId <= apps.length; mapAppId++) {
        for (int y = apps[mapAppId - 1][Y1]; y <= apps[mapAppId - 1][Y2]; y++) {
          for (int x = apps[mapAppId - 1][X1]; x <= apps[mapAppId - 1][X2]; x++) {
            board[y][x] = mapAppId;
          }
        }
      }

      for (int mapAppId : map.keySet()) {
        for (int y = apps[mapAppId - 1][Y1]; y <= apps[mapAppId - 1][Y2]; y++) {
          for (int x = apps[mapAppId - 1][X1]; x <= apps[mapAppId - 1][X2]; x++) {
            board[y][x] = 0;
          }
        }
      }

      for (int mapAppId : map.keySet()) {
        Delta delta = map.get(mapAppId);
        int x1 = Math.floorMod(apps[mapAppId - 1][X1] + delta.dx, board[0].length);
        int x2 = Math.floorMod(apps[mapAppId - 1][X2] + delta.dx, board[0].length);
        int y1 = Math.floorMod(apps[mapAppId - 1][Y1] + delta.dy, board.length);
        int y2 = Math.floorMod(apps[mapAppId - 1][Y2] + delta.dy, board.length);

        for (int y = y1; y <= y2; y++) {
          for (int x = x1; x <= x2; x++) {
            board[y][x] = mapAppId;
          }
        }
      }
    }



    return board;
  }

  private void move(int[][] board, int[][] apps, int appId, Map<Integer, Delta> map, int dx,
      int dy) {

    Delta curAppDelta = map.get(appId);

    int ndx = curAppDelta.dx;
    int curX1 = Math.floorMod(apps[appId - 1][X1] + ndx, board[0].length);
    int curX2 = Math.floorMod(apps[appId - 1][X2] + ndx, board[0].length);
    while (curX2 < curX1) {
      ndx += dx;
      curX1 = Math.floorMod(apps[appId - 1][X1] + ndx, board[0].length);
      curX2 = Math.floorMod(apps[appId - 1][X2] + ndx, board[0].length);
    }

    int ndy = curAppDelta.dy;
    int curY1 = Math.floorMod(apps[appId - 1][Y1] + ndy, board.length);
    int curY2 = Math.floorMod(apps[appId - 1][Y2] + ndy, board.length);
    while (curY2 < curY1) {
      ndy += dy;
      curY1 = Math.floorMod(apps[appId - 1][Y1] + ndy, board.length);
      curY2 = Math.floorMod(apps[appId - 1][Y2] + ndy, board.length);
    }

    curAppDelta.dx = ndx;
    curAppDelta.dy = ndy;


    List<Integer> nextApps = new ArrayList<>();
    for (int i = 0; i < apps.length; i++) {
      if (appId - 1 == i) {
        continue;
      }

      int nextAppDeltaDx;
      int nextAppDeltaDy;
      Delta nextAppDelta = map.get(i + 1);
      if (nextAppDelta == null) {
        nextAppDeltaDx = 0;
        nextAppDeltaDy = 0;
      } else {
        nextAppDeltaDx = nextAppDelta.dx;
        nextAppDeltaDy = nextAppDelta.dy;
      }

      int nextX1 = Math.floorMod(apps[i][X1] + nextAppDeltaDx, board[0].length);
      int nextX2 = Math.floorMod(apps[i][X2] + nextAppDeltaDx, board[0].length);
      int nextY1 = Math.floorMod(apps[i][Y1] + nextAppDeltaDy, board.length);
      int nextY2 = Math.floorMod(apps[i][Y2] + nextAppDeltaDy, board.length);

      if (((curX1 <= nextX1 && nextX1 <= curX2) && (curY1 <= nextY1 && nextY1 <= curY2))
          || ((curX1 <= nextX1 && nextX1 <= curX2) && (curY1 <= nextY2 && nextY2 <= curY2))
          || ((curX1 <= nextX2 && nextX2 <= curX2) && (curY1 <= nextY1 && nextY1 <= curY2))
          || ((curX1 <= nextX2 && nextX2 <= curX2) && (curY1 <= nextY2 && nextY2 <= curY2))) {

        if (ndx > 0) {
          nextAppDeltaDx = curX2 - nextX1 + 1;
        } else if (ndx < 0) {
          nextAppDeltaDx = nextX2 - curX1 - 1;
        } else if (ndy > 0) {
          nextAppDeltaDy = curY2 - nextY1 + 1;
        } else if (ndy < 0) {
          nextAppDeltaDy = nextY2 - curY1 - 1;
        }

        if (nextAppDelta == null) {
          map.put(i + 1, new Delta(nextAppDeltaDx, nextAppDeltaDy));
        } else {
          nextAppDelta.dx += nextAppDeltaDx;
          nextAppDelta.dy += nextAppDeltaDy;
        }

        nextApps.add(i + 1);
      }
    }

    for (int nextAppId : nextApps) {
      move(board, apps, nextAppId, map, dx, dy);
    }
  }

  public int[][] getApps(int[][] board) {
    Map<Integer, int[]> result = new TreeMap<>();

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {

        int appId = board[i][j];

        if (appId != 0 && !result.containsKey(appId)) {
          int y1 = i;
          int x1 = j;
          int y2 = y1;
          int x2 = x1;

          for (int y = y1; y < board.length; y++) {
            if (board[y][x1] != appId) {
              y2 = y - 1;
              break;
            }

            if (y == board.length - 1) {
              y2 = y;
              break;
            }
          }

          for (int x = x1; x < board[y2].length; x++) {
            if (board[y2][x] != appId) {
              x2 = x - 1;
              break;
            }

            if (x == board[y2].length - 1) {
              x2 = x;
              break;
            }
          }

          result.put(appId, new int[] {x1, y1, x2, y2});
        }
      }
    }


    int[][] r = new int[result.size()][4];
    for (int k : result.keySet()) {
      r[k - 1] = result.get(k);
    }
    return r;
  }
}
