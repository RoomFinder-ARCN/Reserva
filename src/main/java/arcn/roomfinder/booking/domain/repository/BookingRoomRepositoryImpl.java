package arcn.roomfinder.booking.domain.repository;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BookingRoomRepositoryImpl implements BookingRoomRepository {

    Map<Integer, Boolean> rooms = new HashMap<>();

    @Override
    public boolean searchRoomByNumber(int roomNumber) {
        return rooms.containsKey(roomNumber);
    }

    @Override
    public boolean availabilityByRoomNumber(int roomNumber) {
        if (rooms.containsKey(roomNumber)) {
            return rooms.get(roomNumber);
        }
        return false;
    }
}
