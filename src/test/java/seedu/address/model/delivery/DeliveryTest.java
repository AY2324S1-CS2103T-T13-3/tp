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
    public void deliveryId_success() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        System.out.println(delivery);
        Delivery delivery1 = new DeliveryBuilder().autoBuild();
        System.out.println(delivery1);
        assertTrue(delivery.getDeliveryId() != delivery1.getDeliveryId());
    }

    @Test
    public void deliveryId_failure() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        System.out.println(delivery);
        Delivery delivery1 = new DeliveryBuilder().autoBuild();
        System.out.println(delivery1);
        assertFalse(delivery.getDeliveryId() == delivery1.getDeliveryId());
    }

    @Test
    public void setOrderDate() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        OrderDate orderDate = new OrderDate("2020-10-10");
        delivery.setOrderDate(orderDate);
        assertTrue(delivery.getOrderDate().equals(orderDate));
    }

    @Test
    public void setDeliveryDate() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        DeliveryDate deliveryDate = new DeliveryDate("2020-10-10");
        delivery.setDeliveryDate(deliveryDate);
        assertTrue(delivery.getDeliveryDate().equals(deliveryDate));
    }

    @Test
    public void setStatus() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        DeliveryStatus status = DeliveryStatus.SHIPPED;
        delivery.setStatus(status);
        assertTrue(delivery.getStatus().equals(status));
        status = DeliveryStatus.COMPLETED;
        delivery.setStatus(status);
        assertTrue(delivery.getStatus().equals(status));
        status = DeliveryStatus.CANCELLED;
        delivery.setStatus(status);
        assertTrue(delivery.getStatus().equals(status));
    }

    @Test
    public void setNote() {
        Delivery delivery = new DeliveryBuilder().autoBuild();
        Note note = new Note("Hi!");
        delivery.setNote(note);
        assertTrue(delivery.getNote().equals(note));
    }

    @Test
    public void isSameDelivery() {
        // same object -> returns true
        assertTrue(GABRIELS_MILK.isSameDelivery(GABRIELS_MILK));

        // null -> returns false
        assertFalse(GABRIELS_MILK.isSameDelivery(null));

        // same id, other attributes different -> returns true
        Delivery editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel Milk Updated").build();
        assertTrue(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));

        // same id, different status -> returns true
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withStatus(DeliveryStatus.COMPLETED).build();
        assertTrue(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));

        // same id, different customer -> returns true
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withCustomer(TypicalPersons.BOB).build();
        assertTrue(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));

        // different id, all other attributes same -> returns false
        Delivery gambesRice = new DeliveryBuilder(GAMBES_RICE).build();
        assertFalse(GABRIELS_MILK.isSameDelivery(gambesRice));

        // different id, all other attributes different -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel Milk Updated")
            .withStatus(DeliveryStatus.COMPLETED).withCustomer(TypicalPersons.BOB).withOrderDate("2019-12-12")
            .withDeliveryDate("2024-12-12").autoBuild();
        assertFalse(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));
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
        Delivery editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel Milk Updated").build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different status -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withStatus(DeliveryStatus.COMPLETED).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different customer -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withCustomer(TypicalPersons.BOB).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different id -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withId(2).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // same id, different attributes -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel Milk Updated")
            .withStatus(DeliveryStatus.COMPLETED).withCustomer(TypicalPersons.BOB).build();
        assertFalse(GABRIELS_MILK.equals(editedGabrielsMilk));

        // different id, all other attributes different -> returns false
        editedGabrielsMilk = new DeliveryBuilder(GABRIELS_MILK).withName("Gabriel Milk Updated")
            .withStatus(DeliveryStatus.COMPLETED).withCustomer(TypicalPersons.BOB).withOrderDate("2019-12-12")
            .withDeliveryDate("2024-12-12").autoBuild();
        assertFalse(GABRIELS_MILK.isSameDelivery(editedGabrielsMilk));
    }
}
