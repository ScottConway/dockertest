package org.conway.dockertest.domain;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.util.Objects;

public class Bank implements Serializable {
    @CsvBindByName(column = "Bank Id")
    private long bankId;
    @CsvBindByName(column = "Bank Name")
    private String name;

    public Bank() {
    }

    public Bank(long bankId, String bankName) {
        this.bankId = bankId;
        this.name = bankName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank)) return false;
        Bank bank = (Bank) o;
        return bankId == bank.bankId &&
                Objects.equals(name, bank.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankId, name);
    }
}
