package sample;

import javafx.scene.chart.XYChart;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class SpikePath represents path for integrated parameters.
 * @author Monika Regula and Katarzyna Jurkowska
 * @version 1.0
 */
public class SpikePath implements StepHandler {
    /**
     * Represents aray with potencial values.
     */
    private ArrayList<Double> uValues = new ArrayList<>();
    /**
     * Represents array with m parameter values.
     */
    private ArrayList<Double> mValues = new ArrayList<>();
    /**
     * Represents array with h parameter values.
     */
    private ArrayList<Double> hValues = new ArrayList<>();
    /**
     * Represents array with n parameter values.
     */
    private ArrayList<Double> nValues = new ArrayList<>();
    /**
     * Represents array with time values.
     */
    private ArrayList<Double> times = new ArrayList<>();
    /**
     * Represents array with sodium ion current values.
     */
    private ArrayList<Double> IkValues = new ArrayList<>();
    /**
     * Represents array with potassium ion current values.
     */
    private ArrayList<Double> INaValues = new ArrayList<>();
    /**
     * Represents aray with other ion current values,
     */
    private ArrayList<Double> IlValues = new ArrayList<>();
    /**
     * Represents series with potential's values.
     */
    private XYChart.Series<Number, Number> uSeries = new XYChart.Series();
    /**
     * Represents series with sodium ion current values.
     */
    private XYChart.Series<Number, Number> INaSeries = new XYChart.Series();
    /**
     * Represents seires with potassium ion current values.
     */
    private XYChart.Series<Number, Number> IKSeries = new XYChart.Series();
    /**
     * Represents series with other ion current values.
     */
    private XYChart.Series<Number, Number> ILSeries = new XYChart.Series();
    /**
     * Represents series with paramater m values.
     */
    private XYChart.Series<Number, Number> mSeries = new XYChart.Series();
    /**
     * Represents series with parameter n values.
     */
    private XYChart.Series<Number, Number> nSeries = new XYChart.Series();
    /**
     * Represents series with parameter h values.
     */
    private XYChart.Series<Number, Number> hSeries = new XYChart.Series();
    /**
     * Represents time value.
     */
    private double time = 0;
    /**
     * Represents sodium conductance
     */
    private  double gNa ;
    /**
     * Represents potassium conductance
     */
    private  double gK ;
    /**
     * Represents other conductance.
     */
    private  double gL ;
    /**
     * Represents sodium potential.
     */
    private  double eNa ;
    /**
     * Represents potassium potential.
     */
    private  double eK ;
    /**
     * Represents other potentials.
     */
    private  double eL ;


    /**
     * Constructor creates an Object with given parameters.
     * @param gNa sodium conductance
     * @param gK potassium conductance
     * @param gL other conductance
     * @param eNa sodium potential
     * @param eK potasum potential
     * @param eL other potential
     */
    public SpikePath( double gNa, double gK, double gL, double eNa, double eK, double eL) {
        this.gNa = gNa;
        this.gK = gK;
        this.gL = gL;
        this.eNa = eNa;
        this.eK = eK;
        this.eL = eL;
    }


    /**
     * Returns array of potential
     * @return u (potential) array
     */
    public ArrayList<Double> getuValues() { return uValues; }

    /**
     * Returns arrray of parameters m.
     * @return parameter m array
     */
    public ArrayList<Double> getmValues() { return mValues; }
    /**
     * Returns arrray of parameters h.
     * @return parameter h array
     */
    public ArrayList<Double> gethValues() { return hValues; }
    /**
     * Returns arrray of parameters n.
     * @return parameter n array
     */
    public ArrayList<Double> getnValues() { return nValues; }

    /**
     * Returns array ot time values.
     * @return times array
     */
    public ArrayList<Double> getTimes() { return times; }

    /**
     * Returns single time value
     * @return time value
     */
    public double getTime() { return time; }

    /**
     * Returns sodium ion current values
     * @return sodium ion current aray
     */
    public XYChart.Series<Number, Number> getINaSeries() { return INaSeries; }
    /**
     * Returns potassium ion current values
     * @return potassium ion current aray
     */
    public XYChart.Series<Number, Number> getIKSeries() { return IKSeries; }
    /**
     * Returns  other ion current values
     * @return  other ion current aray
     */
    public XYChart.Series<Number, Number> getILSeries() { return ILSeries; }

    /**
     * Rerurns series with paramter m values
     * @return series parameter m
     */
    public XYChart.Series<Number, Number> getmSeries() { return mSeries; }
    /**
     * Rerurns series with paramter n values
     * @return series parameter n
     */
    public XYChart.Series<Number, Number> getnSeries() { return nSeries; }
    /**
     * Rerurns series with paramter h values
     * @return series parameter h
     */
    public XYChart.Series<Number, Number> gethSeries() { return hSeries; }
    /**
     * Rerurns series with  u values
     * @return series with u values
     */
    public XYChart.Series<Number, Number> getuSeries() { return uSeries; }


    /**
     * Method is not used.
     * @param v
     * @param doubles
     * @param v1
     */
    @Override
    public void init(double v, double[] doubles, double v1) {

    }


    /**
     * Method sets parameters for integration and calculates ion current values.
     * @param stepInterpolator
     * @param b
     * @throws MaxCountExceededException
     */
    @Override
    public void handleStep(StepInterpolator stepInterpolator, boolean b) throws MaxCountExceededException {
        //czas obecny
        double t = stepInterpolator.getCurrentTime();
        // stan pobierany z integratora czyli wyniki równań róniczkowych tj. dudt[..]
        double[] u = stepInterpolator.getInterpolatedState();
        //ustawiam czas
        time = t;

        //licze prądy jonowe na podstawie pobranych wartości z integratora
        double iNa= gNa*Math.pow(u[1],3.0)*u[3]*(u[0]-eNa);
        double iK = gK*Math.pow(u[2],4)*(u[0] -eK);
        double iL= gL*(u[0]-eL);

        //do serii danych dodaje wartości w zależności od czasu
        uSeries.getData().add(new XYChart.Data<>(time,u[0]));

        INaSeries.getData().add(new XYChart.Data<>(time,iNa));
        IKSeries.getData().add(new XYChart.Data<>(time,iK));
        ILSeries.getData().add(new XYChart.Data<>(time,iL));

        mSeries.getData().add(new XYChart.Data<>(time, u[1]));
        nSeries.getData().add(new XYChart.Data<>(time, u[2]));
        hSeries.getData().add(new XYChart.Data<>(time, u[3]));

        //do list tablicowych dodaje pojedyncze wyliczone wartości prądów jonowych
        INaValues.add(iNa);
        IkValues.add(iK);
        IlValues.add(iL);

        // do list tablicowych dodaje wartości u,m,n,h
        uValues.add(u[0]);
        mValues.add(u[1]);
        nValues.add(u[2]);
        hValues.add(u[3]);

        //do listy tablicowej wrzucam  pojedynczy czas
        times.add(time);

        System.out.println("t= " + t + " " + Arrays.toString(u));
    }
}
