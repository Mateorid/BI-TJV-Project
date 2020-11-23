package cz.cvut.fit.gorgomat.dto;

public class EquipmentCreateDTO {
    private final int size;
    private final String type;
    private final boolean available;

    public EquipmentCreateDTO(int size, String type, boolean available) {
        this.size = size;
        this.type = type;
        this.available = available;
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
