/*
package dynamische_variante;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

*/
/**
 *
 *//*

public class MyFunction {


  private static final Scanner scanner = new Scanner(System.in);

  public static void inputFormula() {

    System.out.println("Geben eine Funktion ein fur die Suche nach Nullstellen mit dem <<Newton-Verfahren>>: ");
    System.out.print("f(x)= ");

    formula = getInput();
    while (!validateFormula()) {
      System.out.println("Die Eingabe ist invalide. Wiederholen die Eingabe:");
      System.out.print("f(x)= ");
      formula = scanner.nextLine();
      System.out.println(formula);
    }

    prepareFormula();
  }

  */
/**
   *        + - * /    ^ pow() sqrt() ! sin cos tan e ln() log()
   *
   * @return
   *//*

  private static Map<Character, String> getInput() {

    String input = scanner.nextLine();
    String prepared = prepareFormula(input);
    char[] operators = new char[] {'*','/','+','-'};
    Map<Character, String> splitTerm = new HashMap<>();

    for (char o : operators) {
      if (input.contains(String.valueOf(o))) {
        int index = prepared.indexOf(o);
      }
    }
  }

  public static boolean validateFormula(String formula) {

    boolean isValid = false;
    String regex = "(-?[0-9]+X?|-?X|(-?[0-9]*(?<=[0-9]),?(?<=,)[0-9]+X?))";

    if(formula.matches(regex)) {
      isValid = true;
    }
    return isValid;
  }

  private static String prepareFormula(String formula) {
    String result = formula.strip();
    result = result.toLowerCase();
    StringBuilder old = new StringBuilder(result);
    while (old.toString().contains("x")) {
      int index = old.indexOf("x");
      if (old.charAt(index-1) != '*') {
        System.out.println(index);
        old.replace(index, (index + 1), "*X");
      }
    }
    result = old.toString();
    return result;
  }

  private static double f(double x) {

    double y = 0;
    StringBuilder sb = new StringBuilder();
    String sub = "";
    // 3*x^2+2*x-5


    for (int i = 0; i < formula.length(); i++) {
      char c = formula.charAt(i);
      if (Character.isDigit(c)) {
        sb.append(c);
      }
    }
    return y;
  }

  private static double addition(double a, double b) {
    return a + b;
  }

  private static double subtraction(double a, double b) {
    return a - b;
  }

  private static double multiply(double a, double b) {
    return a * b;
  }

  private static double divide (double a , double b) {
    return a / b;
  }

  private static double df(double x) {

    return 0;
  }
}
*/
