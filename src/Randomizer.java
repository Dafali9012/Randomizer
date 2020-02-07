import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Randomizer {
    private static final Randomizer instance = new Randomizer();
    private List<String> options = new ArrayList<>();
    private Path optionsPath = Paths.get("options.cfg");
    private SecureRandom sr = new SecureRandom();

    private Randomizer() {
        loadOptions();
    }

    public static Randomizer getInstance() {
        return instance;
    }

    public void randomize() {
        if(options.isEmpty()) {
            System.out.println("No options available, edit the options config.");
        } else {
            int index = sr.nextInt(options.size());
            System.out.println(options.get(index));
        }
    }

    private void loadOptions() {
        try {
            if(!Files.exists(optionsPath)) {
                createOptions();
            }
            for (String line:Files.readAllLines(optionsPath)) {
                if(line.matches("[ ]*#.*")) {
                    //System.out.println("comment");
                } else if(line.matches("[^:]+:[ ]*[0-9]+[ ]*[\n]?")) {
                    int amount = Integer.parseInt(line.split(":")[1].trim());
                    String option = line.split(":")[0].trim();

                    while(amount>0) {
                        options.add(option);
                        --amount;
                    }
                }
            }
        } catch (Exception e) {}
    }

    private void createOptions() {
        try {
            String defaultOptions =
                    "# \"Option\" \":\" \"Amount\"\n"
                    +"# \"#\": Comment\n"
                    +"\n"
                    +"Option 1:1\n"
                    +"Option 2:1\n"
                    +"Option 3:1\n";

            Files.createFile(optionsPath);
            Files.write(optionsPath, defaultOptions.getBytes());
        } catch (Exception e) {}
    }
}
