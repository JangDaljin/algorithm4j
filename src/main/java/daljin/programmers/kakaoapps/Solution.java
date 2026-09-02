package daljin.programmers.kakaoapps;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.TreeMap;

public class Solution {

  private static final int[][] DIRECTIONS = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
  private static final int X1 = 0;
  private static final int Y1 = 1;
  private static final int X2 = 2;
  private static final int Y2 = 3;
  private static final int APP_ID = 4;

  private int[][] board;
  private int[][] commands;

  public int[][] solution(int[][] board, int[][] commands) {
    this.board = board;
    this.commands = commands;

    int[][] appPositions = getAppPoistions();

    Deque<int[]> queue = new ArrayDeque<>();
    Deque<int[]> addQueue = new ArrayDeque<>();

    for (int[] command : commands) {
      int appId = command[0];
      int[] direction = DIRECTIONS[command[1] - 1];

      int x1 = appPositions[appId - 1][X1];
      int y1 = appPositions[appId - 1][Y1];
      int x2 = appPositions[appId - 1][X2];
      int y2 = appPositions[appId - 1][Y2];
      queue.offer(new int[] {x1, y1, x2, y2, appId});


      while (!queue.isEmpty()) {
        int[] current = queue.poll();

        int cx1 = current[X1];
        int cy1 = current[Y1];
        int cx2 = current[X2];
        int cy2 = current[Y2];
        int cAppId = current[APP_ID];

        int dx = direction[0];
        int dy = direction[1];

        int nx1 = cx1 + dx;
        int ny1 = cy1 + dy;
        int nx2 = cx2 + dx;
        int ny2 = cy2 + dy;

        if (nx2 == board[ny2].length) {
          nx2 = (nx2 - nx1);
          nx1 = 0;
        } else if (nx1 == -1) {
          nx1 = board[ny2].length - 1 - (nx2 - nx1);
          nx2 = board[ny2].length - 1;
        } else if (ny2 == board.length) {
          ny2 = (ny2 - ny1);
          ny1 = 0;
        } else if (ny1 == -1) {
          ny1 = board.length - 1 - (ny2 - ny1);
          ny2 = board.length - 1;
        }

        // 현재 흔적 지우기
        for (int cy = cy1; cy <= cy2; cy++) {
          for (int cx = cx1; cx <= cx2; cx++) {
            board[cy][cx] = 0;
          }
        }

        // 새로 생성에 추가
        addQueue.add(new int[] {nx1, ny1, nx2, ny2, cAppId});

        // 다음 앱 검색
        for (int k = 0; k < appPositions.length; k++) {
          int[] appPos = appPositions[k];
          if (appPos[APP_ID] == cAppId) {
            break;
          }
          if ((appPos[X1] <= nx1 && nx1 <= appPos[X2]) && (appPos[Y1] <= ny1 && ny1 <= appPos[Y2])
              || (appPos[X1] <= nx2 && nx2 <= appPos[X2])
                  && (appPos[Y1] <= ny1 && ny1 <= appPos[Y2])
              || (appPos[X1] <= nx1 && nx1 <= appPos[X2])
                  && (appPos[Y1] <= ny2 && ny2 <= appPos[Y2])
              || (appPos[X1] <= nx2 && nx2 <= appPos[X2])
                  && (appPos[Y1] <= ny2 && ny2 <= appPos[Y2])) {
            queue.add(new int[] {appPos[X1], appPos[Y1], appPos[X2], appPos[Y2], k + 1});
          }
        }
      }

      // addQueue 처리
      System.out.println("qwe");
    }


    return board;
  }

  public int[][] getAppPoistions() {

    Map<Integer, int[]> result = new TreeMap<>();

    int x1 = -1;
    int y1 = -1;
    int x2 = -1;
    int y2 = -1;
    int appId = 0;

    edge: for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {

        appId = board[i][j];
        if (appId != 0 && !result.containsKey(appId)) {
          y1 = i;
          x1 = j;

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

            if (x == board[y2].length) {
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
