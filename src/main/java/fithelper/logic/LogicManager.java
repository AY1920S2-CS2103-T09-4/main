package fithelper.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import fithelper.commons.core.GuiSettings;
import fithelper.commons.core.LogsCenter;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.commands.exceptions.CommandException;
import fithelper.logic.parser.FitHelperParser;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.Model;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.entry.Entry;
import fithelper.storage.Storage;

import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final FitHelperParser fitHelperParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        fitHelperParser = new FitHelperParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = fitHelperParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveFitHelper(model.getFitHelper());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyFitHelper getFitHelper() {
        return model.getFitHelper();
    }

    @Override
    public ObservableList<Entry> getFilteredFoodEntryList() {
        return model.getFilteredFoodEntryList();
    }

    @Override
    public ObservableList<Entry> getFilteredSportsEntryList() {
        return model.getFilteredSportsEntryList();
    }

    @Override
    public Path getFitHelperFilePath() {
        return model.getFitHelperFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
