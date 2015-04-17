/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Yeqiu
 */
public class PhotoBean {

    // 图片的ID
    // 小图片URL
    // 大图片URL
    // 翻译后的tags
    
    private String photoID;
    private String photoSmallURL;
    private String photoBigURL;
    private String title;
    private List<String> photoTagsList;
    private String photoURL;
    private String photoSecret;

    public PhotoBean() {
        this.photoID = "";
        this.photoSmallURL = "";
        this.photoBigURL = "";
        this.photoTagsList = new ArrayList<String>();
    }
    
    public PhotoBean(String photoID, String photoSmallURL, String photoBigURL, String title, List<String> photoTagsList) {
        this.photoID = photoID;
        this.photoSmallURL = photoSmallURL;
        this.photoBigURL = photoBigURL;
        this.title = title;
        this.photoTagsList = photoTagsList;
    }
    
    public String getPhotoID() {
        return photoID;
    }

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    public String getPhotoSmallURL() {
        return photoSmallURL;
    }

    public void setPhotoSmallURL(String photoSmallURL) {
        this.photoSmallURL = photoSmallURL;
    }

    public String getPhotoBigURL() {
        return photoBigURL;
    }

    public void setPhotoBigURL(String photoBigURL) {
        this.photoBigURL = photoBigURL;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPhotoTagsList() {
        return photoTagsList;
    }
    
    public void setPhotoTagsList(List<String> photoTagsList) {
        this.photoTagsList = photoTagsList;
    }

    /**
     * @return the photoURL
     */
    public String getPhotoURL() {
        return photoURL;
    }

    /**
     * @param photoURL the photoURL to set
     */
    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    /**
     * @return the photoSecret
     */
    public String getPhotoSecret() {
        return photoSecret;
    }

    /**
     * @param photoSecret the photoSecret to set
     */
    public void setPhotoSecret(String photoSecret) {
        this.photoSecret = photoSecret;
    }
}

