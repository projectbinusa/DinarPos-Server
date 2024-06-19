package com.template.eazypos.dto;

public class ServiceReportDTO {
        private String namaTeknisi;
        private Long idTeknisi;
        private Long totalService;
        private Long totalSuccess;
        private Long totalNotSuccess;

        // Constructors
        public ServiceReportDTO(String namaTeknisi, Long idTeknisi, Long totalService, Long totalSuccess, Long totalNotSuccess) {
            this.namaTeknisi = namaTeknisi;
            this.idTeknisi = idTeknisi;
            this.totalService = totalService;
            this.totalSuccess = totalSuccess;
            this.totalNotSuccess = totalNotSuccess;
        }

        // Getters and Setters
        public String getNamaTeknisi() {
            return namaTeknisi;
        }

        public void setNamaTeknisi(String namaTeknisi) {
            this.namaTeknisi = namaTeknisi;
        }

        public Long getIdTeknisi() {
            return idTeknisi;
        }

        public void setIdTeknisi(Long idTeknisi) {
            this.idTeknisi = idTeknisi;
        }

        public Long getTotalService() {
            return totalService;
        }

        public void setTotalService(Long totalService) {
            this.totalService = totalService;
        }

        public Long getTotalSuccess() {
            return totalSuccess;
        }

        public void setTotalSuccess(Long totalSuccess) {
            this.totalSuccess = totalSuccess;
        }

        public Long getTotalNotSuccess() {
            return totalNotSuccess;
        }

        public void setTotalNotSuccess(Long totalNotSuccess) {
            this.totalNotSuccess = totalNotSuccess;
        }
}
