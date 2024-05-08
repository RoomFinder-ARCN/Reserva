package arcn.roomfinder.booking;

import arcn.roomfinder.booking.application.BookingUseCase;
import arcn.roomfinder.booking.controller.BookingController;
import arcn.roomfinder.booking.domain.dto.BookingDto;
import arcn.roomfinder.booking.domain.entity.Booking;
import arcn.roomfinder.booking.exception.RoomFinderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static arcn.roomfinder.booking.application.Status.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

public class BookingControllerTests {
    @Mock
    private MockMvc mockMvc;
    @Mock
    BookingUseCase bookingUseCase;

    @InjectMocks
    BookingController bookingController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void should_exception_when_doesnt_create_booking() throws Exception {
        //Arrange
        BookingDto newBooking = BookingDto.builder()
                .clientId("cacaf4cb-9899-4b2d-9fd1-3d5b73f8a0ec")
                .status(PENDIENTE)
                .admissionDate(LocalDate.of(2023, 2, 9))
                .departureDate(LocalDate.of(2023, 2, 13))
                .roomNumber(101)
                .price(180.000)
                .payment(45.000)
                .build();
        when(bookingUseCase.createBooking(newBooking)).thenThrow(new RoomFinderException("BAD_REQUEST"));

        //Action and Assert
        mockMvc.perform(post("/booking"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_get_a_booking_by_id() throws Exception {
        //Arrange
        Booking newBooking = Booking.builder()
                .bookingId(UUID.fromString(("203b0e09-0d1a-4d1a-84f6-972284c7e64a")))
                .clientId("7ed6696b-5b92-4192-a0e8-a5b8923467a2")
                .status(APLAZADA)
                .admissionDate(LocalDate.of(2020, 12, 24))
                .departureDate(LocalDate.of(2020, 12, 31))
                .roomNumber(215)
                .price(350.000)
                .payment(350.000)
                .build();
        UUID bookingIdExpect = UUID.fromString("203b0e09-0d1a-4d1a-84f6-972284c7e64a");
        when(bookingUseCase.searchBookingById(bookingIdExpect)).thenReturn(newBooking);

        //Action and Assert
        mockMvc.perform(get("/booking/{bookingId}", bookingIdExpect))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", is("7ed6696b-5b92-4192-a0e8-a5b8923467a2")))
                .andExpect(jsonPath("$.roomNumber", is(215)));
    }

    @Test
    void should_exception_when_bookingId_doesnt_exist_for_get() throws Exception {
        //Arrange
        UUID bookingId = UUID.fromString("c1379c12-d0c7-4ead-a47f-e74d840f2efd");
        when(bookingUseCase.searchBookingById(bookingId)).thenThrow(new RoomFinderException("INTERNAL_SERVER_ERROR"));

        //Action and Assert
        mockMvc.perform(get("/booking/{bookingId}", bookingId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void should_get_all_bookings() throws Exception {
        //Arrange
        List<Booking> bookings = new ArrayList<>();
        Booking newBooking1 = Booking.builder()
                .bookingId(UUID.randomUUID())
                .clientId("147fe1c9-771c-4b54-8cfb-bbb77ac9492d")
                .status(APLAZADA)
                .admissionDate(LocalDate.of(2019, 12, 24))
                .departureDate(LocalDate.of(2019, 12, 31))
                .roomNumber(210)
                .price(100.000)
                .payment(50.000)
                .build();

        Booking newBooking2 = Booking.builder()
                .bookingId(UUID.randomUUID())
                .clientId("2df2dea0b-96eb-416a-a567-5903b8dfe641")
                .status(CONFIRMADA)
                .admissionDate(LocalDate.of(2020, 5, 1))
                .departureDate(LocalDate.of(2020, 5, 1))
                .roomNumber(100)
                .price(100.000)
                .payment(100.000)
                .build();
        bookings.add(newBooking1);
        bookings.add(newBooking2);

        when(bookingUseCase.getAllBookings()).thenReturn(bookings);

        //Action and Assert
        mockMvc.perform(get("/booking"))
                .andExpect(status().isOk());
    }

    @Test
    void should_exception_when_bookingId_doesnt_exist_for_update() throws Exception {
        //Arrange
        BookingDto newBooking = BookingDto.builder()
                .clientId("5a70fe3d-6829-4a59-9f80-16c86a597b3f")
                .status(APLAZADA)
                .admissionDate(LocalDate.of(2018, 12, 10))
                .departureDate(LocalDate.of(2018, 12, 15))
                .roomNumber(301)
                .price(500.000)
                .payment(145.000)
                .build();
        UUID bookingId = UUID.fromString("0e8a02fd-1453-4f03-b60d-ca4c40f43bd2");
        when(bookingUseCase.updateBookingById(bookingId, newBooking)).thenThrow(new RoomFinderException("BAD_REQUEST"));

        //Action and Assert
        mockMvc.perform(put("/booking/{bookingId}", bookingId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_delete_a_booking_by_id() throws Exception {
        //Arrange
        UUID bookingIdExpect = UUID.fromString("203b0e09-0d1a-4d1a-84f6-972284c7e64a");
        when(bookingUseCase.deleteBookingById(bookingIdExpect)).thenReturn(true);

        //Action and Assert
        mockMvc.perform(delete("/booking/{bookingId}", bookingIdExpect))
                .andExpect(status().isOk());
    }

    @Test
    void should_exception_when_bookingId_doesnt_exist_for_delete() throws Exception {
        //Arrange
        UUID bookingId = UUID.fromString("686404aa-112b-4f14-8e37-dfee5183596b");
        when(bookingUseCase.deleteBookingById(bookingId)).thenThrow(new RoomFinderException("INTERNAL_SERVER_ERROR"));

        //Action and Assert
        mockMvc.perform(delete("/booking/{bookingId}", bookingId))
                .andExpect(status().isInternalServerError());
    }


}
