package fithelper.model;

import java.util.function.Predicate;

import fithelper.model.entry.Entry;

import javafx.collections.ObservableList;
//import jfxtras.icalendarfx.components.VEvent;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Entry> PREDICATE_SHOW_ALL_ENTRIES = unused -> true;
    Predicate<Entry> PREDICATE_SHOW_UNDONE_ENTRIES = entry -> entry.getStatus().value.equals("Undone");
    Predicate<Entry> PREDICATE_SHOW_DONE_ENTRIES = entry -> entry.getStatus().value.equals("Done");

    /**
     * Replaces FitHelper data with the data in {@code fitHelper}.
     */
    void setFitHelper(ReadOnlyFitHelper fitHelper);

    /** Returns the FitHelper */
    ReadOnlyFitHelper getFitHelper();

    /**
     * Returns true if an Entry with the same identity as {@code entry} exists in the FitHelper.
     */
    boolean hasEntry(Entry entry);

    /**
     * Deletes the given entry.
     * The entry must exist in the log book.
     */
    void deleteEntry(Entry target);

    /**
     * Adds the given Entry.
     * {@code entry} must not already exist in the log book.
     */
    void addEntry(Entry entry);

    /**
     * Replaces the given entry {@code target} with {@code editedEntry}.
     * {@code target} must exist in the log book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the log book.
     */
    void setEntry(Entry target, Entry editedEntry);

    /** Returns an unmodifiable view of the filtered food entry list */
    ObservableList<Entry> getFilteredFoodEntryList();

    /** Returns an unmodifiable view of the filtered sports entry list */
    ObservableList<Entry> getFilteredSportsEntryList();

    /**
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEntryList(Predicate<Entry> predicate);

   /* ObservableList<VEvent> getVFoodList();
    ObservableList<VEvent> getVSportsList();*/
}

