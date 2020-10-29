package org.conway.dockertest.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.processor.PreAssignmentProcessor;
import org.conway.dockertest.util.TrimSpacesThatIntellijFormatterKeepsPuttingIn;

import java.io.Serializable;
import java.util.Objects;

public class BankAccount implements Serializable {
    @CsvBindByName(column = "Bank Account Id")
    private long bankAccountId;
    @CsvBindByName(column = "Customer Id")
    private long customerId;
    @CsvBindByName(column = "Bank Id")
    private long bankId;
    @PreAssignmentProcessor(processor = TrimSpacesThatIntellijFormatterKeepsPuttingIn.class)
    @CsvBindByName(column = "Account Name")
    private String name;
    @PreAssignmentProcessor(processor = TrimSpacesThatIntellijFormatterKeepsPuttingIn.class)
    @CsvBindByName(column = "Routing Number")
    private String routingNumber;
    @PreAssignmentProcessor(processor = TrimSpacesThatIntellijFormatterKeepsPuttingIn.class)
    @CsvBindByName(column = "Account Number")
    private String accountNumber;

    public BankAccount() {
    }

    public BankAccount(long bankAccountId, long customerId, long bankId, String name, String routingNumber, String accountNumber) {
        this.bankAccountId = bankAccountId;
        this.customerId = customerId;
        this.bankId = bankId;
        this.name = name;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
    }

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

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        BankAccount that = (BankAccount) o;
        return bankAccountId == that.bankAccountId &&
                customerId == that.customerId &&
                bankId == that.bankId &&
                Objects.equals(name, that.name) &&
                Objects.equals(routingNumber, that.routingNumber) &&
                Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankAccountId, customerId, bankId, name, routingNumber, accountNumber);
    }
}
