package top.xiongmingcai.oa.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

public class MyBatisUtils {
    // 利用static属于类不属于对象，且全局唯一
    private static SqlSessionFactory sqlSessionFactory = null;

    // 利用静态块在初始化类时实例化sqlSessionFactory
    static {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
            // 初始化错误时抛出异常ExceptionInInitializerError通知调用者
            throw new ExceptionInInitializerError();
        }
    }

    public static Object executrQuery(Function<SqlSession, Object> func) {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        Object obj = null;
        try {
            obj = func.apply(sqlSession);
        } finally {
            sqlSession.close();
        }
        return obj;
    }

    public static Object executrUpdate(Function<SqlSession, Object> func) {

        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Object obj = null;
        try {
            obj = func.apply(sqlSession);
            sqlSession.commit();
        } catch (RuntimeException e) {
            sqlSession.rollback();
            throw e;
        } finally {
            sqlSession.close();
        }
        return obj;
    }
}
