package org.conway.dockertest.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AccountBill implements Serializable {
    private long customerBillId;
    private long customerAccountId;
    private String accountName;
    private String businessName;
    private boolean isPaid;
    private long amountDuePennies;
    private Date dueDate;

    public AccountBill() {
    }

    public AccountBill(long customerBillId, long customerAccountId, String accountName, String businessName, boolean isPaid, long amountDuePennies, Date dueDate) {
        this.customerBillId = customerBillId;
        this.customerAccountId = customerAccountId;
        this.accountName = accountName;
        this.businessName = businessName;
        this.isPaid = isPaid;
        this.amountDuePennies = amountDuePennies;
        this.dueDate = dueDate;
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

    public long getCustomerBillId() {
        return customerBillId;
    }

    public void setCustomerBillId(long customerBillId) {
        this.customerBillId = customerBillId;
    }

    public long getCustomerAccountId() {
        return customerAccountId;
    }

    public void setCustomerAccountId(long customerAccountId) {
        this.customerAccountId = customerAccountId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public long getAmountDuePennies() {
        return amountDuePennies;
    }

    public void setAmountDuePennies(long amountDuePennies) {
        this.amountDuePennies = amountDuePennies;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountBill)) return false;
        AccountBill that = (AccountBill) o;
        return customerBillId == that.customerBillId &&
                customerAccountId == that.customerAccountId &&
                isPaid == that.isPaid &&
                amountDuePennies == that.amountDuePennies &&
                dueDate.equals(that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerBillId, customerAccountId, isPaid, amountDuePennies, dueDate);
    }
}
