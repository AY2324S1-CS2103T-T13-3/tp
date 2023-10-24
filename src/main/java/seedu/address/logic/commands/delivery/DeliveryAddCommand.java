package seedu.address.logic.commands.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DELIVERY_DATE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBook;
import seedu.address.model.delivery.*;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;

/**
 * Adds a delivery to the delivery book.
 */
public class DeliveryAddCommand extends DeliveryCommand {

    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " " + "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Delivery to the HomeBoss database. "
            + "Parameters: "
            + "DELIVERY_NAME "
            + PREFIX_CUSTOMER_ID + " CUSTOMER_ID "
            + PREFIX_DATE + " DATE\n "
            + "Example: " + COMMAND_WORD + " "
            + "furniture "
            + PREFIX_CUSTOMER_ID + " 5 "
            + PREFIX_DATE + " 2023-12-03 ";

    // public static final String MESSAGE_SUCCESS = "New delivery added: %1$s";
    public static final String MESSAGE_SUCCESS = "New delivery added: ";
    public static final String MESSAGE_DUPLICATE_DELIVERY = "This delivery already exists in HomeBoss";

    private final DeliveryAddDescriptor deliveryAddDescriptor;

    /**
     * Creates a DeliveryAddCommand to add the specified {@code Delivery}
     */
    public DeliveryAddCommand(DeliveryAddDescriptor deliveryAddDescriptor) {
        requireNonNull(deliveryAddDescriptor);
        this.deliveryAddDescriptor = deliveryAddDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        Delivery toAdd = createDelivery(model, deliveryAddDescriptor);

        model.addDelivery(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)), true);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryAddCommand)) {
            return false;
        }

        DeliveryAddCommand otherAddCommand = (DeliveryAddCommand) other;
        return deliveryAddDescriptor.equals(otherAddCommand.deliveryAddDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", deliveryAddDescriptor)
                .toString();
    }

    private static Delivery createDelivery(Model model, DeliveryAddDescriptor deliveryAddDescriptor)
            throws CommandException {

        DeliveryName deliveryName = deliveryAddDescriptor.getDeliveryName().get();
        int customerId = deliveryAddDescriptor.getCustomerId().get();
        Customer customer = null;
        DeliveryDate deliveryDate = null;
        Address address = null;

        LocalDate now = LocalDate.now();
        OrderDate orderDate = new OrderDate(now.toString());

        DeliveryStatus newDeliveryStatus = DeliveryStatus.CREATED;

        ReadOnlyBook<Customer> customerReadOnlyBook = model.getAddressBook();

        if (checkValidCustomer(model, deliveryAddDescriptor)) {
            customer = customerReadOnlyBook.getById(customerId).get();

        } else {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if (DeliveryDate.isValidDeliveryDate(deliveryAddDescriptor.getDate().get().toString())) {
            deliveryDate = deliveryAddDescriptor.getDate().get();
        } else {
            throw new CommandException(MESSAGE_INVALID_DELIVERY_DATE);
        }
        return new Delivery(deliveryName, customer, orderDate, deliveryDate, newDeliveryStatus);

    }

    private static boolean checkValidCustomer(Model model, DeliveryAddDescriptor deliveryAddDescriptor) {
        int customerId = deliveryAddDescriptor.getCustomerId().get();

        ReadOnlyBook<Customer> customerReadOnlyBook = model.getAddressBook();
        boolean found = false;

        for (Customer customer: customerReadOnlyBook.getList()) {
            if (customerId == customer.getCustomerId()) {
                return true;
            }
        } return false;
    }

    /**
     * Stores the details to edit the delivery with. Each non-empty field value will replace the
     * corresponding field value of the delivery.
     */
    public static class DeliveryAddDescriptor {
        private DeliveryName deliveryName;
        private int customerId;
        private DeliveryDate deliveryDate;

        public DeliveryAddDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public DeliveryAddDescriptor(DeliveryAddDescriptor toCopy) {
            setCustomerId(toCopy.customerId);
            setDeliveryName(toCopy.deliveryName);
            setDeliveryDate(toCopy.deliveryDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public void setDeliveryName(DeliveryName deliveryName) {
            this.deliveryName = deliveryName;
        }

        public Optional<DeliveryName> getDeliveryName() {
            return Optional.of(deliveryName);
        }
        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public Optional<Integer> getCustomerId() {
            return Optional.of(customerId);
        }

        public void setDeliveryDate(DeliveryDate deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public Optional<DeliveryDate> getDate() {
            return Optional.of(deliveryDate);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeliveryAddDescriptor)) {
                return false;
            }

            DeliveryAddDescriptor otherDeliveryAddDescriptor = (DeliveryAddDescriptor) other;

            return Objects.equals(deliveryName, otherDeliveryAddDescriptor.deliveryName)
                    && Objects.equals(customerId, otherDeliveryAddDescriptor.customerId)
                    && Objects.equals(deliveryDate, otherDeliveryAddDescriptor.deliveryDate);

        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", deliveryName)
                    .add("customer", customerId)
                    .add("deliveredAt", deliveryDate)
                    .toString();
        }
    }
}
