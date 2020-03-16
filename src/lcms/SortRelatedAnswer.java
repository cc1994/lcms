package lcms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://www.cnblogs.com/guoyaohua/p/8600214.html
public class SortRelatedAnswer {

    public static void main(String[] args) {
        int[] array = new int[] { 7, 8, 3, 4, 65, 8, 12, 45, 64, 21};
//        int[] array = new int[] {3,2,1};
        heapSort(array);
        int index = binSearchLoop(64, array);
        System.out.println(index);
//        for (int i = 0; i < result.length; i++) {
//            System.out.println(result[i]);
//        }
    }
    
    // 贪心算法
    // 活动安排问题，有n项活动使用同一个礼堂，每项活动分别在Si开始，Ei结束。例如S={1,2,4,6,8}，E={3,5,7,8,10}。
    // 在所给的活动集合中选出最大的相容活动子集合。
    // 对E升序排序后，每次都选择结束时间最早的活动
    public static List<Integer> arrangeActivity(int[] s, int[] e) {
        int endFlag = e[0];
        List<Integer> results = new ArrayList<>();
        results.add(0);
        for (int i = 0; i < s.length; i++) {
            if (s[i] > endFlag) {
                results.add(i);
                endFlag = e[i];
            }
        }
        return results;
    }
    
    // 快速排序 O(nlogn)
    // 通过一趟排序将待排记录分隔成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，以达到整个序列有序。
    // 双指针法，从数组的两端分别进行比对，把小于和大于基准数的值分别放置于基准两边
    // 返回pivot(基准)在数组的位置
    public static void quickSort(int[] array, int start, int end) {
        if (start > end || array.length < 1 || start < 0 || end > array.length) {
            return ;
        }
        
        // 选第一个数字作为基准，跟下面的顺序有关
        int pivot = array[start];
        int pivotIndex = start;
        
        int i = start, j = end;
        while(i < j) {
            // 优先级1 先从右边开始往左找，直到找到比base值小的数
            while(array[j] >= pivot && i <j) {
                j--;
            }
            // 优先级2 再从左往右边找，直到找到比base值大的数
            while (array[i] < pivot && i < j) {
                i++;
            }
            swap(array, i, j);
        }
        swap(array, pivotIndex, i);
        
        quickSort(array, start, i -1);
        quickSort(array, i + 1, end);
    }

    // 交换数组中指定位置的元素
    private static boolean swap(int[] array, int place1, int place2) {
        if (null == array || place1 < 0 || place1 > (array.length - 1) || place2 < 0 || place2 > (array.length - 1)) {
            return false;
        }
        int value2 = array[place2];
        array[place2] = array[place1];
        array[place1] = value2;
        return true;
    }
    
    // 归并排序 O(nlogn)
    // 分治法
    public static int[] mergeSort(int[] array) {
        // 只有一个数字的时候返回
        if (null == array || array.length < 2) {
            return array;
        }
        int mid = array.length / 2;
        int[] leftArray = mergeSort(Arrays.copyOfRange(array, 0, mid));
        int[] rightArray = mergeSort(Arrays.copyOfRange(array, mid, array.length));
        return merge(leftArray, rightArray);
    }

    // 合并两个有序数组
    private static int[] merge(int[] leftArray, int[] rightArray) {
        int[] result = new int[leftArray.length + rightArray.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= leftArray.length) {
                result[index] = rightArray[j];
                j++;
            }else if (j >= rightArray.length) {
                result[index] = leftArray[i];
                i++;
            }else if (leftArray[i] <= rightArray[j]) {
                result[index] = leftArray[i];
                i++;
            }else if(leftArray[i] > rightArray[j]){
                result[index] = rightArray[j];
                j++;
            }
        }
        return result;
    }
    
    // 堆排序 大顶堆（升序）
    // 完全二叉树
    public static int[] heapSort(int[] array) {
        int length = array.length;
        // 1.构建大顶堆
        buildMaxHeap(array);
        // 2.循环将堆首位（最大值）与末位交换，然后在重新调整最大堆
        while(length > 0) {
            swap(array, 0, length - 1);
            length--;
            adjustMaxHeap(array, 0, length);
        }
        return array;
    }

    private static void buildMaxHeap(int[] array) {
        // 完全二叉树最后一个非叶子节点的序号是n/2-1
        // 从第一个非叶子结点从下至上，从右至左调整结构
        for(int i = (array.length / 2 -1); i >= 0; i--) {
            adjustMaxHeap(array, i, array.length);
        }
    }
    
    private static void adjustMaxHeap(int[] array, int start, int end) {
        int leftIndex = 2 * start + 1;
        
        while (leftIndex < end) {
            int rightIndex = leftIndex + 1;
            // 存在右孩子，且右孩子>左孩子, 选取右孩子
            if (rightIndex < end  && array[rightIndex] > array[leftIndex]) {
                leftIndex = rightIndex;
            }
            // 父节点大于两个子节点
            if (array[start] >= array[leftIndex]) {
                break;
            }
            // 父节点与子节点中较大的进行交换
            swap(array, start, leftIndex);
            
            // 从交换的子节点继续向下筛选
            start = leftIndex;
            leftIndex = 2 * start + 1;
        }
        return;
    }
    
    //冒泡排序 T(n) = O(n2)
    public static int[] bubbleSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
        }
        return array;
    }
    
    // 二分查找(循环)
    public static int binSearchLoop(int key, int[] array) {
        int low = 0;
        int high = array.length - 1;
        int middle = 0;
        if (key < array[low] || key > array[high] || low > high) {
            return -1;
        }
        while(low <= high) {
            middle = (low + high) / 2;
            if (array[middle] == key) {
                return middle;
            } else if (array[middle] < key) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }
    
    // 二分查找(递归)
    public static int binSearchRecursive(int key, int[] array, int low,int high) {
        if (low > high || array[low] > key || array[high] < key) {
            return -1;
        }
        int mid = (low + high) / 2;
        if (array[mid] == key) {
            return mid;
        }
        if (array[mid] < key) {
            return binSearchRecursive(key, array, mid + 1, high);
        }
        if (array[mid] > key) {
            return binSearchRecursive(key, array, 0, mid - 1);
        }
        return -1;
    }
}
