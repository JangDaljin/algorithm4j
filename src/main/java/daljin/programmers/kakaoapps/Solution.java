package daljin.programmers.kakaoapps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Solution {

  private static final int[][] DIRECTIONS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
  private static final int X1 = 0;
  private static final int Y1 = 1;
  private static final int X2 = 2;
  private static final int Y2 = 3;
  private static final int DX = 4;
  private static final int DY = 5;
  private static final int APP_ID = 6;

  private int[][] apps;
  private int[][] board;

  public int[][] solution(int[][] board, int[][] commands) {
    this.board = board;
    this.apps = getApps();

    for (int[] command : commands) {
      int appId = command[0];
      int[] direction = DIRECTIONS[command[1] - 1];
      int dx = direction[0];
      int dy = direction[1];

      move(board, apps, appId, dx, dy);
    }

    draw();

    return board;
  }

  private void draw() {
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

      if (x2 < x1) {
        for (int y = y1; y <= y2; y++) {
          for (int x = x1; x <= board[0].length - 1; x++) {
            board[y][x] = app[APP_ID];
          }
          for (int x = 0; x <= x2; x++) {
            board[y][x] = app[APP_ID];
          }
        }
      } else if (y1 > y2) {
        for (int y = y1; y <= board.length - 1; y++) {
          for (int x = x1; x <= x2; x++) {
            board[y][x] = app[APP_ID];
          }
        }

        for (int y = 0; y <= y2; y++) {
          for (int x = x1; x <= x2; x++) {
            board[y][x] = app[APP_ID];
          }
        }
      } else {
        for (int y = y1; y <= y2; y++) {
          for (int x = x1; x <= x2; x++) {
            board[y][x] = app[APP_ID];
          }
        }
      }
    }
  }

  private void move(int[][] board, int[][] apps, int appId, int dx, int dy) {
    // 그룹 찾기
    Set<Integer> groupAppIds = getGroupAppIds(apps, appId, dx, dy);

    // 그룹 전체 이동
    for (int groupAppId : groupAppIds) {
      int[] app = apps[groupAppId - 1];
      app[DX] += dx;
      app[DY] += dy;
    }

    // 맵 이탈 탐지
    int curX1;
    int curX2;
    int curY1;
    int curY2;
    Queue<Integer> overflowAppIdQueue = new LinkedList<>();
    for (int[] app : apps) {
      curX1 = Math.floorMod(app[X1] + app[DX], board[0].length);
      curX2 = Math.floorMod(app[X2] + app[DX], board[0].length);
      curY1 = Math.floorMod(app[Y1] + app[DY], board.length);
      curY2 = Math.floorMod(app[Y2] + app[DY], board.length);

      if (curX1 > curX2 || curY1 > curY2) {
        overflowAppIdQueue.add(app[APP_ID]);
      }
    }

    int overflowAppId;
    int[] overflowApp;
    while (!overflowAppIdQueue.isEmpty()) {
      overflowAppId = overflowAppIdQueue.poll();
      overflowApp = apps[overflowAppId - 1];

      curX1 = Math.floorMod(overflowApp[X1] + overflowApp[DX], board[0].length);
      curX2 = Math.floorMod(overflowApp[X2] + overflowApp[DX], board[0].length);
      curY1 = Math.floorMod(overflowApp[Y1] + overflowApp[DY], board.length);
      curY2 = Math.floorMod(overflowApp[Y2] + overflowApp[DY], board.length);

      if (curX1 <= curX2 && curY1 <= curY2) {
        continue;
      }


      int nextX1;
      int nextX2;
      int nextY1;
      int nextY2;
      Set<Integer> overflowGroupAppIds;

      while (true) {
        curX1 = Math.floorMod(overflowApp[X1] + overflowApp[DX], board[0].length);
        curX2 = Math.floorMod(overflowApp[X2] + overflowApp[DX], board[0].length);
        curY1 = Math.floorMod(overflowApp[Y1] + overflowApp[DY], board.length);
        curY2 = Math.floorMod(overflowApp[Y2] + overflowApp[DY], board.length);

        if (curX1 <= curX2 && curY1 <= curY2) {
          break; // 풀렸으면 종료
        }

        overflowGroupAppIds = getGroupAppIds(apps, overflowAppId, dx, dy);

        int[] overflowGroupApp;
        for (int overflowGroupAppId : overflowGroupAppIds) {
          overflowGroupApp = apps[overflowGroupAppId - 1];

          overflowGroupApp[DX] += dx;
          overflowGroupApp[DY] += dy;

          nextX1 = Math.floorMod(overflowGroupApp[X1] + overflowGroupApp[DX], board[0].length);
          nextX2 = Math.floorMod(overflowGroupApp[X2] + overflowGroupApp[DX], board[0].length);
          nextY1 = Math.floorMod(overflowGroupApp[Y1] + overflowGroupApp[DY], board.length);
          nextY2 = Math.floorMod(overflowGroupApp[Y2] + overflowGroupApp[DY], board.length);

          if (nextX1 <= nextX2 && nextY1 <= nextY2) {
            continue;
          }

          overflowAppIdQueue.add(overflowGroupAppId);
        }
      }
    }


  }

  public int[][] getApps() {
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

  private List<int[]> split(int s, int e, int L) {
    List<int[]> r = new ArrayList<>();
    if (s <= e)
      r.add(new int[] {s, e});
    else {
      r.add(new int[] {s, L - 1});
      r.add(new int[] {0, e});
    }
    return r;
  }

  private boolean overlap1D(int a1, int a2, int b1, int b2, int L) {
    for (int[] A : split(a1, a2, L))
      for (int[] B : split(b1, b2, L))
        if (A[0] <= B[1] && B[0] <= A[1])
          return true;
    return false;
  }

  private boolean isConflict(int[] a, int[] b) {
    int W = board[0].length, H = board.length;
    int ax1 = Math.floorMod(a[X1] + a[DX], W), ax2 = Math.floorMod(a[X2] + a[DX], W);
    int ay1 = Math.floorMod(a[Y1] + a[DY], H), ay2 = Math.floorMod(a[Y2] + a[DY], H);
    int bx1 = Math.floorMod(b[X1] + b[DX], W), bx2 = Math.floorMod(b[X2] + b[DX], W);
    int by1 = Math.floorMod(b[Y1] + b[DY], H), by2 = Math.floorMod(b[Y2] + b[DY], H);

    return overlap1D(ax1, ax2, bx1, bx2, W) && overlap1D(ay1, ay2, by1, by2, H);
  }

  private Set<Integer> getGroupAppIds(int[][] apps, int startAppId, int dx, int dy) {
    Set<Integer> groupAppIds = new TreeSet<>();
    Queue<Integer> groupAppIdQueue = new LinkedList<>();

    groupAppIds.add(startAppId);
    groupAppIdQueue.add(startAppId);

    int currentAppId;
    int[] current;
    while (!groupAppIdQueue.isEmpty()) {
      currentAppId = groupAppIdQueue.poll();

      current = apps[currentAppId - 1];

      int nextAppId;
      for (int[] next : apps) {
        nextAppId = next[APP_ID];
        if (nextAppId == currentAppId) {
          continue;
        }

        if (groupAppIds.contains(nextAppId)) {
          continue;
        }

        int[] copyCurrent = Arrays.copyOf(current, current.length);
        int[] copyNext = Arrays.copyOf(next, next.length);
        copyCurrent[DX] += dx;
        copyCurrent[DY] += dy;

        if (isConflict(copyCurrent, copyNext)) {
          groupAppIds.add(nextAppId);
          groupAppIdQueue.add(nextAppId);
        }
      }
    }

    return groupAppIds;
  }
}
