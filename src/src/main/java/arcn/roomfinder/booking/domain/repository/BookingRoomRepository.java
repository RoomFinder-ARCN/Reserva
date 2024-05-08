package arcn.roomfinder.booking.domain.repository;

public interface BookingRoomRepository {
    boolean searchRoomByNumber(int roomNumber);
    boolean availabilityByRoomNumber(int roomNumber);
}
