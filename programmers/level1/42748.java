//K번째수
//sort를 직접 구현하라는 의도가 있는 듯 하다.

import java.util.Arrays;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        
        //Commands 만큼 반복
        for(int i=0; i<commands.length; i++) {
            //자르기
            int idx = 0;
            int start = commands[i][0]-1; //1
            int end = commands[i][1]-1; //4
            int[] arr = new int[end+1-start];
            for(int j=start; j<=end; j++) {
                arr[idx++] = array[j];
            }
            //정렬
            Arrays.sort(arr);
            
            //Return 값 담기
            answer[i] = arr[commands[i][2]-1];
        }
        return answer;
    }
}