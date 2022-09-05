/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roominfo;

/**
 *
 * @author Asus
 */
public class Payment {
     public int readbookId, readTenantId;
    public String readbookDate, Booking_name;

    public int getReadbookId() {
        return readbookId;
    }

    public void setReadbookId(int readbookId) {
        this.readbookId = readbookId;
    }

    public int getReadTenantId() {
        return readTenantId;
    }

    public void setReadTenantId(int readTenantId) {
        this.readTenantId = readTenantId;
    }

    public String getReadbookDate() {
        return readbookDate;
    }

    public void setReadbookDate(String readbookDate) {
        this.readbookDate = readbookDate;
    }

    public String getBooking_name() {
        return Booking_name;
    }

    public void setBooking_name(String Booking_name) {
        this.Booking_name = Booking_name;
    }

    public Payment(int readbookId, int readTenantId, String readbookDate, String Booking_name) {
        this.readbookId = readbookId;
        this.readTenantId = readTenantId;
        this.readbookDate = readbookDate;
        this.Booking_name = Booking_name;
    }
    
}
