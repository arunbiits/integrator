package io.arun.learning.camel.integrator.router;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

import io.arun.learning.camel.integrator.model.Order;
import io.arun.learning.camel.integrator.model.WareHouseOrder;
import io.arun.learning.camel.integrator.processor.OrdersProcessor;

@Component
public class MyRouter extends RouteBuilder {

	@Autowired
	Environment env;
	
	JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(WareHouseOrder[].class);
	
	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").bindingMode(RestBindingMode.json);
		
		
		rest("/api/v1").consumes("application/json")
			.post("/orders")
			.type(Order[].class)
			.to("direct:processBean");

		from("direct:processBean")
			.process(new OrdersProcessor())
			.marshal(jacksonDataFormat)
			.setHeader(Exchange.HTTP_METHOD, simple("POST"))
			.setHeader(Exchange.CONTENT_TYPE, constant("applicatiton/json"))
			.setBody(constant(simple("${body}")))
			.to(env.getProperty("application.wms.orders-url"));
		
	}
 
}
