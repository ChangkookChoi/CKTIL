import java.util.Arrays;
import java.util.Collections;

//과일 장수
class Solution {
    public int solution(int k, int m, int[] score) {
        int answer = 0;
        int[] ta = new int[m];
        int[] arr = new int[score.length];
        
        int cycleCnt = score.length/m;
        if(cycleCnt < 1) {
            return answer;
        }
        Arrays.sort(score);
        arr = reverse(score); //descending
        
        int t = 0;
        for(int i=0; i<cycleCnt; i++) {
            ta = new int[m];
            
            if(t <= arr.length) {
                for(int j=0; j<m; j++) {
                    ta[j] = arr[t];
                    t++;
                }
                Arrays.sort(ta);
                answer += (ta[0] * m);
            }else{
                break;
            }
        }
        
        return answer;
    }
    
    public static int[] reverse(int[] input) {
        int l = input.length;
        int[] t = new int[l];
        
        for(int i=0; i<input.length; i++) {
            t[i] = input[l - 1 - i];
        }
        
        return t;
    } 

}