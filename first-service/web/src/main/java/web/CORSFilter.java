package web;

import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    private static final List<String> ALLOWED_ORIGINS = List.of(
            "http://localhost:5173",
            "http://localhost:8081"
    );
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String origin = requestContext.getHeaderString("Origin");
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
        }
//        responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:8081");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PATCH, OPTIONS, HEAD");
        responseContext.getHeaders().add("Access-Control-Max-Age", "1209600");
    }
}
