import java.util.Map;
import java.util.HashMap;

//2022 카카오 성격유형 검사하기
class Solution {
    public String solution(String[] survey, int[] choices) {
        String answer = "";
        
        char[] c = {'R', 'T', 'C', 'F', 'J', 'M', 'A', 'N'};
        int[] scores = {0, 3, 2, 1, 0, 1, 2, 3};
        Map<Character, Integer> m = new HashMap<Character, Integer>();
        
        for(char w : c) {
            m.put(w, 0);
        }
        
        for(int i=0; i<choices.length; i++) {
            char a = survey[i].charAt(0); //비동의 유형
            char b = survey[i].charAt(1); //동의 유형
            
            int choice = choices[i];
            if(choice < 4) {
                m.put(a, (m.get(a) + scores[choice]));
            }else if(choice > 4) {
                m.put(b, (m.get(b) + scores[choice]));
            }
        }
        
        for(int i=0; i<c.length; i+=2) {
            char a = c[i];
            char b = c[i+1];
            int as = m.get(a);
            int bs = m.get(b);
            
            answer += (as >= bs) ? a : b;
        }
        
        return answer;
    }
}