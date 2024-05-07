package arcn.roomfinder.booking.domain.dto;

import arcn.roomfinder.booking.application.Status;
import java.time.LocalDate;
import java.util.UUID;
import io.micrometer.common.lang.NonNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingDto {
    @NonNull
    private String clientId;
    @NonNull
    private Status status;
    @NonNull
    private LocalDate admissionDate;
    @NonNull
    private LocalDate departureDate;
    @NonNull
    private int roomNumber;
    @NonNull
    private double price;
    @NonNull
    private double payment;
}
