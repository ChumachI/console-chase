import com.beust.jcommander.JCommander;

public class App {
    public static void main(String[] args) {
        Args argsObj = new Args();
        FileProperties properties = new FileProperties();
        JCommander.newBuilder().addObject(argsObj).build().parse(args);
        properties.load(argsObj);
        new Game(argsObj, properties).start();
    }
}
