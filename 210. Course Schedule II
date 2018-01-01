class Solution {
    ArrayList<Integer>[] vertexlist;
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        initialise(numCourses, prerequisites);
        return toplogicalsort(numCourses,prerequisites); 
    }
    public void initialise(int numCourses, int[][] prerequisites){
        vertexlist = new ArrayList[numCourses];
        for(int i=0;i<numCourses;i++)
            vertexlist[i] = new ArrayList();
        for(int[] p: prerequisites){
            int dependent = p[0];
            vertexlist[dependent].add(p[1]);
        }
    }
    public int[] toplogicalsort(int numCourses, int[][] prerequisites){
        int count=0;
        int[] result = new int[numCourses];
        Queue<Integer> q = new LinkedList();
        int[] indegree = new int[numCourses];
        for(int[] p : prerequisites){
            indegree[p[1]]++;
        }
        for(int i=0;i<numCourses;i++){
            if(indegree[i]==0)
                q.add(i);
        }
        while(!q.isEmpty()){
            int temp = q.poll();
            result[numCourses-1-count++] = temp;
            for(int i:vertexlist[temp]){
                if(--indegree[i]==0)
                    q.add(i);
            }
        }
        return (count==numCourses)?result:new int[0];
    }
}
