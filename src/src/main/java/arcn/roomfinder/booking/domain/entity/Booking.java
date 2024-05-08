package arcn.roomfinder.booking.domain.entity;

import arcn.roomfinder.booking.application.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Builder
public class Booking {
    private final UUID bookingId;
    private String clientId;
    private Status status;
    private LocalDate admissionDate;
    private LocalDate departureDate;
    private int roomNumber;
    private double price;
    private double payment;
}
