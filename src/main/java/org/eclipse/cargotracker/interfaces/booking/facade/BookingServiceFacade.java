package org.eclipse.cargotracker.interfaces.booking.facade;

import org.eclipse.cargotracker.interfaces.booking.facade.dto.CargoRoute;
import org.eclipse.cargotracker.interfaces.booking.facade.dto.Location;
import org.eclipse.cargotracker.interfaces.booking.facade.dto.RouteCandidate;

import org.joda.time.LocalDate;
import java.util.List;

/**
 * This facade shields the domain layer - model, services, repositories - from
 * concerns about such things as the user interface and remoting.
 */
public interface BookingServiceFacade {

    String bookNewCargo(String origin, String destination, LocalDate arrivalDeadline);

    CargoRoute loadCargoForRouting(String trackingId);

    void assignCargoToRoute(String trackingId, RouteCandidate route);

    void changeDestination(String trackingId, String destinationUnLocode);

    List<RouteCandidate> requestPossibleRoutesForCargo(String trackingId);

    List<Location> listShippingLocations();

    List<CargoRoute> listAllCargos();
}
