/*
숫자가 담긴 리스트 nums와 임의의 숫자 target이 주어집니다. 
nums 안에서 두 개의 숫자를 더해서 target이 되는 숫자들이 있다면 해당 숫자들의 인덱스를 반환하는 함수를 작성해주세요. 
Input: nums = [2,7,11,15], target = 9 Output: result = [0, 1]

*/
import java.util.List;
import java.Util.ArrayList;
import java.util.Arrays;

class Solution {
    public int[] solution(int[] nums, int target) {
        
        List<Integer> list = new ArrayList<>();

        for(int i=0; i<nums.length; i++) {
            for(int j=i+1; j<nums.length; j++) {
                if((nums[i] + nums[j]) == target) {
                    if(!list.contains(i)) {
                        list.add(i);
                    }
                    
                    if(!list.contains(j)) {
                        list.add(j);
                    }
                }
            }
        }

        int[] answer = new int[list.size()];
        if(list.size() > 0) {
            list.toArray(answer);
            Arrays.sort(answer);
        }
        
        return answer;
    }
}