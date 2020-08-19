package org.eclipse.cargotracker.domain.model.voyage;

import org.eclipse.cargotracker.domain.model.location.Location;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import org.joda.time.LocalDate;

/**
 * A carrier movement is a vessel voyage from one location to another.
 */
@Entity
@Table(name = "carrier_movement")
public class CarrierMovement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "departure_location_id")
    @NotNull
    private Location departureLocation;
    @ManyToOne
    @JoinColumn(name = "arrival_location_id")
    @NotNull
    private Location arrivalLocation;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "departure_time")
    @NotNull
    private LocalDate departureTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "arrival_time")
    @NotNull
    private LocalDate arrivalTime;
    // Null object pattern
    public static final CarrierMovement NONE = new CarrierMovement(
            Location.UNKNOWN, Location.UNKNOWN, new LocalDate(0), new LocalDate(0));

    public CarrierMovement() {
        // Nothing to initialize.
    }

    public CarrierMovement(Location departureLocation,
                           Location arrivalLocation, LocalDate departureTime, LocalDate arrivalTime) {
        Validate.noNullElements(new Object[]{departureLocation,
                arrivalLocation, departureTime, arrivalTime});
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
    }

    public Location getDepartureLocation() {
        return departureLocation;
    }

    public Location getArrivalLocation() {
        return arrivalLocation;
    }

    public LocalDate getDepartureTime() {
        return new LocalDate(departureTime.getValue(0));
    }

    public LocalDate getArrivalTime() {
        return new LocalDate(arrivalTime.getValue(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarrierMovement that = (CarrierMovement) o;

        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.departureLocation)
                .append(this.departureTime).append(this.arrivalLocation)
                .append(this.arrivalTime).toHashCode();
    }

    private boolean sameValueAs(CarrierMovement other) {
        return other != null
                && new EqualsBuilder()
                .append(this.departureLocation, other.departureLocation)
                .append(this.departureTime, other.departureTime)
                .append(this.arrivalLocation, other.arrivalLocation)
                .append(this.arrivalTime, other.arrivalTime).isEquals();
    }
}
