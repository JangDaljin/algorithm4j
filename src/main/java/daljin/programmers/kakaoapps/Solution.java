package daljin.programmers.kakaoapps;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Solution {

  private static final int[][] DIRECTIONS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
  private static final int X1 = 0;
  private static final int Y1 = 1;
  private static final int X2 = 2;
  private static final int Y2 = 3;


  public int[][] solution(int[][] board, int[][] commands) {

    int[][] apps = getApps(board);

    for (int[] command : commands) {
      int appId = command[0];
      int[] direction = DIRECTIONS[command[1] - 1];
      int dx = direction[0];
      int dy = direction[1];

      move(board, apps, appId, dx, dy);
    }

    return board;
  }

  private void move(int[][] board, int[][] apps, int appId, int dx, int dy) {

    int[] curApp = apps[appId - 1];


    for (int y = curApp[Y1]; y <= curApp[Y2]; y++) {
      for (int x = curApp[X1]; x <= curApp[X2]; x++) {

        int ny = Math.floorMod(y, board.length);
        int nx = Math.floorMod(x, board[0].length);

        if (board[ny][nx] == appId) {
          board[ny][nx] = 0;
        }
      }
    }

    Set<Integer> nextAppIds = new TreeSet<>();
    for (int y = curApp[Y1]; y <= curApp[Y2]; y++) {
      for (int x = curApp[X1]; x <= curApp[X2]; x++) {

        int ny = Math.floorMod(y + dy, board.length);
        int nx = Math.floorMod(x + dx, board[0].length);
        int nAppId = board[ny][nx];

        board[ny][nx] = appId;
        if (nAppId != 0 && nAppId != appId) {
          nextAppIds.add(nAppId);
        }
      }
    }

    // 갱신
    apps[appId - 1][X1] += dx;
    apps[appId - 1][Y1] += dy;
    apps[appId - 1][X2] += dx;
    apps[appId - 1][Y2] += dy;

    // 다음 것 처리
    for (int nextAppId : nextAppIds) {
      move(board, apps, nextAppId, dx, dy);
    }

    // 한번 더 실행
    if (Math.floorMod(apps[appId - 1][X1], board[0].length) > Math.floorMod(apps[appId - 1][X2],
        board[0].length)
        || Math.floorMod(apps[appId - 1][Y1], board.length) > Math.floorMod(apps[appId - 1][Y2],
            board.length)) {
      move(board, apps, appId, dx, dy);
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
