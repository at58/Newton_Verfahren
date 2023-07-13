package dynamische_variante;

import java.util.Scanner;

/**
 *
 */
public class MyFunction {

  private static String formula;

  public static void inputFormula() {

    System.out.println("Geben Sie die Funktion ein, dessen Nullstelle via Newton-Verfahren berechnet werden soll: ");
    Scanner scanner = new Scanner(System.in);
    formula = scanner.nextLine();
    while (!validFormula(formula)) {
      System.out.println("Die Funktion enthält invalide Zeichen. Wiederholen Sie die Eingabe:");
      formula = scanner.nextLine();
    }
    prepareFormula();

  }

  private static boolean validFormula(String formula) {
    // TODO: implement whitelist processing
    return true;
  }

  private static void prepareFormula() {
    formula = formula.strip();
    formula = formula.replace(" ", "");
    formula = formula.replace(",", ".");
    formula = formula.toLowerCase();
    StringBuilder old = new StringBuilder(formula);
    while (old.toString().contains("x")) {
      int index = old.indexOf("x");
      if (old.charAt(index-1) != '*') {
        System.out.println(index);
        old.replace(index, (index + 1), "*X");
        System.out.println(old);
      }
    }
    formula = old.toString();
    System.out.println(formula);
  }

  private static double f(double x) {

    double result = 0;
    StringBuilder sb = new StringBuilder();
    String sub = "";
    // 3*x^2+2*x-5


    for (int i = 0; i < formula.length(); i++) {
      char c = formula.charAt(i);
      if (Character.isDigit(c)) {
        sb.append(c);
      }
    }
    return result;
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
