package entity;

import android.graphics.Bitmap;

/**
 * Created by lenovo on 2018/6/20.
 */

public class Bean {
    private int id;
    private Bitmap bitmap;
    private int iconId;
    private String title;
    private String content;
    private String comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bean(int iconId, String title, String content, String comments){
        this.iconId = iconId;
        this.title = title;
        this.content = content;
        this.comments = comments;
    }
    public Bean(Bitmap bitmap,String title,String content,String comments,int id){
        this.bitmap = bitmap;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.id = id;
    }


    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
