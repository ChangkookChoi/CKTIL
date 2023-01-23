//[[1,3][2,6]]
//[[2,3],[5,10],[2,6]]
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    public solution() {
        int[][] intervals;
        List<int[]> list = new ArrayList<>();

        int idx = 0;
        int beforeS = 0;
        int beforeE = 0;
        
        for(int i=0; i<intervals.length; i++) {
            int[] interval = intervals[i]; //[start, end]
            int start = interval[0];
            int end = interval[1];
            
            if(i == 0) {
                continue;
            }

            //앞의 값과 겹치는 경우, 같기만 해도 해당하는 경우엔 >=
            if(beforeE > start) {
                //beforeS와 end 조합
                list.add([beforeS, end]);
            }

            beforeS = start;
            beforeE = end;
        }
        
        int[][] answer = new int[list.length][2];
        for(int i=0; i<list.size(); i++) {
            answer[i] = list.get(i);
        }
        
        return answer;
    }
}