package cn.edu.njtech.manage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * @author jackie chen
 * @create 2018/1/12
 * @description WebSecurityConfig
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final String USER_PASSWORD_SQL = "select user_name ,password, case del_flag when 0 then 1 else 0 end as enabled  " +
			" from t_user_info " +
			"where user_name = ?";
	private final String USER_ROLE_SQL = "SELECT user_id, role_id as authority " +
			" FROM t_user_role ur JOIN t_user_info ui on ur.user_id = ui.id " +
			" join t_role_info ri on ur.role_id = ri.id" +
			" WHERE ui.user_name = ?";

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/webapp/static/**").permitAll()
				.antMatchers("/admin/**").hasRole("1")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
//				.usernameParameter("user_name")
//				.passwordParameter("password")
				.permitAll().defaultSuccessUrl("/", true)
				.and()
				.logout()
				.logoutSuccessUrl("/login?logout")
				.permitAll();
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(USER_PASSWORD_SQL)
				.authoritiesByUsernameQuery(USER_ROLE_SQL);
	}

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/**/js/**", "/**/fonts/**", "/**/css/**", "/**/img/**", "/**/favicon.ico");
//	}

//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(detailsService).passwordEncoder(new BCryptPasswordEncoder());
//	}
}
