package org.conway.dockertest.domain;

import java.io.Serializable;
import java.util.Date;

public class CustomerBills implements Serializable {
    private long customerBillId;
    private long customerAccountId;
    private boolean isPaid;
    private long amountDuePennies;
    private Date dueDate;

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
}
