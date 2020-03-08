package fithelper.model.entry;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Entry in the FitHelper.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Entry {

    private final Type type;
    private final Name name;
    private final Time time;
    private final Location location;
    private final Calorie calorie;
    private Remark remark = new Remark("");

    /**
     * Every field must be present and not null.
     */
    public Entry(Type type, Name name, Time time, Location location, Calorie calorie) {
        requireAllNonNull(type, name, location, time, calorie);
        this.type = type;
        this.name = name;
        this.location = location;
        this.time = time;
        this.calorie = calorie;
    }

    /**
     * Every field must be present and not null.
     */
    public Entry(Type type, Name name, Time time, Location location, Calorie calorie, Remark remark) {
        requireAllNonNull(type, name, location, time, calorie);
        this.type = type;
        this.name = name;
        this.location = location;
        this.time = time;
        this.calorie = calorie;
        this.remark = remark;
    }

    public Type getType() {
        return type;
    }

    public Name getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Time getTime() {
        return time;
    }

    public Calorie getCalorie() {
        return calorie;
    }

    public void addRemark(Remark remark) {
        this.remark = remark;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns true if both Entry of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two entries.
     */
    public boolean isSameEntry(Entry anotherEntry) {
        if (anotherEntry == this) {
            return true;
        }

        return anotherEntry != null
                && anotherEntry.getName().equals(getName())
                && (anotherEntry.getLocation().equals(getLocation()))
                && (anotherEntry.getTime().equals(getTime()));
    }

    /**
     * Returns true if both entries have the same identity and data fields.
     * This defines a stronger notion of equality between two entries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Entry)) {
            return false;
        }

        Entry otherPerson = (Entry) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getLocation().equals(getLocation())
                && otherPerson.getTime().equals(getTime())
                && otherPerson.getCalorie().equals(getCalorie());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, time, location, calorie);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Time: ")
                .append(getTime())
                .append(" Location: ")
                .append(getLocation())
                .append(" Calorie: ")
                .append(getCalorie())
                .append(" Remark: ")
                .append(getRemark().toString());
        return builder.toString();
    }

}