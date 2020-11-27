package cz.cvut.fit.gorgomat.dto;

import java.util.Objects;

public class EquipmentDTO {
    private final Long id;
    private final int size;
    private final String type;
    private final boolean available;

    public EquipmentDTO(Long id, int size, String type, boolean available) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentDTO that = (EquipmentDTO) o;
        return size == that.size &&
                available == that.available &&
                id.equals(that.id) &&
                type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, type, available);
    }
}
