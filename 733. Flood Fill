class Solution {
    int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if(image[sr][sc]==newColor) return image;
        helper(image, sr, sc, newColor, image[sr][sc]);
        return image;
    }
    
    public void helper(int[][] image, int r, int c, int color, int prev){
        if(r<0 || r>=image.length || c<0 || c>=image[0].length || image[r][c]!=prev) return;
        image[r][c] = color;
        for(int[] dir:dirs){
            int x = dir[0]+r;
            int y = dir[1]+c;
            helper(image,x,y,color,prev);
        }
    }
}
