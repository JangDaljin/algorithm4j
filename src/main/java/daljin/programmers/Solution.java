package daljin.programmers;

import java.util.ArrayDeque;

class Solution {

  public final static int[][] RAILS = {
      {0, 1, 0, 1},
      {1, 0, 1, 0},
      {1, 1, 1, 1},
      {1, 0, 0, 1},
      {1, 1, 0, 0},
      {0, 1, 1, 0},
      {0, 0, 1, 1}
  };

  public final static int[][] DIRECTIONS = {
      //{dy, dx}
      {-1, 0},
      {0, 1},
      {1, 0},
      {0, -1}
  };

  public int solution(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;

    int[][] visited = new int[n][m];
    ArrayDeque<int[]> stack = new ArrayDeque<>();

    return dfs(grid, n, m, 0, 0, visited, stack);
  }

  private int dfs(int[][] grid, int n, int m, int x, int y, int[][] visited, ArrayDeque<int[]> stack) {
    if (x < 0 ||
        y < 0 ||
        x > m - 1 ||
        y > n - 1
    ) {
      return 0;
    }

    //장애물 처리하지 않음
    if (grid[y][x] == -1) {
      return 0;
    }

    //재방문 금지
    if (visited[y][x] != 0 && grid[y][x] != 3) {
      return 0;
    }

    //3번 노드 2번 이상 금지
    if (visited[y][x] > 1 && grid[y][x] == 3) {
      return 0;
    }

    //방문 시작
    visited[y][x] += 1;

    //완료 확인
    if (x == m - 1 && y == n - 1) {
      int r = 0;
      if (evaluate(grid, visited)) {
        r = 1;
      }
      if (r == 1) {
        System.out.println(stack.stream().toArray());
      }
      visited[y][x] -= 1;
      return r;
    }

    //누적치
    int acc = 0;

    //비어있는 타일일 때
    if (grid[y][x] == 0) {
      for (int r = 1; r <= RAILS.length; r++) {
        grid[y][x] = r;
        if (valid(grid, n, m, x, y, r)) {
          int nx, ny;
          for (int[] d : DIRECTIONS) {
            nx = x + d[1];
            ny = y + d[0];
            if (!stack.isEmpty() && stack.peek()[0] == ny && stack.peek()[1] == nx) {
              continue;
            }
            stack.push(new int[]{y, x});
            acc += dfs(grid, n, m, nx, ny, visited, stack);
            stack.pop();
          }
        }
        grid[y][x] = 0;
      }
    }

    if (grid[y][x] == 3) {
      int dy = y - stack.peek()[0];
      int dx = x - stack.peek()[1];
      stack.push(new int[]{y, x});
      acc += dfs(grid, n, m, x + dx, y + dy, visited, stack);
      stack.pop();
    }

    //현재 타일이 이미 설치된 타일일때
    if (grid[y][x] != 0 && grid[y][x] != 3) {
      //다음 위치 선정
      int nx, ny;
      for (int[] d : DIRECTIONS) {
        nx = x + d[1];
        ny = y + d[0];
        //역행 금지
        if (!stack.isEmpty() && stack.peek()[0] == ny && stack.peek()[1] == nx) {
          continue;
        }
        stack.push(new int[]{y, x});
        acc += dfs(grid, n, m, nx, ny, visited, stack);
        stack.pop();
      }
    }

    visited[y][x] -= 1;
    return acc;
  }

  private boolean evaluate(int[][] grid, int[][] visited) {
    int acc = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == -1 || grid[i][j] == 0) {
          continue;
        } else if (grid[i][j] == 3) {
          acc += 2;
        } else {
          acc += 1;
        }
      }
    }

    for (int i = 0; i < visited.length; i++) {
      for (int j = 0; j < visited[i].length; j++) {
        acc -= visited[i][j];
      }
    }

    return acc == 0;
  }


  private int[] getTile(int tileNumber) {
    if (tileNumber == -1) {
      return new int[]{0, 0, 0, 0};
    }

    if (tileNumber == 0) {
      return null;
    }

    return RAILS[tileNumber - 1];
  }

  private boolean valid(int[][] grid, int n, int m, int x, int y, int rail) {
    int[] center = getTile(rail);

    if (center[3] == 1 && x == 0) {
      return false;
    }

    if (center[0] == 1 && y == 0) {
      return false;
    }

    if (center[1] == 1 && x == m - 1) {
      return false;
    }

    if (center[2] == 1 && y == n - 1) {
      return false;
    }

    if (x != m - 1 && !validRight(center, getTile(grid[y][x + 1]))) {
      return false;
    }

    if (x != 0 && !validLeft(center, getTile(grid[y][x - 1]))) {
      return false;
    }

    if (y != 0 && !validTop(center, getTile(grid[y - 1][x]))) {
      return false;
    }

    if (y != n - 1 && !validBottom(center, getTile(grid[y + 1][x]))) {
      return false;
    }

    return true;
  }

  private boolean validRight(int[] center, int[] right) {
    return right == null || center[1] == right[3];
  }

  private boolean validLeft(int[] center, int[] left) {
    return left == null || center[3] == left[1];
  }

  private boolean validTop(int[] center, int[] top) {
    return top == null || center[0] == top[2];
  }

  private boolean validBottom(int[] center, int[] bottom) {
    return bottom == null || center[2] == bottom[0];
  }
}