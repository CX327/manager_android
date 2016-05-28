package org.com.manager.bean;


import java.io.Serializable;


/**
 * Created by jie.hua on 2016/3/23.
 */
public class NoteModel implements Serializable {
    int id;
    /**
     * 便签标题
     */
    String noteTitle;

    /**
     * 便签创建时间
     */
    String noteTime;

    /**
     * 便签内容
     */
    String noteContent;
 /*   *//**
     * 是否设置提醒时间
     *//*
    @DatabaseField(defaultValue="false")
    boolean noteRemind;*/
    /**
     * 便签提醒时间
     */
    String noteRemindTime;


    public NoteModel() {
    }

    public NoteModel(int noteId, String noteTitle,
                     String noteTime, String noteContent, String noteRemindTime) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
        this.noteContent = noteContent;
        this.noteRemindTime = noteRemindTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public NoteModel(String noteTitle, String noteTime, String noteContent) {
        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
        this.noteContent = noteContent;
    }

    public NoteModel(String noteTitle, String noteTime, String noteContent, String noteRemindTime) {
        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
        this.noteContent = noteContent;
        this.noteRemindTime = noteRemindTime;
    }
}
