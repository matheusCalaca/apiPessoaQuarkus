import br.com.matheusCalaca.user.JWT.PBKDF2Encoder
import br.com.matheusCalaca.user.model.UserPerson
import br.com.matheusCalaca.user.repository.UserRepository
import br.com.matheusCalaca.user.services.UserServicesImpl
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Shared
import spock.lang.Specification

import javax.persistence.NoResultException

import static org.mockito.Mockito.when

class UserServicesImplTest extends Specification {

    @InjectMocks
    UserServicesImpl servicesImpl

    @Mock
    PBKDF2Encoder pbkdf2Encoder

    @Mock
    UserRepository userRepository

    @Shared
    def person = new UserPerson()

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "CPF user invalid"() {

        given:
        person.setCpf(cpf)
        person.setDataNascimento(new Date(1995, 8, 27))
        person.setEmail("teste@gmail.com")
        person.setNome("matheus")
        person.setSobrenome("Calaça")

        when:
        servicesImpl.validUser(person)

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
        servicesImpl.validUser(person)

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
        servicesImpl.validUser(person)

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
        servicesImpl.validUser(person)

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        dataNasimento || expectedException        | expectedMessage
        new Date()    || IllegalArgumentException | 'Data Nascimento invalido!'
    }


    def "Usuario cadastrado com sucesso"() {

        given:
        def bulidPerson = builderUser(cpf, dataNasimento, email, nome, sobrnome, senha)

        when:
        when(userRepository.insertUser(bulidPerson)).thenReturn(bulidPerson)


        def personReturn = servicesImpl.insertUser(person)

        then:

        bulidPerson == personReturn

        where:
        cpf           | dataNasimento         | email              | nome           | sobrnome      | senha
        "12345678909" | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234"
        "12345678909" | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234"
    }

    def "Teste update"() {

        given:
        def buildPerson = builderUser(cpf, dataNasimento, email, nome, sobrnome, senha)

        when:
        when(servicesImpl.findUserByCpf("11111111111")).thenThrow(NoResultException)

        servicesImpl.updateUser(buildPerson)

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        cpf           | dataNasimento         | email              | nome           | sobrnome      | senha  || expectedException        | expectedMessage
        "11111111111" | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234" || NoResultException        | null
        ""            | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234" || IllegalArgumentException | 'Valor invalido para a ação!'
        null          | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234" || IllegalArgumentException | 'Valor invalido para a ação!'
    }

    def "Teste DELETE"() {

        when:
        when(servicesImpl.findUserByCpf("11111111111")).thenThrow(NoResultException)

        servicesImpl.deleteUser(cpf)

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        cpf           || expectedException        | expectedMessage
        "11111111111" || NoResultException        | null
        ""            || IllegalArgumentException | 'Valor invalido para a ação!'
        null          || IllegalArgumentException | 'Valor invalido para a ação!'

    }


    def "buscar cliente por CPF"() {

        when:
        if ("11111111111" == cpf) {
            when(servicesImpl.findUserByCpf(cpf)).thenThrow(NoResultException)
        }
        servicesImpl.findUserByCpf(cpf)

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        cpf           || expectedException        | expectedMessage
        null          || IllegalArgumentException | 'Valor invalido para a ação!'
        ""            || IllegalArgumentException | 'Valor invalido para a ação!'
        "11111111111" || NoResultException        | null
        //todo: adicionar validação para cpf invalido

    }


    def "Validacao para a busca"() {

        when:
        servicesImpl.validaBusca(cpf)

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        cpf  || expectedException        | expectedMessage
        null || IllegalArgumentException | 'Valor invalido para a ação!'
        ""   || IllegalArgumentException | 'Valor invalido para a ação!'
        //todo: adicionar validação para cpf invalido

    }


    UserPerson builderUser(cpf, dataNasimento, email, nome, sobrenome, senha) {
        person = new UserPerson()
        person.setCpf(cpf)
        person.setDataNascimento(dataNasimento)
        person.setEmail(email)
        person.setNome(nome)
        person.setSobrenome(sobrenome)
        person.setSenha(senha)

        return person
    }
}
