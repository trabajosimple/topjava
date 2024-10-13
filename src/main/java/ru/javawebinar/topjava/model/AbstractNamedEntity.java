package ru.javawebinar.topjava.model;

public abstract class AbstractNamedEntity extends AbstractBaseEntity implements Comparable<AbstractNamedEntity> {

    protected String name;

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return super.toString() + '(' + name + ')';
    }

    @Override
    public int compareTo(AbstractNamedEntity object) {
        return getName().compareTo(object.getName());
    }
}