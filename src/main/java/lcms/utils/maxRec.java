package lcms.utils;

public class maxRec {
        public static int getMaxOneCount(int []arr){
            int max = 0;
            for(int i=0;i<arr.length;i++){
                int left = (i==0)?0:i-1;
                int right = (i==arr.length-1)?arr.length-1:i+1;
                while (left>=0&& arr[i]<=arr[left])
                    left--;
                while (right<arr.length && arr[i]<=arr[right])
                    right++;
                int sum=(right-left-1)*arr[i];
                max=max>sum?max:sum;
            }
            return  max;
        }
        
        public  static int maxSize(int[][] arr){
            int res[]=new int[arr[0].length];
            int max=0;
            // 直方图 以当前行为底，该列有几个连续的1
            for(int i=0;i<arr.length;i++){
                for(int j=0;j<arr[0].length;j++){
                    // 如果为0则0，如果为1则在上一行结果基础上加一
                    res[j]=(arr[i][j]==0) ? 0:(res[j]+1);
                }
                // 直方图 以当前为底最大矩阵面积
                int sum=getMaxOneCount(res);
                max=max>sum? max:sum;
            }
            return max;
        }
        // 1 0 1 0 0
        // 1 1 1 0 1
        // 1 1 0 1 1
        // 1 0 0 1 0
        public static void main(String args[]){
            int arr[][]={{1 ,0, 1, 0 ,0},{1 ,1 ,1 ,0 ,1},{1, 1, 0 ,1, 1},{1 ,0 ,0 ,1 ,0}};
            System.out.println(maxSize(arr));
        }
}
