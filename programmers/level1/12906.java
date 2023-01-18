//같은 숫자는 싫어 (스택/큐)
// 스택 또는 큐를 이용해 풀라는 거 같은데, 해당 자료구조 구현을 까먹었다. 공부해서 구현해보자

import java.util.*;

public class Solution {
    public int[] solution(int []arr) {
        int[] answer = {};
        
        ArrayList<Integer> list = new ArrayList();
        int idx = 0;
        
        for(int a : arr) {
            if(list.size() > 0 && a == list.get(idx-1)) {    
                continue;
            }
            
            list.add(a);
            idx++;
        }
        
        answer = new int[list.size()];
        for(int i=0; i<list.size(); i++) {
            answer[i] = list.get(i);
        }
        
        return answer;
    }
}