package arcn.roomfinder.booking.domain.entity;

import arcn.roomfinder.booking.application.Status;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class Booking {
    private final UUID bookingId;
    private final  UUID clientId;
    private final Status status;
    private final LocalDate admissionDate;
    private final LocalDate departureDate;
    private final int roomNumber;
    private final double price;
    private final double payment;

}
