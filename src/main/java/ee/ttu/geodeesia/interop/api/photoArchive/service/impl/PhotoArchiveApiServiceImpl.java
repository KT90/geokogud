package ee.ttu.geodeesia.interop.api.photoArchive.service.impl;

import ee.ttu.geodeesia.interop.api.Response.Response;
import ee.ttu.geodeesia.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geodeesia.interop.api.builder.search.FluentPhotoArchiveSearchApiBuilder;
import ee.ttu.geodeesia.interop.api.photoArchive.pojo.PhotoArchiveEntity;
import ee.ttu.geodeesia.interop.api.photoArchive.pojo.PhotoArchiveSearchCriteria;
import ee.ttu.geodeesia.interop.api.photoArchive.service.PhotoArchiveApiService;
import ee.ttu.geodeesia.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoArchiveApiServiceImpl implements PhotoArchiveApiService{
    @Autowired
    private ApiService apiService;

    //https://api.arendus.geokogud.info/image/?paginate_by=30&order_by=id&page=1&format=json&filename__isnull=false
    @Override
    public Response findPhoto(PhotoArchiveSearchCriteria searchCriteria) {
        String requestParams = FluentPhotoArchiveSearchApiBuilder.aRequest()
                .queryId(searchCriteria.getId())
                .queryFileName(searchCriteria.getFileName())
                .queryAuthorAgent(searchCriteria.getAuthorAgent())
                .queryDateTaken(searchCriteria.getDateTaken())
                .queryKeywords(searchCriteria.getKeywords())
                .queryNumber(searchCriteria.getImageNumber())
                .queryPeople(searchCriteria.getPeople())
                .queryLocality(searchCriteria.getLocality())
                .queryCountry(searchCriteria.getAdminUnit())
                .querySizeX(searchCriteria.getSizeX())
                .querySizeY(searchCriteria.getSizeY())
                .buildWithoutReturningCertainFields();
        return apiService.searchEntities("image", searchCriteria.getPage(), searchCriteria.getSortField(), requestParams, PhotoArchiveEntity.class);

    }

    @Override
    public Response findById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.findEntity("image", requestParams, PhotoArchiveEntity.class);
    }
}