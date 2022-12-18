//가장 가까운 글자

class Solution {
    public int[] solution(String s) {
        int[] answer = new int[s.length()];
        
        for(int i=0; i<s.length(); i++) {
            answer[i] = getIdx(i, s);
        }
        return answer;
    }
    
    public int getIdx(int idx, String str) {
        int answer = -1;
        char c = str.charAt(idx);
        for(int i=0; i<idx; i++) {
            char compare = str.charAt(i);
            if(compare == c) {
                answer = idx - i;
            }
        }
        
        return answer;
    }
}