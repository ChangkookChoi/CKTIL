//2019 카카오 개발자 겨울 인턴십 > 크레인 인형뽑기 게임

import java.util.Stack;

class Solution {
    public int solution(int[][] board, int[] moves) {
        int answer = 0;
        
        Stack<Integer> stack = new Stack<>();
        for(int idx : moves) {
            idx--; //idx에 맞게 하려면 -1 처리
            for(int i=0; i<board.length; i++) {
                int[] b = board[i];
                
                if(b[idx] == 0) {
                    //타겟이 빈 곳인 경우 다음 줄로 이동
                    continue;
                }else {
                    //담긴 값과 타겟이 일치. 인형 터뜨려짐
                    if(stack.size() > 0 && (b[idx] == stack.peek())) {
                        stack.pop();
                        answer+=2;
                    }else{
                        //타겟 담기
                        stack.push(b[idx]);
                    }
                    
                    //타겟의 인형은 옮겨졌으므로 0 처리
                    board[i][idx] = 0;
                    break;
                }
            }
        }
        
        return answer;
    }
}