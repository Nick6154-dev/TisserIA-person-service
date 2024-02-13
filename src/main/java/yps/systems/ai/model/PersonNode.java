package yps.systems.ai.model;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("person")
public class PersonNode {

    @Id
    @Property("id_person")
    private Long idPerson;

    @Version
    private Long version;

    public PersonNode() {
    }

    public PersonNode(Long idPerson) {
        this.idPerson = idPerson;
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
