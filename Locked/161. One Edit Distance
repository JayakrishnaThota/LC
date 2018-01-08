//O(min(m,n)) time and O(1) space
class Solution {
    public boolean isOneEditDistance(String s, String t) {
        int m = s.length(), n = t.length();
        if(Math.abs(m-n)>1) return false;
        for(int i=0;i<Math.min(s.length(),t.length());i++){
            if(s.charAt(i)!=t.charAt(i)){
                //If they are of both same length, change the rest of the strings
                if(s.length()==t.length())
                    return s.substring(i+1).equals(t.substring(i+1));
                else if(s.length()>t.length())
                    return s.substring(i+1).equals(t.substring(i));
                else 
                    return t.substring(i+1).equals(s.substring(i));
            }
        }
        return (Math.abs(s.length()-t.length())==1);
    }
}
