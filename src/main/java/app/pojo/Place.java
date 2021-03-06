package app.pojo;

/**
 * Created by Alex_Frankiv on 14.02.2017.
 */
public class Place {

    private double lat;
    private double lng;
    private String address;

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Place{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", address='" + address + '\'' +
                '}';
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Place() {}

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
