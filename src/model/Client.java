package model;

/**
 * Created by 1494778 on 2016-03-07.
 */public class Client {
    String address;
    public Client( String address) {
        this.address = address;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address the address to set
     */
    public void setAddresse(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return  address ;
    }
}
