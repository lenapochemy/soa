package web;

import ejb.dto.HumanBeingDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import web.requests.GetWithFilterRequest;
import web.requests.UpdateHumanRequest;
import web.response.HumanResponse;
import web.response.HumanServiceFault;


@WebService(
        name = "HumanService",
        targetNamespace = "http://web/"
)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface HumanWebService {

    @WebMethod
    @WebResult
    public String sayHello();

    @WebMethod
    @WebResult
    public HumanResponse getHuman(@WebParam(name = "id") Integer id) throws HumanServiceFault;

    @WebMethod
    public HumanResponse createHuman(@WebParam(name = "human") HumanBeingDTO humanDTO) throws HumanServiceFault;


    @WebMethod
    public HumanResponse deleteHuman(@WebParam(name = "id") Integer id) throws HumanServiceFault;

    @WebMethod
    public HumanResponse updateHuman(@WebParam(name = "patch") UpdateHumanRequest updateHumanRequest) throws HumanServiceFault;


    @WebMethod
    public HumanResponse getWithGreaterMinutesOfWaiting(@WebParam(name = "minutes") int minutes) throws HumanServiceFault;


    @WebMethod
    public HumanResponse getUniqueMinutesOfWaiting() throws HumanServiceFault;


    @WebMethod
    public HumanResponse groupingByCreationDate() throws HumanServiceFault;


    @WebMethod
    public HumanResponse getAll(
            @WebParam(name = "filter") GetWithFilterRequest getWithFilterRequest
    ) throws HumanServiceFault;


}
