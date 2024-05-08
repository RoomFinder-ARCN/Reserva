package arcn.roomfinder.booking;

import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
import arcn.roomfinder.booking.domain.repository.BookingRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static arcn.roomfinder.booking.application.Status.*;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

class BookingRepositoryTests {

    private List<Booking> bookings;

    @InjectMocks
    BookingRepositoryImpl bookingRoomRepositoryImpl;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setUp() {
        bookings = new ArrayList<>();
        Booking newBooking1 = Booking.builder()
                .bookingId(UUID.randomUUID())
                .clientId("15182b76-fafe-4a17-89e9-18a238e26be4")
                .status(APLAZADA)
                .admissionDate(LocalDate.of(2020, 12, 24))
                .departureDate(LocalDate.of(2020, 12, 31))
                .roomNumber(215)
                .price(350.000)
                .payment(350.000)
                .build();

        Booking newBooking2 = Booking.builder()
                .bookingId(UUID.fromString("913e8f50-82e9-48a3-8342-d8a883678702"))
                .clientId("24582b76-fafe-4a17-89e9-18a238e26ct6")
                .status(CONFIRMADA)
                .admissionDate(LocalDate.of(2023, 6, 1))
                .departureDate(LocalDate.of(2023, 6, 12))
                .roomNumber(112)
                .price(120.000)
                .payment(120.000)
                .build();
        bookings.add(newBooking1);
        bookings.add(newBooking2);
        bookingRoomRepositoryImpl.setBookings(bookings);

    }

    @Test
    void should_create_a_booking() {
        //Arrange
        BookingDto newBooking = BookingDto.builder()
                .clientId("79785753-ac9f-4019-b255-1ded18e0d751")
                .status(CANCELADA)
                .admissionDate(LocalDate.of(2018, 5, 10))
                .departureDate(LocalDate.of(2018, 5, 15))
                .roomNumber(100)
                .price(150.600)
                .payment(30.000)
                .build();

        //Action
        UUID bookingId = bookingRoomRepositoryImpl.createBooking(newBooking);

        //Assert
        assertNotNull(bookingId);
    }

    @Test
    void should_search_booking_by_id_existing_booking(){
        //Arrange
        UUID existingBookingId = (UUID.fromString("913e8f50-82e9-48a3-8342-d8a883678702"));

        //Action
        Booking booking = bookingRoomRepositoryImpl.searchBookingById(existingBookingId);

        //Assert
        assertEquals(existingBookingId, booking.getBookingId());
    }

    @Test
    void should_not_search_booking_when_doesnt_exists_bookingId() {
        // Arrange
        UUID nonExistingBookingId = UUID.randomUUID();

        // Act
        Booking booking = bookingRoomRepositoryImpl.searchBookingById(nonExistingBookingId);

        // Assert
        assertNull(booking);
    }

    @Test
    void should_get_all_bookings(){
        //Arrange and action
        int size = bookingRoomRepositoryImpl.getAllBookings().size();

        //Assert
        assertEquals(2, size);
    }

    @Test
    void should_update_an_existing_booking() {
        //Arrange
        BookingDto newBooking = BookingDto.builder()
                .clientId("79785753-ac9f-4019-b255-1ded18e0d751")
                .status(CONFIRMADA)
                .admissionDate(LocalDate.of(2018, 5, 11))
                .departureDate(LocalDate.of(2018, 5, 15))
                .roomNumber(105)
                .price(150.600)
                .payment(30.000)
                .build();

        //Action
        Booking booking = bookingRoomRepositoryImpl.updateBookingById(UUID.fromString("913e8f50-82e9-48a3-8342-d8a883678702"),newBooking);

        //Assert
        assertEquals(105, booking.getRoomNumber());
    }

    @Test
    void should_not_update_booking_when_doesnt_exists_bookingId() {
        // Arrange
        BookingDto newBooking = BookingDto.builder()
                .clientId("048fb29b-1719-451d-95fe-4c35cba6d56d")
                .status(APLAZADA)
                .admissionDate(LocalDate.of(2019, 5, 11))
                .departureDate(LocalDate.of(2019, 5, 15))
                .roomNumber(105)
                .price(150.600)
                .payment(50.000)
                .build();
        UUID nonExistingBookingId = UUID.randomUUID();

        // Act
        Booking booking = bookingRoomRepositoryImpl.searchBookingById(nonExistingBookingId);

        // Assert
        assertNull(booking);
    }

    @Test
    void should_delete_a_booking_by_id() {
        //Arrange and action
        boolean deletedBooking = bookingRoomRepositoryImpl.deleteBookingById((UUID.fromString("913e8f50-82e9-48a3-8342-d8a883678702")));

        //Assert
        assertTrue(deletedBooking);
        assertEquals(1, bookings.size());
    }

    @Test
    void should_not_delete_booking_when_doesnt_exists_bookingId() {
        //Arrange and action
        boolean deletedBooking = bookingRoomRepositoryImpl.deleteBookingById(UUID.randomUUID());

        //Assert
        assertFalse(deletedBooking);
        assertEquals(2, bookings.size());

    }
}
