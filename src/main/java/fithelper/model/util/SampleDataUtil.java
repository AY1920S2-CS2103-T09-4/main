package fithelper.model.util;

import fithelper.model.FitHelper;
import fithelper.model.ReadOnlyFitHelper;

import fithelper.model.entry.Calorie;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Location;
import fithelper.model.entry.Name;
import fithelper.model.entry.Remark;
import fithelper.model.entry.Time;
import fithelper.model.entry.Type;

/**
 * Contains utility methods for populating {@code FitHelper} with sample data.
 */
public class SampleDataUtil {

    /**
     * Gets sample food and sports entries.
     *
     * @return An array of food and sports entries.
     */
    public static Entry[] getSampleEntries() {
        return new Entry[]{
            new Entry(new Type("food"), new Name("Noodles"), new Time("2020-03-01-15:30"),
                    new Location("Utown canteen"), new Calorie("150.5"), new Remark("Too expensive")),
            new Entry(new Type("food"), new Name("Apple juice"), new Time("2020-03-01-11:30"),
                    new Location("Utown 711"), new Calorie("79")),
            new Entry(new Type("sports"), new Name("Running"), new Time("2020-03-01-10:00"),
                    new Location("Utown gym"), new Calorie("300")),
            new Entry(new Type("s"), new Name("Swimming"), new Time("2020-03-01-20:20"),
                    new Location("Sports Center"), new Calorie("450.5"), new Remark("Very tired.")),
            new Entry(new Type("f"), new Name("Ice-cream"), new Time("2020-03-31-14:30"),
                    new Location("YIH 711"), new Calorie("700"), new Remark("Not healthy")),
            new Entry(new Type("food"), new Name("Chicken Rice"), new Time("2020-03-01-11:30"),
                    new Location("Utown FineFood"), new Calorie("200")),
            new Entry(new Type("sports"), new Name("Football"), new Time("2019-12-01-14:00"),
                    new Location("Utown towngreen"), new Calorie("400")),
            new Entry(new Type("s"), new Name("Walking"), new Time("2020-02-01-19:20"),
                    new Location("PGP"), new Calorie("450.5"), new Remark("Feels good")),
        };
    }

    /**
     * Gets sample FitHelper.
     *
     * @return A FitHelper only with the sample entries.
     */
    public static ReadOnlyFitHelper getSampleFitHelper() {
        FitHelper sampleFitHelper = new FitHelper();
        for (Entry sampleEntry : getSampleEntries()) {
            sampleFitHelper.addEntry(sampleEntry);
        }
        return sampleFitHelper;
    }

}


