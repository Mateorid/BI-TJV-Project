package cz.cvut.fit.gorgomat.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class EquipmentModel extends RepresentationModel<EquipmentModel> {
    private final Long id;
    private final int size;
    private final String type;
    private final boolean available;

    public EquipmentModel(Long id, int size, String type, boolean available) {
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
        if (!super.equals(o)) return false;
        EquipmentModel model = (EquipmentModel) o;
        return size == model.size && available == model.available && Objects.equals(id, model.id) && Objects.equals(type, model.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, size, type, available);
    }
}
