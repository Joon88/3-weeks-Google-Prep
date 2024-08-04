import java.util.*;

public class binarySearch {
    public static void main(String args[]) {
        int[] arr = new int[]{1,3,5,7,9};
        System.out.println(Arrays.binarySearch(arr, 0, arr.length, 110));
        System.out.println(binarySearch(arr, 110));
    }
    
    private static int binarySearch(int[] arr, int key) {
        int left = 0, right = arr.length-1;
        
        while(left <= right) {
            int mid = (left+right)/2;

            if(arr[mid] == key)
                return mid;

            if(arr[mid] > key)
                right = mid-1;
            else if(arr[mid] < key)
                left = mid+1;
        }
        return -(left+1);
    }
}
