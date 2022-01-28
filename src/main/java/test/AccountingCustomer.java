package test;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(catalog = "test", name = "accounting_customers")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "accounting_customers_id", nullable = false, length = 11))
})
public class AccountingCustomer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, name = "id", nullable = false)
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customers_id")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
