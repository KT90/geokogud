package ee.ttu.geocollection.interop.api.soil.service.impl;

import ee.ttu.geocollection.interop.api.Response.Response;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentSoilSearchApiApiBuilder;
import ee.ttu.geocollection.interop.api.service.ApiService;
import ee.ttu.geocollection.interop.api.soil.pojo.Soil;
import ee.ttu.geocollection.interop.api.soil.pojo.SoilHorizon;
import ee.ttu.geocollection.interop.api.soil.pojo.SoilSearchCriteria;
import ee.ttu.geocollection.interop.api.soil.service.SoilApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoilApiServiceImpl implements SoilApiService {
    @Autowired
    private ApiService apiService;

    @Override
    public Response findSoil(SoilSearchCriteria searchCriteria) {
        String requestParams = FluentSoilSearchApiApiBuilder.aRequest()
                .queryId(searchCriteria.getId()).andReturn()
                .querySite(searchCriteria.getSite()).andReturn()
                .queryAreaName(searchCriteria.getAreaOfStudy()).andReturn()
                .queryLandUse(searchCriteria.getLandUse()).andReturn()
                .querySoil(searchCriteria.getSoilName()).andReturn()
                .queryTransect(searchCriteria.getTransect()).andReturn()
                .queryDeepMining(searchCriteria.getDeepMining()).andReturn()
                .returnLatitude()
                .returnLongitude()
                .returnTransectPoint()
                .build();
        return apiService.searchEntities("soil_site", searchCriteria.getPage(),searchCriteria.getSortField(), requestParams, Soil.class);
    }

    @Override
    public Soil findById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.findEntityAndMagicallyDeserialize("soil_site", requestParams, Soil.class);
    }

    @Override
    public Response<SoilHorizon> findSoilHorizons(SoilSearchCriteria searchCriteria) {
        return null;
    }
}