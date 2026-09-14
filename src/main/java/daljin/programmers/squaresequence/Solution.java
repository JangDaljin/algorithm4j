package daljin.programmers.squaresequence;

class Solution {

    private static final int FROM = 0;
    private static final int TO = 1;
    private static final int VALUE = 2;

    private static final int COUNT = 0;
    private static final int ACC = 1;

    public long[] solution(int[] arr, long l, long r) {


        long[][] table = new long[arr.length][3];
        table[0][FROM] = 0;
        table[0][TO] = table[0][FROM] + arr[0] - 1;
        table[0][VALUE] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            table[i][FROM] = table[i - 1][TO] + 1;
            table[i][TO] = table[i][FROM] + arr[i] - 1;
            table[i][VALUE] = arr[i];
        }


        long cl = l - 1;
        long cr = r - 1;
        long K = 0;
        for (long[] row : table) {
            if (row[FROM] <= cl && cl <= row[TO]) {
                K += row[VALUE] * (row[TO] - cl + 1);
            } else if (row[FROM] <= cr && cr <= row[TO]) {
                K += row[VALUE] * (cr - row[FROM] + 1);
                break;
            } else if (K != 0) {
                K += row[VALUE] * (row[TO] - row[FROM] + 1);
            }
        }


        // 초기 확인
        long LEN = r - l + 1;
        int li = 0;
        int ri = 0;
        long lli = 0;
        long rri = 0;
        long curAcc = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i][TO] >= (LEN - 1)) {
                ri = i;
                rri = (LEN - 1) - table[i][FROM];
                curAcc += table[i][VALUE] * ((LEN - 1) - table[i][FROM] + 1);
                break;
            }

            curAcc += table[i][VALUE] * (table[i][TO] - table[i][FROM] + 1);
            ri += 1;
        }

        long C = 0;
        // 계산
        while (ri >= table.length) {
            if(curAcc == K) {
                C++;
            }

            long moveIndex = Math.min(table[li][TO], table[ri][TO]);

            for(int i = 0 ; i <)

            ri++;
            li++;
        }



        long[] answer = {K, 0};
        return answer;
    }
}
