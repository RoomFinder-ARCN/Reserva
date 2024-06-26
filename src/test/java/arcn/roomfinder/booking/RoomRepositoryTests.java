package arcn.roomfinder.booking;

import arcn.roomfinder.booking.domain.repository.BookingRoomRepositoryImpl;
import arcn.roomfinder.booking.exception.RoomFinderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


class RoomRepositoryTests {

    @InjectMocks
    BookingRoomRepositoryImpl bookingRoomRepositoryImpl;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setUp() {
        Map<Integer, Boolean> rooms = new HashMap<>();
        rooms.put(102, true);
        rooms.put(303, false);
        rooms.put(201, true);
        bookingRoomRepositoryImpl.setRooms(rooms);
    }

    @Test
    void should_search_room_by_existing_number() throws RoomFinderException {
        //Arrange and action
        boolean roomExists = bookingRoomRepositoryImpl.searchRoomByNumber(102);

        //Assert
        assertTrue(roomExists);
    }

    @Test
    void should_not_search_room_when_doesnt_exists_number() throws RoomFinderException {
        //Arrange and action
        boolean roomExists = bookingRoomRepositoryImpl.searchRoomByNumber(501);

        //Assert
        assertFalse(roomExists);
    }

    @Test
    void should_search_availability_by_room_number() throws RoomFinderException {
        //Arrange and action
        boolean roomExists = bookingRoomRepositoryImpl.availabilityByRoomNumber(201);

        //Assert
        assertTrue(roomExists);
    }

    @Test
    void should_not_search_availability_room_when_doesnt_exists_number() throws RoomFinderException {
        //Arrange and action
        boolean roomExists = bookingRoomRepositoryImpl.availabilityByRoomNumber(601);

        //Assert
        assertFalse(roomExists);
    }

}
