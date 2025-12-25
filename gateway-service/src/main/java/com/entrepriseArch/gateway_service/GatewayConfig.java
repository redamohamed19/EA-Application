package com.entrepriseArch.gateway_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

// Correct static imports based on your Spring Cloud Gateway Server Web MVC documentation
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath; // <-- CORRECTED IMPORT
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


@Configuration
public class GatewayConfig {

    /**
     * Defines the routing logic for the Customer Service.
     * Incoming path: /demo/gfg
     * Target service: lb://CUSTOMER-SERVICE/demo/gfg
     */
    @Bean
    public RouterFunction<ServerResponse> customerServiceRoute() {

        // Start building the route with a unique ID
        return route("customer-service")

                // 1. Define the Predicate (incoming request path and HTTP method)
                .GET("/demo/**", http())

                // 2. Define the Pre-Filters

                // Filter A: Explicitly rewrite the path to ensure the full /demo/gfg
                // path is preserved and forwarded, resolving ambiguity issues.
                // Regex: /demo followed by anything (/?.*) becomes /demo + captured segment.
                .before(
                        rewritePath("/demo(?<segment>/.*)", "/demo${segment}")
                )

                // Filter B: Set the target URI to the load-balanced service ID
                .before(
                        uri("lb://CUSTOMER-SERVICE")
                )

                // 3. Build the final route
                .build();
    }
}