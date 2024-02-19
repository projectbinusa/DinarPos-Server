package com.template.eazypos.dto;

public class CustomerCPDTO {
    private Long id_customer;
    private Long id_salesman;
    private String nama_cp;
    private String jabatan;
    private String email;
    private String no_telp;

    public Long getId_customer() {
        return id_customer;
    }

    public void setId_customer(Long id_customer) {
        this.id_customer = id_customer;
    }

    public Long getId_salesman() {
        return id_salesman;
    }

    public void setId_salesman(Long id_salesman) {
        this.id_salesman = id_salesman;
    }

    public String getNama_cp() {
        return nama_cp;
    }

    public void setNama_cp(String nama_cp) {
        this.nama_cp = nama_cp;
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

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }
}
