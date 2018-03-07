package sample;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
//3x^2-4x

public class NewtonRaphson {

    //singleton pattern
    private static NewtonRaphson INSTANCE;

    private NewtonRaphson() {
    }

    public static NewtonRaphson getInstance() {
        if (INSTANCE == null) INSTANCE = new NewtonRaphson();
        return INSTANCE;

    }

    private double[] coefficients;
    private boolean flag = true;
    private double x0;
    private double error, givenError, root;
    private int iteration = 0;
    private String[] pochodna;
    private String[] opis;
    private String fullInfo = "";
    private String wzorPochodnej = "";
    private double derivatives[];
    private String polynomial;


    //getters and setters
    public String getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(String polynomial) {
        this.polynomial = polynomial;
    }

    public double getError() {
        return error;
    }

    public double getRoot() {
        return root;
    }

    public int getIteration() {
        return iteration;
    }

    public double getX0() {
        return x0;
    }

    public String getFullInfo() {
        return fullInfo;
    }

    public double[] getDerivatives() {
        return derivatives;
    }

    public String getWzorPochodnej() {
        return wzorPochodnej;
    }

    public void setWzorPochodnej(String wzorPochodnej) {
        this.wzorPochodnej = wzorPochodnej;
    }

    //find derivative and set the formula, it doesnt compute antyhing
    public void findDerivative(double[] C) {
        //local variables
        coefficients = C;
        double derivative[] = new double[coefficients.length - 1];

        //stores full formula of derivative
        pochodna = new String[coefficients.length - 1];

        //counter for parser
        int counter = coefficients.length - 2;
        //create full formula of derivative from coefficients which are stored in derivative array
        //start from the biggest coefficient to minimal
        for (int i = 0; i < coefficients.length - 1; i++) {
            derivative[i] = coefficients[i] * (coefficients.length - i - 1);
            pochodna[i] = derivative[i] + "*x^" + counter;
            counter--;


        }
        //full derivative STRING
        for (int i = 0; i < pochodna.length; i++) {
            System.out.println("Pochodna: " + pochodna[i]);

            wzorPochodnej += pochodna[i] + "+";
        }

        //the last char is '+' - need to remove
        wzorPochodnej = removeLastChar(wzorPochodnej);

        //copy to local variable
        derivatives = derivative;
        System.out.println("Ostateczny wzor wyliczonej pochodnej wynosi: " + wzorPochodnej);
    }


    public void solvePolynomial(double x, String F, String F_Prime) {
        //copy input to local variable
        x0 = x;
        //givenError = x0;
        int counter = 0;
        //opis contains parts of informations for 'full info'
        opis = new String[1000];

        //assuming our tolerance
        double tolerance = 0.00001;
        while (flag) {
            //create formulas for Newton-Raphson's Method
            root = x0 - (F(x0, F) / F_prime(x0, F_Prime));
            error = Math.abs((root - x0) / root) * 100;

            //store info in String array
            opis[counter] = "Iteration: " + iteration++ + "\nRoot: " + root + "\nError: " + error + "\n\n";
            counter++; //counter for opis[] array
            //new x0
            x0 = root;


            //if we got good tolerance then stop the algorithm
            if (tolerance > error) {
                flag = false;
            }
        }
        //create fullInfo from opis array, if element is null dont copy that - type ""
        for (int i = 0; i < opis.length; i++) {
            if (opis[i] != null)
                fullInfo += opis[i];
            else {
                fullInfo += "";
            }
        }

    }

    //compute F function by giving x value
    public double F(double x, String function) {
        Expression e = new ExpressionBuilder(function).variable("x").build();
        e.setVariable("x", x);
        double result = e.evaluate();

        return result;


    }

    //compute F prime by giving x value, and function giving by setDerivative function
    public double F_prime(double x, String function) {

        Expression e = new ExpressionBuilder(function).variable("x").build();
        e.setVariable("x", x);
        double result = e.evaluate();

        return result;


    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
}


