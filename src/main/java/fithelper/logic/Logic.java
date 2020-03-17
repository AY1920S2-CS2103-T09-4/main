package fithelper.logic;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.commands.exceptions.CommandException;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.entry.Entry;
import javafx.collections.ObservableList;

import jfxtras.icalendarfx.components.VEvent;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, IllegalValueException;

    /**
     * Returns the FitHelper.
     *
     * @see fithelper.model.Model#getFitHelper()
     */
    ReadOnlyFitHelper getFitHelper();

    ObservableList<VEvent> getVEvents();

    /** Returns an unmodifiable view of the filtered list of food entries*/
    ObservableList<Entry> getFilteredFoodEntryList();

    /** Returns an unmodifiable view of the filtered list of sports entries*/
    ObservableList<Entry> getFilteredSportsEntryList();

    /** Returns an unmodifiable view of the filtered list of sports entries*/
    ObservableList<Entry> getFilteredReminderEntryList();

}
