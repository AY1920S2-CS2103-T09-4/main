package fithelper.ui.calendar;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import jfxtras.icalendarfx.components.VEvent;

/**
 * Display two calendars.
 */
public class CalendarPanel extends UiPart<AnchorPane> {
    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private final CalendarPage calendarPage;

    @FXML
    private StackPane calendarPagePlaceholder;

    @FXML
    private AnchorPane fullCalendarPlaceholder;

    /**
     * Creates a calendar page displaying two components from {@code }.
     */
    public CalendarPanel(ObservableList<Entry> foodList, ObservableList<Entry> sportList, ObservableList<VEvent> events) {
        super(FXML);
        logger.info("Initializing Calendar Page");
        calendarPage = new CalendarPage(events);
        calendarPage.updateScheduler();
        calendarPagePlaceholder.getChildren().add(calendarPage.getRoot());
        fullCalendarPlaceholder.getChildren().add(new FullCalendar(foodList, sportList).getView());
    }
}
