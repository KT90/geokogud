package ee.ttu.geocollection.interop.api.analyses.search.impl;

import ee.ttu.geocollection.domain.AppException;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.Response.Response;
import ee.ttu.geocollection.interop.api.analyses.pojo.AnalysesEntity;
import ee.ttu.geocollection.interop.api.analyses.pojo.AnalysesSearchCriteria;
import ee.ttu.geocollection.interop.api.analyses.search.AnalysesApiService;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentAnalysesApiBuilder;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnalysesApiServiceImpl implements AnalysesApiService{
    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findAnalyses(AnalysesSearchCriteria searchCriteria) throws AppException{
        String requestParams = FluentAnalysesApiBuilder.aRequest()
                .queryId(searchCriteria.getId()).andReturn()
                .querySample(searchCriteria.getSample()).andReturn()
                .queryStratigraphyBed(searchCriteria.getStratigraphyBed()).andReturn()
                .queryDepth(searchCriteria.getDepthSince()).andReturn()
                .queryDepth(searchCriteria.getDepthTo())
                .queryLocality(searchCriteria.getLocality()).andReturn()
                .queryAdminUnit(searchCriteria.getAdminUnit())
                .queryStratigraphy(searchCriteria.getStratigraphy()).andReturn()
                .queryAnalysisMethod(searchCriteria.getAnalysisMethod()).andReturn()
                .queryAnalysisMethodEng(searchCriteria.getAnalysisMethodEng()).andReturn()
                .queryMethodDetails(searchCriteria.getMethodDetails())
                .queryLab(searchCriteria.getLab()).andReturn()
                .queryInstrument(searchCriteria.getInstrument())
                .queryInstrumentTxt(searchCriteria.getInstrumentTxt())
                .queryDate(searchCriteria.getDate())
                .queryDateFree(searchCriteria.getDateFree())
                .returnStratigraphyId()
                .returnLithostratigraphyId()
                .buildWithReturningCertainFields();
        System.err.println(requestParams);
        return apiService.searchRawEntities("analysis", searchCriteria.getPage(), searchCriteria.getSortField(), requestParams);
    }

        @Override
    public Response findById(Long id) {
            String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                    .id(id)
                    .buildWithDefaultReturningFields();
            return apiService.findEntity("analysis", requestParams, AnalysesEntity.class);
        }

    @Override
    public List<?> searchByField(String table, String term, String searchField) {
        return null;
    }

    @Override
    public Map findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.findRawEntity("analysis", requestParams);
    }
}