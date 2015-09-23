package cn.zmlio.cas.support;

import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.*;
import org.jasig.cas.authentication.handler.BadPasswordAuthenticationException;
import org.jasig.cas.authentication.handler.BadUsernameOrPasswordAuthenticationException;
import org.jasig.cas.authentication.principal.Principal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import sun.jdbc.odbc.ee.DataSource;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import javax.validation.constraints.NotNull;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

/**
 * Created by ZML on 2015/9/22.
 */

public class SSOAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

    private static final String USREID = "userId";
    Logger logger = Logger.getLogger(SSOAuthenticationHandler.class);

    private static final String PASSWORD = "password";

    private static final String SALT = "salt";

    @Override
    protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential transformedCredential) throws GeneralSecurityException, PreventedException {

        String username = transformedCredential.getUsername();

        String password = transformedCredential.getPassword();

        String sql = "SELECT userId,password,salt from t_user where username = ? or email= ? or phone = ?";

        SSOPrincipal user = null;

        String[] args = {username, username, username};

        List result = getJdbcTemplate().queryForList(sql, args);

        if (result == null || result.size() == 0) {
            throw  new GeneralSecurityException("该用户不存在");
        }
        if (result.size() > 1) {
            logger.error("同一个用户名(" + username + ")查询到多个结果");
        }

        for (Object obj : result) {
            Map mapedresult = (Map) obj;
            Assert.notNull(mapedresult.get(PASSWORD));

            String storedpass = (String) mapedresult.get(PASSWORD);

            if (!executeAuthication(password, username, mapedresult.get(SALT) == null ? null : (String) mapedresult.get(SALT), storedpass)) {
                throw  new GeneralSecurityException("用户名或密码错误");
            }
            user = new SSOPrincipal();
            user.setId((String) mapedresult.get(USREID));
            mapedresult.remove(PASSWORD);
            mapedresult.remove(SALT);
            user.setAttributes(mapedresult);
        }
        return createHandlerResult(transformedCredential, user, null);
    }

    public boolean executeAuthication(String password, String username, String salt, String storedpassword) {
        //对password进行两次MD5加密
        String encyptedPassword = new Md5Hash(password, username + salt, 2).toBase64();

        return encyptedPassword.equals(storedpassword);
    }

}
