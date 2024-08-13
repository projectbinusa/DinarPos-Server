package com.template.eazypos.model;

import com.template.eazypos.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "tabel_salesman")
public class Salesman extends DateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salesman")
    private Long id;

    @Column(name = "nama_salesman", nullable = false, length = 100)
    private String namaSalesman;

    @Column(name = "alamat_salesman", nullable = false, length = 100)
    private String alamatSalesman;

    @Column(name = "no_telp_salesman", nullable = false, length = 20)
    private String noTelpSalesman;

    @Column(name = "target")
    private String target;

    @Column(name = "del_flag", nullable = false, columnDefinition = "int default 1")
    private int delFlag;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaSalesman() {
        return namaSalesman;
    }

    public void setNamaSalesman(String namaSalesman) {
        this.namaSalesman = namaSalesman;
    }

    public String getAlamatSalesman() {
        return alamatSalesman;
    }

    public void setAlamatSalesman(String alamatSalesman) {
        this.alamatSalesman = alamatSalesman;
    }

    public String getNoTelpSalesman() {
        return noTelpSalesman;
    }

    public void setNoTelpSalesman(String noTelpSalesman) {
        this.noTelpSalesman = noTelpSalesman;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
