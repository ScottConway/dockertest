package org.conway.dockertest.domain;

import java.io.Serializable;

public class BankAccount implements Serializable {
    private long bankAccountId;
    private long customerId;
    private long bankId;
    private String name;
    private long accountBalancePennies;

    public long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

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

    public long getAccountBalancePennies() {
        return accountBalancePennies;
    }

    public void setAccountBalancePennies(long accountBalancePennies) {
        this.accountBalancePennies = accountBalancePennies;
    }
}
