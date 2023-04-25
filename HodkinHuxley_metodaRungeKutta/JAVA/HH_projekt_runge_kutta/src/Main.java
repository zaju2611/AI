public class Main {
    public static void main(String[] args) {
        HH hodkin = new HH();
        double[][] tab = hodkin.main();
        hodkin.show(tab, hodkin.getTime(),"wykres V",0);
        hodkin.show(tab, hodkin.getTime(),"wykres n",1);
        hodkin.show(tab, hodkin.getTime(),"wykres m",2);
        hodkin.show(tab, hodkin.getTime(),"wykres h",3);
        hodkin.show_all(tab, hodkin.getTime(), "wykresWszystko");
        double [] tabI= hodkin.methodI();
        hodkin.show_I(tabI, hodkin.getTime(), "WykresPradu");
    }


}
