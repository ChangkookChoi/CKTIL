//명예의 전당(1)
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class Solution {
    public int[] solution(int k, int[] score) {
        int len = score.length;
        int[] answer = new int[len];

        List<Integer> scList = new ArrayList();

        for(int i=0; i<len; i++) {
            scList.add(score[i]);
            scList.sort(Collections.reverseOrder());
            answer[i] = i<k ? scList.get(scList.size()-1) : scList.get(k-1);
        }
    
        return answer;
    }
}

//내림차순 정렬 Collections.reverseOrder()를 사용하니 훨씬 쉽게 풀리는 문제
//Queue를 의도한 문제인 거 같으니 그렇게 다시 한 번 풀어보자

