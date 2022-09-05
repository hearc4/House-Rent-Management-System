package roominfo;

public class villa_info {

    int villa_price, totalRoom;
    String type;

    public villa_info(int villa_price, int totalRoom, String type) {
        this.villa_price = villa_price;
        this.totalRoom = totalRoom;
        this.type = type;
    }

    public int getVilla_price() {
        return villa_price;
    }

    public void setVilla_price(int villa_price) {
        this.villa_price = villa_price;
    }

    public int getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(int totalRoom) {
        this.totalRoom = totalRoom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
