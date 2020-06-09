package io.arun.learning.camel.integrator.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Shipment {
	
	@JsonProperty("shipment_number")
	private String shipmentNumber;
	
	@JsonProperty("order_lines")
	private List<OrderLine> orderLines;

	public String getShipmentNumber() {
		return shipmentNumber;
	}

	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	@Override
	public String toString() {
		return "Shipment [shipmentNumber=" + shipmentNumber + ", orderLines=" + orderLines + "]";
	}

}
