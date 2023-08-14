package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{notetile}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insertNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    Integer deleteNote(Integer noteid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    Note update(Note note);
}
