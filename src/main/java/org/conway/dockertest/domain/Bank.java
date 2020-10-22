package org.conway.dockertest.domain;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Bank implements Serializable {
    private long bankId;
    private String name;

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
