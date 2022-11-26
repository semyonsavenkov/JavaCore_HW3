import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Integer> intArrayList = Arrays.asList(1, 2, 5, 16, -1, -2, 0, 32, 3, 5, 8, 23, 4);
        LinkedList<Integer> intList = new LinkedList<Integer>();
        intList.addAll(intArrayList);

        for (int i = intList.size()-1; i >= 0; i--) {
            Integer myInt = intList.get(i);
            if (myInt < 0) {
                intList.remove(myInt);
            }
            else if (myInt % 2 != 0) {
                intList.remove(myInt);
            }
        }
        System.out.println(intList.toString());
    }

}
