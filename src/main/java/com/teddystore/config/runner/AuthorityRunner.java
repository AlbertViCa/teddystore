package com.teddystore.config.runner;

import com.teddystore.model.Authority;
import com.teddystore.service.AuthorityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AuthorityRunner implements CommandLineRunner {

    private final AuthorityService authorityService;

    public AuthorityRunner(AuthorityService authorityService) {
        this.authorityService = authorityService;

    }

    @Override
    public void run(String... args) throws Exception {
        Authority read = Authority.builder().id("READ").build();

        Authority create = Authority.builder().id("CREATE").build();

        Authority delete = Authority.builder().id("DELETE").build();

        Authority update = Authority.builder().id("UPDATE").build();

        Authority admin = Authority.builder().id("ADMIN").build();

        authorityService.saveAuthority(read);
        authorityService.saveAuthority(create);
        authorityService.saveAuthority(delete);
        authorityService.saveAuthority(update);
        authorityService.saveAuthority(admin);
    }
}
