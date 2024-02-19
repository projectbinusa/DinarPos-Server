package com.template.eazypos.dto;

public class CustomerDTO {
    private Long id_salesman;
    private String jenis;
    private String email;
    private String nama_customer;
    private String alamat;

    private String not_telp;

    public Long getId_salesman() {
        return id_salesman;
    }

    public void setId_salesman(Long id_salesman) {
        this.id_salesman = id_salesman;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNot_telp() {
        return not_telp;
    }

    public void setNot_telp(String not_telp) {
        this.not_telp = not_telp;
    }
}
