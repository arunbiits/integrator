package io.arun.learning.camel.integrator.router;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.arun.learning.camel.integrator.model.Order;
import io.arun.learning.camel.integrator.model.WareHouseOrder;
import io.arun.learning.camel.integrator.processor.OrdersProcessor;

@Component
public class MyRouter extends RouteBuilder {

	@Autowired
	Environment env;
	
	JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(WareHouseOrder.class);
	
	@Override
	public void configure() throws Exception {
		
		//Camel-Servlet Configuration
		restConfiguration().component("servlet").bindingMode(RestBindingMode.json);
		
		//Orders - POST API
		rest("/api/v1").consumes("application/json")
			.post("/orders")
			.type(Order[].class)
			.to("direct:processOrders");
		
		//Code to convert Orders to WareHouseOrder and POST it to WMS
		from("direct:processOrders")
			.process(new OrdersProcessor())
			.marshal(jacksonDataFormat)
			.setHeader(Exchange.HTTP_METHOD, simple("POST"))
			.setHeader(Exchange.CONTENT_TYPE, constant("applicatiton/json"))
			.to(env.getProperty("application.wms.orders-url"));
		
	}
 
}
