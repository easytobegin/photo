package demo.minisheep.com.photo.bean;

/**
 * Created by minisheep on 17/3/16.
 */

public class LanguageSelect {
    private String language;

    public LanguageSelect(String language) {
        this.language = language;
    }

    public LanguageSelect(){

    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "LanguageSelect{" +
                "language='" + language + '\'' +
                '}';
    }
}
