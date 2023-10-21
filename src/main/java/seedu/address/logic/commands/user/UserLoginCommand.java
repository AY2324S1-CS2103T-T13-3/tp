package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.User;

/**
 * Logs in the user and allows the user to access other functionalities.
 */
public class UserLoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Login to HomeBoss.\n"
            + "Parameters: " + PREFIX_USER + " " + PREFIX_PASSWORD + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USER + " yourUsername "
            + PREFIX_PASSWORD + " yourPassword ";
    public static final String MESSAGE_SUCCESS = "Welcome back to HomeBoss!";
    public static final String MESSAGE_WRONG_CREDENTIALS = "Wrong username/password";
    public static final String MESSAGE_ALREADY_LOGGED_IN = "You are already logged in!";

    private final User user;

    /**
     * Creates a UserLoginCommand to log in the specified {@code User}
     */
    public UserLoginCommand(User user) {
        requireNonNull(user);
        this.user = user;
    }

    /**
     * Executes the user login command.
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that indicates success.
     * @throws CommandException if the user is already logged in or the user credentials are wrong.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Logged in user cannot login again
        if (model.getUserLoginStatus()) {
            throw new CommandException(MESSAGE_ALREADY_LOGGED_IN);
        }

        // Check if the user matches the user loaded in model
        if (!model.userMatches(user)) {
            throw new CommandException(MESSAGE_WRONG_CREDENTIALS);
        }

        model.setLoginSuccess();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    /**
     * Checks if the user login command is equal to another object.
     * @param other The other object to compare to.
     * @return True if the other object is a user login command with the same user.
     *        False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserLoginCommand)) {
            return false;
        }

        UserLoginCommand otherUserLoginCommand = (UserLoginCommand) other;
        return user.equals(otherUserLoginCommand.user);
    }
}
