package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalDeliveries.GABRIELS_MILK;
import static seedu.address.testutil.TypicalDeliveries.GAMBES_RICE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.TypicalPersons;


public class DeliveryTest {
    @Test
    public void isSameDelivery() {
        // same object -> returns true
        assertTrue(GABRIELS_MILK.isSameDelivery(GABRIELS_MILK));

        // null -> returns false
        assertFalse(GABRIELS_MILK.isSameDelivery(null));

        // same id, other attributes different -> returns false
        Delivery editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel's Milk Updated").build();
        assertFalse(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));

        // same id, different status -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withStatus(DeliveryStatus.DELIVERED).build();
        assertFalse(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));

        // same id, different customer -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withCustomer(TypicalPersons.BOB).build();
        assertFalse(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));

        // different id, all other attributes same -> returns false
        Delivery gambesRice = new DeliveryBuilder(GAMBES_RICE).build();
        assertFalse(GABRIELS_MILK.isSameDelivery(gambesRice));
    }

    @Test
    public void equals() {
        Delivery gabrielsMilkCopy = new DeliveryBuilder(GABRIELS_MILK).build();

        // same object -> returns true
        assertTrue(GABRIELS_MILK.equals(GABRIELS_MILK));

        // null -> returns false
        assertFalse(GABRIELS_MILK.equals(null));

        // different type -> returns false
        assertFalse(GABRIELS_MILK.equals(5));

        // different delivery -> returns false
        assertFalse(GABRIELS_MILK.equals(GAMBES_RICE));

        // different name -> returns false
        Delivery editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel's Milk Updated").build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different status -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withStatus(DeliveryStatus.DELIVERED).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different customer -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withCustomer(TypicalPersons.BOB).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different id -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withId(2).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // same id, different attributes -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel's Milk Updated")
            .withStatus(DeliveryStatus.DELIVERED).withCustomer(TypicalPersons.BOB).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));


    }
}