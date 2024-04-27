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
    private final UUID clientId;
    @NonNull
    private final Status status;
    @NonNull
    private final LocalDate admissionDate;
    @NonNull
    private final LocalDate departureDate;
    @NonNull
    private final int roomNumber;
    @NonNull
    private final double price;
    @NonNull
    private final double payment;
}
