//[[1,3][2,6]]
//[[2,3],[5,10],[2,6]]
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    public solution() {
        int[][] intervals;
        List<int[]> list = new ArrayList<>();

        for(int i=0; i<intervals.length; i++) {
            int[] interval = intervals[i]; //[start, end]

            int start = interval[0];
            int end = interval[1];

            for(int j=i+1; j<intervals.length; j++) {
                int[] compare = intervals[j];
                int comS = compare[0];
                int comE = compare[1];

                //앞의 값과 겹치는 경우, 같기만 해도 해당하는 경우엔 >=
                if(end > comS) {
                    //beforeS와 end 조합
                    list.add([start, comE]);
                }
            }
        }
        
        int[][] answer = new int[list.length][2];
        for(int i=0; i<list.size(); i++) {
            answer[i] = list.get(i);
        }
        
        return answer;
    }

    //이미 담았지만 중복되는 것이 있는지 체크
    public List<int[]> isNested(List<int>[]> list, int[] compare) {
        for(int i=0; i<list.size(); i++) {
            int[] ele = list.get(i);
            
            if(ele[1] > compare[0]) {
                //ele ~~ compare 순인 경우
                ele[0],compare[1]
            }else if(compare[1] > ele[0]) {
                //compare ~~ ele 순인 경우
                compare[0],ele[1]
            }

        }
    }
}