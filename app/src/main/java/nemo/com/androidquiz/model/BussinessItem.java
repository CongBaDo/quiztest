package nemo.com.androidquiz.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by doba on 5/26/17.
 */

public class BussinessItem implements Serializable {

    private String id;
    private String name;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("is_closed")
    private boolean isClose;

    @SerializedName("review_count")
    private int reviewCount;

    private float rating;

    private AddressLocation location;

    private String phone;

    @SerializedName("display_phone")
    private String displayPhone;

    private double distance;

    @SerializedName("coordinates")
    private CoordinateBussiness coordinateBussiness;

    @SerializedName("categories")
    private List<CategoryItem> categoryItems;

    public class CoordinateBussiness implements  Serializable{
        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    public class AddressLocation implements Serializable{
        private String address1;
        private String address2;
        private String city;

        @SerializedName("zip_code")
        private String zipCode;

        private String country;
        private String state;

        @SerializedName("display_address")
        private String[] displayAdresses;

        public String getAddress1() {
            return address1;
        }

        public String getAddress2() {
            return address2;
        }

        public String getCity() {
            return city;
        }

        public String getZipCode() {
            return zipCode;
        }

        public String getCountry() {
            return country;
        }

        public String getState() {
            return state;
        }

        public String[] getDisplayAdresses() {
            return displayAdresses;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isClose() {
        return isClose;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public float getRating() {
        return rating;
    }

    public AddressLocation getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public double getDistance() {
        return distance;
    }

    public CoordinateBussiness getCoordinateBussiness() {
        return coordinateBussiness;
    }


    public class CategoryItem implements Serializable{
        private String alias;
        private String title;

        public String getAlias() {
            return alias;
        }

        public String getTitle() {
            return title;
        }
    }

    public List<CategoryItem> getCategoryItems() {
        return categoryItems;
    }
}
