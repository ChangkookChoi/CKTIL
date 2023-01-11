//소수찾기
//에라토스테네스의 체 라는 방식인데 몇배는 빠르다.
/*
일정한 규칙성을 고려해 소수인 경우 자신의 배수는 미리 지워
여러 번  작업하지 않도록 하는 게  특징
*/
class Solution {
    public int solution(int n) {
        int answer = 0;
        
        int[] arr = new int[n+1];
        
        //2부터 n까지의 수를 배열 idx 로 활용해 초기화
        //0은 false, 1은 true
        for(int i=2; i<=n; i++) {
            arr[i] = 1;
        }
        
        //2부터 시작해 배수들을 0으로 변경
        for(int i=2; i<=n; i++) {
            if(arr[i] == 0) continue;
            
            for(int j= 2*i; j<=n; j+=i) {
                arr[j] = 0;
            }
        }
        
        for(int i=0; i<arr.length; i++) {
            if(arr[i] == 1) {
                answer++;
            }
        }
        
        return answer;
    }
}


//기존 소스
class Solution {
    public int solution(int n) {
        int rtn = 1;
        
        for(int i=3; i<=n; i++) {
            rtn += isDecimal(i) ? 1 : 0;
        }
        
        return rtn;
    }
    
    public boolean isDecimal(int n) {
        for(int i=2; i<n; i++) {
            if(n%i == 0) {
                return false;
            }
        }
        
        return true;
    }
}
//이건 시간 초과가 뜬다. 효율적이지 못한 코드