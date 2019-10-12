package ca.bcit.okiebisu_abrary;

import java.util.List;

public class Book {
    private String title;

    private String smallThumbnail;

    private List<String> authors;

    private String publisher;

    private String publishedDate;

    private String description;

    private String identifier;

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setSmallThumbnail(String smallThumbnail){
        this.smallThumbnail = smallThumbnail;
    }
    public String getSmallThumbnail(){
        return this.smallThumbnail;
    }
    public void setAuthors(List<String> authors){
        this.authors = authors;
    }
    public List<String> getAuthors(){
        return this.authors;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public String getPublisher(){
        return this.publisher;
    }
    public void setPublishedDate(String publishedDate){
        this.publishedDate = publishedDate;
    }
    public String getPublishedDate(){
        return this.publishedDate;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setIdentifier(String identifier){
        this.identifier = identifier;
    }
    public String getIdentifier(){
        return this.identifier;
    }
}
