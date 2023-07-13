package lf08_abgabe;

import java.util.ArrayList;
import java.util.List;
import lf08_abgabe.functions.BaseFunction;
import lf08_abgabe.functions.Function_1;
import lf08_abgabe.functions.Function_2;
import lf08_abgabe.functions.Function_3;

public class Main {

  public static void main(String[] args) {

    NewtonMethod newtonMethod = new NewtonMethod();

    List<BaseFunction> functions = new ArrayList<>();
    functions.add(new Function_1());
    functions.add(new Function_2());
    functions.add(new Function_3());

    for (BaseFunction f : functions) {
      List<Double> zeroPoints = newtonMethod.zeroPoints(f);
      System.out.println("Nullstellen von f(x) = " + f.toString() + ": ");
      for (double d : zeroPoints) {
        if(!String.valueOf(d).startsWith("-")){
          System.out.print("+" + d);
        } else {
          System.out.print(d + " | ");
        }
      }
      System.out.println("\n___________________________\n");
    }
  }
}