package dynamische_variante;

import java.util.List;

public class Main {

  public static void main(String[] args) {

    List<String> functions = List.of(
        "x",
        "2x"

    );
    List<Double>nullstellen = Newton_Dynamic.process();

    //System.out.println(Newton_Dynamic.eliminateConstants("(3*x-2)*(2*x-1)"));

  }
}