package daljin.programmers.squaresequence;

class Solution {

    private static final int FROM = 0;
    private static final int TO = 1;
    private static final int VALUE = 2;

    private static final int COUNT = 0;
    private static final int ACC = 1;

    private int[] dpCount;

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

        long acc = 0;
        long remain = K;
        for (long[] row : table) {
        
            long dv = (long) Math.floor(remain / row[VALUE]);
            long dd = K % row[VALUE];

            //현재 위치에서 종료된 경우
            if(dd == 0 && dv <= row[VALUE]) {
                acc += 1;
                continue;
            }

            //모두 사용하지 않았는데도 나머지가 남는 경우
            if(dd != 0 && dv < row[VALUE]) {
                continue;
            }
            


            
        }
        

        long[] answer = {K, 0};
        return answer;
    }

    private void dp(int[][] table, )
}
