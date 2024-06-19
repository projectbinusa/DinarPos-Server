package com.template.eazypos.dto;

public class PoinHistoryMonthDTO {
        private Long teknisiId;
        private String teknisiNama;
        private Double totalPoin;
        private Long totalNominal;

        public PoinHistoryMonthDTO(Long teknisiId, String teknisiNama, Double totalPoin, Long totalNominal) {
            this.teknisiId = teknisiId;
            this.teknisiNama = teknisiNama;
            this.totalPoin = totalPoin;
            this.totalNominal = totalNominal;
        }

        // Getters and Setters
        public Long getTeknisiId() {
            return teknisiId;
        }

        public void setTeknisiId(Long teknisiId) {
            this.teknisiId = teknisiId;
        }

        public String getTeknisiNama() {
            return teknisiNama;
        }

        public void setTeknisiNama(String teknisiNama) {
            this.teknisiNama = teknisiNama;
        }

        public Double getTotalPoin() {
            return totalPoin;
        }

        public void setTotalPoin(Double totalPoin) {
            this.totalPoin = totalPoin;
        }

        public Long getTotalNominal() {
            return totalNominal;
        }

        public void setTotalNominal(Long totalNominal) {
            this.totalNominal = totalNominal;
        }
}
