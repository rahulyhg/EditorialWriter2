package app.vacabulary.editorialwriter.gamefever.editorialwriter;

/**
 * Created by gamef on 01-03-2017.
 */

public class EditorialGeneralInfo {


    private String editorialHeading, editorialDate, editorialSource, editorialID, editorialSubHeading, editorialTag, editorialCategory ,editorialSourceLink;
    private int editorialSourceIndex ,editorialCategoryIndex ,editorialLike ;
    private long timeInMillis;
    boolean editorialPushNotification;
    boolean mustRead;

    public EditorialGeneralInfo() {
    }

    public EditorialGeneralInfo(String editorialHeading, String editorialTag, String editorialSubHeading, String editorialID, String editorialSource, String editorialDate) {
        this.editorialHeading = editorialHeading;
        this.editorialTag = editorialTag;
        this.editorialSubHeading = editorialSubHeading;
        this.editorialID = editorialID;
        this.editorialSource = editorialSource;
        this.editorialDate = editorialDate;
    }


    public String getEditorialHeading() {
        return editorialHeading;
    }

    public void setEditorialHeading(String editorialHeading) {
        this.editorialHeading = editorialHeading;
    }

    public String getEditorialDate() {
        return editorialDate;
    }

    public void setEditorialDate(String editorialDate) {
        this.editorialDate = editorialDate;
    }

    public String getEditorialSource() {
        return editorialSource;
    }

    public void setEditorialSource(String editorialSource) {
        this.editorialSource = editorialSource;
    }

    public String getEditorialID() {
        return editorialID;
    }

    public void setEditorialID(String editorialID) {
        this.editorialID = editorialID;
    }

    public String getEditorialSubHeading() {
        return editorialSubHeading;
    }

    public void setEditorialSubHeading(String editorialSubHeading) {
        this.editorialSubHeading = editorialSubHeading;
    }

    public String getEditorialTag() {
        return editorialTag;
    }

    public void setEditorialTag(String editorialTag) {
        this.editorialTag = editorialTag;
    }

    public String getEditorialCategory() {
        return editorialCategory;
    }

    public void setEditorialCategory(String editorialCategory) {
        this.editorialCategory = editorialCategory;
    }

    public int getEditorialSourceIndex() {
        return editorialSourceIndex;
    }

    public void setEditorialSourceIndex(int editorialSourceIndex) {
        this.editorialSourceIndex = editorialSourceIndex;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public boolean isEditorialPushNotification() {
        return editorialPushNotification;
    }

    public void setEditorialPushNotification(boolean editorialPushNotification) {
        this.editorialPushNotification = editorialPushNotification;
    }

    public String getEditorialSourceLink() {
        return editorialSourceLink;
    }

    public void setEditorialSourceLink(String editorialSourceLink) {
        this.editorialSourceLink = editorialSourceLink;
    }

    public int getEditorialCategoryIndex() {
        return editorialCategoryIndex;
    }

    public void setEditorialCategoryIndex(int editorialCategoryIndex) {
        this.editorialCategoryIndex = editorialCategoryIndex;
    }

    public int getEditorialLike() {
        return editorialLike;
    }

    public void setEditorialLike(int editorialLike) {
        this.editorialLike = editorialLike;
    }


    public boolean isMustRead() {
        return mustRead;
    }

    public void setMustRead(boolean mustRead) {
        this.mustRead = mustRead;
    }
}
