package org.eclipse.cargotracker.infrastructure.routing;

import org.eclipse.cargotracker.domain.model.location.Location;
import org.eclipse.cargotracker.domain.model.location.SampleLocations;
import org.eclipse.cargotracker.domain.model.voyage.VoyageRepository;
import org.eclipse.cargotracker.domain.model.cargo.*;

import org.junit.Assert;
import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import java.util.List;

public class ExternalRoutingServiceTest {

    private ExternalRoutingService externalRoutingService;
    private VoyageRepository voyageRepository;

    //    protected void setUp() throws Exception {
//        externalRoutingService = new ExternalRoutingService();
//        LocationRepository locationRepository = new LocationRepositoryInMem();
//        externalRoutingService.setLocationRepository(locationRepository);
//
//        voyageRepository = createMock(VoyageRepository.class);
//        externalRoutingService.setVoyageRepository(voyageRepository);
//
//        GraphTraversalService graphTraversalService = new GraphTraversalServiceImpl(new GraphDAO() {
//            public List<String> listLocations() {
//                return Arrays.asList(SampleLocations.TOKYO.getUnLocode().getIdString(), SampleLocations.STOCKHOLM.getUnLocode().getIdString(), SampleLocations.GOTHENBURG.getUnLocode().getIdString());
//            }
//
//            public void storeCarrierMovementId(String cmId, String from, String to) {
//            }
//        });
//        externalRoutingService.setGraphTraversalService(graphTraversalService);
//    }
    // TODO this test belongs in com.pathfinder
    public void testCalculatePossibleRoutes() {
        TrackingId trackingId = new TrackingId("ABC");
        RouteSpecification routeSpecification = new RouteSpecification(SampleLocations.HONGKONG, SampleLocations.HELSINKI, new LocalDate());
        Cargo cargo = new Cargo(trackingId, routeSpecification);

//        expect(voyageRepository.find(isA(VoyageNumber.class))).andStubReturn(SampleVoyages.CM002);
//
//        replay(voyageRepository);

        List<Itinerary> candidates = externalRoutingService.fetchRoutesForSpecification(routeSpecification);
        assertNotNull(candidates);

        for (Itinerary itinerary : candidates) {
            List<Leg> legs = itinerary.getLegs();
            assertNotNull(legs);
            assertFalse(legs.isEmpty());

            // Cargo origin and start of first leg should match
            Assert.assertEquals(cargo.getOrigin(), legs.get(0).getLoadLocation());

            // Cargo final destination and last leg stop should match
            Location lastLegStop = legs.get(legs.size() - 1).getUnloadLocation();
            assertEquals(cargo.getRouteSpecification().getDestination(), lastLegStop);

            // Assert that all legs are connected
            for (int i = 0; i < legs.size() - 1; i++) {
                assertEquals(legs.get(i).getUnloadLocation(), legs.get(i + 1).getLoadLocation());
            }
        }

//        verify(voyageRepository);
    }
}
