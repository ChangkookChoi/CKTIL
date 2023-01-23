//콜라 문제 재귀함수
class Solution {
    public int solution(int a, int b, int n) {
        return recursive(a, b, n);
    }
    
    public int recursive(int a, int b, int n) {
        int changedTarget = n/a; //교환에 쓰일 콜라 수
        int remains = n%a; //교환하고 남은 갖고 있던 콜라 수
        
        if(changedTarget == 0) {
            //교환할 수가 더 없는 경우
            return 0;
        }
        
        int newThings = changedTarget*b; //교환 받은 콜라 수
        
        //계속 교환, newThings + remains는 교환 + 교환 타겟이 못된 애매한 수
        return newThings + recursive(a, b, newThings+remains);
    }
}