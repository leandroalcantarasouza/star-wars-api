package br.com.leandro.starwarsapi.validator;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

public class PessoaValidatorTest {

    private JavaxValidator<Planeta> planetaJavaxValidator;
    private Planeta planetaTest;

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        planetaJavaxValidator = new JavaxValidator<>(factory.getValidator());
        planetaTest = PlanetaObjectMother.planetaSalvoBatata();
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveValidarNomeNulo() {
        planetaTest = PlanetaObjectMother.planetaSalvoBatata();
        planetaTest.setNome(null);

        try {
            planetaJavaxValidator.validate(planetaTest);
        } catch(ConstraintViolationException exc) {
            assertThat(exc.getConstraintViolations().size()).isEqualTo(1);
            throw exc;
        }

        fail("Deveria ter causado ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveValidarNomeVazio() {
        planetaTest = PlanetaObjectMother.planetaSalvoBatata();
        planetaTest.setNome("");

        try {
            planetaJavaxValidator.validate(planetaTest);
        } catch(ConstraintViolationException exc) {
            assertThat(exc.getConstraintViolations().size()).isEqualTo(1);
            throw exc;
        }

        fail("Deveria ter causado ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveValidarClimaNulo() {
        planetaTest = PlanetaObjectMother.planetaSalvoBatata();
        planetaTest.setClima(null);

        try {
            planetaJavaxValidator.validate(planetaTest);
        } catch(ConstraintViolationException exc) {
            assertThat(exc.getConstraintViolations().size()).isEqualTo(1);
            throw exc;
        }

        fail("Deveria ter causado ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveValidarClimaVazio() {
        planetaTest = PlanetaObjectMother.planetaSalvoBatata();
        planetaTest.setClima("");

        try {
            planetaJavaxValidator.validate(planetaTest);
        } catch(ConstraintViolationException exc) {
            assertThat(exc.getConstraintViolations().size()).isEqualTo(1);
            throw exc;
        }

        fail("Deveria ter causado ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveValidarTerrenoNulo() {
        planetaTest = PlanetaObjectMother.planetaSalvoBatata();
        planetaTest.setTerreno(null);

        try {
            planetaJavaxValidator.validate(planetaTest);
        } catch(ConstraintViolationException exc) {
            assertThat(exc.getConstraintViolations().size()).isEqualTo(1);
            throw exc;
        }

        fail("Deveria ter causado ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveValidarTerrenoVazio() {
        planetaTest = PlanetaObjectMother.planetaSalvoBatata();
        planetaTest.setTerreno("");

        try {
            planetaJavaxValidator.validate(planetaTest);
        } catch(ConstraintViolationException exc) {
            assertThat(exc.getConstraintViolations().size()).isEqualTo(1);
            throw exc;
        }

        fail("Deveria ter causado ConstraintViolationException");
    }
}
