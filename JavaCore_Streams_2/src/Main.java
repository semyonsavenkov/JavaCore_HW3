import java.util.*;

public class Main {

    public static void main(String[] args) {
        Collection<Person> persons = Person.generateData();
        System.out.println(persons.toString());
        System.out.println();
        System.out.println(Person.getTeens(persons));
        System.out.println();
        Person.getRecruits(persons);
        System.out.println();
        Person.getAdults(persons);
    }


}
