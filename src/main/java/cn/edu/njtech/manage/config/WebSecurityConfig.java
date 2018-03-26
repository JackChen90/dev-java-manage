package cn.edu.njtech.manage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.thymeleaf.spring4.context.SpringWebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

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

	/**
	 * 数据库中，后台管理员角色id（偷懒直接代码写死了）
	 */
	private static final String ADMIN_ROLE_ID = "1";

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/webapp/static/**").permitAll()
				.antMatchers("/admin/**").access("hasAuthority('1')")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
//				.usernameParameter("user_name")
//				.passwordParameter("password")
				.successHandler(successHandler())
				.permitAll()
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

	@Bean
	public AuthenticationSuccessHandler successHandler() {

		AuthenticationSuccessHandler handler = new AuthenticationSuccessHandler() {
			private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
			private String targetUrl = null;

			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
				handle(request, response, authentication);
			}

			private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
				List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
				targetUrl = null;
				if (authorities == null || authorities.isEmpty()) {
					targetUrl = "/login";
				}else {
					for (GrantedAuthority authority :
							authorities) {
						if (authority.getAuthority().equals(ADMIN_ROLE_ID)) {
							targetUrl = "/admin/home";
							break;
						}
					}
					//没有admin权限，跳转至/home
					if (targetUrl == null) {
						targetUrl = "/home";
					}
				}
				redirectStrategy.sendRedirect(request, response, targetUrl);
			}
		};

//		SimpleUrlAuthenticationSuccessHandler handler = null;
//		List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder
//				.getContext().getAuthentication().getAuthorities();
//		if (authorities == null || authorities.isEmpty()) {
//			handler = new SimpleUrlAuthenticationSuccessHandler("login");
//		} else {
//			for (GrantedAuthority authority :
//					authorities) {
//				if (authority.getAuthority().equals(ADMIN_ROLE_ID)) {
//					handler = new SimpleUrlAuthenticationSuccessHandler("admin/home");
//					break;
//				}
//			}
//			//没有admin权限，跳转至/home
//			if (handler == null) {
//				handler = new SimpleUrlAuthenticationSuccessHandler("home");
//			}
//		}
//		handler.setUseReferer(true);
		return handler;
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
