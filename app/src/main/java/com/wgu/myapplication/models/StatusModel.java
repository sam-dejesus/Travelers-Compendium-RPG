//package com.wgu.myapplication.models;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.ForeignKey;
//import androidx.room.PrimaryKey;
//
//@Entity(tableName = "status")
//public class StatusModel {
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//
//    @ColumnInfo(name = "status")
//    private boolean status;
//
//    @ColumnInfo(name = "journey_id")
//    private int journey_id;
//
//    public StatusModel(boolean status, int journey_id) {
//        this.status = status;
//        this.journey_id = journey_id;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
//
//    public int getJourney_id() {
//        return journey_id;
//    }
//
//    public void setJourney_id(int journey_id) {
//        this.journey_id = journey_id;
//    }
//}
