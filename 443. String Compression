//O(n) time and O(1) space
class Solution {
    public int compress(char[] chars) {
        //Sanity check
        if(chars==null || chars.length==0) return 0;
        int index = 0,count=1;
        for(int i=0;i<chars.length;i++){
            if(i==chars.length-1 || chars[i]!=chars[i+1]){
                if(count==1){
                    chars[index++] = chars[i];
                }
                else{
                    chars[index++] = chars[i];
                    String key = String.valueOf(count);
                    for(char c:key.toCharArray()){
                        chars[index++] = c;
                    }
                }
                count = 1;
            }
            else
                count++;
        }
        return index;
    }
}
