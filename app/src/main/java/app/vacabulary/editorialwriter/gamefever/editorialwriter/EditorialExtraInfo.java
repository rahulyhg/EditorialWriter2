package app.vacabulary.editorialwriter.gamefever.editorialwriter;

/**
 * Created by gamef on 01-03-2017.
 */

public class EditorialExtraInfo {

    private String editorialText ,editorialId ;

    public EditorialExtraInfo(String editorialId, String editorialText) {
        this.editorialId = editorialId;
        this.editorialText = editorialText;
    }

    public EditorialExtraInfo() {
    }


    public String getEditorialText() {
        return editorialText;
    }

    public void setEditorialText(String editorialText) {
        this.editorialText = editorialText;
    }

    public String getEditorialId() {
        return editorialId;
    }

    public void setEditorialId(String editorialId) {
        this.editorialId = editorialId;
    }
}
