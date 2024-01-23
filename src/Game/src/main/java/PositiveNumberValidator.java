import com.beust.jcommander.IParameterValidator;

public class PositiveNumberValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) {
        try {
            int num = Integer.parseInt(value);
            if (num <= 0) {
                throw new IllegalParametersException(name + " should be a positive number.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalParametersException(name + " should be a valid input.");
        }
    }
}
