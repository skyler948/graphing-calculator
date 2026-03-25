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
    private int minX, maxX, minY, maxY;

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
            writer.write("\nminX=-14");
            writer.write("\nmaxX=14");
            writer.write("\nminY=-10");
            writer.write("\nmaxY=10");

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

            // Get data
            if (data.getFirst().contains("scale=")) {
                scale = Integer.parseInt(data.getFirst().substring(6));
            }
            if (data.get(1).contains("minX=")) {
                minX = Integer.parseInt(data.get(1).substring(5));
            }
            if (data.get(2).contains("maxX=")) {
                maxX = Integer.parseInt(data.get(2).substring(5));
            }
            if (data.get(3).contains("minY=")) {
                minY = Integer.parseInt(data.get(3).substring(5));
            }
            if (data.get(4).contains("maxY=")) {
                maxY = Integer.parseInt(data.get(4).substring(5));
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
            writer.write("\nminX=" + minX);
            writer.write("\nmaxX=" + maxX);
            writer.write("\nminY=" + minY);
            writer.write("\nmaxY=" + maxY);

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
        if (this.scale < 1) {
            this.scale = 1;
        }
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

}
