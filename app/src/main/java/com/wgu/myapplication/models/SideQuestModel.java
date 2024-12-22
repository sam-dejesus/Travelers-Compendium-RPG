package com.wgu.myapplication.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "side_quests",
        foreignKeys = @ForeignKey
                (
                        entity = JourneyModel.class,
                        parentColumns = "id",
                        childColumns = "journey_id",
                        onDelete = ForeignKey.CASCADE
                )
)
public class SideQuestModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "journey_id")
    private int journey_id;

    public SideQuestModel(String title, String date, int journey_id) {
        this.title = title;
        this.date = date;
        this.journey_id = journey_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getJourney_id() {
        return journey_id;
    }

    public void setJourney_id(int journey_id) {
        this.journey_id = journey_id;
    }
}
