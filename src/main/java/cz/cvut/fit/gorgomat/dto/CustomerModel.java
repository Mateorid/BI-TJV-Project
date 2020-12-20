package cz.cvut.fit.gorgomat.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class CustomerModel extends RepresentationModel<CustomerModel> {

    private final Long id;
    private final String name;
    private final String email;

    public CustomerModel(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerModel that = (CustomerModel) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
