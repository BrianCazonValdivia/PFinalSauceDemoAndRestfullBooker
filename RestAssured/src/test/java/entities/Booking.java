package entities;

import lombok.Getter;
import lombok.Setter;

public class Booking {
    @Getter @Setter
    private String firstname;

    @Getter @Setter
    private String lastname;

    @Getter @Setter
    private int totalprice;

    @Getter @Setter
    private boolean depositpaid;

    @Getter @Setter
    private BookingDates bookingdates;

    @Getter @Setter
    private String additionalneeds;

    // Clase interna para las fechas de la reserva
    public static class BookingDates {
        @Getter @Setter
        private String checkin;

        @Getter @Setter
        private String checkout;
    }
}
