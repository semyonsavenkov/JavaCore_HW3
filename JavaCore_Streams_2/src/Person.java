import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Person {
    private String name;
    private String family;
    private Integer age;
    private Sex sex;
    private Education education;

    public Person(String name, String family, int age, Sex sex, Education education) {
        this.name = name;
        this.family = family;
        this.age = age;
        this.sex = sex;
        this.education = education;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public Integer getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public Education getEducation() {
        return education;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", education=" + education +
                '}';
    }

    public static Collection<Person> generateData() {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)]));
        }
        return persons;
    }

    public static long getTeens(Collection<Person> persons) {
        Stream<Person> stream = persons.stream()
                .filter(x -> x.age < 18 );
        long result = stream.count();
        return result;
    }

    public static void getRecruits(Collection<Person> persons) {
        List<String> stream = persons.stream()
                .filter(x -> x.age > 18 )
                .filter(x -> x.age < 27)
                .filter(x -> x.sex == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println(stream);
    }

    public static void getAdults(Collection<Person> persons) {
        List<String> stream = persons.stream()
                .filter(x -> (x.age > 18 && x.age > 60 && x.sex == Sex.WOMAN) || (x.age > 18 && x.age > 65 && x.sex == Sex.MAN))
               // .filter(x -> x.age > 18 && x.age > 65 && x.sex == Sex.MAN)
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::toString)
                .collect(Collectors.toList());
        System.out.println(stream);

    }

}
