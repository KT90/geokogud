package ee.ttu.geocollection.interop.api.photoArchive.pojo;

import ee.ttu.geocollection.interop.api.Response.Response;

public class PhotoArchiveDetailsDialogDto {
    private Response photo;

    public PhotoArchiveDetailsDialogDto(Response photo) {
        this.photo = photo;
    }

    public Response getPhoto() {
        return photo;
    }

    public void setPhoto(Response photo) {
        this.photo = photo;
    }
}