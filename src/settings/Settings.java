package settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Settings {

    private final String PATH = ".config";

    private final File configFile;

    // Settings
    private int scale;

    public Settings() {
        configFile = new File(PATH);

        // Check if the file exists
        checkConfigFile();

        // Read file
        readConfigFile();
    }

    private void checkConfigFile() {
        // Check if file exists
        try {
            if (configFile.createNewFile()) {
                System.out.println("Creating config file...");
                createDefaultConfig();
            } else {
                System.out.println("Config file found.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDefaultConfig() {
        try {
            FileWriter writer = new FileWriter(configFile);

            // Default configs
            writer.write("scale=1");

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readConfigFile() {
        try {
            Scanner reader = new Scanner(configFile);

            // Read file
            ArrayList<String> data = new ArrayList<String>();
            while (reader.hasNextLine()) {
                data.add(reader.nextLine());
            }

            // First line should be scale
            if (data.getFirst().contains("scale=")) {
                scale = Integer.parseInt(data.getFirst().substring(6));
            }

            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeChangesToConfigFile() {
        try {
            FileWriter writer = new FileWriter(configFile);

            // Write all values to file
            writer.write("scale=" + scale);

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getConfigPath() {
        return PATH;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

}
