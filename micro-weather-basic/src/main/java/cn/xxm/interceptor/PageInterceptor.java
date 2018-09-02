package cn.xxm.interceptor;

import cn.xxm.vo.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;


/**
 * 分页拦截器,需要实现mybatis的拦截器接口
 * 下面的注解解释：
 *
 * @Signature 用于指定要拦截的类中的某个方法(注意方法可能重载),
 * 不同版本的mybatis该方法可能不一样
 * type:用于指定拦截哪个接口
 * method:用于指定拦截哪个方法
 * args:指定被拦截的方法的参数
 * 该类中的三个方法的执行顺序:
 * 1、setProperties:获取配置文件中的值
 * 2、plugin:对不需要进行分页的SQL语句进行过滤
 * 3、intercept:执行拦截的逻辑
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor {


    /**
     * 该方法拦截的是以ByPage结尾的方法
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //获取被拦截的对象,这里获取到的对象实现了StatementHandler接口  
        StatementHandler statementHandler =
                (StatementHandler) invocation.getTarget();
        //将statementHandler进行包装,进行包装之后,  
        //可以将metaObject看成是statementHandler  
        //注意forObject方法不同版本不同,这里使用的3.4.5版本,最后一个参数需要这么写  
        MetaObject metaObject = MetaObject.forObject(statementHandler,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                new DefaultReflectorFactory());
        //进行上述包装的目的是因为metaObject可以很容易的获取属性的值  
        //这里是根据StatementHandler的子类层次路由拿到MappedStatement,  
        //可以理解为固定写法  
        MappedStatement mappedStatement = (MappedStatement) metaObject
                .getValue("delegate.mappedStatement");
        // 配置文件中SQL语句的ID  
        String id = mappedStatement.getId();
        if (id.matches(".+ByPage$")) {
            BoundSql boundSql = statementHandler.getBoundSql();
            // 原始的SQL语句  
            String sql = boundSql.getSql();
            // 查询总条数的SQL语句  
            String countSql = "select count(*) from (" + sql + ")a";
            //获取到参数的第一个参数  
            Connection connection = (Connection) invocation.getArgs()[0];
            PreparedStatement countStatement = connection.prepareStatement(countSql);
            ParameterHandler parameterHandler = (ParameterHandler) metaObject
                    .getValue("delegate.parameterHandler");
            //设置查询总条数的SQL语句的总条数  
            parameterHandler.setParameters(countStatement);
            ResultSet rs = countStatement.executeQuery();
            //获取传递给SQL语句的参数  
            Map<?, ?> parameter = (Map<?, ?>) boundSql.getParameterObject();
            //获取到page参数  
            Page page = (Page) parameter.get("page");
            //设置page的总条数  
            if (rs.next()) {
                page.setTotalNumber(rs.getInt(1));
            }
            // 改造后带分页查询的SQL语句  
            String pageSql = sql + " limit " + page.getStartLine() + ","
                    + page.getPageSize();
            log.info("mybatis 拦截器偷偷转换的sql语句是:{}", pageSql);
            //设置需要执行的分页SQL,这里采用OGNL的写法  
            metaObject.setValue("delegate.boundSql.sql", pageSql);
        }
        //释放主权,最终使用拦截器调用instantiateStatement  
        return invocation.proceed();
    }

    /**
     * @param target 被拦截的对象,例如拦住一条SQL语句,
     *               询问需不需要进行分页
     *               该方法的作用是判断每条SQL需不需要进行分页
     *               不需要分页的方法会经过plugin方法,
     *               但是不会经过intercept方法
     */
    @Override
    public Object plugin(Object target) {
        /**
         * 将类PageInterceptor比作代理公司, 
         * this就相当于该公司的业务员 
         */
        return Plugin.wrap(target, this);
    }

    /**
     * 该方法的作用是拿到配置文件中的值
     */
    @Override
    public void setProperties(Properties properties) {
    }

}  