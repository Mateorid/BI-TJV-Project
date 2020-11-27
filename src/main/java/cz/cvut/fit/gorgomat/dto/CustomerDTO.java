package cz.cvut.fit.gorgomat.dto;

import java.util.Objects;

public class CustomerDTO {

    private final Long id;
    private final String name;
    private final String email;

    public CustomerDTO(Long id, String name, String email) {
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
        CustomerDTO that = (CustomerDTO) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
