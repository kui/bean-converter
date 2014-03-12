import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.k_ui.beanconverter.BeanConverter;
import jp.k_ui.beanconverter.json.command.InteractiveCommandConverter;
import jp.k_ui.beanconverter.utils.streamgrabber.InputStreamGrabber;
import jp.k_ui.beanconverter.utils.streamgrabber.JulInputStreamGrabberFactory;

public class InteractiveCommandConverterSample {

  public static void main(String[] args) throws Exception {
    DateFormat format = DateFormat.getDateInstance();
    Person foo = new Person("Foo Smith", format.parse("1985/6/18"));
    Person bar = new Person("Bar Smith", format.parse("1985/9/16"));
    Person qux = new Person("Qux Doe", format.parse("1991/7/3"));

    // ▼sample 1▼
    // replace names
    String[] editingCommand =
        new String[] { "sed",
            "--unbuffered", // require this option
            "-e", "s/Smith/Norris/" };
    BeanConverter<Person, Person> nameEditing =
        new InteractiveCommandConverter<>(Person.class, editingCommand);

    System.out.println("# edited");
    System.out.println(nameEditing.convert(foo));
    // => Person [name=Foo Norris, birthDay=Tue Jun 18 00:00:00 JST 1985]
    System.out.println("# not-edited");
    System.out.println(nameEditing.convert(qux));
    // => Person [name=Qux Doe, birthDay=Wed Jul 03 00:00:00 JST 1991]

    System.out.println("---");

    // ▼sample 2▼
    // filter persons, output stderr
    File script = createScript();
    List<String> eightiesFilterCommand = Arrays.asList("python", script.getAbsolutePath());
    InputStreamGrabber errorOutputGrabber = new JulInputStreamGrabberFactory().getGrabber();
    BeanConverter<Person, Person> eightiesFilter =
        new InteractiveCommandConverter<>(Person.class, eightiesFilterCommand, errorOutputGrabber);

    System.out.println("# filtered out");
    System.out.println(eightiesFilter.convert(foo));
    // => null
    System.out.println("# filtered out");
    System.out.println(eightiesFilter.convert(bar));
    // => null
    System.out.println("# NOT filtered out");
    System.out.println(eightiesFilter.convert(qux));
    // => Person [name=Qux Doe, birthDay=Wed Jul 03 00:00:00 JST 1991]
  }

  /**
   * create a script which filtered-out persons older than 80's
   */
  private static File createScript() throws IOException {
    File pythonScript = File.createTempFile("sample", ".py");
    String s = "import json\nimport sys\nimport datetime\n" +
        "threashold = 631119599000\n" + // 1989/12/31 23:59:59
        "stdin = sys.stdin\n" +
        "line = stdin.readline()\n" +
        "while line:\n" +
        "    sys.stderr.write(line)\n" +
        "    sys.stderr.flush()\n" +
        "    j = json.loads(line)\n" +

        "    if j[\"birthDay\"] > threashold:\n" +
        "        print json.dumps(j)\n" +
        "    else:\n" +
        "        print \"null\"\n" +
        "    sys.stdout.flush()\n" + // require stdout-flush
        "    line = stdin.readline()\n";

    OutputStream o = null;
    try {
      o = new FileOutputStream(pythonScript);
      o.write(s.getBytes());
    } finally {
      if (o != null)
        o.close();
    }

    return pythonScript;
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
