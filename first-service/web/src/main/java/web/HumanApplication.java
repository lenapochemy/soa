package web;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import web.CORSFilter;
import web.HumanResource;
import web.ObjectMapperProvider;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class HumanApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(HumanResource.class);
        classes.add(ObjectMapperProvider.class);
        classes.add(CORSFilter.class);
        return classes;
    }
}
