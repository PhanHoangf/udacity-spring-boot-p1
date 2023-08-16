package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFilesByUserid(Integer userid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFileByFilename(String filename);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    File getFileById(Integer fileid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    Integer insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    Integer deleteFile(Integer fileid);
}
