//2021 Dev Matching 웹 백엔드 개발 로또의 최고 순위와 최저 순위

import java.util.Arrays;

class Solution {
    public int[] solution(int[] lottos, int[] win_nums) {
        int[] answer = new int[2];
        int highestRank = -1; //최고 순위
        int lowestRank = -1; //최저 순위
        
        //lowest는 그냥 비교했을 때가 몇인지로 알 수 있고
        //highest도 0을 제외한 rank에 0을 ++ 해주면 됨.
        int removedCnt = getRemovedCount(lottos);
        lowestRank = getRank(lottos, win_nums);
        highestRank = (lowestRank - removedCnt) < 1 ? 1 : (lowestRank - removedCnt);
        
        answer[0] = highestRank;
        answer[1] = lowestRank;
        
        return answer;
    }
    
    public int getRemovedCount(int[] lottos) {
        int cnt = 0;
        
        for(int lotto : lottos) {
            if(lotto == 0) {
                cnt++;
            }
        }
        
        return cnt;
    }
    
    public int getRank(int[] lottos, int[] win_nums) {
        Arrays.sort(lottos);
        Arrays.sort(win_nums);
        int rank = 7; //숫자가 하나씩 맞을 때마다 --
        for(int i=0; i<win_nums.length; i++) {
            for(int j=0; j<win_nums.length; j++) {
                if(win_nums[i] == lottos[j]) {
                    rank--;
                    break;
                }
            }
        }
        
        return rank >= 6 ? 6 : rank;
    }
}