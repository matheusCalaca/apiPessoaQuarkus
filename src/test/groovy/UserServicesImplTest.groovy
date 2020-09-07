import br.com.matheusCalaca.user.model.UserPerson
import br.com.matheusCalaca.user.repository.UserRepository
import br.com.matheusCalaca.user.repository.UserRepositoryImpl
import br.com.matheusCalaca.user.services.UserServicesImpl
import org.hamcrest.Matchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.OngoingStubbing
import spock.lang.Shared
import spock.lang.Specification

class UserServicesImplTest extends Specification {
//    @Shared
//    def servicesImpl = new UserServicesImpl()

    @InjectMocks
    UserServicesImpl servicesImpl;

    @Mock
    UserRepositoryImpl userRepository;

    @Shared
    def person = new UserPerson()


    def setup() {
        MockitoAnnotations.initMocks(this);
    }

    def "CPF user invalid"() {

        given:
        person.setCpf(cpf)
        person.setDataNascimento(new Date(1995, 8, 27))
        person.setEmail("teste@gmail.com")
        person.setNome("matheus")
        person.setSobrenome("Calaça")

        when:
        servicesImpl.insertUser(person)

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        cpf  || expectedException        | expectedMessage
        ''   || IllegalArgumentException | 'CPF invalido!'
        null || IllegalArgumentException | 'CPF invalido!'

    }

    def "Email user invalid"() {

        given:
        person.setCpf("12345678909")
        person.setDataNascimento(new Date(1995, 8, 27))
        person.setEmail(email)
        person.setNome("matheus")
        person.setSobrenome("Calaça")

        when:
        servicesImpl.insertUser(person)

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        email        || expectedException        | expectedMessage
        ''           || IllegalArgumentException | 'E-mail invalido!'
        null         || IllegalArgumentException | 'E-mail invalido!'
        'teste'      || IllegalArgumentException | 'E-mail invalido!'
        'teste@mail' || IllegalArgumentException | 'E-mail invalido!'

    }

    def "Nome user invalid"() {

        given:
        person.setCpf("12345678909")
        person.setDataNascimento(new Date(1995, 8, 27))
        person.setEmail("teste@gmail.com")
        person.setNome(nome)
        person.setSobrenome("Calaça")

        when:
        servicesImpl.insertUser(person)

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        nome || expectedException        | expectedMessage
        ''   || IllegalArgumentException | 'Nome invalido!'
        null || IllegalArgumentException | 'Nome invalido!'

    }

    def "Data Nascimento user invalid"() {

        given:
        person.setCpf("12345678909")
        person.setDataNascimento(dataNasimento)
        person.setEmail("teste@gmail.com")
        person.setNome("matheus")
        person.setSobrenome("Calaça")

        when:
        servicesImpl.insertUser(person)

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        dataNasimento || expectedException        | expectedMessage
        new Date()    || IllegalArgumentException | 'Data Nascimento invalido!'

    }


    def "Usuario cadastrado com sucesso"() {

        given:
        def bulidPerson = builderUser(cpf, dataNasimento, email, nome, sobrnome)

        when:
        Mockito.when(userRepository.insertUser(bulidPerson)).thenReturn(bulidPerson)

        def personReturn = servicesImpl.insertUser(person)

        then:

        bulidPerson == personReturn

        where:
        cpf           | dataNasimento         | email              | nome           | sobrnome
        "12345678909" | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock"

    }

    UserPerson builderUser(cpf, dataNasimento, email, nome, sobrenome) {
        person = new UserPerson();
        person.setCpf(cpf)
        person.setDataNascimento(dataNasimento)
        person.setEmail(email)
        person.setNome(nome)
        person.setSobrenome(sobrenome)

        return person
    }
}
