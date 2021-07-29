package org.cmsc495.bpo.dao.interfaces;

public interface Park {
    
    /**
     * Get the ID associated with this Park
     * 
     * @return
     */
    public String getId();

    /**
     * Get the Name of this Park
     * 
     * @return
     */
    public String getName();

    /**
     * Get the Unique Location (Address) of this Park
     * 
     * @return
     */
    public String getLocation();
}
