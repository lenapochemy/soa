package web;


import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.net.InetAddress;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConsulServiceRegistration {


    private static final Logger logger = Logger.getLogger(ConsulServiceRegistration.class.getName());

    private static final String SERVICE_NAME = "myapp-web-service";
    private static final String SERVICE_ID = "myapp-web-1";
    private static final int SERVICE_PORT = 8080;

    private ConsulClient consulClient;
    private String serviceId;

    @PostConstruct
    public void init() {
        try {
            logger.info("Initializing Consul Service Registration...");

            // Создаем клиент Consul (по умолчанию localhost:8500)
            consulClient = new ConsulClient("localhost");

            // Регистрируем сервис
            registerService();

            logger.info("Service successfully registered in Consul");

        } catch (Exception e) {
            logger.severe("Failed to register service in Consul: " + e.getMessage());
        }
    }

    private void registerService() throws Exception {
        NewService newService = new NewService();

        // Основные параметры сервиса
        newService.setId(SERVICE_ID);
        newService.setName(SERVICE_NAME);

        // Получаем IP адрес машины
//        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        String ipAddress = "localhost";
        newService.setAddress(ipAddress);


        // Порт сервиса
        NewService.Check serviceCheck = new NewService.Check();
        serviceCheck.setHttp("http://" + ipAddress + ":" + SERVICE_PORT + "/human-service/api/humans/hello");
//        serviceCheck.setHttp("http://localhost:8080/human-service/api/humans/hello");
        serviceCheck.setInterval("10s");  // Проверка каждые 10 секунд
        serviceCheck.setTimeout("5s");    // Таймаут 5 секунд
        serviceCheck.setDeregisterCriticalServiceAfter("1m"); // Удалить через 1 минуту если unhealthy

        newService.setCheck(serviceCheck);
        newService.setPort(SERVICE_PORT);

        // Регистрируем сервис
        consulClient.agentServiceRegister(newService);

        this.serviceId = SERVICE_ID;

        logger.info("Registered service in Consul: " + SERVICE_NAME +
                " at " + ipAddress + ":" + SERVICE_PORT);
    }

    @PreDestroy
    public void cleanup() {
        if (consulClient != null && serviceId != null) {
            try {
                // Дерегистрируем сервис при остановке приложения
                consulClient.agentServiceDeregister(serviceId);
                logger.info("Service deregistered from Consul: " + serviceId);
            } catch (Exception e) {
                logger.warning("Failed to deregister service from Consul: " + e.getMessage());
            }
        }
    }

}
