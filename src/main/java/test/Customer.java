package test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

import static javax.persistence.CascadeType.REMOVE;

@Entity
@Table(catalog = "test", name = "customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, name = "customers_id", nullable = false)
    private int id;

    @OneToOne(cascade = {REMOVE}, mappedBy = "customer")
    private AccountingCustomerOther accountingCustomerOther;

    @OneToOne(cascade = {REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "accounting_customers_id")
    private AccountingCustomer accountingCustomer;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public AccountingCustomerOther getAccountingCustomerOther() {
        return accountingCustomerOther;
    }

    public void setAccountingCustomerOther(AccountingCustomerOther accountingCustomerOther) {
        this.accountingCustomerOther = accountingCustomerOther;
    }

    public AccountingCustomer getAccountingCustomer() {
        return accountingCustomer;
    }

    public void setAccountingCustomer(AccountingCustomer accountingCustomer) {
        this.accountingCustomer = accountingCustomer;
    }
}
