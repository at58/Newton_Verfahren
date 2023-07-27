package lf08_abgabe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import lf08_abgabe.functions.BaseFunction;

/**
 * Diese Klasse implementiert das Newton-Verfahren zur Suche von Nullstellen in einem vorgegebenen
 * Intervall.
 */
public class NewtonMethod implements ZeroPointProcess{

  final double epsilon = 0.01;
  Scanner scanner = new Scanner(System.in);

  /**
   * <p>
   * Sucht Nullstellen innerhalb eines gegebenen Intervalls nach dem Newton-Verfahren. Dieses
   * Verfahren ist eine iterative Annäherung an die Nullstelle, in dem für eine Anfangs-Stelle x die
   * Nullstelle der Tangente der gegebenen Funktion berechnet wird und an dieser Nullstelle erneut
   * die Tangente an der Funktion gebildet wird. Dieses Verfahren wird so lange wiederholt, bis
   * entweder das delta von xn - xn-1 kleiner als ein gegebenes epsilon ε ist oder die Funktionswerte
   * sehr nah an 0 sind. Wichtig ist, dass die Anfangs-Stelle x0 nah genug an der Ziel-Nullstelle
   * liegen muss, damit die Ziel-Nullstelle angenähert werden kann. Ansonsten besteht die Gefahr, dass
   * es gegen eine andere Nullstelle konvergiert oder zwischen zwei Werten oszilliert.
   * </p>
   * <p>
   * Die Formel für die Berechnung der Nullstelle der Tangente lautet:
   * f(xn) = xn-1 - f(xn-1) / f'(xn-1)
   * </p>
   *
   * @param function Die Funktionsgleichung, dessen Nullstellen gesucht werden soll.
   */
  @Override
  public void zeroPoints(BaseFunction function) {

    boolean repeat = true;
    List<Double> zeroPointAreas;
    List<Double> zeroPoints = new ArrayList<>();

    while(repeat) {
      int[] intervall = getIntervall(function.toString());
      int start = intervall[0];
      int end = intervall[1];

      zeroPointAreas = getZeroPointCandidates(start, end, function);

      if (zeroPointAreas.isEmpty()) {
        System.out.println("Es gibt keine Nullstellen im vorgegebenen Intervall!");
        System.out.println("Soll ein neues Intervall fur f(x) = " + function
                               + " untersucht werden?\nj = ja\nn = nein");
        String yesOrNo = scanner.nextLine();
        if (!yesOrNo.equals("j")) {
          repeat = false;
        }
      }
      else {
        System.out.println("Folgende x-Werte werden fur die Nullstellensuche mit dem Newton-Verfahren als Startwert Xo verwendet: ");
        for (double x : zeroPointAreas) {
          System.out.print(x + " ");
        }
        System.out.println();
        for (double d : zeroPointAreas) {
          zeroPoints.add(newtonMethod(function, d));
        }
        repeat = false;
      }
    }
    // entferne doppelte Elemente
    Set<Double> zeroSet = new HashSet<>(zeroPoints);
    // Gebe Nullstelle in der Konsole aus
    printZeroPoints(new ArrayList<>(zeroSet), function);
  }

  private double newtonMethod(BaseFunction function, double x0) {
    double xn = x0;
    double xn_min_1;

    while (true) {
      xn_min_1 = xn;
      xn = xn_min_1 - (function.f(xn_min_1) / function.df(xn_min_1));
      if (deltaX(xn, xn_min_1) < epsilon || isAlmostZero(function.f(xn))) {
        break;
      }
    }
    return round(xn);
  }

  private double deltaX(double x0, double x1) {
    return Math.abs(x0-x1);
  }

  private boolean isAlmostZero(double value) {
    if (value <= 0.001 && value >= -0.001) {
      return true;
    }
    else {
      return false;
    }
  }

  private double round(double value) {

    double factor = Math.pow(10, 4);
    return (Math.round(value * factor) / factor);
  }

  private int[] getIntervall(String function) {

    int[] intervall = new int[2];
    System.out.println("Gebe das Intervall an fur die Nullstellensuche von f(x) = " + function);

    System.out.print("Start: ");
    String start = scanner.nextLine().strip().replace(" ", "");
    while (isNotInteger(start)) {
      System.out.println("Fehlerhafte Eingabe! Geben Sie einen ganzzahligen Wert ein.");
      System.out.print("Start: ");
      start = scanner.nextLine().strip().replace(" ", "");
    }

    System.out.print("Ende: ");
    String end = scanner.nextLine().strip().replace(" ", "");
    while (isNotInteger(end)) {
      System.out.println("Fehlerhafte Eingabe! Geben Sie einen ganzzahligen Wert ein.");
      System.out.print("Ende: ");
      end = scanner.nextLine().strip().replace(" ", "");
    }
    intervall[0] = Integer.parseInt(start);
    intervall[1] = Integer.parseInt(end);
    return intervall;
  }

  private List<Double> getZeroPointCandidates(int start, int end, BaseFunction function) {

    List<Double> zeroPointAreas = new ArrayList<>();
    /*
    Math.signum() zeigt an, ob der als Argument übergebene Wert eine positive oder negative Zahl
    oder 0 ist.
    */
    double sign1 = Math.signum(function.f(start));
    for (int i = (start + 1); i <= end; i++) {
      double sign2 = Math.signum(function.f(i));
      if (sign2 != sign1) {
        zeroPointAreas.add(i - 0.5);
        sign1 = sign2;
      }
    }
    return zeroPointAreas;
  }

  private boolean isNotInteger(String str) {

    boolean isNotInt = false;

    for (int i = 0; i < str.length() ; i++) {
      if (i == 0) {
        if (Character.isDigit(str.charAt(i))) {
          continue;
        } else if (str.charAt(i) == '-' || str.charAt(i) == '+') {
          continue;
        } else {
          isNotInt = true; // is not integer
        }
      }
      if (!Character.isDigit(str.charAt(i))) {
        isNotInt = true;
      }
    }
    if(str.isEmpty()) {
      isNotInt = true;
    }
    if(str.contains("-") && str.length() == 1) {
      isNotInt = true;
    }
    if (str.contains("+") && str.length() == 1) {
      isNotInt = true;
    }
    return isNotInt;
  }

  private void printZeroPoints(List<Double> zeroPoints, BaseFunction f) {

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