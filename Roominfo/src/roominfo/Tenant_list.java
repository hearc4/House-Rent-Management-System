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
public class Tenant_list {
    int ID;
    String name, phone, address, email, gender;

   
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Tenant_list(int ID, String name, String phone, String address, String email, String gender) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.gender = gender;
    }
     public Tenant_list(int ID, String name, String phone) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
          }
   
   
    
    
}
