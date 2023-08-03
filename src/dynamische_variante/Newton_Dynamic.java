package dynamische_variante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Newton_Dynamic {

  static final Scanner scanner = new Scanner(System.in);
  static final double epsilon = 0.001;
  static String function;

  public static List<Double> process() {

    // Get Function by console input
    System.out.println("Funktion eingeben: ");
    System.out.print("f(x)= ");
    String uncheckedFunction = scanner.nextLine();
    String validFunction = prepareInput(uncheckedFunction);

    List<Double> zeroPointAreas;
    List<Double> zeroPoints = new ArrayList<>();

    // Get boundary of x-axis for zero point search
    boolean repeat = true;
    while(repeat) {
      int[] intervall = getIntervall(uncheckedFunction);
      int start = intervall[0];
      int end = intervall[1];

      zeroPointAreas = getZeroPointCandidates(validFunction, start, end);
      if (!(zeroPointAreas instanceof ArrayList<Double>)) {
        zeroPoints = zeroPointAreas;
        break;
      }
      if (zeroPointAreas.isEmpty()) {
        System.out.println("Es gibt keine Nullstellen im vorgegebenen Intervall!");
        System.out.println("Soll ein neues Intervall fur f(x) = " + validFunction
                               + " untersucht werden?\nj = ja\nn = nein");
        String yesOrNo = scanner.nextLine();
        if (yesOrNo.equals("j")) {
          continue;
        } else break;
      }
      else {
        System.out.println("\nFolgende Xo-Werte werden fur die Nullstellensuche verwendet: ");
        for (double point : zeroPointAreas) {
          String pointStr = String.valueOf(point).replace(".", ",");
          String p = pointStr.startsWith("-")
                     ? pointStr
                     : ("+"+pointStr);
          System.out.println(p);
        }
        System.out.println();

        for (double x : zeroPointAreas) {
          // Applying Newton Method
          zeroPoints.add(newtonMethod(validFunction, x));
        }
      }
      repeat = false;
    }
    printZeroPoints(zeroPoints);
    return zeroPoints;
  }

  /**
   *
   * BUGGY !
   * @param function
   * @return
   */
  @Deprecated
  public static boolean isConstantsFree(String function) {

    String modified = function.replaceAll("[0-9]+\\*x", "x");
    modified = modified.replaceAll("(\\^[0-9]+,?[0-9]+)|(\\^[0-9]+)", "");

    if (modified.matches("[0-9]") || modified.contains("^")) {
      return false;
    }
    return true;
  }

  /**
   *
   * BUG-FREE
   * @param zeroPoints
   */
  private static void printZeroPoints(List<Double> zeroPoints) {

    if(!zeroPoints.isEmpty()) {
      System.out.println("Die ermittelten Nullstellen sind:");
      for (double d : zeroPoints) {
        String pointStr = String.valueOf(d).replace(".", ",");
        String printable = pointStr.startsWith("-")
                           ? pointStr
                           : ("+"+pointStr);
        System.out.println(printable);
      }
    }
    else {
      System.out.println("Keine Nullstellen gefunden!");
    }
  }

  /**
   *
   * BUG-FREE
   * @param function
   * @return
   */
  public static String getDerivation(String function) {

    String derivation;

    if (function.contains("x")) {
      // eliminate all constants without x
      String constantFree = eliminateConstants(function);
      // differentiate by x
      String differentiated = differentiate_X(constantFree);

      derivation = deriveDegree_1(differentiated);
    } else {
      derivation = "0";
    }
    return derivation;
  }

  public static String differentiate_X(String function) {

    StringBuilder exponentDerived = new StringBuilder();
    int length = (function.length() - 1);

    for (int i = 0; i <= length; i++) {
      exponentDerived.append(function.charAt(i));
      if (function.charAt(i) == 'x') {
        if (i != length) {
          if (function.charAt(i+1) == '^' ) { // x^...
            i++;
            String exponent = getExponent(function.substring(i + 1))
                .replace(",",".");
            double newExp = calc(exponent + "-1","0");
            String newExpStr = String.valueOf(newExp);
            if (isInteger(newExpStr)) {
              newExpStr = newExpStr.substring(0, newExpStr.indexOf('.'));
            } else {
              newExpStr = newExpStr.replace(".", ",");
            }
            exponentDerived.deleteCharAt(exponentDerived.length()-1);
            exponentDerived.append(exponent.replace(".", ","))
                           .append("*x^")
                           .append(newExpStr);
            i = i + exponent.length();
          }
        }
      }
    }
    return exponentDerived.toString();
  }

  public static String deriveDegree_1(String function) {

    StringBuilder builder = new StringBuilder();
    int length = (function.length() -1);
    int index = 0;

    while (index <= length) {
      char character = function.charAt(index);
      builder.append(character);
      if (character == 'x') {
        int currentBuilderIndex = builder.length() - 1;
        if (index == length) {
          builder.replace(currentBuilderIndex, currentBuilderIndex + 1, "1");
        } else {
          if (function.charAt(index+1) != '^') {
            builder.replace(currentBuilderIndex, currentBuilderIndex + 1, "1");
          } else if (function.charAt(index+1) == '^') {
            String exponent = getExponent(function.substring(index+2));
            if (exponent.equals("0")) {
              builder.replace(currentBuilderIndex, currentBuilderIndex + 1, "1");
              index += 2;
            }
            if (exponent.equals("1")) {
              index += 2;
            }
          }
        }
      }
      index++;
    }
    return builder.toString();
  }

  /**
   *
   * BUG-FREE
   * @param number
   * @return
   */
  public static boolean isInteger(String number) {
    if (number.contains(".")) {
      String decimal = number.substring(number.indexOf(".") + 1);
      for (char c : decimal.toCharArray()) {
        if (c != '0') {
          return false;
        }
      }
    }
    return true;
  }

  /**
   *
   * Bug free
   * @param subString
   * @return
   */
  public static String getExponent(String subString) {
    StringBuilder exponentBuilder;
    if (subString.length() == 1) {
      return subString;
    } else {
      exponentBuilder = new StringBuilder();
      int index = 0;
      while (Character.isDigit(subString.charAt(index))
          || subString.charAt(index) == ',') {
        exponentBuilder.append(subString.charAt(index));
        index++;
        if (index == subString.length()-1) {
          break;
        }
      }
    }
    return exponentBuilder.toString();
  }

  /**
   * BUG-FREE
   *
   * @param func
   * @return
   */
  public static String eliminateConstants(String func) {

    StringBuilder derivation = new StringBuilder();
    String function = func.startsWith("+")
                      ? func.substring(1)
                      : func;
    int leftBorder = 0;

    for (int i = function.startsWith("-") ? 1 : 0; i < function.length(); i++) {
      if (function.charAt(i) == '+'
          || function.charAt(i) == '-') {

        String subString = function.substring(leftBorder, i);
        if (subString.contains("x")) {
          derivation.append(subString);
        }
        leftBorder = i++;
      }
    }
    String lastPart = function.substring(leftBorder);
    if (lastPart.contains("x")) {
      derivation.append(lastPart);
    }
    if (lastPart.contains(")")) {
      derivation.append(lastPart);
    }


    return derivation.toString();
  }

  public static double newtonMethod(String function, double Xo) {
    double xn = Xo;
    double xn_min_1;
    String derivation = getDerivation(function);

    while (true) {
      xn_min_1 = xn;
      xn = xn_min_1 - (calc(function, String.valueOf(xn_min_1)) / calc(derivation, String.valueOf(xn_min_1)));
      if (deltaX(xn, xn_min_1) < epsilon
          || isAlmostZero(calc(function, String.valueOf(xn)))) {
        break;
      }
    }
    return round(xn);
  }

  /**
   *
   * BUG-FREE.
   * @param value
   * @return
   */
  public static double round(double value) {

    double factor = Math.pow(10, 4);
    return (Math.round(value * factor) / factor);
  }

  public static double deltaX(double x0, double x1) {
    return Math.abs(x0-x1);
  }

  public static boolean isAlmostZero(Double value) {

    if (value == null) {
      return true;
    }
    if (value <= 0.0001 && value >= -0.0001) {
      return true;
    } else {
      return false;
    }
  }

  /**
   *
   * BUG-FREE
   * @param function
   * @param start
   * @param end
   * @return
   */
  public static List<Double> getZeroPointCandidates(String function, int start, int end) {

    List<Double> zeroPointAreas = new ArrayList<>();
    class FinalList extends ArrayList<Double> {
      public FinalList() {
      }
    }
    List<Double> exactZeroPoints = new FinalList();
    String startAsStr = String.valueOf(start);
    /*
    Math.signum() zeigt an, ob der als Argument übergebene Wert eine positive oder negative Zahl
    oder 0 ist.
    */
    if (end <= (start + 1) && end >= (start-1)) {
      zeroPointAreas.add((double) start);
    }
    else  {
      double sign_1 = Math.signum(calc(function, startAsStr));

      for (double i = (start + 0.5); i <= end; i+=0.5) {
        double functionValue = calc(function, String.valueOf(i));
        if (functionValue == 0.0) {
          exactZeroPoints.add(i);
        }
        else {
          double sign_2 = Math.signum(calc(function, String.valueOf(i)));
          if (sign_2 != sign_1) {
            zeroPointAreas.add(i - 0.5);
            sign_1 = sign_2;
          }
        }
      }
    }
    if (!exactZeroPoints.isEmpty()) {
      return exactZeroPoints;
    }
    return zeroPointAreas;
  }

  private static List<Double> isDoubleZeroPoint(double negativeX, double positiveX) {
    return null;
  }

  public static int[] getIntervall(String function) {

    int[] intervall = new int[2];
    System.out.println("Gebe das Intervall an fur die Nullstellensuche von f(x) = " + function);

    System.out.print("Start: ");
    String start = scanner.nextLine().strip().replace(" ", "");
    while (invalidIntervall(start)) {
      System.out.println("Fehlerhafte Eingabe! Geben Sie einen ganzzahligen Wert ein.");
      System.out.print("Start: ");
      start = scanner.nextLine().strip().replace(" ", "");
    }

    System.out.print("Ende: ");
    String end = scanner.nextLine().strip().replace(" ", "");
    while (invalidIntervall(end)) {
      System.out.println("Fehlerhafte Eingabe! Geben Sie einen ganzzahligen Wert ein.");
      System.out.print("Ende: ");
      end = scanner.nextLine().strip().replace(" ", "");
    }
    intervall[0] = Integer.parseInt(start);
    intervall[1] = Integer.parseInt(end);
    return intervall;
  }

  /**
   * BUG-FREE
   * .
   * Input preparation for {@link Newton_Dynamic#calc(String, String)}
   *
   * @param function the unprepared function
   * @return prepared function
   */
  public static String prepareInput(String function) {

    String normalisedWhiteSpace = normaliseWhiteSpaces(function);

    String prepared = convertConstants(normalisedWhiteSpace);

    String tidyFunction = processBrackets(prepared);

    // skipping leading + or - x by increasing the index
    int index = 0;
    if(tidyFunction.startsWith("-") || tidyFunction.startsWith("+")) {
      index++;
      if(tidyFunction.charAt(1) == 'x') {
        index++;
      }
    } else {
      if(tidyFunction.startsWith("x")) {
        index++;
      }
    }

    // final processing of items within brackets
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < tidyFunction.length(); i++) {
      char c = tidyFunction.charAt(i);
      builder.append(c);
      if (i >= index) {
        if (tidyFunction.charAt(i) == 'x') {
          if(Character.isDigit(tidyFunction.charAt(i-1))) {
            int builderIndex = (builder.length()-1);
            builder.insert(builderIndex, "*");
          }
        }
      }
    }
    return builder.toString().replaceAll(",", ".");
  }

  /**
   * BUG-FREE
   *
   * @param function
   * @return
   */
  public static String convertConstants(String function) {

    Map<String, String> constants = new HashMap<>();
    constants.put("e", "2,718282");
    constants.put("p", "3,14159");
    Set<String> symbols = constants.keySet();

    String term = function.toLowerCase()
                          .replaceAll("(pi)", "p")
                          .replaceAll("π", "p");
    int length = term.length()-1;
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i <= length; i++) {
      char character = term.charAt(i);
      builder.append(character);
      if (symbols.contains(String.valueOf(character))) {
        if (i > 0) {
          if (Character.isDigit(term.charAt(i-1)) || term.charAt(i-1) == ')') {
            int builderIndex = (builder.length()-1);
            builder.insert(builderIndex-1, "*");
          }
        }
        if (i < length) {
          if (Character.isDigit(term.charAt(i+1))
              || term.charAt(i+1) == '('
              || term.charAt(i+1) == 'x') {
            builder.append("*");
          }
        }
      }
    }
    return builder.toString()
                  .replaceAll("e","2,718282")
                  .replaceAll("p", "3,14159");
  }

  /**
   *
   * BUG-FREE.
   *
   * @param function
   * @return
   */
  public static String normaliseWhiteSpaces(String function) {

    int index = 0;
    String stripedFunction = function.strip();
    int length = (stripedFunction.length() - 1);
    StringBuilder builder = new StringBuilder();

    while (true) {
      char character = stripedFunction.charAt(index);
      if (character == ' ') {
        index++;
        continue;
      }
      builder.append(character);
      if (character == '^') {
        String exponent = getExponent(stripedFunction.substring(index + 1));
        builder.append(exponent).append(" ");
        index += exponent.length();
      }
      if (index >= length) {
        break;
      }
      index++;
    }
    return builder.toString();
  }

  /**
   *
   * BUG-FREE
   * @param function
   * @return
   */
  public static String processBrackets(String function) {

    String term = function.replaceAll("\\[", "(")
            .replaceAll("]", ")");

    StringBuilder preparedSub = new StringBuilder();
    for (int i = 0; i < term.length(); i++) {
      char character = term.charAt(i);
      preparedSub.append(character);
      if (character == '(') {
        if (term.charAt(i-1) != '+'
            && term.charAt(i-1) != '-'
            && term.charAt(i-1) != '/'
            && term.charAt(i-1) != '*') {
          preparedSub.insert(preparedSub.length()-1, "*");
        }
      }
      if (character == ')') {
        if (i < (term.length() - 1)) {
          if (term.charAt(i+1) == 'x'
              || Character.isDigit(term.charAt(i+1))) {
            preparedSub.append("*");
          }
        }
      }
    }

    // TODO: extend method by + and - processing
    /*char charAt0 = term.charAt(0);
    char firstChar = charAt0 == '*' || charAt0 == '/'
                     ? '#'
                     : charAt0;
    StringBuilder builder = new StringBuilder();
    if ()

    switch (firstChar) {
      case '#' ->
    }*/
    return preparedSub.toString();
  }

  public static String getBracketItems(String term) {

    int termLength = (term.length() - 1);
    int index = 0;
    StringBuilder builder = new StringBuilder();
    while (true) {
      char character = term.charAt(index);
      builder.append(character);
      if (character == ')' && index < termLength) {
        if (term.charAt(index+1) == '(') {
          builder.append("*");
          builder.insert(0, "(");
          index++;
          continue;
        }
        String subSequence = term.subSequence(index+1, index+3).toString();
        if (subSequence.equals("*(")) {
          builder.append(subSequence);
          builder.insert(0, "(");
          index += 2;
          continue;
        }
        else {
          break;
        }
      }
      if (index == (term.length()-1)) {
        break;
      }
      index++;
    }
    if (builder.toString().startsWith("+")) {
      builder.replace(0,1, " ");
    }
    System.out.println(builder);
    return builder.toString();
  }

  public static String reverseSigns(String subString) {

    System.out.println("reverse");
    int index = 0;
    StringBuilder builder = new StringBuilder();

    while (subString.charAt(index) != ')') {
      char character = subString.charAt(index);

      if (character != '+' && character != '-') {
        builder.append(character);
      }
      else if (character == '-') {
        builder.append('+');
      }
      else {
        builder.append('-');
      }
      index++;
    }
    index++;

    if (index < (subString.length()-1)) {
      String subSequence = subString.subSequence(index+1, index+3).toString();
      if (subString.charAt(index) == '(' || subSequence.equals("*(")) {
        builder = new StringBuilder(getBracketItems(subString));
        builder.insert(0, "-");
      }
    }
    return builder.toString();
  }

  public static boolean invalidIntervall(String str) {

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

  public static double sum_Operator(int k, int n, String function) {

    String f = prepareInput(function);

    double sum = 0;

    for (int i = k; i <= n; i++) {
      sum += calc(f, String.valueOf(i));
    }
    return sum;
  }

  public static Double calc(String function, String argument) {
    String finalStr;
    if (argument.strip().startsWith("-")) {
      finalStr = wrapNegativeXintoBrackets(function)
          .replaceAll("x", argument);
    } else {
      finalStr = function.replaceAll("x", argument);
    }
    if (finalStr.contains("E")) {
      return null;
    }
    //System.out.println(finalStr);

    return new Object() {
      int pos = -1, ch;

      void nextChar() {

        ch = (++pos < finalStr.length()) ? finalStr.charAt(pos) : -1;
      }

      boolean eat(int charToEat) {

        while (ch == ' ') nextChar();
        if (ch == charToEat) {
          nextChar();
          return true;
        }
        return false;
      }

      double parse() {

        nextChar();
        double x = parseExpression();
        if (pos < finalStr.length()) throw new RuntimeException("Unexpected: " + (char) ch);
        return x;
      }

      // Grammar:
      // expression = term | expression `+` term | expression `-` term
      // term = factor | term `*` factor | term `/` factor
      // factor = `+` factor | `-` factor | `(` expression `)` | number
      //        | functionName `(` expression `)` | functionName factor
      //        | factor `^` factor

      double parseExpression() {

        double x = parseTerm();
        for (; ; ) {
          if (eat('+')) x += parseTerm(); // addition
          else if (eat('-')) x -= parseTerm(); // subtraction
          else return x;
        }
      }

      double parseTerm() {

        double x = parseFactor();
        for (; ; ) {
          if (eat('*')) x *= parseFactor(); // multiplication
          else if (eat('/')) x /= parseFactor(); // division
          else return x;
        }
      }

      double parseFactor() {

        if (eat('+')) return +parseFactor(); // unary plus
        if (eat('-')) return -parseFactor(); // unary minus

        double x;
        int startPos = this.pos;
        if (eat('(')) { // parentheses
          x = parseExpression();
          if (!eat(')')) throw new RuntimeException("Missing ')'");
        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
          while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
          x = Double.parseDouble(finalStr.substring(startPos, this.pos));
        } else if (ch >= 'a' && ch <= 'z') { // functions
          while (ch >= 'a' && ch <= 'z') nextChar();
          String func = finalStr.substring(startPos, this.pos);
          if (eat('(')) {
            x = parseExpression();
            if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
          } else {
            x = parseFactor();
          }
          if (func.equals("sqrt")) x = Math.sqrt(x);
          else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
          else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
          else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
          else throw new RuntimeException("Unknown function: " + func);
        } else {
          throw new RuntimeException("Unexpected: " + (char) ch);
        }

        if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

        return x;
      }
    }.parse();
  }

  /**
   *
   * BUG-FREE
   * @param function
   * @return
   */
  public static String wrapNegativeXintoBrackets(String function) {
    StringBuilder builder = new StringBuilder();
    int index = 0;
    while(true) {
      char character = function.charAt(index);
      builder.append(character);
      if (character == 'x') {
        builder.insert(builder.length()-1, "(");
        builder.append(")");
      }
      if (index == function.length()-1) {
        break;
      }
      index++;
    }
    return builder.toString();
  }
}