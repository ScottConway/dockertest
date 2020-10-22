package org.conway.dockertest.domain;

public class CustomerAccount {
    private long customerAccountId;
    private long customerId;
    private String accountName;
    private String businessName;

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
}
