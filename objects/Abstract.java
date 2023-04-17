package objects;

public class Abstract {

    private int abstractID;
    private String title;
    private String body;

    public Abstract(int abstractID, String title, String body) {
        this.abstractID = abstractID;
        this.title = title;
        this.body = body;
    }

    public Abstract(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public int getAbstractID() {
        return abstractID;
    }

    public void setAbstractID(int abstractID) {
        this.abstractID = abstractID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Abstract{" +
                "abstractID=" + abstractID +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
