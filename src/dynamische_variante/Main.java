package dynamische_variante;

import java.util.List;

public class Main {

  public static void main(String[] args) {


/*    List<Double> result = Newton_Dynamic.process();
    for (Double d : result) {
      System.out.println(d);
    }*/

    System.out.println(Newton_Dynamic.getDerivation("x-2*x^3,5-x^2+x-10"));
/*    StringBuilder sb = new StringBuilder("Hey");
    sb.insert(sb.length()-1, "i");
    System.out.println(sb);*/

  }
}
