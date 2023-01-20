//소수 만들기

class Solution {
    public int solution(int[] nums) {
        int answer = 0;

        for(int i=0; i<nums.length; i++) {
            int a = nums[i];
            for(int j=i+1; j<nums.length; j++) {
                int b = nums[j];
                for(int k=j+1; k<nums.length; k++) {
                    int c = nums[k];
                    
                    if(isP(a+b+c)) {
                        answer++;
                    }
                }
            }
        }

        return answer;
    }
    
    //소수 여부를 판단하는 구조를 외워두면 도움이 될 거 같다.
    public boolean isP(int num) {
        for(int i=2; i<num/2; i++) {
            if(num%i == 0) {
                //소수가 아니다.
                return false;
            }
        }
        
        //소수
        return true;
    }
}