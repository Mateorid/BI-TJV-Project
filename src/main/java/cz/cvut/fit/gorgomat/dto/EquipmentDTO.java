package cz.cvut.fit.gorgomat.dto;

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
}
