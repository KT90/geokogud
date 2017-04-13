package ee.ttu.geocollection.interop.api.specimen.service.impl;

import ee.ttu.geocollection.domain.AppException;
import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentSpecimenImageSearchApiBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentSpecimenSearchApiBuilder;
import ee.ttu.geocollection.interop.api.service.ApiService;
import ee.ttu.geocollection.interop.api.specimen.pojo.SpecimenSearchCriteria;
import ee.ttu.geocollection.interop.api.specimen.service.SpecimenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SpecimenApiServiceImpl implements SpecimenApiService {
    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findSpecimen(SpecimenSearchCriteria searchCriteria) throws AppException {
        String requestParams = FluentSpecimenSearchApiBuilder.aRequest()
                .queryId(searchCriteria.getId()).andReturn()
                .querySpecimenNumber(searchCriteria.getSpecimenNumber()).andReturn()
                .queryCollectionNumber(searchCriteria.getCollectionNumber())
                .queryClassification(searchCriteria.getClassification())
                .queryMineralRock(searchCriteria.getMineralRock())
                .queryLocality(searchCriteria.getLocality()).andReturn()
                .queryDepth(searchCriteria.getDepth()).andReturn()
                .queryStratigraphy(searchCriteria.getStratigraphy()).andReturn()
                .queryDateAdded(searchCriteria.getRegDate())
                .queryPartOfFossil(searchCriteria.getPartOfFossil())
                .queryOriginalStatus(searchCriteria.getTypeStatus()).andReturn()
                .queryCollector(searchCriteria.getCollector()).andReturn()
                .queryKeywords(searchCriteria.getKeyWords())
                .queryReference(searchCriteria.getReference())
                .queryNameOfFossil(searchCriteria.getFossilName())
                .queryFossilMineralRock(searchCriteria.getFossilMineralRock())
                .returnDatabaseName()
                .buildWithReturningCertainFields();
        return apiService.searchRawEntities("specimen", searchCriteria.getPage(), searchCriteria.getSortField(), requestParams);
    }

    @Override
    public ApiResponse findSpecimenImage(SearchField specimenId) throws AppException{
        String requestParams = FluentSpecimenImageSearchApiBuilder.aRequest()
                .querySpecimenIdForUrl(specimenId)
                .buildWithoutReturningCertainFields();
        return apiService.searchRawEntities("specimen_image", 1, new SortField(), requestParams);
    }

    @Override
    public Map findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .relatedData("specimen_identification")
                .relatedData("specimen_image")
                .relatedData("specimen_reference")
                .buildWithDefaultReturningFields();
        return apiService.findRawEntity("specimen", requestParams);
    }
}
