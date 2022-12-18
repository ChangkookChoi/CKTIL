import java.lang.Math;
import java.util.ArrayList;

//기사단원의 무기
class Solution {
    public int solution(int number, int limit, int power) {
        int answer = 0;
        int t = 0;
        for(int i=1; i<=number; i++) {
            t = getDivisor(i);
            answer += t > limit ? power : t;
        }
        
        return answer;
    }
    
    public int getDivisor(int num) {
        int sqrt = (int) Math.sqrt(num);
        ArrayList<Integer> arr = new ArrayList<>();
        
        for(int i=1; i<=sqrt; i++) {
            if(num%i == 0) {
                arr.add(i);
                if(num/i != i) {
                    arr.add(num/i);
                }
            }
        }
        
        return arr.size();
    }
}