package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class Controller is repsonsible for GUI
 * @author Monika Regula and Katarzyna Jurkowska
 * @version 1.0
 */
public class Controller {

    /**
     * Represents capacity.
     */
    @FXML
    private TextField txtC;
    /**
     * Represents potential.
     */
    @FXML
    private TextField txtU;
    /**
     * Represents sodium potential.
     */
    @FXML
    private TextField txtENa;
    /**
     * Represents chart with potential. u(t)
     */
    @FXML
    private ScatterChart<Number,Number> scatterU;
    /**
     * Represents x axis of chart U
     */
    @FXML
    private NumberAxis xU;
    /**
     * Represents y Axis of chart U
     */
    @FXML
    private NumberAxis yU;
    /**
     * Represents chart with given electricity.
     */
    @FXML
    private ScatterChart<Number,Number> scatterI;
    /**
     * Represents x axis form chart I
     */
    @FXML
    private NumberAxis xI;
    /**
     * Represents y axis form chart I
     */
    @FXML
    private NumberAxis yI;
    /**
     * Represents potassium potenctial.
     */
    @FXML
    private TextField txtEK;
    /**
     * Represents other potntials.
     */
    @FXML
    private TextField txtEL;
    /**
     * Represents sodium conductance.
     */
    @FXML
    private TextField txtENa1;
    /**
     * Represents potassium conductance.
     */
    @FXML
    private TextField txtEK1;
    /**
     * Represents other conductance.
     */
    @FXML
    private TextField txtEL1;
    /**
     * Represents chart with sodium ion  values
     */
    @FXML
    private ScatterChart<Number,Number> scatterINa;
    /**
     * Represents x axis of sodium ion values chart
     */
    @FXML
    private NumberAxis xIna;
    /**
     * Represents y axis of sodium ion values chart
     */
    @FXML
    private NumberAxis yINa;
    /**
     * Represents chart with potassium ion values
     */
    @FXML
    private ScatterChart<Number,Number> scatterIK;
    /**
     * Represents x axis of potassium ion chart
     */
    @FXML
    private NumberAxis xIK;
    /**
     * Represents y axis of potassium ion chart
     */
    @FXML
    private NumberAxis yIK;
    /**
     * Represents chart with other ion avlues.
     */
    @FXML
    private ScatterChart<Number,Number> scatterIL;
    /**
     * Represents x axis of other ion values.
     */
    @FXML
    private NumberAxis xIL;
    /**
     * Represents y axis other ion values.
     */
    @FXML
    private NumberAxis yIL;
    /**
     * Represents chart with parameter m values.
     */
    @FXML
    private ScatterChart<Number,Number> scatterM;
    /**
     * Represents x axis of chart m
     */
    @FXML
    private NumberAxis xM;
    /**
     * Represents y axis of chart m
     */
    @FXML
    private NumberAxis yM;
    /**
     * Represents chart with parameter n values.
     */
    @FXML
    private ScatterChart<Number,Number> scatterN;
    /**
     * Represents x axis of chart n
     */
    @FXML
    private NumberAxis xN;
    /**
     * Represents y axis of chart n
     */
    @FXML
    private NumberAxis yN;
    /**
     * Represents chart with parameter h values.
     */
    @FXML
    private ScatterChart<Number,Number> scatterH;
    /**
     * Represents x axis of chart n
     */
    @FXML
    private NumberAxis xH;
    /**
     * Represents y axis of chart n
     */
    @FXML
    private NumberAxis yH;
    /**
     * Represents imageview
     */
    @FXML
    private ImageView model;
    /**
     * Represents button
     */
    @FXML
    private Button btn;
    /**
     * Represents frequency of potential
     */
    @FXML
    private TextField txtF;
    /**
     * Represents maximal value of potential
     */
    @FXML
    private TextField txtMax;
    /**
     * Represents mean value of potential
     */
    @FXML
    private TextField txtMean;
    /**
     * Represents standard deviation of potential values.
     */
    @FXML
    private TextField txtStd;

    /**
     * Represents array with time values
     */
    private ArrayList<Double> time;
    /**
     * Represents array with potential values taken from path
     */
    private ArrayList<Double> uValues;


    /**
     * Method is called when button is clicked.
     * Removes previous series from charts and makes object of class: SpikeODE and SpikePath.
     * Thus gets new parameters and values. Then plots are drawn.
     * @param event btnClicked
     */
    @FXML
    void btnClicked(ActionEvent event) {

        //usuwam stare daen z wykresów
        scatterU.getData().removeAll(scatterU.getData());
        scatterI.getData().removeAll(scatterU.getData());
        scatterINa.getData().removeAll(scatterU.getData());
        scatterIK.getData().removeAll(scatterU.getData());
        scatterIL.getData().removeAll(scatterU.getData());
        scatterM.getData().removeAll(scatterU.getData());
        scatterN.getData().removeAll(scatterU.getData());
        scatterH.getData().removeAll(scatterU.getData());

        //prądu na początku nie ma czyli zero
        double I = 0;
        //Pobieram parametry z textfieldów:
        double C = Double.valueOf(txtC.getText());
        double eNa = Double.valueOf(txtENa.getText());
        double eK = Double.valueOf(txtEK.getText());
        double eL = Double.valueOf(txtEL.getText());
        double gNa = Double.valueOf(txtENa1.getText());
        double gK = Double.valueOf(txtEK1.getText());
        double gL = Double.valueOf(txtEL1.getText());

        //tworze obiekt do rózniczkowania
        FirstOrderDifferentialEquations ode = new SpikeODE(C,eNa,eK,eL,gNa,gK,gL,I);
        //tworze integrator z krokiem całkowania 0.01
        FirstOrderIntegrator integrator = new ClassicalRungeKuttaIntegrator(0.01);
        //tworze ścieżkę dla integratora
        SpikePath path = new SpikePath(gNa,gK,gL,eNa,eK,eL);
        //dodaje ścieżkę do integratrora
        integrator.addStepHandler(path);

        //granica błędu czasu
        double Tk = 0.001;
        //czas końcowy [ms]
        double te = 50;
        //czas odpowiadający 10% przedziału czasu po którym zmienia się prąd
        double T10 = (0.10 * te);

            //wartośći początkowe potencjału, alf oraz bet
        double u0 = Double.valueOf(txtU.getText());
        double am = (0.1 * (25 - u0)) / (Math.exp((25 - u0) / 10) - 1); //17a
        double bm = 4 * Math.exp(-u0 / 18); //17b
        double an = (0.01 * (10 - u0)) / (Math.exp((10 - u0) / 10) - 1); //18a
        double bn = 0.125 * Math.exp(-u0 / 80); //18b
        double ah = 0.07 * Math.exp(-u0 / 20); //19a
        double bh = 1 / (Math.exp((30 - u0) / 10) + 1); //19b


        double m0 = am / (am + bm);
        double n0 = an / (an + bn);
        double h0 = ah / (ah + bh);
        System.out.println("m" +m0+" n" +n0+" h"+h0);

        //warunki początkowe
        double[] yStart = new double[]{u0,m0, n0, h0 };
        double[] yStop = new double[]{0, 1, 0, 1};

        //całkuje się równanie na predziale czasowym 0-T10 czyli od 0 do 5 [ms]
        integrator.integrate(ode, 0, yStart, T10, yStop);

        //dla czasu w sąsiedztwie T10 oraz kolejnych zmienia się prąd
        if ((path.getTime() < (T10) + Tk) && (path.getTime() > (T10) - Tk)) {

            System.out.println("Prąd się zmienia");
            //nowy prąd I = 10
            I = 10;
            // ustawienie nowych wartosci początkowych
            m0 = path.getmValues().get(path.getmValues().size() - 1);
            n0 = path.getnValues().get(path.getnValues().size() - 1);
            h0 = path.gethValues().get(path.gethValues().size() - 1);
            u0 = path.getuValues().get(path.getuValues().size() - 1);


            ode = new SpikeODE(C, eNa, eK, eL, gNa, gK, gL, I);

            yStart = new double[]{u0,m0, n0, h0 };
            System.out.println(Arrays.toString(yStart));
            yStop = new double[]{0, 1, 0, 1};

            //teraz całkuje się równanie na przedziale 5-50 [ms]
            integrator.integrate(ode, T10, yStart, te, yStop);
        }

        // do listy tablicowej wrzucam listę tablicową ze ścieżki ze wszystkimi czasami
        time = path.getTimes();
        // do listy tablicowej wrzucam listę potencjałów ze ścieżki
        uValues = path.getuValues();

        //tworze  serie danych pod wykresy
        XYChart.Series<Number, Number> uSeries = path.getuSeries();
        XYChart.Series<Number, Number> ISeries = new XYChart.Series();

        XYChart.Series<Number, Number> INaSeries = path.getINaSeries();
        XYChart.Series<Number, Number> IKSeries = path.getIKSeries();
        XYChart.Series<Number, Number> ILSeries = path.getILSeries();

        XYChart.Series<Number, Number> mSeries = path.getmSeries();
        XYChart.Series<Number, Number> nSeries = path.getnSeries();
        XYChart.Series<Number, Number> hSeries = path.gethSeries();

        //seria danych dla prądu musi być zrobiona tak bo zachodzi przejście skokowe
        for (int i = 0; i < uValues.size(); i++) {
            if (time.get(i) > 5) ISeries.getData().add(new XYChart.Data<>(time.get(i), I));
            else ISeries.getData().add(new XYChart.Data<>(time.get(i), 0));

        }

        //dodaje serie danych do wykresów
        scatterU.getData().add(uSeries);
        scatterI.getData().add(ISeries);

        scatterM.getData().add(mSeries);
        scatterN.getData().add(nSeries);
        scatterH.getData().add(hSeries);

        scatterINa.getData().add(INaSeries);
        scatterIK.getData().add(IKSeries);
        scatterIL.getData().add(ILSeries);


        xH.setTickUnit(1);
        xH.setAutoRanging(true);
        xM.setTickUnit(1);
        xM.setAutoRanging(true);
        xN.setTickUnit(1);
        xN.setAutoRanging(true);
        yH.setTickUnit(1);
        yH.setAutoRanging(true);
        yM.setTickUnit(1);
        yM.setAutoRanging(true);
        yN.setTickUnit(1);
        yN.setAutoRanging(true);

        xU.setTickUnit(1);
        xU.setAutoRanging(true);
        yU.setTickUnit(1);
        yU.setAutoRanging(true);

        xI.setTickUnit(1);
        xI.setAutoRanging(true);
        yI.setTickUnit(1);
        yI.setAutoRanging(true);

        xIK.setTickUnit(1);
        xIK.setAutoRanging(true);
        yIK.setTickUnit(1);
        yIK.setAutoRanging(true);

        xIna.setTickUnit(1);
        xIna.setAutoRanging(true);
        yINa.setTickUnit(1);
        yINa.setAutoRanging(true);

        xIL.setTickUnit(1);
        xIL.setAutoRanging(true);
        yIL.setTickUnit(1);
        yIL.setAutoRanging(true);


        //wywołuje metode obliczająca paramtery statystyczne
        calculateStatistics(uValues);

    }


    /**
     * Method initialize the app with setting  textfields default values.
     */
    @FXML
    void initialize(){
        txtC.setText("1.0");
        txtU.setText("0");
        txtENa.setText("115.0");
        txtEK.setText("-12.0");
        txtEL.setText("10.6");
        txtENa1.setText("120.0");
        txtEK1.setText("36.0");
        txtEL1.setText("0.3");

}


    /**
     * Method calculates statistic of simulation.
     * - frequency
     * - maximum potential
     * - mean potential
     * -standard deviation of potential
     * @param u potential  values array
     */
    private void calculateStatistics(ArrayList<Double> u ){

        ArrayList <Double> times = new ArrayList<>();
        ArrayList <Double> uMax = new ArrayList<>();

        for (int i = 1;i<u.size()-1;i++){
            if (u.get(i) > u.get(i-1) && u.get(i) > u.get(i+1) && u.get(i)>1){
                times.add(time.get(i));
                uMax.add(uValues.get(i));
            }
        }

     System.out.println(Arrays.toString(uMax.toArray()));


        double mean = 0,sum = 0,sumStd = 0,max=0,fs=0,sumTime =0;
        double std=0;

        for (int i =1;i<times.size();i++){
            if (max<uMax.get(i)) max = uMax.get(i);

            sum+=uMax.get(i);

            if (i>0) sumTime+=times.get(i) - times.get(i-1);
            else sumTime = time.get(i);

        }

        mean = sum/uMax.size();
        fs = 1/(sumTime / (times.size() - 1));

        for (int i=0;i<times.size();i++){
            sumStd+=(Math.pow(uMax.get(i)-mean,2)) / (times.size()-1);
        }

        std = Math.pow(sumStd,0.5);

        System.out.println(fs+" "+max+" "+mean);

        txtF.setText(String.format("%.4f",fs));
        txtMax.setText(String.format("%.2f",max));
        txtMean.setText(String.format("%.2f",mean));
        txtStd.setText(String.format("%.2f",std));

}



}
