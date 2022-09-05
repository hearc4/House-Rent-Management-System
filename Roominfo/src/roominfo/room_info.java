
package roominfo;

public class room_info {

    private int room_id;
    private String room_status;

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_status() {
        return room_status;
    }

    public void setRoom_status(String room_status) {
        this.room_status = room_status;
    }

    public room_info(int room_id, String room_status) {
        this.room_id = room_id;
        this.room_status = room_status;
    }
     


   
   
}
