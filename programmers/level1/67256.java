import java.util.Map;
import java.util.HashMap;
import java.lang.Math;

//2020 카카오 인턴십 키패드 누르기
//아직 성공 못함 60점. 초반에 * # 일 때  0으로 이동하거나 하는 변수를 생각해봐야 할듯

class Solution {
    public String solution(int[] numbers, String hand) {
        String answer = "";
        
        int l = 0; //왼손 위치
        int r = 0; //오른손 위치
        String s = "";
        
        Map<Integer, Integer> map = getMapInit();
        
        for(int i=0; i<numbers.length; i++) {
            int n = numbers[i];
            
            if(n == 1 || n == 4 || n == 7) {
                s = "L";
            }else if(n == 3 || n == 6 || n == 9) {
                s = "R";
            }else {
                s = getHand(hand, r, l, n, map);
            }
            
            if(s == "R") {
                r = n;
                System.out.println("오른손 위치 조정 : " + n + " 위치 값 : " + map.get(n));
            }else {
                l = n;
                System.out.println("왼손 위치 조정 : " + n + " 위치 값 : " + map.get(n));
            }
            
            answer += s;
        }
        
        return answer;
    }
    
    public String getHand(String hand, int r, int l, int n, Map<Integer, Integer> map) {
        String result = "";
        int numV = map.get(n);
        int leftV = Math.abs(map.get(l) - numV);
        int rightV = Math.abs(map.get(r) - numV);
        if(isCenter(n, r)) {
            rightV--;
        }else if(isCenter(n, l)) {
            leftV--;
        }
        
        System.out.println("n : " + n + " l : " + l + " r : " + r);
        System.out.println("num : " + numV + " leftV : " + leftV + " rightV : " + rightV + "hand : " + hand);
        if(leftV < rightV) {
            //왼손이 더 가까울 때
            result = "L";
        }else if(leftV > rightV) {
            //오른손이 더 가까울 때
            result = "R";
        }else {
            //leftV == rigthV
            result = hand.equals("right") ? "R" : "L";
        }
        return result;
    }
    
    public boolean isCenter(int n, int c) {
        if((n == 2 || n == 5 || n == 8 || n == 0)
          && (c == 2 || c == 5 || c == 8 || c == 0)) {
            return true;
        }
        
        return false;
    }
    
    public Map<Integer, Integer> getMapInit() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        for(int i=0; i<10; i++) {
            if(1 <= i && i <= 3) {
                map.put(i, 1);
            }else if(4 <= i && i <= 6) {
                map.put(i, 2);
            }else if(7 <= i && i <= 9) {
                map.put(i, 3);
            }else {
                map.put(i, 4);
            }
        }
        
        return map;
    }
}