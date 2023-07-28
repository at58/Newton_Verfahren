package dynamische_variante;

import java.util.List;

public class Main {

  public static void main(String[] args) {


    List<Double> result = Newton_Dynamic.process();
    for (Double d : result) {
      System.out.println(d);
    }
  }
}
