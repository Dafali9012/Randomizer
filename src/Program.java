public class Program {
    private static final Program instance = new Program();
    private Program() {
        Randomizer randomizer = Randomizer.getInstance();
        randomizer.randomize();
    }
    public static Program getInstance() {
        return instance;
    }
}
