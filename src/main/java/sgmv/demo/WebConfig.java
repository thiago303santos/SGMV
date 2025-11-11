package sgmv.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                // URLs Protegidas (Exigem Login)
                .addPathPatterns(
                    "/home/**", 
                    "/perfil/**",           // Novo: Perfil Pessoal
                    "/admin/**",            // Novo: Painel Administrativo
                    "/manutencao/**",       // Ex: lista, nova, peças
                    "/estoque/**",          // Ex: lista, novo
                    "/agendamentos/**",     // Ex: lista, novo
                    "/clientes/**",
                    "/veiculos/**"
                ) 
                // URLs Públicas (Não Exigem Login)
                .excludePathPatterns(
                    "/",                    // Landing Page
                    "/login",               // Formulário de Login
                    "/cadastro",            // Formulário de Cadastro
                    "/logarUsuario",        // Submissão do Login
                    "/cadastrarUsuario",    // Submissão do Cadastro
                    "/logout",              // Logout (sempre deve ser acessível)
                    "/css/**", 
                    "/js/**", 
                    "/images/**",
                    "/lib/**"
                );
    }
}