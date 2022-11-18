import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(1, 2, 5, 16, -1, -2, 0, 32, 3, 5, 8, 23, 4);
//        Iterator<Integer> iter = intList.iterator();
//        while (iter.hasNext()) {
//            Integer myInt = iter.next();
//            if (myInt < 0) {
//                iter.remove();
//            }
//        }
        for (int i = 0; i < intList.size(); i++) {
            Integer myInt = intList.get(i);
            if (myInt < 0) {
//                intList.set(i, 100);
                intList.remove(i);
            }

        }
        System.out.println(intList.toString());
    }

}
