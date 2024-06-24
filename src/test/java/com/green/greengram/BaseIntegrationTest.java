package com.green.greengram;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("tdd")
@Import({CharEncodingConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 포트번호 바꿔가면서 테스트함
@AutoConfigureMockMvc  // http 통신 전송할 수 있는 mockMvc, 컨트롤러 테스트 할 때 쓰임
@Transactional // 롤백을 하기 위해
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // 매퍼 테스트할때 쓰임 , 데이터베이스 세팅을 바꾸지 않겠다
public class BaseIntegrationTest {
    // 나중에 상속받을거기 때문에 private 안쓰고 protected 씀
    @Autowired protected MockMvc mvc;  // request 해볼 수 있다, 백엔드는 요청이 와야 일을 하니까 필요함
    @Autowired protected ObjectMapper om;

}
