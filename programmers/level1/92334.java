import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

//2022 카카오 블라인드 신고 결과 받기
class Solution {
    public int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = new int[id_list.length];
        
        //내가 신고한 사람 내역
        Map<String, HashSet<String>> myMap = new HashMap<String, HashSet<String>>();
        
        //해당 사람이 신고당한 횟수 내역
        Map<String, Integer> reMap = new HashMap<String, Integer>();
        
        for(String id : id_list) {
            myMap.put(id, new HashSet<String>());
            reMap.put(id, 0);
        }
        
        for(String r : report) {
            String reporter = r.split(" ")[0]; //신고자
            String reported = r.split(" ")[1]; //신고 당한 사람
            
            if(!myMap.get(reporter).contains(reported)) {
                myMap.get(reporter).add(reported);
                reMap.put(reported, reMap.get(reported) + 1);
            }
        }
        
        for(int i=0; i<id_list.length; i++) {
            String id = id_list[i];
            for(String r : myMap.get(id)) {
                if(reMap.get(r) >= k) {
                    answer[i]++;
                }
            }
        }
        
        return answer;
    }
}