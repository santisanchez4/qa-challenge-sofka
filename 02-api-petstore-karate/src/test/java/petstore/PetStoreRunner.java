package petstore;

import com.intuit.karate.junit5.Karate;

class PetStoreRunner {

    // Ejecuta todos los .feature de petstore/user.
    // Filtrar por tag: mvn test -Dkarate.options="--tags @crud"
    @Karate.Test
    Karate userTests() {
        return Karate.run("classpath:petstore/user");
    }
}
