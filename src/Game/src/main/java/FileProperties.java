import java.io.*;
import java.util.Properties;

public class FileProperties implements TransferData {
    private String enemyChar;
    private String playerChar;

    private String wallChar;

    private String goalChar;

    private String emptyChar;

    private String enemyColor;

    private String playerColor;
    private String wallColor;

    private String goalColor;

    private String emptyColor;
    private String profile;
    public void load(Args params) {
        profile = params.getProfile();
        String propertiesPath = "src/main/resources/application-" + profile + ".properties";
        File file = new File(propertiesPath);
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        enemyChar = getProperty(properties, "enemy.char", " ");
        playerChar = getProperty(properties, "player.char", " ");
        wallChar = getProperty(properties, "wall.char", " ");
        goalChar = getProperty(properties, "goal.char", " ");
        emptyChar = getProperty(properties, "empty.char", " ");

        enemyColor = getProperty(properties, "enemy.color", "WHITE");
        playerColor = getProperty(properties, "player.color", "BLACK");
        wallColor = getProperty(properties, "wall.color", "WHITE");
        goalColor = getProperty(properties, "goal.color", "BLACK");
        emptyColor = getProperty(properties, "empty.color", "WHITE");
    }

    private static String getProperty(Properties properties, String key, String defaultValue) {
        String value = properties.getProperty(key, defaultValue);
        return value.isEmpty() ? " " : value;
    }

    public String getProfile() {
        return profile;
    }

    public String getEmptyChar() {
        return emptyChar;
    }

    public String getEnemyChar() {
        return enemyChar;
    }

    public String getGoalChar() {
        return goalChar;
    }

    public String getPlayerChar() {
        return playerChar;
    }

    public String getWallChar() {
        return wallChar;
    }

    public String getEmptyColor() {
        return emptyColor;
    }

    public String getEnemyColor() {
        return enemyColor;
    }

    public String getGoalColor() {
        return goalColor;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public String getWallColor() {
        return wallColor;
    }
}

