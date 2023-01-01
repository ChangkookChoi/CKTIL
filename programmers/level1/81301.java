//2021 카카오 채용연계형 인턴십 숫자 문자열과 영단어
class Solution {
    public int solution(String s) {
        return Integer.parseInt(replaceNum(s));
    }
    
    public String replaceNum(String s) {
        return s.replaceAll("zero", "0")
                .replaceAll("one", "1")
                .replaceAll("two", "2")
                .replaceAll("three", "3")
                .replaceAll("four", "4")
                .replaceAll("five", "5")
                .replaceAll("six", "6")
                .replaceAll("seven", "7")
                .replaceAll("eight", "8")
                .replaceAll("nine", "9");
    }
}