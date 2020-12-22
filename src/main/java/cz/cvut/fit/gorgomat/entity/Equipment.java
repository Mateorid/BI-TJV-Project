package cz.cvut.fit.gorgomat.entity;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Equipment {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private int size;

    @NotNull
    private String type;

    @NotNull
    private boolean available;

    public Equipment() {
    }

    public Equipment(int size, String type, boolean available) {
        this.size = size;
        this.type = type;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return id == equipment.id;
    }
}
