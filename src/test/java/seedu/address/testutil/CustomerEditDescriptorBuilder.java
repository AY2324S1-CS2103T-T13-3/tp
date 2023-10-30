package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.customer.CustomerEditCommand;
import seedu.address.logic.commands.customer.CustomerEditCommand.CustomerEditDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


/**
 * A utility class to help with building CustomerEditDescriptor objects.
 */
public class CustomerEditDescriptorBuilder {

    private CustomerEditCommand.CustomerEditDescriptor descriptor;

    public CustomerEditDescriptorBuilder() {
        descriptor = new CustomerEditDescriptor();
    }

    public CustomerEditDescriptorBuilder(CustomerEditCommand.CustomerEditDescriptor descriptor) {
        this.descriptor = new CustomerEditDescriptor(descriptor);
    }

    /**
     * Returns an {@code CustomerEditDescriptor} with fields containing {@code person}'s details
     */
    public CustomerEditDescriptorBuilder(Customer customer) {
        descriptor = new CustomerEditCommand.CustomerEditDescriptor();
        descriptor.setCustomerId(customer.getCustomerId());
        descriptor.setName(customer.getName());
        descriptor.setPhone(customer.getPhone());
        descriptor.setEmail(customer.getEmail());
        descriptor.setAddress(customer.getAddress());
        descriptor.setTags(customer.getTags());
    }

    /**
     * Sets the customerId of the {@code CustomerEditDescriptor} that we are building.
     */
    public CustomerEditDescriptorBuilder withCustomerId(int customerId) {
        descriptor.setCustomerId(customerId);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code CustomerEditDescriptor} that we are building.
     */
    public CustomerEditDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code CustomerEditDescriptor} that we are building.
     */
    public CustomerEditDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code CustomerEditDescriptor} that we are building.
     */
    public CustomerEditDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code CustomerEditDescriptor} that we are building.
     */
    public CustomerEditDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code CustomerEditDescriptor}
     * that we are building.
     */
    public CustomerEditDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public CustomerEditDescriptor build() {
        return descriptor;
    }
}