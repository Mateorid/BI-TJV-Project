package cz.cvut.fit.gorgomat;

public class Welcome {
    private final long id;
    private final String text;

    public Welcome(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
