package io.arun.learning.camel.integrator.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
	
	@JsonProperty("order_number")
	private String orderNumber;
	private List<Shipment> shipments;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<Shipment> getShipments() {
		return shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", shipments=" + shipments + "]";
	}

}
