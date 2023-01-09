//2023 Kakao blind recruitment 개인정보 수집 유효기간
/*
    풀긴 했는데, 문제 조건을 명확하게 확인 할 것.
    머릿 속에 조건을 제대로 정리해둔 뒤 들어가야 2번 작업하지 않는다.
    
*/

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

class Solution {
    public int[] solution(String today, String[] terms, String[] privacies) {
        int[] answer = new int[privacies.length];
        ArrayList<Integer> a = new ArrayList<Integer>();
        try {
            //today 오늘 날짜
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            Date to = sdf.parse(today);
            //terms 유효기간(달)이 담겨있는데, 만료일을 map으로 담기 ["A 6", "B 12", "C 3"]
            Map<String, String> term = new HashMap<String, String>();
            for(String te : terms) {
                term.put(te.split(" ")[0], te.split(" ")[1]);
            }
            
            int idx = 0;
            for(int i=0; i<privacies.length; i++) {
                String p = privacies[i];
                
                String agDay = p.split(" ")[0]; //2021.05.02
                String type = p.split(" ")[1]; //A B C

                Date isEx = sdf.parse(getChangedDate(agDay, term.get(type)));
                
                long diffSec = (to.getTime() - isEx.getTime()) / 1000; //초 차이
                long diffDay = diffSec / (24*60*60);
                
                if(diffDay >= 0) {
                    a.add(i+1);
                }
            }
            
        }catch(Exception e) {
            
        }
        
        answer = new int[a.size()];
        for(int i=0; i<a.size(); i++) {
            answer[i] = a.get(i);
        }
        
        return answer;
    }

    public String getChangedDate(String agDay, String typeMonth) {
        String rtn = "";
        String year = agDay.substring(0, 4);
        String month = agDay.substring(5, 7);
        String day = agDay.substring(8, 10);

        int m = Integer.parseInt(month) + Integer.parseInt(typeMonth);
        
        //12월이 넘는 경우 년 올리고, 남은 걸 m으로 넣어주기
        if(m > 12) {
            int y = m/12;
            year = Integer.toString(Integer.parseInt(year) + y);
            m = m - (y*12);
        }

        //m이 10월 이하인 경우 포맷을 맞추기 위해 앞에 0 붙여주기
        month = (m<10) ? "0"+Integer.toString(m) : Integer.toString(m);
        
        rtn = year+"."+month+"."+day;
        return rtn;
    }
}

/*
고객의 약관 동의를 얻어서 수집된 1~n번으로 분류되는 개인정보 n개가 있습니다. 약관 종류는 여러 가지 있으며 각 약관마다 개인정보 보관 유효기간이 정해져 있습니다. 당신은 각 개인정보가 어떤 약관으로 수집됐는지 알고 있습니다. 수집된 개인정보는 유효기간 전까지만 보관 가능하며, 유효기간이 지났다면 반드시 파기해야 합니다.

예를 들어, A라는 약관의 유효기간이 12 달이고, 2021년 1월 5일에 수집된 개인정보가 A약관으로 수집되었다면 해당 개인정보는 2022년 1월 4일까지 보관 가능하며 2022년 1월 5일부터 파기해야 할 개인정보입니다.
당신은 오늘 날짜로 파기해야 할 개인정보 번호들을 구하려 합니다.

모든 달은 28일까지 있다고 가정합니다.
*/