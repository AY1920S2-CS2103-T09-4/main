package fithelper.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import fithelper.model.entry.Entry;
import fithelper.model.entry.UniqueEntryList;
import javafx.collections.ObservableList;

/**
 * Wraps all entry-related data at the FitHelper level
 * Duplicates are not allowed (by .isSameEntry comparison)
 */
public class FitHelper implements ReadOnlyFitHelper {

    private final UniqueEntryList foodEntries = new UniqueEntryList();
    private final UniqueEntryList sportsEntries = new UniqueEntryList();
    private final UniqueEntryList reminderEntries = new UniqueEntryList();

    public FitHelper() {}

    /**
     * Creates an FitHelper using the Entries in the {@code toBeCopied}
     */
    public FitHelper(ReadOnlyFitHelper toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the entry list with {@code entries}.
     * {@code entries} must not contain duplicate entries.
     */
    public void setEntries(List<Entry> entries) {
        List<Entry> foodList = new ArrayList<>();
        List<Entry> sportsList = new ArrayList<>();
        List<Entry> reminderList = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.isFood()) {
                foodList.add(entry);
            } else {
                sportsList.add(entry);
            }
            if (entry.getStatus().value.equalsIgnoreCase("Undone")) {
                reminderList.add(entry);
            }
        }
        this.foodEntries.setEntries(foodList);
        this.sportsEntries.setEntries(sportsList);
        this.reminderEntries.setEntries(reminderList);
    }

    public void setEntries(List<Entry> foods, List<Entry> sports, List<Entry> reminders) {
        this.foodEntries.setEntries(foods);
        this.sportsEntries.setEntries(sports);
        this.reminderEntries.setEntries(reminders);
    }

    /**
     * Replaces the given entry {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the log book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the log book.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireNonNull(editedEntry);
        if (target.isFood()) {
            foodEntries.setEntry(target, editedEntry);
        } else {
            sportsEntries.setEntry(target, editedEntry);
        }
        if (!editedEntry.isDone()) {
            reminderEntries.add(editedEntry);
        } else {
            reminderEntries.remove(target);
        }
    }

    /**
     * Resets the existing data of this {@code FitHelper} with {@code newData}.
     */
    public void resetData(ReadOnlyFitHelper newData) {
        requireNonNull(newData);
        setEntries(newData.getFoodList(), newData.getSportsList(), newData.getReminderList());
    }

    //// entry-level operations

    /**
     * Returns true if a entry with the same identity as {@code entry} exists in FitHelper.
     */
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        if (entry.isFood()) {
            return foodEntries.contains(entry);
        } else {
            return sportsEntries.contains(entry);
        }
    }

    /**
     * Adds an entry to FitHelper.
     * The entry must not already exist in FitHelper.
     */
    public void addEntry(Entry entry) {
        if (entry.isFood()) {
            foodEntries.add(entry);
        } else {
            sportsEntries.add(entry);
        }
        if (!entry.isDone()) {
            reminderEntries.add(entry);
        }
    }

    /**
     * Removes {@code key} from this {@code FitHelper}.
     * {@code key} must exist in FitHelper.
     */
    public void removeEntry(Entry key) {
        if (key.isFood()) {
            foodEntries.remove(key);
        } else {
            sportsEntries.remove(key);
        }
        if (!key.isDone()) {
            reminderEntries.remove(key);
        }
    }

    //// util methods

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Food: ")
                .append(foodEntries.asUnmodifiableObservableList().size())
                .append("\n")
                .append("Sports ")
                .append(sportsEntries.asUnmodifiableObservableList().size())
                .append("\n")
                .append("Reminders ")
                .append(reminderEntries.asUnmodifiableObservableList().size())
                .append("\n");
        return builder.toString();
    }

    /**
     * Returns an unmodifiable view of the food entry list.
     * This list will not contain any duplicate entries.
     */
    @Override
    public ObservableList<Entry> getFoodList() {
        return foodEntries.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the sports entry list.
     * This list will not contain any duplicate entries.
     */
    @Override
    public ObservableList<Entry> getSportsList() {
        return sportsEntries.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the sports entry list.
     * This list will not contain any duplicate entries.
     */
    @Override
    public ObservableList<Entry> getReminderList() {
        return reminderEntries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FitHelper // instanceof handles nulls
                && foodEntries.equals(((FitHelper) other).foodEntries)
                && sportsEntries.equals(((FitHelper) other).sportsEntries)
                && reminderEntries.equals(((FitHelper) other).reminderEntries));
    }

    @Override
    public int hashCode() {
        List<UniqueEntryList> allList = new ArrayList<>();
        allList.add(foodEntries);
        allList.add(sportsEntries);
        allList.add(reminderEntries);
        return allList.hashCode();
    }
}
