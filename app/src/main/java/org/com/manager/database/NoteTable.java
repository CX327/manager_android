package org.com.manager.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * Created by jie.hua on 2016/3/23.
 */
@DatabaseTable(tableName = "NoteTable")
public class NoteTable {
    /**
     * 便签id
     */
    @DatabaseField(generatedId = true)

    int noteId;
    /**
     * 便签标题
     */
    @DatabaseField
    String noteTitle;

    /**
     * 便签创建时间
     */
    @DatabaseField
    String noteTime;

    /**
     * 便签内容
     */
    @DatabaseField
    String noteContent;
 /*   *//**
     * 是否设置提醒时间
     *//*
    @DatabaseField(defaultValue="false")
    boolean noteRemind;*/
    /**
     * 便签提醒时间
     */
    @DatabaseField(defaultValue = "")
    String noteRemindTime;


    public NoteTable() {
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }


    public String getNoteRemindTime() {
        return noteRemindTime;
    }

    public void setNoteRemindTime(String noteRemindTime) {
        this.noteRemindTime = noteRemindTime;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public NoteTable(String noteTitle, String noteTime, String noteContent) {
        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
        this.noteContent = noteContent;
    }

    public NoteTable(String noteTitle, String noteTime,
                     String noteContent, String noteRemindTime) {

        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
        this.noteContent = noteContent;
        this.noteRemindTime = noteRemindTime;
    }

    public NoteTable(int noteId, String noteTitle, String noteTime,
                     String noteContent, String noteRemindTime) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
        this.noteContent = noteContent;
        this.noteRemindTime = noteRemindTime;
    }
}
