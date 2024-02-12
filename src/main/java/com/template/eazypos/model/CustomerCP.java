package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "cp")
public class CustomerCP extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "id_customer")
//    private Long customer;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_customer", nullable = false, updatable = false)
    private Customer customer;

    @Column(name = "nama_cp")
    private String nama_cp;

//    @Column(name = "id_salesman")
//    private Long id_salesman;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_salesman", nullable = false, updatable = false)
    private Salesman salesman;

    @Column(name = "jabatan")
    private String jabatan;

    @Column(name = "email")
    private String email;

    @Column(name = "no_hp")
    private String no_hp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Long customer) {
//        this.customer = customer;
//    }

    public String getNama_cp() {
        return nama_cp;
    }

    public void setNama_cp(String nama_cp) {
        this.nama_cp = nama_cp;
    }

//    public Long getId_salesman() {
//        return id_salesman;
//    }
//
//    public void setId_salesman(Long id_salesman) {
//        this.id_salesman = id_salesman;
//    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }
}
