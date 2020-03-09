package fithelper.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntaxUtil {

    /* Prefix definitions */
    public static final Prefix PREFIX_TYPE = new Prefix("x/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_CALORIE = new Prefix("c/");
    public static final Prefix PREFIX_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");

    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_DURATION = new Prefix("dr/");

    public static final Prefix PREFIX_ATTRIBUTE = new Prefix("attr/");
    public static final Prefix PREFIX_VALUE = new Prefix("v/");
}
