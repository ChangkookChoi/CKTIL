import java.util.Arrays;

class Solution {
    public int solution(int n, int[] lost, int[] reserve) {
        //answer은 체육복이 다 갖춰진 학생들의 수로만 우선 셋팅
        int answer = n - lost.length;
        
        Arrays.sort(lost);
        Arrays.sort(reserve);
        
        //상황은 두 가지, 여벌 체육복 가져온 사람이 도난도 당한 경우 (lost와 reserve의 값이 같음)
        //이런 경우 lost와 reserve에선 해당 값들을 못 쓰게 만들고, answer의 값을 올려줌
        for(int i=0; i<reserve.length; i++) {
            for(int j=0; j<lost.length; j++) {
                if(reserve[i] == lost[j]) {
                    answer++;
                    reserve[i] = -1;
                    lost[j] = -1;
                    break;
                }
            }
        }
        
        //여벌 체육복 가져온 사람이 다른 도난당한 사람 빌려주는 경우 (lost가 reserve의 -1 || +1)
        /*
        특정 값을 -1 처리하려면 상대 배열을 -1 || +1 비교급으로 만들어줘야 함.
        또한 반복문의 겉, 속도 그에 따라 다르게 해줘야 
        */
        for(int i=0; i<reserve.length; i++) {
            for(int j=0; j<lost.length; j++) {
                if(reserve[i]-1 == lost[j] || reserve[i]+1 == lost[j]) {
                    answer++;
                    lost[j] = -1;
                    break;
                }
            }
        }
        /*for(int i=0; i<lost.length; i++){
            for(int j=0; j<reserve.length; j++){
                if((lost[i]-1 == reserve[j]) || (lost[i]+1 == reserve[j])){
                    answer++;
                    reserve[j] = -1; 
                    break; 
                }
            }
        }*/
        
        return answer;
    }
}