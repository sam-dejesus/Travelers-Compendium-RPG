package com.wgu.myapplication.models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserModel {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "avatar")
    private String avatar;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "level")
    private int level;

    @ColumnInfo(name = "experience")
    private int exp;

    @ColumnInfo(name = "exp_needed")
    private int expNeeded;

    @ColumnInfo(name="status")
    private boolean status;
    public UserModel( String name, String avatar,String title, int level, int exp, int expNeeded, boolean status){
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.title = title;
        this.exp = exp;
        this.level = level;
        this.expNeeded = expNeeded;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar(){return avatar;}

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle(){return title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel(){return level;}

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExpNeeded() {
        return expNeeded;
    }

    public void setExpNeeded(int expNeeded) {
        this.expNeeded = expNeeded;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
