package pcd_p1;

/**
 * Interfaz que define los métodos de la cola circular.
 * @author josea
 */
public interface ICola {
    
    public int getNum();
    
    public void acola(Object elemento) throws Exception;
    
    public Object desacola() throws Exception;
    
    public Object primero() throws Exception;
    
}