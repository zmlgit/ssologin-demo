package cn.zmlio.cas.support;

import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.PrincipalResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created by ZML on 2015/9/23.
 */
public class SSOPrincipalResolver implements PrincipalResolver {
    private static final String SQL="select userId,email,phone,username from t_user where username= ? or email=? or phone = ?";

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public SSOPrincipalResolver(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    @Override
    public Principal resolve(Credential credential) {

        UsernamePasswordCredential usernamePasswordCredential= (UsernamePasswordCredential) credential;

        String username= usernamePasswordCredential.getUsername();

        String[] args={username,username,username};

        List result= jdbcTemplate.queryForList(SQL, args);

        SSOPrincipal principal=new SSOPrincipal();

        for (Object obj:result){
            Map map=(Map)obj;
            principal.setId((String) map.get("userId"));
            principal.setAttributes(map);
        }

        return principal;
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof UsernamePasswordCredential;
    }
}
