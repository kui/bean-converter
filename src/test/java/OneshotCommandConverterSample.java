import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.k_ui.beanconverter.BeanConverter;
import jp.k_ui.beanconverter.json.command.OneshotCommandConverter;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * {@link OneshotCommandConverter} samples
 */
public class OneshotCommandConverterSample {
  public static void main(String[] args) throws Exception {
    DateFormat format = DateFormat.getDateInstance();
    Person foo = new Person("Foo Smith", format.parse("1985/6/18"));
    Person bar = new Person("Bar Smith", format.parse("1985/9/16"));
    Person qux = new Person("Qux Doe", format.parse("1991/7/3"));

    // ▼sample 1▼
    // replace names
    String[] editingCommand = new String[] { "sed", "-e", "s/Smith/Norris/" };
    BeanConverter<Person, Person> nameEditing = new OneshotCommandConverter<>(Person.class, editingCommand);

    System.out.println("# edited");
    System.out.println(nameEditing.convert(foo));
    // => Person [name=Foo Norris, birthDay=Tue Jun 18 00:00:00 JST 1985]
    System.out.println("# not-edited");
    System.out.println(nameEditing.convert(qux));
    // => Person [name=Qux Doe, birthDay=Wed Jul 03 00:00:00 JST 1991]

    System.out.println("---");

    // ▼sample 2▼ (require ruby)
    // filter persons, output stderr
    List<String> eightiesFilterCommand = Arrays.asList("ruby", "-rjson", "-rdate", "-e",
        "t = Date.parse('1989/12/31 23:59:59.999').strftime('%s').to_i * 1000;" +
            "puts JSON.parse(STDIN.read)" +
            ".tap{|s|STDERR.puts '# before: ' + s.inspect}" +
            ".reject{|p| p['birthDay'] < t }" +
            ".tap{|s|STDERR.puts '# after:  ' + s.inspect}" +
            ".to_json");
    TypeReference<Set<Person>> personSetType = new TypeReference<Set<Person>>() {};
    BeanConverter<Set<Person>, Set<Person>> eightiesFilter =
        new OneshotCommandConverter<>(personSetType, eightiesFilterCommand);

    Set<Person> persons = new HashSet<>(Arrays.asList(foo, bar, qux));
    System.out.println(eightiesFilter.convert(persons));
    // => [Person [name=Qux Doe, birthDay=Wed Jul 03 00:00:00 JST 1991]]
  }

  static class Person {
    private String name;
    private Date birthDay;

    public Person() {}

    public Person(String name, Date birthDay) {
      this.name = name;
      this.birthDay = birthDay;
    }

    public String getName() {
      return name;
    }

    public Date getBirthDay() {
      return birthDay;
    }

    @Override
    public String toString() {
      return "Person [name=" + name + ", birthDay=" + birthDay + "]";
    }
  }
}
