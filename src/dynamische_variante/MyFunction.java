package dynamische_variante;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class MyFunction {

  private static String formula;

  public static void inputFormula() {

    System.out.println("Geben Sie die Funktion ein, dessen Nullstellen via Newton-Verfahren berechnet werden soll: ");
    System.out.print("f(x)= ");
    Scanner scanner = new Scanner(System.in);
    formula = scanner.nextLine();
    while (!validateFormula(formula)) {
      System.out.println("Die Funktion enthält invalide Zeichen. Wiederholen Sie die Eingabe:");
      formula = scanner.nextLine();
    }
    prepareFormula();

  }

  public static boolean validateFormula(String formula) {
    // TODO: implement whitelist processing
    boolean result = false;
    String regex = "(-?[0-9]+|-?x|(-?[0-9]*(?<=[0-9]),?(?<=,)[0-9]+x?))";
    Pattern whitelist = Pattern.compile(regex);


    Matcher matcher = whitelist.matcher(formula);
    if(formula.matches(regex)) {
      result = true;
    }
    return result;
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
