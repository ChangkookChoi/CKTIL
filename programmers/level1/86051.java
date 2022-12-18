//월간 코드 챌린지 3 없는 숫자 더하기
class Solution {
    int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    
    public int solution(int[] numbers) {
        int answer = 0;
        for(int a : nums) {
            answer += a;
        }
        for(int n : numbers) {
            answer -= n;
        }
        
        return answer;
    }
}