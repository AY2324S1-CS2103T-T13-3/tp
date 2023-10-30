package seedu.address.logic.commands.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Customer;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class CustomerDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(), new UserPrefs(), true);

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Customer customerToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CustomerDeleteCommand deleteCommand = new CustomerDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(CustomerDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(customerToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(),
                new UserPrefs(), model.getUserLoginStatus());
        expectedModel.deletePerson(customerToDelete);
        expectedModel.deleteDeliveryByCustomer(customerToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CustomerDeleteCommand deleteCommand = new CustomerDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Customer customerToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CustomerDeleteCommand deleteCommand = new CustomerDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(CustomerDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(customerToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(),
                new UserPrefs(), model.getUserLoginStatus());
        expectedModel.deletePerson(customerToDelete);
        expectedModel.deleteDeliveryByCustomer(customerToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getList().size());

        CustomerDeleteCommand deleteCommand = new CustomerDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CustomerDeleteCommand deleteFirstCommand = new CustomerDeleteCommand(INDEX_FIRST_PERSON);
        CustomerDeleteCommand deleteSecondCommand = new CustomerDeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        CustomerDeleteCommand deleteFirstCommandCopy = new CustomerDeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        CustomerDeleteCommand deleteCommand = new CustomerDeleteCommand(targetIndex);
        String expected = CustomerDeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
