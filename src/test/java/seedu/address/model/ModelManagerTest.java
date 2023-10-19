package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.GABRIELS_MILK;
import static seedu.address.testutil.TypicalDeliveries.JAMES_RICE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.DeliveryBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    // Delivery
    @Test
    public void setDeliveryBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDeliveryBookFilePath(null));
    }

    @Test
    public void setDeliveryBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("delivery/book/file/path");
        modelManager.setDeliveryBookFilePath(path);
        assertEquals(path, modelManager.getDeliveryBookFilePath());
    }

    @Test
    public void hasDelivery_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasDelivery(null));
    }

    @Test
    public void hasDelivery_deliveryNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasDelivery(GABRIELS_MILK));
    }

    @Test
    public void hasDelivery_deliveryInAddressBook_returnsTrue() {
        modelManager.addDelivery(GABRIELS_MILK);
        assertTrue(modelManager.hasDelivery(GABRIELS_MILK));
    }

    @Test
    public void getFilteredDeliveryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredDeliveryList().remove(0));
    }

    @Test
    public void sortDeliveryBookByNameAsc() {
        modelManager.setLoginSuccess();
        modelManager.addDelivery(GABRIELS_MILK);
        modelManager.addDelivery(JAMES_RICE);
        modelManager.sortFilteredDeliveryList(Comparator.comparing(Delivery::getName));
        assertEquals(Arrays.asList(GABRIELS_MILK, JAMES_RICE), modelManager.getSortedDeliveryList());
    }

    @Test
    public void sortDeliveryBookByNameDesc() {
        modelManager.setLoginSuccess();
        modelManager.addDelivery(GABRIELS_MILK);
        modelManager.addDelivery(JAMES_RICE);
        modelManager.sortFilteredDeliveryList(Comparator.comparing(Delivery::getName).reversed());
        assertEquals(Arrays.asList(JAMES_RICE, GABRIELS_MILK), modelManager.getSortedDeliveryList());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        DeliveryBook deliveryBook =
            new DeliveryBookBuilder().withDelivery(GABRIELS_MILK).withDelivery(JAMES_RICE).build();
        AddressBook differentAddressBook = new AddressBook();
        DeliveryBook differentDeliveryBook = new DeliveryBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, deliveryBook, userPrefs, true);
        ModelManager modelManagerCopy = new ModelManager(addressBook, deliveryBook, userPrefs, true);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, deliveryBook, userPrefs, true)));

        // different deliverybook -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentDeliveryBook, userPrefs, true)));

        // different filteredPersonList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, deliveryBook, userPrefs, true)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_CUSTOMERS);

        // different filteredDeliveryList -> returns false
        String[] deliveryKeywords = GABRIELS_MILK.getName().deliveryName.split("\\s+");
        modelManager.updateFilteredDeliveryList(new DeliveryNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, deliveryBook, userPrefs, true)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, deliveryBook, differentUserPrefs, true)));
    }

    @Test
    public void getDelivery_returnsDelivery() {
        modelManager.addDelivery(GABRIELS_MILK);
        assertEquals(modelManager.getDelivery(1), Optional.of(GABRIELS_MILK));
    }
}
