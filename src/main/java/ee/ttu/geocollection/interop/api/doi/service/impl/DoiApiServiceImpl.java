package ee.ttu.geocollection.interop.api.doi.service.impl;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.Response.Response;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentCommonSearchApiBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentDoiSearchApiBuilder;
import ee.ttu.geocollection.interop.api.doi.pojo.Doi;
import ee.ttu.geocollection.interop.api.doi.pojo.DoiSearchCriteria;
import ee.ttu.geocollection.interop.api.doi.service.DoiApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import ee.ttu.geocollection.search.domain.LookUpType;
import ee.ttu.geocollection.search.domain.SearchField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoiApiServiceImpl implements DoiApiService {
    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findDoi(DoiSearchCriteria searchCriteria) {
        String requestParams = FluentDoiSearchApiBuilder.aRequest()
                .queryId(searchCriteria.getId()).andReturn()
                .queryAuthor(searchCriteria.getAuthor()).andReturn()
                .queryTitle(searchCriteria.getTitle()).andReturn()
                .queryYear(searchCriteria.getYear()).andReturn()
                .queryIdentifier(searchCriteria.getDoi()).andReturn()
                .queryPublishedBy(searchCriteria.getPublishedBy()).andReturn()
                .queryAbstract(searchCriteria.getAbstractText()).andReturn()
                .buildWithoutReturningCertainFields();
        return apiService.searchRawEntities("doi", searchCriteria.getPage(), searchCriteria.getSortField(), requestParams);
    }

    @Override
    public Response<Doi> findById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .relatedData("doi_agent")
                .relatedData("doi_geolocation")
                .relatedData("doi_related_identifier")
                .buildWithDefaultReturningFields();
        return apiService.findEntity("doi", requestParams, Doi.class);
    }

    @Override
    public List<?> searchByField(String table, String term, String searchField) {
        SearchField searchField_ = new SearchField();
        searchField_.setLookUpType(LookUpType.istartswith);
        searchField_.setName(term);
        String requestParams = FluentCommonSearchApiBuilder.aRequest()
                .queryField(searchField,searchField_)
                .returnField(searchField)
                .build();
        return apiService.findByParam("doi", requestParams);
    }


}