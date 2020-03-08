package fithelper.logic.commands;

import static fithelper.logic.parser.CliSyntax.PREFIX_CALORIE;
import static fithelper.logic.parser.CliSyntax.PREFIX_LOCATION;
import static fithelper.logic.parser.CliSyntax.PREFIX_NAME;
import static fithelper.logic.parser.CliSyntax.PREFIX_REMARK;
import static fithelper.logic.parser.CliSyntax.PREFIX_TIME;
import static fithelper.logic.parser.CliSyntax.PREFIX_TYPE;
import static java.util.Objects.requireNonNull;

import fithelper.logic.commands.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.entry.Entry;

/**
 * Adds a entry to FitHelper.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entry to FitHelper. "
            + "Parameters: "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_NAME + "NAME "
            + PREFIX_TIME + "TIME "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_CALORIE + "CALORIE "
            + "[" + PREFIX_REMARK + "REMARK]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "food"
            + PREFIX_NAME + "noodles"
            + PREFIX_TIME + "2020-03-01-15:30"
            + PREFIX_LOCATION + "Utown canteen"
            + PREFIX_CALORIE + "100.5"
            + PREFIX_REMARK + "too expensive";

    public static final String MESSAGE_SUCCESS = "New Entry added: %1$s";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in FitHelper";

    private final Entry toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Entry}
     */
    public AddCommand(Entry entry) {
        requireNonNull(entry);
        toAdd = entry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEntry(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.addEntry(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
