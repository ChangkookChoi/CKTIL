import java.util.HashSet;
//가장 많은 종류의 폰켓몬을 선택해 개수를 return
//중복 제거한 폰켓몬 수를 리턴해주면 된다. N/2마리까지 선택 가능하다.
//[3,3,1,1,1,5,4,2] 8마리, 종류 5 set.size는5, nums/2는 4 ==> 4
//중복 제거해서 보여주는 건 HashSet이 좋은 듯 하다
class Solution {
    public int solution(int[] nums) {
        int answer = 0;
        HashSet<Integer> set = new HashSet<Integer>();
        for(int i=0; i<nums.length; i++) {
            set.add(nums[i]);
        }
        
        answer = set.size() > (nums.length/2) ? nums.length/2 : set.size();
        return answer;
    }
}