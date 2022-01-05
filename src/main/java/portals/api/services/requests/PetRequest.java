package portals.api.services.requests;

public class PetRequest {

    private String name;
    private String[] photoUrls;

    public String getName() {
        return name;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }

    public PetRequest setName(String name) {
        this.name = name;
        return this;
    }

    public PetRequest setPhotoUrls(String[] photoUrls) {
        this.photoUrls = photoUrls;
        return this;
    }
}

