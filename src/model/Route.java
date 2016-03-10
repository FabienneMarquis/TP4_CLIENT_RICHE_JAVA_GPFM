package model;

/**
 * Created by mathildemarquis on 16-03-09.
 */
public class Route {
    int id;
    String route;
    public Route(int id, String route) {
        super();
        this.id = id;
        this.route = route;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the route
     */
    public String getRoute() {
        return route;
    }
    /**
     * @param route the route to set
     */
    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return  id + " "+route;
    }
}

