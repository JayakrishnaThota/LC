//O(nlogn) time and O(n) space
class Solution {
    public int[] avoidFlood(int[] rains) {
        if(rains==null || rains.length==0) return new int[0];
        Map<Integer, Integer> map = new HashMap();
        TreeSet<Integer> set = new TreeSet();
        int n = rains.length;
        int[] result = new int[n];
        for(int i=0;i<rains.length;i++) {
            if(rains[i]==0) {
                set.add(i);
                result[i] = 1;
            }
            else {
                if(map.containsKey(rains[i])) {
                    Integer ceiling = set.ceiling(map.get(rains[i]));
                    if(ceiling==null) {
                        return new int[]{};
                    }
                    result[ceiling] = rains[i];
                    set.remove(ceiling);
                }
                map.put(rains[i], i);
                result[i] = -1;
            }
        }
        return result;
    }
}
