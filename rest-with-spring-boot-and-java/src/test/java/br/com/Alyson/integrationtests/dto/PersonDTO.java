package br.com.Alyson.integrationtests.dto;



//import br.com.Alyson.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.xml.txw2.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


import java.io.Serializable;
import java.util.Objects;


//@JsonPropertyOrder({"id","first_Name","last_Name","address", "gender"})
//@JsonFilter("PersonFilter")
@XmlRootElement
public class PersonDTO implements Serializable {
    private static final long serialVersionUID = 1l;


    private Long id;

    //@JsonProperty("first_Name")
    private String firstName;

    // @JsonProperty("last_Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;


    private String address;

    // @JsonIgnore
//    @JsonSerialize(using = GenderSerializer.class)
    private String gender;

    private Boolean enabled;



    public PersonDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(phoneNumber, personDTO.phoneNumber) && Objects.equals(getAddress(), personDTO.getAddress()) && Objects.equals(getGender(), personDTO.getGender()) && Objects.equals(getEnabled(), personDTO.getEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), phoneNumber, getAddress(), getGender(), getEnabled());
    }
}
