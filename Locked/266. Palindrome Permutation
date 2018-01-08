//There must be even number of all characters and atmost one odd number
class Solution {
    public boolean canPermutePalindrome(String s) {
        int[] map = new int[256];
        int odd = 0;
        for(char c:s.toCharArray()){
            map[c]++;
        }
        for(int i:map){
            if(i%2!=0)
                odd++;
        }
        return (odd<=1);
    }
}
