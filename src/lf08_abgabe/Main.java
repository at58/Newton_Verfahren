package lf08_abgabe;

import java.util.ArrayList;
import java.util.List;
import lf08_abgabe.functions_hardcode.BaseFunction;
import lf08_abgabe.functions_hardcode.Function_1;
import lf08_abgabe.functions_hardcode.Function_2;
import lf08_abgabe.functions_hardcode.Function_3;

public class Main {

  public static void main(String[] args) {

    NewtonMethod newtonMethod = new NewtonMethod();

    List<BaseFunction> functions = new ArrayList<>();
    functions.add(new Function_1());
    functions.add(new Function_2());
    functions.add(new Function_3());

    for (BaseFunction f : functions) {
      newtonMethod.zeroPoints(f);
    }
  }
}