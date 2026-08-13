package daljin.programmers;

class Solution {

  public static final int UP = 0;
  public static final int RIGHT = 1;
  public static final int DOWN = 2;
  public static final int LEFT = 3;

  public static final int OBSTACLE_TILE = -1;
  public static final int EMPTY_TILE = 0;

  public static final int[][] TILES = {
      {-1, LEFT, -1, RIGHT},    //1
      {DOWN, -1, UP, -1},       //2
      {DOWN, LEFT, UP, RIGHT},  //3
      {LEFT, -1, -1, UP},       //4
      {RIGHT, UP, -1, -1},      //5
      {-1, DOWN, RIGHT, -1},    //6
      {-1, -1, LEFT, DOWN}      //7
  };

  public int solution(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;

    int[][] visited = new int[n][m];
    visited[0][0] += 1;

    return dfs(grid, n, m, 1, 0, LEFT, visited);
  }

  private int dfs(int[][] grid, int n, int m, int x, int y, int from, int[][] visited) {

    //그리드 초과
    if (x < 0 ||
        y < 0 ||
        x > m - 1 ||
        y > n - 1
    ) {
      return 0;
    }

    int current = grid[y][x];

    int acc = 0;
    //장애물 일 때
    if (current == OBSTACLE_TILE) {
      return 0;
    } else if (current == EMPTY_TILE) {
      //신규 타일을 넣어서 다음으로 진행
      for (int i = 0; i < TILES.length; i++) {
        //진행 가능한 방향이면 추가하고 다음으로 진행
        int d = TILES[i][from];
        if (d != -1) {
          int nextX = x;
          int nextY = y;
          int nextTile = i + 1;
          switch (d) {
            case UP: {
              nextY--;
              break;
            }
            case DOWN: {
              nextY++;
              break;
            }
            case RIGHT: {
              nextX++;
              break;
            }
            case LEFT: {
              nextX--;
              break;
            }
          }
          grid[y][x] = nextTile;
          visited[y][x] += 1;
          acc += dfs(grid, n, m, nextX, nextY, reverse(d), visited);
          visited[y][x] -= 1;
          grid[y][x] = EMPTY_TILE;
        }
      }
    }
    //기존에 존재하는 타일
    else {
      if (TILES[current - 1][from] == -1) {
        return 0;
      }

      //완료 확인
      if (x == m - 1 && y == n - 1) {
        visited[y][x] += 1;

        for (int i = 0; i < n; i++) {
          for (int j = 0; j < m; j++) {
            //3인데 2번 방문하지 않으면 실패 경로
            if (grid[i][j] == 3 && visited[i][j] != 2) {
              visited[y][x] -= 1;
              return 0;
            }

            //모든 경로를 거쳐오지 않음
            if (grid[i][j] != 3 && grid[i][j] > 0 && visited[i][j] != 1) {
              visited[y][x] -= 1;
              return 0;
            }
          }
        }

        visited[y][x] -= 1;
        return 1;
      }

      int d = TILES[current - 1][from];
      if (d != -1) {
        int nextX = x;
        int nextY = y;
        switch (d) {
          case UP: {
            nextY--;
            break;
          }
          case DOWN: {
            nextY++;
            break;
          }
          case RIGHT: {
            nextX++;
            break;
          }
          case LEFT: {
            nextX--;
            break;
          }
        }
        visited[y][x] += 1;
        acc += dfs(grid, n, m, nextX, nextY, reverse(d), visited);
        visited[y][x] -= 1;
      }
    }
    return acc;
  }

  private int reverse(int d) {
    return (d + 2) % 4;
  }
}