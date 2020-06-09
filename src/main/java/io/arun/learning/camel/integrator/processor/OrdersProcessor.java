package io.arun.learning.camel.integrator.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import io.arun.learning.camel.integrator.model.Order;
import io.arun.learning.camel.integrator.model.OrderLine;
import io.arun.learning.camel.integrator.model.Shipment;
import io.arun.learning.camel.integrator.model.WareHouseOrder;

public class OrdersProcessor implements Processor{

	List<WareHouseOrder> wareHouseOrders =  null;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		wareHouseOrders = new ArrayList<WareHouseOrder>();
		List body = exchange.getIn().getBody(List.class);
		Iterator<Order> itr = body.iterator();
		while(itr.hasNext()) {
			WareHouseOrder wareHouseOrder = new WareHouseOrder();
			Order order = (Order)itr.next();
			for(Shipment s : order.getShipments()){
				for(OrderLine ol:s.getOrderLines()){
					wareHouseOrder.setOrderId(order.getOrderNumber());
					wareHouseOrder.setShipmentId(s.getShipmentNumber());
					wareHouseOrder.setOrderLineId(ol.getOrderLine());
					wareHouseOrder.setProductId(ol.getProductNumber());
					wareHouseOrder.setPaymentMode(ol.getPaymentMode());
					wareHouseOrders.add(wareHouseOrder);
				}
			}
		}
		exchange.getIn().setBody(wareHouseOrders);
		System.out.println(wareHouseOrders);
	}

}
