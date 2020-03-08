package fithelper.model.entry;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Entry's calorie in FitHelper.
 * Guarantees: immutable; is valid as declared in {@link #isValidCalorie(String)}
 */
public class Calorie {

    public static final String MESSAGE_CONSTRAINTS = "Calorie should a positive number.";
    public final double TOLERANCE = 0.0001; // tolerance for double comparision.

    public final double value;

    /**
     * Constructs an {@code Type}.
     *
     * @param calorie A valid calorie of entry.
     */
    public Calorie(String calorie) {
        requireNonNull(calorie);
        checkArgument(isValidCalorie(calorie), MESSAGE_CONSTRAINTS);
        value = Double.parseDouble(calorie);
    }

    /**
     * Returns true if a given string is a valid calorie.
     */
    public static boolean isValidCalorie(String test) {
        double testValue;
        try {
            testValue = Double.parseDouble(test);
        } catch (NumberFormatException e) {
            return false;
        }
        return testValue > 0;
    }

    @Override
    public String toString() {
        return String.format("%.3f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Calorie // instanceof handles nulls
                && Math.abs(value - ((Calorie) other).value) < TOLERANCE); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
