package sample;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

/**
 * Claass SpikeODE represents base for derivatives
 * @author Monika Regula and Katarzyna Jurkowska
 * @version 1.0
 */
public class SpikeODE implements FirstOrderDifferentialEquations {

    /**
     * Represents capacity.
     */
    private double C;
    /**
     * Represents sodium potential
     */
    private double eNa;
    /**
     * Represents potassium potential
     */
    private double eK;
    /**
     * Represents potential
     */
    private double eL;
    /**
     * Represents sodium conductance
     */
    private double gNa;
    /**
     * Represents sodium conductance
     */
    private double gK;
    /**
     * Represents conductance
     */
    private double gL;
    /**
     * Represents current electricity
     */
    private double I;


    /**
     * Constructor creates object with given parameters.
     * @param c capacity
     * @param eNa sodium potential
     * @param eK potassium potential
     * @param eL potential
     * @param gNa sodium conductance
     * @param gK potassium conductance
     * @param gL conductance
     * @param i current
     */
    public SpikeODE(double c, double eNa, double eK, double eL, double gNa, double gK, double gL, double i) {
        C = c;
        this.eNa = eNa;
        this.eK = eK;
        this.eL = eL;
        this.gNa = gNa;
        this.gK = gK;
        this.gL = gL;
        I = i;
    }


    /**
     * Method sets matrix's dimension
     * @return matrix's dimension
     */
    @Override
    public int getDimension() {
        return 4;
    }


    /**
     * Method is responsible for calculating deriatives
     * @param t time
     * @param u variable to calculate derivative
     * @param dudt derivatives
     * @throws MaxCountExceededException
     * @throws DimensionMismatchException
     */
    @Override
    public void computeDerivatives(double t, double[] u, double[] dudt) throws MaxCountExceededException, DimensionMismatchException {

        //wzory na alfy i bety:
        double am = (0.1 * (25 - u[0])) / (Math.exp((25 - u[0]) / 10) - 1);
        double bm = 4 * Math.exp(-u[0] / 18);
        double an = (0.01 * (10 - u[0])) / (Math.exp((10 - u[0]) / 10) - 1);
        double bn = 0.125 * Math.exp(-u[0] / 80);
        double ah = 0.07 * Math.exp(-u[0] / 20);
        double bh = 1 / (Math.exp((30 - u[0]) / 10) + 1);

            /*dudt[0] to pochodna du/dt zmiana napiecia
            dudt[1] to pochodna dm/dt aktywacja kanału sodowego
            dudt[2] to pochodna dn/dt aktywacja kanału potasowego
            dudt[3] to pochodna dh/dt dezaktywacja kanału sodowego,

            u[0] to u
            u[1] to m
            u[2] to n
            u[3] to h*/

        dudt[0] = ( -(( gNa*Math.pow(u[1],3.0)*u[3]*(u[0]-eNa))  + gK*Math.pow(u[2],4)*(u[0] -eK) + gL*(u[0]-eL))+ I)/C;
        dudt[1] = am*(1-u[1]) - bm*u[1];
        dudt[2] = an*(1-u[2]) - bn*u[2];
        dudt[3] = ah*(1-u[3]) - bh*u[3];

    }
}
