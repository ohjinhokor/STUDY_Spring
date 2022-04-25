package example;

import java.lang.String;

public class BepiPerson implements Person {
  public String getJob() {
    return "backend developer";
  }

  public void printHobby(String hobby) {
    System.out.println("bepi usually do " + hobby);
  }
}
