// SortingAlgorithms Source Code
// Name: Shyam Venkatesan
/* Implements Mergesort and median-of three Quicksort algorithms.
   Then, arrays of varied lengths (10, 100, 1000) and generation (random, almost ordered) are filled.
   The arrays are sorted using merge and quick sort and the execution time durations are calculated and displayed.
*/

// Imports the Java Math class for the Math.random method for random values in arrays
import java.lang.Math;

// The SortingAlgorithms class
public class SortingAlgorithms 
{
    // The overloaded mergeSort method that takes in an integer array and has no return. Used for initial method call.
    public static void mergeSort(int arr[])
    {
        // Checks if array has less than 2 elements (no sorting), outputs message, and returns
        if(arr.length < 2)
        {
            System.out.println("1 or 0 elements, already sorted");
            return;
        }
        // Calls second overloaded mergeSort method with arr, left value of 0 and right value of length - 1
        mergeSort(arr, 0, arr.length - 1);
    }
    // The overloaded mergeSort method that takes in an integer array, left integer, right integer, and has no return
    private static void mergeSort(int arr[], int left, int right) 
    {
        // Divides the array as long as there isn't only 1 element (left = right)
        if (left < right) 
        {
            // Finds the midpoint
            int mid = (left + right) / 2;
 
            // Recursively divides the left and right halves of the array
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
 
            // Merges and sorts the two halves
            merge(arr, left, mid, right);
        }
    }
    
    // The merge method that takes in an integer array, left integer, right integer, middle integer and has no return
    private static void merge(int arr[], int left, int mid, int right) {
        // Find lengths of two subarrays that will be merged
        int len1 = mid - left + 1;
        int len2 = right - mid;
 
        // Create temporary left and right arrays with len1 and len2, respectively
        int leftArr[] = new int[len1];
        int rightArr[] = new int[len2];
 
        // Copies data from the original array to each temporary array
        for (int i = 0; i < len1; i++)
            leftArr[i] = arr[left + i];
        for (int i = 0; i < len2; i++)
            rightArr[i]= arr[mid + 1 + i];
 
        // Initial indices of left and right subarrays
        int i = 0, j = 0;
 
        // Initial index of merged array
        int k = left;
        // Iterates as long as each index is less than the corresponding array length (enough comparable elements)
        while (i < len1 && j < len2) 
        {
            // Compares left and right array values for sorting, and stores the smaller one in the merged array
            if (leftArr[i] <= rightArr[j]) 
            {
                arr[k] = leftArr[i];
                // Moves one index right on the left array
                i++;
            } 
            else 
            {
                arr[k] = rightArr[j];
                // Moves one index right on the right array
                j++;
            }
            // Moves one index right in the merged array
            k++;
        }
 
        // Remaining elements of the left array appended to the merged array
        while (i < len1) 
        {
            arr[k] = leftArr[i];
            i++;
            k++;
        }
        
        // Remaining elements of the right array appended to the merged array
        while (j < len2) 
        {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }
    
    // The overloaded quickSort method that takes in an integer array and has no return. Used for initial method call. 
    public static void quickSort(int[] arr) 
    {
        // Checks if array has less than 2 elements (no sorting), outputs message, and returns
        if (arr.length < 2) 
        {
            System.out.println("1 or 0 elements, already sorted");
            return;
        }
        // Calls second overloaded quickSort method with arr, left value of 0 and right value of length - 1
        quickSort(arr, 0, arr.length - 1);
    }

    // The overloaded quickSort method that takes in an integer array, left integer, right integer, and has no return
    private static void quickSort(int[] arr, int left, int right) 
    {
        // Partitions the array as long as there isn't only 1 element (left = right)
        if (left < right) 
        {
            // If the distance between right and left values is less than 15, the cutoff condition is met
            if (right - left <= 15) 
            {
                // Insertion sort is called with array arr, and integers left and right
                insertionSort(arr, left, right);
            } 
            // Non-cutoff case
            else 
            {
                // Finds the midpoint
                int mid = left + (right - left) / 2;
                
                //Median-of-three Partitioning (sorts the three and median value is found at the mid position)
                
                // If left value > mid value, the two are swapped
                if (arr[left] > arr[mid])
                    swap(arr, left, mid);
                // If left value > right value, the two are swapped
                if (arr[left] > arr[right])
                    swap(arr, left, right);
                // If mid value > right value, the two are swapped
                if (arr[mid] > arr[right])
                    swap(arr, mid, right);
                // The pivot point is the value stored at the mid point of the array
                int pivot = arr[mid];
                
                // The partition value, par, is found by calling the partition method with array arr, and integers left, right, and pivot
                int par = partition(arr, left, right, pivot);
                // Calls quickSort recursively on the left and right subarrays formed around the partition point
                quickSort(arr, left, par - 1);
                quickSort(arr, par + 1, right);
            }
        }
    }
    
    // The partition method that takes in an integer array, left integer, right integer, pivot integer, and has no return
    private static int partition(int[] arr, int left, int right, int pivot) 
    {
        // Index i starts from the left and j from the right
        int i = left;
        int j = right;
        // Loops as long as i < j
        while (i < j) 
        {
            // i increments as long as the element's value is < pivot (only stops when i is greater)
            while (arr[i] < pivot) 
            {
                i++;
            }
            // j increments as long as the element's value is > pivot (only stops when j is less than)
            while (arr[j] > pivot) 
            {
                j--;
            }
            // if the previous conditions are met and i < j, i and j are swapped, i is incremented, and j is decremented
            if (i < j) 
            {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        
        // Returns j, which is the partition point
        return j;
    }

    // The insertionSort method that takes in an integer array, left integer, right integer, and has no return
    private static void insertionSort(int[] arr, int left, int right) 
    {
        // Starts one position to the right of the left index
        for (int i = left + 1; i <= right; i++) 
        {
            // Stores the current element
            int k = arr[i];
            int j = i - 1;
            // Elements are shifted to make space for the current element
            while (j >= left && arr[j] > k) 
            {
                arr[j + 1] = arr[j];
                j--;
            }
            // The current element is placed in its location
            arr[j + 1] = k;
        }
    }

    // The swap helper method that takes in an integer array, integers x and y, and has no return
    private static void swap(int[] arr, int x, int y) 
    {
        // Current value of arr[x] stored in temp
        int temp = arr[x];
        // arr[x] given value of arr[y]
        arr[x] = arr[y];
        // arr[y] given value of temp
        arr[y] = temp;
    }

    // The main method is used to make a variety of arrays for experimental studies
    public static void main(String[] args)
    {
        // 2 exactly same arrays, a1 and a2, made and filled with random values 0-99
        int[] a1 = new int[10];
        int[] a2 = new int[10];
        for(int i = 0; i < 10; i++)
        {
            int rand = (int) (Math.random() * 100);
            a1[i] = rand;
            a2[i] = rand;
        }
        
        // Measures execution time of mergeSort
        long start1 = System.nanoTime();
        mergeSort(a1);
        long end1 = System.nanoTime();
        long len1 = end1 - start1;
        
        // Measures execution time of quickSort
        long start2 = System.nanoTime();
        quickSort(a2);
        long end2 = System.nanoTime();
        long len2 = end2 - start2;
        
        // Outputs the execution times
        System.out.println("Random Length 10");
        System.out.println("Execution Time for Mergesort (nanoseconds): " + len1);
        System.out.println("Execution Time for Quicksort (nanoseconds): " + len2);
        System.out.println();
        
        // Repeats previous logic but for 100 elements
        int[] b1 = new int[100];
        int[] b2 = new int[100];
        for(int i = 0; i < 100; i++)
        {
            int rand = (int) (Math.random() * 100);
            b1[i] = rand;
            b2[i] = rand;
        }
        
        long start3 = System.nanoTime();
        mergeSort(b1);
        long end3 = System.nanoTime();
        long len3 = end3 - start3;
        
        long start4 = System.nanoTime();
        quickSort(b2);
        long end4 = System.nanoTime();
        long len4 = end4 - start4;
        
        System.out.println("Random Length 100");
        System.out.println("Execution Time for Mergesort (nanoseconds): " + len3);
        System.out.println("Execution Time for Quicksort (nanoseconds): " + len4);
        System.out.println();
        
        // Repeats previous logic but for 1000 elements
        int[] c1 = new int[1000];
        int[] c2 = new int[1000];
        for(int i = 0; i < 1000; i++)
        {
            int rand = (int) (Math.random() * 100);
            c1[i] = rand;
            c2[i] = rand;
        }
        
        long start5 = System.nanoTime();
        mergeSort(c1);
        long end5 = System.nanoTime();
        long len5 = end5 - start5;
        
        long start6 = System.nanoTime();
        quickSort(c2);
        long end6 = System.nanoTime();
        long len6 = end6 - start6;
        
        System.out.println("Random Length 1000");
        System.out.println("Execution Time for Mergesort (nanoseconds): " + len5);
        System.out.println("Execution Time for Quicksort (nanoseconds): " + len6);
        System.out.println();
        
        // 2 exactly same arrays, a1 and a2, made and filled with almost sorted values 1-99
        int[] d1 = new int[10];
        int[] d2 = new int[10];
        for(int i = 1; i < 10; i++)
        {
            d1[i - 1] = i;
            d2[i - 1] = i;
        }
        
        // Last index in each array given value 0 to make them unsorted 
        d1[9] = 0;
        d2[9] = 0;
        
        // Measures execution time of mergeSort
        long start7 = System.nanoTime();
        mergeSort(d1);
        long end7 = System.nanoTime();
        long len7 = end7 - start7;
        
        // Measures execution time of quickSort
        long start8 = System.nanoTime();
        quickSort(d2);
        long end8 = System.nanoTime();
        long len8 = end8 - start8;
        
        // Outputs the execution times
        System.out.println("Almost Sorted Length 10");
        System.out.println("Execution Time for Mergesort (nanoseconds): " + len7);
        System.out.println("Execution Time for Quicksort (nanoseconds): " + len8);
        System.out.println();
        
        // Repeats previous logic but for 100 elements
        int[] e1 = new int[100];
        int[] e2 = new int[100];
        for(int i = 1; i < 100; i++)
        {
            e1[i - 1] = i;
            e2[i - 1] = i;
        }
        
        e1[99] = 0;
        e2[99] = 0;
        
        long start9 = System.nanoTime();
        mergeSort(e1);
        long end9 = System.nanoTime();
        long len9 = end9 - start9;
        
        long start10 = System.nanoTime();
        quickSort(e2);
        long end10 = System.nanoTime();
        long len10 = end10 - start10;
        
        System.out.println("Almost Sorted Length 100");
        System.out.println("Execution Time for Mergesort (nanoseconds): " + len9);
        System.out.println("Execution Time for Quicksort (nanoseconds): " + len10);
        System.out.println();
        
        // Repeats previous logic but for 1000 elements
        int[] f1 = new int[1000];
        int[] f2 = new int[1000];
        for(int i = 1; i < 1000; i++)
        {
            // Modulus by 100 because values need to fit in range 0-99
            f1[i - 1] = i % 100;
            f2[i - 1] = i % 100;
        }
        
        f1[999] = 0;
        f2[999] = 0;
        
        long start11 = System.nanoTime();
        mergeSort(f1);
        long end11 = System.nanoTime();
        long len11 = end11 - start11;
        
        long start12 = System.nanoTime();
        quickSort(f2);
        long end12 = System.nanoTime();
        long len12 = end12 - start12;
        
        System.out.println("Almost Sorted Length 1000");
        System.out.println("Execution Time for Mergesort (nanoseconds): " + len11);
        System.out.println("Execution Time for Quicksort (nanoseconds): " + len12);
        System.out.println();
    }
}