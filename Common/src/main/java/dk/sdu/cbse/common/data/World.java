package dk.sdu.cbse.common.data;

import java.util.concurrent.CopyOnWriteArrayList;

public class World {
    CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<>();

    public CopyOnWriteArrayList<Entity> getEntities() {
        return entities;
    }

    public <E extends Entity> CopyOnWriteArrayList<E> getEntities(Class<E> type) {
        CopyOnWriteArrayList<E> arrayList = new CopyOnWriteArrayList<>();
        for (Entity e : this.entities) {
            if (type.isInstance(e)) {
                arrayList.add(type.cast(e));
            }
        }
        return arrayList;
    }

    public void setEntities(CopyOnWriteArrayList<Entity> entities) {
        this.entities = entities;
    }

    public void addEntity(Entity e) {
        this.entities.add(e);
    }

    public void removeEntity(Entity e) {
        this.entities.remove(e);
    }
}