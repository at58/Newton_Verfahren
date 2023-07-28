package dynamische_variante;

import java.util.List;

public class Main {

  public static void main(String[] args) {


/*    List<Double> result = Newton_Dynamic.process();
    for (Double d : result) {
      System.out.println(d);
    }*/

    String pre = Newton_Dynamic.prepareInput("3x");
    System.out.println(pre);

    String foo = "";
    String[] bo = new String[1];
    for (String s:bo) {
      System.out.println(s);
    }
    StringBuilder sb = new StringBuilder("Hallo");
    int lastIndex = sb.length()-1;
    sb.replace(lastIndex, lastIndex+1, "-");
    System.out.println(sb);

    System.out.println(Newton_Dynamic.getDerivation("2x+3-4*1-3x^2+x-1+3*x"));
  }
}
