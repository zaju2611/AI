import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Izhikevich {
    private double a = 0.02;
    private double b = 0.2;
    private double c = -65;
    private double d = 8;
    private double ypeak = 30;
    private double tau = 0.1;
    private double V_curr = -70;
    private double U_curr = -20;
    private int time = 5000;
    private double poczatek = 1000;
    private double koniec = 4000;

    public int getTime() {
        return time;
    }

    public Izhikevich() {
    }
    public Izhikevich(double a, double b, double c, double d, double ypeak, double tau, double v_curr, double u_curr, int time, double poczatek, double koniec) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.ypeak = ypeak;
        this.tau = tau;
        V_curr = v_curr;
        U_curr = u_curr;
        this.time = time;
        this.poczatek = poczatek;
        this.koniec = koniec;
    }

    public int I_init(double t){
        if(poczatek<t && t<koniec){
            return 10;
        }
        else {
            return 0;
        }
    }

    public double[][] izhikevich_model(){
        double [][] arr= new double[2][time];
        int [] t = new int[time];
        for(int i=0;i<time;i++){
            t[i]=i;
        }

        double [] V = new double[time];
        double [] U = new double[time];
        double [] V_curr = new double[time];
        double [] U_curr = new double[time];
        V[0]= -70;
        U[0]= -20;

        for(int i=0; i<time-1;i++){
                V_curr[i+1]=V[i]+(tau*((0.04*V[i]+5)*V[i]+140-U[i]+I_init(t[i+1])));
            if(V_curr[i+1]>ypeak) {
                V[i + 1] = c;
            }
            else {
                V[i+1]=V_curr[i+1];
            }
                U_curr[i+1]=U[i]+tau*a*(b*V[i+1]-U[i]);
            if(V_curr[i+1]>ypeak) {
                U[i + 1] = U_curr[i + 1] + d;
            }
            else {
                U[i + 1] = U_curr[i + 1];
            }
        arr[0][i]=V[i];
        arr[1][i]=U[i];
            System.out.println(arr[0][i]+" "+arr[1][i]);
        }
        return arr;
    }

    public double [] methodI(){
        int [] t = new int[time];
        for(int i=0;i<time;i++){
            t[i]=i;
        }
        double [] arrI = new double[time];
        for (int i=0;i<arrI.length;i++){
            arrI[i]=I_init(t[i]);
        }
        return arrI;
    }
    public void show(double[][] tab, int ile,String nazwa){
        XYSeries series = new XYSeries("Dane");
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(int i=0;i< ile;i++) {
            series.add(i, tab[0][i]);
        }
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createXYLineChart("Wykres", "Czas(ms)", "Vm(mV)",dataset, PlotOrientation.VERTICAL,true,true,false);
        ChartFrame frame = new ChartFrame("Wykres",chart);
        frame.setVisible(true);
        frame.setSize(500,400);
        save(chart, new File(nazwa+".png"));
    }

    public void show_I(double [] tab, int ile, String nazwa){
        XYSeries series = new XYSeries("Dane");
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(int i=0;i< ile;i++) {
            series.add(i, tab[i]);
        }
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createXYLineChart("Wykres", "Czas(ms)", "Prąd (uA/cm^2)",dataset, PlotOrientation.VERTICAL,true,true,false);
        ChartFrame frame = new ChartFrame("Wykres",chart);
        frame.setVisible(true);
        frame.setSize(500,400);
        save(chart, new File(nazwa+".png"));
    }
    public void save(JFreeChart chart, File chartFile){
        BufferedImage chartImage = chart.createBufferedImage(1024,768);
        try (OutputStream out = new FileOutputStream(chartFile)) {
            ImageIO.write(chartImage, "png", out);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed writing chartFile (" + chartFile + ").", e);
        }
    }


}

