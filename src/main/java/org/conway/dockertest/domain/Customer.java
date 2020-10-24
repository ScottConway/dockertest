package org.conway.dockertest.domain;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {
    private long customerId;
    private String name;

    public Customer() {
    }

    public Customer(long customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId &&
                name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name);
    }
}
