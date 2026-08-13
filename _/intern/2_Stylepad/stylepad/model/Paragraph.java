package stylepad.model;

public class Paragraph {
    public String logical;
    public Run[] data;

    public Paragraph(String logical, Run[] data) {
        this.logical = logical;
        this.data = data;
    }
}
