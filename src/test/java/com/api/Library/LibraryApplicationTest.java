package com.api.Library;



import com.api.Library.exception.ResourceNotFoundException;
import com.api.Library.model.User;
import com.api.Library.repository.UserRepository;
import com.api.Library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
public class LibraryApplicationTest {
    @Test
    public void contextLoads() {}
}