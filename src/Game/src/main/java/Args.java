import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {
    @Parameter(names = "--enemiesCount", required = true, validateWith = PositiveNumberValidator.class)
    private int enemiesCount;

    @Parameter(names = "--wallsCount", required = true, validateWith = PositiveNumberValidator.class)
    private int wallsCount;

    @Parameter(names = "--profile", required = true)
    private String profile;

    @Parameter(names = "--size", required = true, validateWith = PositiveNumberValidator.class)
    private int size;

    public int getEnemiesCount() {
        return enemiesCount;
    }

    public int getWallsCount() {
        return wallsCount;
    }

    public String getProfile() {
        return profile;
    }

    public int getSize() {
        return size;
    }
}
