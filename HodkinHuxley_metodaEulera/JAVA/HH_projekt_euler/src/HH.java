import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

public class HH {

    private double Vk= -12;
    private double Vna = 120;
    private double Vl= 10.6;
    private double gK = 36;
    private double gNa = 120;
    private double gL = 0.3;
    private double Cm =1;
    private double m0=0;
    private double n0=0.316;
    private double h0 = 0.607;
    private int time =5000;

    public HH() {
    }

    public HH(double vk, double vna, double vl, double gK, double gNa, double gL, double cm, double m0, double n0, double h0, int time) {
        Vk = vk;
        Vna = vna;
        Vl = vl;
        this.gK = gK;
        this.gNa = gNa;
        this.gL = gL;
        Cm = cm;
        this.m0 = m0;
        this.n0 = n0;
        this.h0 = h0;
        this.time = time;
    }

    private double alfa_m(double V){
        return (0.1*(V+25))/(Math.exp((V+25)/10)-1);
    }

    private double beta_m(double V){
        return 4*Math.exp(V/18);
    }

    private double alfa_n(double V){
        return (0.01*(V+10))/(Math.exp((V+10)/10)-1);
    }

    private double beta_n(double V){
        return 0.125*Math.exp(V/80);
    }

    private double alfa_h(double V){
        return 0.07*Math.exp(V/20);
    }

    private double beta_h(double V){
        return 1/(Math.exp((V+30)/10)+1);
    }

    private double I_init(int time){
        return 0.1;
    }

    public int getTime() {
        return time-1;
    }

    public double[][] main(){
        double tau = 0.01;
        double [][] arr= new double[4][time];
        double [] V = new double[time];
        double [] m = new double[time];
        double [] n= new double[time];
        double [] h = new double[time];

        V[0]=-65;
        m[0]=m0;
        n[0]=n0;
        h[0]=h0;

        for (int i=0; i<time-1; i++) {
            V[i + 1] = V[i] + tau * ((I_init(i) - gK * (pow(n[i], 4)) * (V[i] - Vk) - gNa * (pow(m[i], 3)) * h[i] * (V[i] - Vna) - gL * (V[i] - Vl)) * (1 / Cm));
            n[i + 1] = n[i] + tau * (alfa_n(V[i]) * (1 - n[i]) - beta_n(V[i]) * n[i]);
            m[i + 1] = m[i] + tau * (alfa_m(V[i]) * (1 - m[i]) - beta_m(V[i]) * m[i]);
            h[i + 1] = h[i] + tau * (alfa_h(V[i]) * (1 - h[i]) - beta_h(V[i]) * h[i]);
            System.out.println(i + " "+ V[i]+" " +n[i]+" "+ m[i]+" "+ h[i]);
            arr[0][i]=V[i];
            arr[1][i]=n[i];
            arr[2][i]=m[i];
            arr[3][i]=h[i];

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
    public void show(double[][] tab, int ile,String nazwa, int ktory){
        XYSeries series = new XYSeries("Dane");
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(int i=0;i< ile;i++) {
            series.add(i, tab[ktory][i]);
        }
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createXYLineChart("Wykres", "Czas(ms)", "Vm(mV)",dataset, PlotOrientation.VERTICAL,true,true,false);
        ChartFrame frame = new ChartFrame("Wykres",chart);
        frame.setVisible(true);
        frame.setSize(500,400);
        save(chart, new File(nazwa+".png"));
    }

    public void show_I(double [] tab, int ile, String nazwa){
        XYSeries series = new XYSeries("dane");
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
    public void show_all(double[][] tab, int ile,String nazwa){
        XYSeries series = new XYSeries("V");
        XYSeries series1 = new XYSeries("n");
        XYSeries series2 = new XYSeries("m");
        XYSeries series3 = new XYSeries("h");
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(int i=0;i< ile;i++) {
            series.add(i,tab[0][i]);
            series1.add(i,tab[1][i]);
            series2.add(i,tab[2][i]);
            series3.add(i,tab[3][i]);
        }
        dataset.addSeries(series);
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        JFreeChart chart = ChartFactory.createXYLineChart("Wykres", "Czas(ms)", "Vm(mV)",dataset, PlotOrientation.VERTICAL,true,true,false);
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
