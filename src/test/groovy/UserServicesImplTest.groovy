import br.com.matheusCalaca.user.model.UserPerson
import br.com.matheusCalaca.user.services.UserServicesImpl
import spock.lang.Shared
import spock.lang.Specification

class UserServicesImplTest extends Specification {
    @Shared
    def servicesImpl = new UserServicesImpl()

    @Shared
    def person = new UserPerson()


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
        email          || expectedException        | expectedMessage
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
        nome  || expectedException        | expectedMessage
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
        dataNasimento  || expectedException        | expectedMessage
        new Date()   || IllegalArgumentException | 'Data Nascimento invalido!'

    }
}
