package web.requests;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@NoArgsConstructor
@AllArgsConstructor
public class GetWithFilterRequest {

    private Integer pageNumber;
    private Integer pageSize;
    private String filter;
    private String sortBy;
    private String sortOrder;
}
