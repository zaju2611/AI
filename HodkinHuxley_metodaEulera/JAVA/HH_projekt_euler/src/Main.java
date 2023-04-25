public class Main {
    public static void main(String[] args) {
        HH hodkin = new HH();
        double[][] tab = hodkin.main();
        hodkin.show(tab, hodkin.getTime(),"wykres V",0);
        hodkin.show(tab, hodkin.getTime(),"wykres V",1);
        hodkin.show(tab, hodkin.getTime(),"wykres V",2);
        hodkin.show(tab, hodkin.getTime(),"wykres V",3);
        hodkin.show_all(tab, hodkin.getTime(), "wykresWszystko");
        double [] tabI= hodkin.methodI();
        hodkin.show_I(tabI, hodkin.getTime(), "WykresPradu");
    }


}
