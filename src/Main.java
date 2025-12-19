import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Laboratory Work №6 — Collections in Java.
 *
 * <p>For record book number 9085:</p>
 * <ul>
 *   <li>C2 = 1 → {@link Set}</li>
 *   <li>C3 = 1 → singly linked list</li>
 * </ul>
 *
 * <p>Element type: {@link CoffeeProduct} from Lab5.</p>
 */
public class Main
{
    /**
     * Program entry point.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args)
    {
        try
        {
            final int recordBookNumber = 9085;
            final int c2 = recordBookNumber % 2;
            final int c3 = recordBookNumber % 3;

            System.out.println("Record book number = " + recordBookNumber);
            System.out.println("C2 = " + c2);
            System.out.println("C3 = " + c3);
            System.out.println();

            CoffeeProduct a = new CoffeeBeans("Arabica Beans", "Colombia Supremo", 1.0, 0.0045, 650.00, 88, RoastLevel.MEDIUM);
            CoffeeProduct b = new GroundCoffee("Ground Coffee", "Ethiopia Yirgacheffe", 0.5, 0.0028, 420.00, 92, GrindLevel.MEDIUM);
            CoffeeProduct c = new InstantCoffeeJar("Instant Coffee Jar", "Gold Classic", 0.2, 0.0014, 310.00, 78, "Glass");
            CoffeeProduct d = new InstantCoffeeSachets("Instant Sachets", "3-in-1 Pack", 0.3, 0.0025, 210.00, 55, 30);

            CoffeeProductLinkedSet empty = new CoffeeProductLinkedSet();
            System.out.println("Empty size (initial): " + empty.size());
            empty.add(a);
            empty.add(b);
            System.out.println("Empty size (after add): " + empty.size());
            empty.remove(a);
            System.out.println("Empty size (after remove): " + empty.size());
            System.out.println();

            CoffeeProductLinkedSet single = new CoffeeProductLinkedSet(a);
            System.out.println("Single size (initial): " + single.size());
            System.out.println("Single contains a: " + single.contains(a));
            System.out.println("Single add duplicate a: " + single.add(a));
            single.add(c);
            System.out.println("Single size (after adding c): " + single.size());
            System.out.println();

            List<CoffeeProduct> initial = new ArrayList<>();
            initial.add(a);
            initial.add(b);
            initial.add(c);
            initial.add(d);

            CoffeeProductLinkedSet fromCollection = new CoffeeProductLinkedSet(initial);

            System.out.println("From collection size: " + fromCollection.size());
            System.out.println("From collection containsAll(initial): " + fromCollection.containsAll(initial));
            System.out.println();

            CoffeeProduct equalToB = new GroundCoffee("Ground Coffee", "Ethiopia Yirgacheffe", 0.5, 0.0028, 420.00, 92, GrindLevel.MEDIUM);
            System.out.println("Contains new object equal to b: " + fromCollection.contains(equalToB));
            System.out.println();

            System.out.println("Iterate set (before iterator remove):");
            for (CoffeeProduct p : fromCollection)
            {
                System.out.println(p);
            }
            System.out.println();

            Iterator<CoffeeProduct> it = fromCollection.iterator();
            while (it.hasNext())
            {
                CoffeeProduct p = it.next();
                if (p.equals(c))
                {
                    it.remove();
                    break;
                }
            }

            System.out.println("After iterator.remove(c): size=" + fromCollection.size());
            System.out.println();

            Set<CoffeeProduct> std = new HashSet<>(initial);
            std.remove(c);

            System.out.println("Equals standard HashSet after same removals: " + fromCollection.equals(std));
            System.out.println();

            CoffeeProduct[] arr = fromCollection.toArray(new CoffeeProduct[0]);
            System.out.println("toArray result: " + Arrays.toString(arr));
            System.out.println();

            List<CoffeeProduct> keepOnly = List.of(a, d);
            boolean retained = fromCollection.retainAll(keepOnly);
            System.out.println("retainAll([a, d]) changed: " + retained);
            System.out.println("After retainAll: " + Arrays.toString(fromCollection.toArray(new CoffeeProduct[0])));
            System.out.println();

            fromCollection.clear();
            System.out.println("After clear size: " + fromCollection.size());
        }
        catch (Exception e)
        {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
