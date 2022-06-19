import java.util.HashSet;

/**
 * An implementation of the Subsidiaries problem from the 2022 CITS2200 Project
 */
public class SubsidiariesImpl implements Subsidiaries {
  /**
   * {@inheritdoc}
   */

  /**
   * Function that finds for each transaction query the smallest company
   * for which the payment could be considered internal, if there is one
   * @param owners  the company id of each company's owner, or -1 if none
   * @param queries a list of payments from one company to another
   * @return arrays contains owners for every payer and payee company
   */
  public int[] sharedOwners(int[] owners, Query[] queries) {
    // TODO: Implement your solution

    int[] results = new int[queries.length];
    int owner1, owner2;
    int payee, payer;

    // loop through every query and add owners of both payer and payee company into result array
    for (int i = 0; i < queries.length; i++) {
      HashSet<Integer> corpSet1 = new HashSet<>();
      HashSet<Integer> corpSet2 = new HashSet<>();
      results[i] = -1;
      payee = queries[i].payee;
      payer = queries[i].payer;

      if (payer == payee) {
        results[i] = payer;
        continue;
      }

      // find owner for payer and payee company
      owner1 = owners[payee];
      owner2 = owners[payer];
      while (owner1 != -1 || owner2 != -1) {
        if (owner1 != -1) corpSet1.add(owner1);
        if (owner2 != -1) corpSet2.add(owner2);

        if (corpSet1.contains(payer)) {
          results[i] = payer;
          break;
        } else if (corpSet1.contains(owner2)) {
          results[i] = owner2;
          break;
        } else if (corpSet2.contains(payee)) {
          results[i] = payee;
          break;
        } else if (corpSet2.contains(owner1)) {
          results[i] = owner1;
          break;
        }

        // find the higher owners
        if (owner1 != -1) owner1 = owners[owner1];
        if (owner2 != -1) owner2 = owners[owner2];
      }
    }

    return results;
  }
}