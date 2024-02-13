package yps.systems.ai.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("person")
public class PersonEntity {

    @Id
    @Column("id_person")
    private Long idPerson;

    @Column("name")
    private String name;

    @Column("lastname")
    private String lastname;

    @Column("email")
    private String email;

    @Column("phone")
    private String phone;

    public PersonEntity() {
    }

    public PersonEntity(Long idPerson, String name, String lastname, String email, String phone) {
        this.idPerson = idPerson;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
