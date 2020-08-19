package org.eclipse.cargotracker.domain.model.handling;

import org.eclipse.cargotracker.application.util.DateUtil;
import org.eclipse.cargotracker.domain.model.cargo.Cargo;
import org.eclipse.cargotracker.domain.model.cargo.RouteSpecification;
import org.eclipse.cargotracker.domain.model.cargo.TrackingId;
import org.eclipse.cargotracker.domain.model.location.SampleLocations;
import org.eclipse.cargotracker.domain.model.voyage.Voyage;
import org.eclipse.cargotracker.domain.model.voyage.VoyageNumber;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.joda.time.LocalDate;


//TODO This set of tests is very trivial, consider removing them.
public class HandlingHistoryTest {

    Cargo cargo = new Cargo(new TrackingId("ABC"), new RouteSpecification(
            SampleLocations.SHANGHAI, SampleLocations.DALLAS,
            DateUtil.toDate("2009-04-01")));
    Voyage voyage = new Voyage.Builder(new VoyageNumber("X25"),
            SampleLocations.HONGKONG)
            .addMovement(SampleLocations.SHANGHAI, new LocalDate(), new LocalDate())
            .addMovement(SampleLocations.DALLAS, new LocalDate(), new LocalDate())
            .build();
    HandlingEvent event1 = new HandlingEvent(cargo,
            DateUtil.toDate("2009-03-05"), new LocalDate(100),
            HandlingEvent.Type.LOAD, SampleLocations.SHANGHAI, voyage);
    HandlingEvent event1duplicate = new HandlingEvent(cargo,
            DateUtil.toDate("2009-03-05"), new LocalDate(200),
            HandlingEvent.Type.LOAD, SampleLocations.SHANGHAI, voyage);
    HandlingEvent event2 = new HandlingEvent(cargo,
            DateUtil.toDate("2009-03-10"), new LocalDate(150),
            HandlingEvent.Type.UNLOAD, SampleLocations.DALLAS, voyage);
    HandlingHistory handlingHistory = new HandlingHistory(Arrays.asList(event2,
            event1, event1duplicate));

    @Test
    public void testDistinctEventsByCompletionTime() {
        assertEquals(Arrays.asList(event1, event2),
                handlingHistory.getDistinctEventsByCompletionTime());
    }

    @Test
    public void testMostRecentlyCompletedEvent() {
        assertEquals(event2, handlingHistory.getMostRecentlyCompletedEvent());
    }
}
