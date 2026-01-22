package daljin.kpm;

public class Kpm {

  private static Kpm instance = null;
  public static Kpm getInstance() {
    if (instance == null) {
      instance = new Kpm();
    }
    return instance;
  }

  public void run() {
      System.out.println("KPM");
  }
}
