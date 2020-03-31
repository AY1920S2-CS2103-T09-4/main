package fithelper.logic.commands.diary;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DIARYCONTENT;
import static fithelper.model.Model.PREDICATE_SHOW_ALL_DIARIES;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.core.Messages;
import fithelper.commons.util.CollectionUtil;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.commands.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.diary.Content;
import fithelper.model.diary.Diary;
import fithelper.model.diary.DiaryDate;

/**
 * Appends the content of the diary of a specific date.
 */
public class AppendDiaryCommand extends Command {

    public static final String COMMAND_WORD = "appendDiary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Appends new content to the diary identified."
            + "Existing values will remain unchanged.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_DIARYCONTENT + "DIARY CONTENT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2020-03-31"
            + PREFIX_DIARYCONTENT + "Today is my birthday. I ate a huge birthday cake, but I also went to the gym with"
            + " my friends. Everything was just perfect. In my twenty, I'm gonna turn heat up.";

    public static final String MESSAGE_APPEND_DIARY_SUCCESS = "Appended Diary: %1$s";

    private static final String MESSAGE_COMMIT = "Append a diary";

    private static final Logger logger = LogsCenter.getLogger(AppendDiaryCommand.class);

    private final String diaryId;
    private final AppendDiaryDescriptor appendDiaryDescriptor;

    /**
     * @param diaryId of the diary in the filtered diary list to append
     * @param appendDiaryDescriptor details to append the diary with
     */
    public AppendDiaryCommand(String diaryId, AppendDiaryDescriptor appendDiaryDescriptor) {
        requireNonNull(diaryId);
        requireNonNull(appendDiaryDescriptor);

        this.diaryId = diaryId;
        this.appendDiaryDescriptor = new AppendDiaryDescriptor(appendDiaryDescriptor);
    }

    public String getDiaryId() {
        return diaryId;
    }

    public AppendDiaryDescriptor getAppendDiaryDescriptor() {
        return appendDiaryDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Diary> lastShownList;

        lastShownList = model.getFilteredDiaryList();

        if (!model.hasDiaryDate(appendDiaryDescriptor.getDiaryDate())) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIARY_DATE);
        }

        DiaryDate prevDiaryDate = appendDiaryDescriptor.getDiaryDate();
        Diary prevDiary = getDiaryByDate(lastShownList, prevDiaryDate);

        Diary appendedDiary = createAppendedDiary(prevDiary, diaryId, appendDiaryDescriptor);

        model.setDiary(prevDiary, appendedDiary);
        model.updateFilteredDiaryList(PREDICATE_SHOW_ALL_DIARIES);

        model.commit(MESSAGE_COMMIT);
        logger.info(String.format("appended a new diary [%s]", appendDiaryDescriptor.toString()));

        //model.updateFil
        return new CommandResult(String.format(MESSAGE_APPEND_DIARY_SUCCESS, appendedDiary),
                CommandResult.DisplayedPage.DIARY, false);
    }

    /**
     * Creates and returns a {@code Diary} with the details of {@code diaryToAppend}
     * appended with {@code appendDiaryDescriptor}.
     */
    private static Diary createAppendedDiary(Diary prevDiary, String diaryId, AppendDiaryDescriptor appendDiaryDescriptor) {
        assert diaryId != null;
        assert prevDiary.getDiaryDate().toString()
                .equalsIgnoreCase(appendDiaryDescriptor.getDiaryDate().toString());

        DiaryDate updatedDiaryDate = appendDiaryDescriptor.getDiaryDate();
        Content updatedContent = prevDiary.getContent().appendContent(appendDiaryDescriptor.getContent());

        return new Diary(updatedDiaryDate, updatedContent);
    }

    public Diary getDiaryByDate(List<Diary> diaryList, DiaryDate diaryDate) {
        Diary tempDiary = new Diary(diaryDate, new Content(""));
        for (Diary diary : diaryList) {
            if (diary.getDiaryDate().equals(diaryDate)) {
                tempDiary.setContent(diary.getContent());
            }
        }
        return tempDiary;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppendDiaryCommand)) {
            return false;
        }

        // state check
        AppendDiaryCommand e = (AppendDiaryCommand) other;
        return diaryId.equals(e.diaryId)
                && appendDiaryDescriptor.equals(e.appendDiaryDescriptor);
    }

    /**
     * Stores the details to append the diary with. Each non-empty field value will replace the
     * corresponding field value of the diary.
     */
    public static class AppendDiaryDescriptor {
        private DiaryDate diaryDate;
        private Content content;

        public AppendDiaryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code status} is used internally.
         */
        public AppendDiaryDescriptor(AppendDiaryDescriptor toCopy) {
            setDiaryDate(toCopy.diaryDate);
            setContent(toCopy.content);
        }

        /**
         * Returns true if at least one field is appended.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(diaryDate, content);
        }

        public DiaryDate getDiaryDate() {
            return diaryDate;
        }

        public Content getContent() {
            return content;
        }

        public void setDiaryDate(DiaryDate diaryDate) {
            this.diaryDate = diaryDate;
        }

        public void setContent(Content content) {
            this.content = content;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AppendDiaryDescriptor)) {
                return false;
            }

            // state check

            AppendDiaryCommand.AppendDiaryDescriptor e = (AppendDiaryCommand.AppendDiaryDescriptor) other;

            return diaryDate.getDiaryDate().equals(e.getDiaryDate())
                    && getContent().equals(e.getContent());
        }
    }
}
