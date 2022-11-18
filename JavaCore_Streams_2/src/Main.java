import java.util.*;

public class Main {

    public static void main(String[] args) {
        Collection<Person> persons = Person.generateData();
        System.out.println(Person.getTeens(persons));
    }


}
