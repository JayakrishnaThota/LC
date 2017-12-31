class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        helper(list, new ArrayList<>(), nums, 0);
        return list;
    }
    private void helper(List<List<Integer>> list, List<Integer> sublist, int [] nums, int start){
        list.add(new ArrayList<>(sublist));
        for(int i = start; i < nums.length; i++){
            if(i > start && nums[i] == nums[i-1]) continue; 
            sublist.add(nums[i]);
            helper(list, sublist, nums, i + 1);
            sublist.remove(sublist.size() - 1);
    }
} 
}
