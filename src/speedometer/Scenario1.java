/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package speedometer;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Alexandru Popescu
 */
@Entity
@Table(name = "SCENARIO1")
@NamedQueries({
    @NamedQuery(name = "Scenario1.findAll", query = "SELECT s FROM Scenario1 s"),
    @NamedQuery(name = "Scenario1.findBySpeed", query = "SELECT s FROM Scenario1 s WHERE s.speed = :speed"),
    @NamedQuery(name = "Scenario1.findByRevs", query = "SELECT s FROM Scenario1 s WHERE s.revs = :revs"),
    @NamedQuery(name = "Scenario1.findById", query = "SELECT s FROM Scenario1 s WHERE s.id = :id")})
    
public class Scenario1 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "SPEED")
    private Double speed;
    @Column(name = "REVS")
    private Integer revs;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    public Scenario1() {
    }

    public Scenario1(Integer id) {
        this.id = id;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getRevs() {
        return revs;
    }

    public void setRevs(Integer revs) {
        this.revs = revs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scenario1)) {
            return false;
        }
        Scenario1 other = (Scenario1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "speedometer.Scenario1[id=" + id + "]";
    }

}
