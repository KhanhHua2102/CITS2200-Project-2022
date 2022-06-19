// Quang Khanh Hua (22928469), Nathan Pham (23082182)
/**
 * An implementation of the Fines problem from the 2022 CITS2200 Project
 */
public class FinesImpl implements Fines {
  /**
   * {@inheritdoc}
   */
  private long fines = 0;

  /**
   * countFines implementation to count and
   * return the total amount of fines for a given order of ship priorities
   * @param priorities the priorities of each ship in the order they passed
   * @return the total amount of fines
   */
  public long countFines(int[] priorities) {
    // TODO: Implement your solution
    // insert your code here

    int length = priorities.length;

    // Implementing merge sort to sort in descending order and count the total fines
    mergeSort(priorities, 0, length - 1);
    return fines;
  }

  /**
   * Implementation of merge sort with modification for counting fines
   * @param arr the input unsorted array
   * @param l   left index
   * @param r   right index
   */
    public void mergeSort (int[] arr, int l, int r){
      if (l < r) {
        int m = l + (r - l) / 2;
        mergeSort(arr, l, m);
        mergeSort(arr, m + 1, r);
        merge(arr, l, m, r);
      }
    }
    private void merge (int[] arr, int l, int m, int r){
      int n1 = m - l + 1;
      int n2 = r - m;
      int[] L = new int[n1];
      int[] R = new int[n2];

      System.arraycopy(arr, l, L, 0, n1);
      System.arraycopy(arr, m + 1, R, 0, n2);

      int i = 0;
      int j = 0;
      int k = l;

      while (i < n1 && j < n2) {
        if (L[i] >= R[j]) {
          arr[k++] = L[i++];
        }
        else {
          arr[k++] = R[j++];
          // Accumulating fines for each ship that fail to give way
          fines += n1 - i;
        }
      }

      while (i < n1) {
        arr[k++] = L[i++];
      }
      while (j < n2) {
        arr[k++] = R[j++];
      }
    }
  }