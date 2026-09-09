package daljin.programmers.kakaoapps;

import java.util.Map;
import java.util.TreeMap;

public class Solution {

  private static final int[][] DIRECTIONS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
  private static final int X1 = 0;
  private static final int Y1 = 1;
  private static final int X2 = 2;
  private static final int Y2 = 3;
  private static final int DX = 4;
  private static final int DY = 5;
  private static final int APP_ID = 6;



  public int[][] solution(int[][] board, int[][] commands) {

    int[][] apps = getApps(board);

    for (int[] command : commands) {
      int appId = command[0];
      int[] direction = DIRECTIONS[command[1] - 1];
      int dx = direction[0];
      int dy = direction[1];
      move(board, apps, appId, dx, dy);

      for (int[] board1 : board) {
        for (int x = 0; x < board1.length; x++) {
          board1[x] = 0;
        }
      }

      for (int[] app : apps) {
        int x1 = Math.floorMod(app[X1] + app[DX], board[0].length);
        int x2 = Math.floorMod(app[X2] + app[DX], board[0].length);
        int y1 = Math.floorMod(app[Y1] + app[DY], board.length);
        int y2 = Math.floorMod(app[Y2] + app[DY], board.length);

        for (int y = y1; y <= y2; y++) {
          for (int x = x1; x <= x2; x++) {
            board[y][x] = app[APP_ID];
          }
        }
      }
    }

    return board;
  }

  private void move(int[][] board, int[][] apps, int appId, int dx, int dy) {
    // 맵 이탈 탐지
    int moveCurDx = 0;
    int curX1;
    int curX2;
    do {
      moveCurDx += dx;
      curX1 = Math.floorMod(apps[appId - 1][X1] + apps[appId - 1][DX] + moveCurDx, board[0].length);
      curX2 = Math.floorMod(apps[appId - 1][X2] + apps[appId - 1][DX] + moveCurDx, board[0].length);
    } while (curX2 < curX1);

    int moveCurDy = 0;
    int curY1;
    int curY2;
    do {
      moveCurDy += dy;
      curY1 = Math.floorMod(apps[appId - 1][Y1] + apps[appId - 1][DY] + moveCurDy, board.length);
      curY2 = Math.floorMod(apps[appId - 1][Y2] + apps[appId - 1][DY] + moveCurDy, board.length);
    } while (curY2 < curY1);


    for (int c = 0; c < Math.max(Math.abs(moveCurDx), Math.abs(moveCurDy)); c++) {
      apps[appId - 1][DX] += dx;
      apps[appId - 1][DY] += dy;

      curX1 = Math.floorMod(apps[appId - 1][X1] + apps[appId - 1][DX], board[0].length);
      curX2 = Math.floorMod(apps[appId - 1][X2] + apps[appId - 1][DX], board[0].length);
      curY1 = Math.floorMod(apps[appId - 1][Y1] + apps[appId - 1][DY], board.length);
      curY2 = Math.floorMod(apps[appId - 1][Y2] + apps[appId - 1][DY], board.length);

      for (int[] app : apps) {
        if (app[APP_ID] == appId) {
          continue;
        }

        int nextX1 = Math.floorMod(app[X1] + app[DX], board[0].length);
        int nextX2 = Math.floorMod(app[X2] + app[DX], board[0].length);
        int nextY1 = Math.floorMod(app[Y1] + app[DY], board.length);
        int nextY2 = Math.floorMod(app[Y2] + app[DY], board.length);

        if (curX1 <= nextX2 && nextX1 <= curX2 && curY1 <= nextY2 && nextY1 <= curY2) {
          move(board, apps, app[APP_ID], dx, dy);
        }
      }
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

          result.put(appId, new int[] {x1, y1, x2, y2, 0, 0, appId});
        }
      }
    }


    int[][] r = new int[result.size()][7];
    for (int k : result.keySet()) {
      r[k - 1] = result.get(k);
    }
    return r;
  }
}
