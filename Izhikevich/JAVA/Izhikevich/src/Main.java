import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {
    public static void main(String[] args) {
        Izhikevich izhikevich = new Izhikevich();
        double [][] tab = izhikevich.izhikevich_model();
        izhikevich.show(tab, izhikevich.getTime(),"wykresV");
        izhikevich.show(tab,1000, "wykresPrzyblizony");
        double [] tabI = izhikevich.methodI();
        izhikevich.show_I(tabI, izhikevich.getTime(),"wykresPrad");

    }


}
