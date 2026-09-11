package daljin.programmers.squaresequence;

public class Main {
    private final static int[][] arr = {{3, 2, 3, 1, 1}, {2, 2, 2}, {8, 8, 6, 5, 2, 9, 8, 4, 3, 10},
            {70195, 25471, 7389, 58187, 18454, 90532, 97667, 17148, 91636, 2810},
            {16952, 70276, 16771, 37992, 87549, 54906, 36718, 20478, 57088, 27916, 51509, 83422,
                    51707, 18807, 80859, 2673, 37734, 93380},
            {49134, 86806, 94548, 88849, 95022, 28334, 16637, 79487, 23773, 7314, 47370, 50269,
                    36573, 9415, 44674, 28096}};
    private final static long[] l = {5, 2, 25, 126058, 149845, 61242};
    private final static long[] r = {7, 2, 27, 462933, 228204, 88535};
    private final static long[][] result =
            {{8, 2}, {2, 6}, {15, 3}, {27554327568L, 1}, {6860339640L, 9190}, {2369282964L, 59513}};

    public static void main(String[] args) {
        Solution solution = new Solution();

        outer: for (int i = 0; i < arr.length; i++) {
            int[] cArr = arr[i];
            long cr = r[i];
            long cl = l[i];
            long[] cResult = result[i];


            long[] s = solution.solution(cArr, cl, cr);
            if (s.length == 0) {
                System.err.println((i + 1) + "번째 케이스 응답값 없음");
                continue;
            }

            for (int j = 0; j < s.length; j++) {
                if (cResult[j] != s[j]) {
                    System.err.println((i + 1) + "번째 케이스 실패");
                    continue outer;
                }
            }
            System.out.println((i + 1) + "번째 케이스 성공");
        }
    }
}
