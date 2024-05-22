    package com.project.srv.bean;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import jakarta.persistence.*;

    import java.time.LocalDate;
    import java.util.List;

    @Entity
    @JsonIgnoreProperties
    public class Vol {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int idVol;
        @ManyToOne(fetch = FetchType.EAGER)
        private Ville origin;
        @ManyToOne(fetch = FetchType.EAGER)
        private Ville destination;
        private LocalDate heureDepart;
        private LocalDate heureArrivee;
        private float prix;
        private int placesDisponibles;
        private String imageUrl;

        @OneToMany(mappedBy = "vol")
        @JsonIgnore // Assurez-vous que cette annotation est toujours appropriée selon vos besoins
        private List<ReservationVol> reservationVol;

        public Vol(int idVol, Ville origin, Ville destination, LocalDate heureDepart, LocalDate heureArrivee, float prix, int placesDisponibles, String imageUrl) {
            this.idVol = idVol;
            this.origin = origin;
            this.destination = destination;
            this.heureDepart = heureDepart;
            this.heureArrivee = heureArrivee;
            this.prix = prix;
            this.placesDisponibles = placesDisponibles;
            this.imageUrl = imageUrl;
        }

        public Vol() {

        }

        public int getIdVol() {
            return idVol;
        }

        public void setIdVol(int idVol) {
            this.idVol = idVol;
        }

        public Ville getOrigin() {
            return origin;
        }

        public void setOrigin(Ville origin) {
            this.origin = origin;
        }

        public Ville getDestination() {
            return destination;
        }

        public void setDestination(Ville destination) {
            this.destination = destination;
        }

        public LocalDate getHeureDepart() {
            return heureDepart;
        }

        public void setHeureDepart(LocalDate heureDepart) {
            this.heureDepart = heureDepart;
        }

        public LocalDate getHeureArrivee() {
            return heureArrivee;
        }

        public void setHeureArrivee(LocalDate heureArrivee) {
            this.heureArrivee = heureArrivee;
        }

        public float getPrix() {
            return prix;
        }

        public void setPrix(float prix) {
            this.prix = prix;
        }

        public int getPlacesDisponibles() {
            return placesDisponibles;
        }

        public void setPlacesDisponibles(int placesDisponibles) {
            this.placesDisponibles = placesDisponibles;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
