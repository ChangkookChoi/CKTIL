//2021 카카오 블라인드 신규 아이디 추천
class Solution {
    public String solution(String new_id) {
        String answer = "";
        //1단계 new_id의 모든 대문자를 대응되는 소문자로 치환합니다.
        new_id = new_id.toLowerCase();
        //2단계 new_id에서 알파벳 소문자, 숫자, 빼기(-), 밑줄(_), 마침표(.)를 제외한 모든 문자를 제거합니다.
        String regex = "[^a-z0-9\\.\\-\\_]";
        new_id = new_id.replaceAll(regex, "");
        //3단계 new_id에서 마침표(.)가 2번 이상 연속된 부분을 하나의 마침표(.)로 치환합니다.
        while(new_id.indexOf("..") != -1) {
            new_id = new_id.replaceAll("\\.\\.", ".");
        }
        //4단계 new_id에서 마침표(.)가 처음이나 끝에 위치한다면 제거합니다.
        if (!new_id.isEmpty() && new_id.charAt(0) == '.') {
            new_id = new_id.substring(1);
        }
        if (!new_id.isEmpty() && new_id.charAt(new_id.length() - 1) == '.') {
            new_id = new_id.substring(0, new_id.length() - 1);
        }  
        //5단계 new_id가 빈 문자열이라면, new_id에 "a"를 대입합니다.
        new_id += new_id.isEmpty() ? "a" : "";
        //6단계 new_id의 길이가 16자 이상이면, new_id의 첫 15개의 문자를 제외한 나머지 문자들을 모두 제거합니다.
        //만약 제거 후 마침표(.)가 new_id의 끝에 위치한다면 끝에 위치한 마침표(.) 문자를 제거합니다.
        if(new_id.length() >= 16) {
            new_id = new_id.substring(0, 15);
            if(new_id.charAt(new_id.length() -1) == '.') {
                new_id = new_id.substring(0, new_id.length()-1);
            }
        }
        //7단계 new_id의 길이가 2자 이하라면, new_id의 마지막 문자를 new_id의 길이가 3이 될 때까지 반복해서 끝에 붙입니다.
        while (new_id.length() < 3) {
            new_id += new_id.charAt(new_id.length() - 1);
        }
        
        answer = new_id;
        return answer;
    }
}

/*
다른 사람들의 좋은 코드
2번 temp = temp.replaceAll("[^-_.a-z0-9]","");
    -> - _ . a부터 z, 0부터 9까지가 아닌 것들은 제거
3번 temp = temp.replaceAll("[.]{2,}",".");
    -> .이 2개 이상인 것들은 .으로 치환 ==> while이 아니어도 한 번에.
4번 temp = temp.replaceAll("^[.]|[.]$","");
    -> .시작 또는 종료인 경우의 것 공백 치환
*/