package example;

/**
 * Hello world!
 */
public class App 
{
    public static void main( String[] args ) {
        Person person = new BepiPerson();

        String job = person.getJob();
        System.out.println("Bepi's job : " + job);

        person.printHobby("programming");
    }
}