import br.com.matheusCalaca.user.JWT.PBKDF2Encoder
import br.com.matheusCalaca.user.model.User
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
    def user = new User()

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "Teste de validacao de usuario"() {
        when:
        servicesImpl.validUser(user)

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        cpf           | dataNasimento         | email              | nome           | sobrnome      | senha  || expectedException        | expectedMessage
        ""            | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234" || IllegalArgumentException | 'CPF invalido!'
        null          | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234" || IllegalArgumentException | 'CPF invalido!'
        "12345678909" | new Date(1995, 8, 27) | "teste"            | "matheus mock" | "calaça mock" | "1234" || IllegalArgumentException | 'E-mail invalido!'
        "12345678909" | new Date(1995, 8, 27) | null               | "matheus mock" | "calaça mock" | "1234" || IllegalArgumentException | 'E-mail invalido!'
        "12345678909" | new Date(1995, 8, 27) | "teste@gmail1"     | "matheus mock" | "calaça mock" | "1234" || IllegalArgumentException | 'E-mail invalido!'
        "12345678909" | new Date(1995, 8, 27) | "teste@gmail1.com" | ""             | "calaça mock" | "1234" || IllegalArgumentException | 'Nome invalido!'
        "12345678909" | new Date(1995, 8, 27) | "teste@gmail1.com" | null           | "calaça mock" | "1234" || IllegalArgumentException | 'Nome invalido!'
        "12345678909" | new Date()            | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234" || IllegalArgumentException | 'Data Nascimento invalido!'

    }


    def "Usuario cadastrado com sucesso"() {

        given:
        def bulidUser = builderUser(cpf, dataNasimento, email, nome, sobrnome, senha)

        when:
        when(userRepository.insertUser(bulidUser)).thenReturn(bulidUser)


        def userReturn = servicesImpl.insertUser(user)

        then:

        bulidUser == userReturn

        where:
        cpf           | dataNasimento         | email              | nome           | sobrnome      | senha
        "12345678909" | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234"
        "12345678909" | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234"
    }

    def "Teste update"() {

        given:
        def buildUser = builderUser(cpf, dataNasimento, email, nome, sobrnome, senha)

        when:
        when(servicesImpl.findUserByCpf("11111111111")).thenThrow(NoResultException)

        servicesImpl.updateUser(cpf, buildUser)

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        cpf           | dataNasimento         | email              | nome           | sobrnome      | senha  || expectedException        | expectedMessage
        "11111111111" | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234" || NoResultException        | null
        ""            | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234" || IllegalArgumentException | 'Valor invalido para a ação!'
        null          | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234" || IllegalArgumentException | 'Valor invalido para a ação!'
    }

    def "Validar cadastro"() {

        given:
        def buildUser = builderUser(cpf, dataNasimento, email, nome, sobrnome, senha)

        when:
        when(userRepository.insertUser(user)).thenReturn(user)
        def user = servicesImpl.insertUser(buildUser)

        then:
        user == this.user

        where:
        cpf           | dataNasimento         | email              | nome           | sobrnome      | senha
        "11111111111" | new Date(1995, 8, 27) | "teste@gmail1.com" | "matheus mock" | "calaça mock" | "1234"
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

    }


    User builderUser(cpf, dataNasimento, email, nome, sobrenome, senha) {
        user = new User()
        user.setCpf(cpf)
        user.setDateOfBirth(dataNasimento)
        user.setEmail(email)
        user.setName(nome)
        user.setLastname(sobrenome)
        user.setPassword(senha)

        return user
    }
}
