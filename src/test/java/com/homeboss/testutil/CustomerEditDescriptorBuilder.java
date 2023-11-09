package com.homeboss.testutil;

import com.homeboss.logic.commands.customer.CustomerEditCommand;
import com.homeboss.logic.commands.customer.CustomerEditCommand.CustomerEditDescriptor;
import com.homeboss.model.person.Address;
import com.homeboss.model.person.Customer;
import com.homeboss.model.person.Email;
import com.homeboss.model.person.Name;
import com.homeboss.model.person.Phone;


/**
 * A utility class to help with building CustomerEditDescriptor objects.
 */
public class CustomerEditDescriptorBuilder {

    private CustomerEditCommand.CustomerEditDescriptor descriptor;

    public CustomerEditDescriptorBuilder() {
        descriptor = new CustomerEditCommand.CustomerEditDescriptor();
    }

    /**
     * Returns a {@code CustomerEditDescriptor} with the passed in descriptor details
     */
    public CustomerEditDescriptorBuilder(CustomerEditCommand.CustomerEditDescriptor descriptor) {
        this.descriptor = new CustomerEditCommand.CustomerEditDescriptor(descriptor);

    }


    /**
     * Returns an {@code CustomerEditDescriptor} with fields containing {@code customer}'s details
     */
    public CustomerEditDescriptorBuilder(Customer customer) {
        descriptor = new CustomerEditCommand.CustomerEditDescriptor();
        descriptor.setCustomerId(customer.getCustomerId());
        descriptor.setName(customer.getName());
        descriptor.setPhone(customer.getPhone());
        descriptor.setEmail(customer.getEmail());
        descriptor.setAddress(customer.getAddress());
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
     * Returns an {@code CustomerEditDescriptor} with fields given
     */
    public CustomerEditDescriptor build() {
        return descriptor;
    }
}
