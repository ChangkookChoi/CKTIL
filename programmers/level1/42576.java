import java.util.Map;
import java.util.HashMap;

//해시 문제
class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        Map<String, Integer> cMap = new HashMap<String, Integer>();
        
        //Null 방지 초기화를 위해 모든 인원 입력
        for(String p : participant) {
            cMap.put(p, 0);
        }
        
        //동명이인 체크 오류 방지를 위해 이름마다 완주자 수 기록
        for(String s : completion) {
            cMap.put(s, cMap.get(s)+1);
        }
        
        for(String p : participant) {
            //완주한 경우
            if(cMap.get(p) > 0) {
                cMap.put(p, cMap.get(p) -1); //완주 처리
            }else{
                //완주 못 한 경우
                answer = p;
            }
        }
        
        return answer;
    }
}
/*
문제 설명

수많은 마라톤 선수들이 마라톤에 참여하였습니다. 단 한 명의 선수를 제외하고는 모든 선수가 마라톤을 완주하였습니다.
마라톤에 참여한 선수들의 이름이 담긴 배열 participant와 완주한 선수들의 이름이 담긴 배열 completion이 주어질 때, 완주하지 못한 선수의 이름을 return 하도록 solution 함수를 작성해주세요.
제한사항
마라톤 경기에 참여한 선수의 수는 1명 이상 100,000명 이하입니다.
completion의 길이는 participant의 길이보다 1 작습니다.
참가자의 이름은 1개 이상 20개 이하의 알파벳 소문자로 이루어져 있습니다.
참가자 중에는 동명이인이 있을 수 있습니다.

다른 샤람의 풀이
- 굳이 나처럼 일일이 초기화 하지 않고, getOrDefault를 쓴게 인상적이고,
keySet으로 풀어낸 것도 좋다고 생각한다.
HashMap<String, Integer> hm = new HashMap<>();
for (String player : participant) hm.put(player, hm.getOrDefault(player, 0) + 1);
for (String player : completion) hm.put(player, hm.get(player) - 1);

for (String key : hm.keySet()) {
    if (hm.get(key) != 0){
        answer = key;
    }
}
*/