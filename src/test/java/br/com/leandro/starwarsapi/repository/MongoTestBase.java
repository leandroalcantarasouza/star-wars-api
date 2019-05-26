package br.com.leandro.starwarsapi.repository;

import br.com.leandro.starwarsapi.domain.Planeta;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class MongoTestBase {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @After
    @Before
    public void limparDataBase(){
        mongoTemplate.remove(new Query(), Planeta.class);
    }

}
