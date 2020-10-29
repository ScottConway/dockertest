package org.conway.dockertest.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.processor.PreAssignmentProcessor;
import org.conway.dockertest.util.TrimSpacesThatIntellijFormatterKeepsPuttingIn;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class CustomerBill implements Serializable {
    @CsvBindByName(column = "Customer Billing Id")
    private long customerBillId;
    @CsvBindByName(column = "Customer Account Id")
    private long customerAccountId;
    @PreAssignmentProcessor(processor = TrimSpacesThatIntellijFormatterKeepsPuttingIn.class)
    @CsvBindByName(column = "Paid In Full")
    private boolean isPaid;
    @CsvBindByName(column = "Amount Due (Pennies)")
    private long amountDuePennies;
    @CsvBindByName(column = "Due Date")
    @CsvDate("MM/dd/yyyy")
    private Date dueDate;

    public CustomerBill() {
    }

    public CustomerBill(long customerBillId, long customerAccountId, boolean isPaid, long amountDuePennies, Date dueDate) {
        this.customerBillId = customerBillId;
        this.customerAccountId = customerAccountId;
        this.isPaid = isPaid;
        this.amountDuePennies = amountDuePennies;
        this.dueDate = dueDate;
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
        if (!(o instanceof CustomerBill)) return false;
        CustomerBill that = (CustomerBill) o;
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
