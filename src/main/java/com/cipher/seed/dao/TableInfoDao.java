package com.cipher.seed.dao;

import com.cipher.seed.domain.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cipher on 2017/5/5.
 */
@Mapper
public interface TableInfoDao {

    List<TableInfo> selectTableInfo(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

}
