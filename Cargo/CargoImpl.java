// Quang Khanh Hua (22928469), Nathan Pham (23082182)
import java.util.Arrays;

/**
 * An implementation of the Cargo problem from the 2022 CITS2200 Project
 */
public class CargoImpl implements Cargo {
  /**
   * {@inheritdoc}
   */

  /**
   * This function perform the query operations in the order given,
   * and return an array of the result of each query in the same order
   * @param stops   the number of stops on the route
   * @param queries a list of queries to be performed
   * @return an array of result mass at collect stop for each query
   */
  public int[] departureMasses(int stops, Query[] queries) {
    // TODO: Implement your solution

    // Array use to store and update the result masses in collect stop after finishing a query
    int[] result = new int[queries.length];

    // Arrays storing prefix sum at the collect stop after update mass at collect stop
    int[] sumCollect = new int[queries.length];

    // Arrays storing prefix sum at the collect stop after update mass at deliver stop
    int[] sumDeliver = new int[queries.length];

    // Initial an array storing the current mass
    int[] stopsArr = new int[stops];
    Arrays.fill(stopsArr, 0);


     // Create a Fenwick tree storing and updating mass at collect stop

    FenwickTree tree1 = new FenwickTree();
    tree1.constructFenTree(stopsArr, stops);

    // Create a Fenwick tree storing and updating mass at deliver stop
    FenwickTree tree2 = new FenwickTree();
    tree2.constructFenTree(stopsArr, stops);

    /*
     * Loop through each individual queries,
     * update two Fenwick trees accordingly,
     * then calculate the result mass at collect stops by subtracting sumDeliver from sumCollect
     */
    for (int query = 0; query < queries.length; query++) {
      int mass = queries[query].cargoMass;
      int collect = queries[query].collect;
      int deliver = queries[query].deliver;

      // Update Fenwick tree1 with mass on collect stop
      tree1.updateFenwick(stops, collect, mass);
      // Assign a prefix sum masses up until collect stop to sumCollect
      sumCollect[query] = tree1.getArrSum(collect);

      // Update Fenwick tree2 with mass on deliver stop
      tree2.updateFenwick(stops, deliver, mass);
      // Assign a prefix sum masses up until collect stop to sumDeliver
      sumDeliver[query] = tree2.getArrSum(collect);


      // Calculate result mass at collect stop by subtracting sumDeliver from sumCollect
      result[query] = sumCollect[query] - sumDeliver[query];
    }
    return result;
  }

  /**
   * Implementation of a Fenwick tree
   * Adapted from https://www.javatpoint.com/fenwick-tree-in-java
   */
  public static class FenwickTree {
    // Max size based on the max size of test cases
    final static int MAX_SIZE = 510000;
    // Initial an array with Max size for Fenwick tree
    private int[] fenArr = new int[MAX_SIZE];

    /**
     * Sum all prefix values up until the idx position
     * @param idx the destination index to sum up to
     * @return    the prefix sum value
     */
    int getArrSum(int idx) {
      int total = 0;
      idx = idx + 1;

      while (idx > 0) {
        total = total + fenArr[idx];
        // Find the parent node index from current node index by bitwise calculation
        idx -= idx & (-idx);
      }
      return total;
    }

    /**
     * Update Fenwick tree with the value v at index idx
     * @param s   the last node in Fenwick tree
     * @param idx index to perform update
     * @param v   the value to update
     */
    public void updateFenwick(int s, int idx, int v) {
      idx = idx + 1;
      while (idx <= s) {
        fenArr[idx] = fenArr[idx] + v;
        // Find the children node index from current node index by bitwise calculation
        idx = idx + (idx & (-idx));
      }
    }

    /**
     * Construct a new Fenwick tree using an input array with size s
     * @param arr the input array
     * @param s   size s
     */
    void constructFenTree(int[] arr, int s) {
      for (int i = 1; i <= s; i++) {
        fenArr[i] = 0;
      }
      for (int j = 0; j < s; j++) {
        updateFenwick(s, j, arr[j]);
      }
    }
  }
}