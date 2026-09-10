package daljin.programmers.kakaoapps;

import java.util.Random;

public class Test {

    public static void main(String[] args) {
        Random rnd = new Random(42);

        for (int trial = 0; trial < 500000; trial++) {
            int H = 2 + rnd.nextInt(7); // 2~6
            int W = 2 + rnd.nextInt(7);

            int[][] board = randomBoard(H, W, rnd);
            int maxId = maxId(board);
            if (maxId == 0)
                continue;

            int cmdCount = 1 + rnd.nextInt(6);
            int[][] cmds = new int[cmdCount][2];
            for (int i = 0; i < cmdCount; i++) {
                cmds[i][0] = 1 + rnd.nextInt(maxId);
                cmds[i][1] = 1 + rnd.nextInt(4);
            }

            try {
                new Solution().solution(deepCopy(board), cmds);
            } catch (Throwable e) {
                System.out.println("=== 재현 성공 (trial " + trial + ") ===");
                System.out.println("예외: " + e);
                System.out.println("board:");
                print(board);
                System.out.println("commands:");
                for (int[] c : cmds)
                    System.out.println("  {" + c[0] + ", " + c[1] + "}");
                return;
            }
        }
        System.out.println("재현 실패 — 범위를 넓혀보세요");
    }

    /** 겹치지 않게 정사각형 앱들을 무작위 배치. 빽빽하게 채우도록 큰 것부터 시도. */
    static int[][] randomBoard(int H, int W, Random rnd) {
        int[][] b = new int[H][W];
        int id = 1;
        int maxSize = Math.min(H, W);

        // 큰 것부터 배치해야 빈칸이 적은 배치가 잘 나온다
        for (int size = maxSize; size >= 1; size--) {
            // 각 크기마다 여러 번 시도
            int attempts = (size == 1) ? H * W : 20;
            for (int a = 0; a < attempts; a++) {
                int r = rnd.nextInt(H - size + 1);
                int c = rnd.nextInt(W - size + 1);
                if (canPlace(b, r, c, size)) {
                    place(b, r, c, size, id++);
                }
            }
            // 1x1까지 다 채우면 빈칸 0인 보드가 됨
            if (size == 1 && rnd.nextBoolean())
                break; // 가끔은 빈칸을 남긴다
        }
        return b;
    }

    static boolean canPlace(int[][] b, int r, int c, int size) {
        for (int i = r; i < r + size; i++)
            for (int j = c; j < c + size; j++)
                if (b[i][j] != 0)
                    return false;
        return true;
    }

    static void place(int[][] b, int r, int c, int size, int id) {
        for (int i = r; i < r + size; i++)
            for (int j = c; j < c + size; j++)
                b[i][j] = id;
    }

    static int maxId(int[][] b) {
        int m = 0;
        for (int[] row : b)
            for (int v : row)
                m = Math.max(m, v);
        return m;
    }

    static int[][] deepCopy(int[][] b) {
        int[][] r = new int[b.length][];
        for (int i = 0; i < b.length; i++)
            r[i] = b[i].clone();
        return r;
    }

    static void print(int[][] b) {
        for (int[] row : b) {
            StringBuilder sb = new StringBuilder("  {");
            for (int i = 0; i < row.length; i++) {
                if (i > 0)
                    sb.append(", ");
                sb.append(row[i]);
            }
            System.out.println(sb.append("},"));
        }
    }
}
