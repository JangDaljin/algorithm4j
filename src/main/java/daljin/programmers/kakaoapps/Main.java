package daljin.programmers.kakaoapps;

public class Main {

    private static final int[][][] board = {
            {{0, 2, 2, 0, 0, 0, 0, 0}, {0, 2, 2, 0, 0, 4, 4, 0}, {0, 3, 3, 3, 1, 4, 4, 0},
                    {0, 3, 3, 3, 0, 0, 0, 0}, {0, 3, 3, 3, 5, 5, 6, 0}, {0, 0, 0, 0, 5, 5, 0, 0}},
            {{0, 9, 1, 1, 6, 0, 0, 0}, {2, 2, 1, 1, 0, 0, 0, 0}, {2, 2, 3, 4, 4, 4, 0, 0},
                    {5, 0, 0, 4, 4, 4, 7, 0}, {0, 0, 0, 4, 4, 4, 8, 8}, {0, 0, 0, 0, 0, 0, 8, 8}},
            {{1, 1, 0}, {1, 1, 0}}};

    private static final int[][][] commands =
            {{{3, 1}, {3, 1}}, {{2, 1}, {3, 1}, {9, 2}, {4, 1}}, {{1, 4}, {1, 3}, {1, 2}}};

    private static final int[][][] result = {
            {{0, 0, 2, 2, 0, 0, 0, 0}, {4, 4, 2, 2, 0, 0, 0, 0}, {4, 4, 0, 3, 3, 3, 1, 0},
                    {0, 0, 0, 3, 3, 3, 0, 0}, {6, 0, 0, 3, 3, 3, 5, 5}, {0, 0, 0, 0, 0, 0, 5, 5}},
            {{8, 8, 0, 1, 1, 6, 0, 0}, {8, 8, 0, 1, 1, 0, 0, 0}, {4, 4, 4, 9, 3, 0, 0, 0},
                    {4, 4, 4, 7, 2, 2, 0, 0}, {4, 4, 4, 0, 2, 2, 0, 0}, {0, 5, 0, 0, 0, 0, 0, 0}},
            {{0, 1, 1}, {0, 1, 1}}};

    public static void main(String[] args) {
        Solution s = new Solution();

        testCase: for (int i = 0; i < result.length; i++) {
            int[][] resultBoard = s.solution(board[i], commands[i]);

            if (resultBoard.length == 0) {
                System.out.println((i + 1) + "번 테스트 케이스 실패");
                continue;
            }

            for (int a = 0; a < resultBoard.length; a++) {
                for (int b = 0; b < resultBoard[a].length; b++) {
                    if (result[i][a][b] != resultBoard[a][b]) {
                        System.out.println((i + 1) + "번 테스트 케이스 실패");
                        continue testCase;
                    }
                }
            }
            System.out.println((i + 1) + "번 테스트 케이스 성공");
        }
    }
}
