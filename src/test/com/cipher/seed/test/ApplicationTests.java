package com.cipher.seed.test;

import com.cipher.seed.Application;
import com.cipher.seed.dao.TableInfoDao;
import com.cipher.seed.domain.TableInfo;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 测试类
 * Created by cipher on 2017/5/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTests {

    @Autowired
    private TableInfoDao dao;

    @Autowired
    private VelocityEngine engine;

    @Test
    public void test() throws Exception {
        List<TableInfo> list = dao.selectTableInfo("cipher", "account");
        System.out.println(list);
        engine.getLog();
    }

}
