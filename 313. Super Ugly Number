//O(kn) and O(max(k,n))
class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] index = new int[primes.length];
        int[] ugly = new int[n+1];
        ugly[0] = 1;
        for(int i=1;i<=n;i++){
            ugly[i] = Integer.MAX_VALUE;
            for(int j=0;j<index.length;j++){
                ugly[i] = Math.min(ugly[i],ugly[index[j]]*primes[j]);
            }
            for(int j=0;j<index.length;j++){
                while(ugly[index[j]]*primes[j]<=ugly[i]) index[j]++;
            }
        }
        return ugly[n-1];
    }
}
