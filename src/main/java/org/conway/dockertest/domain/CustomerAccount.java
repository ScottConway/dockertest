package org.conway.dockertest.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.processor.PreAssignmentProcessor;
import org.conway.dockertest.util.TrimSpacesThatIntellijFormatterKeepsPuttingIn;

import java.util.Objects;

public class CustomerAccount {
    @CsvBindByName(column = "Customer Account Id")
    private long customerAccountId;
    @CsvBindByName(column = "Customer Id")
    private long customerId;
    @PreAssignmentProcessor(processor = TrimSpacesThatIntellijFormatterKeepsPuttingIn.class)
    @CsvBindByName(column = "Account Name")
    private String accountName;
    @PreAssignmentProcessor(processor = TrimSpacesThatIntellijFormatterKeepsPuttingIn.class)
    @CsvBindByName(column = "Business Name")
    private String businessName;

    public CustomerAccount() {
    }

    public CustomerAccount(long customerAccountId, long customerId, String accountName, String businessName) {
        this.customerAccountId = customerAccountId;
        this.customerId = customerId;
        this.accountName = accountName;
        this.businessName = businessName;
    }

    public long getCustomerAccountId() {
        return customerAccountId;
    }

    public void setCustomerAccountId(long customerAccountId) {
        this.customerAccountId = customerAccountId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerAccount)) return false;
        CustomerAccount that = (CustomerAccount) o;
        return customerAccountId == that.customerAccountId &&
                customerId == that.customerId &&
                Objects.equals(accountName, that.accountName) &&
                Objects.equals(businessName, that.businessName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerAccountId, customerId, accountName, businessName);
    }
}
