public class Tester {
    public static void main(String[] args) {

        GameInit gf = new GameInit(300, 300, 10, 10);
        gf.initFields();
        gf.initTarget();
        gf.printAllFields();
    }
}
