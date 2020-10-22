package org.conway.dockertest.domain;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Customer implements Serializable {
    private long customerId;
    private String name;

    public Customer() {
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
}
