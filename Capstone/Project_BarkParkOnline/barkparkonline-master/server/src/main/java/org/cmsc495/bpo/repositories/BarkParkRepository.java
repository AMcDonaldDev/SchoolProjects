package org.cmsc495.bpo.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.DeleteResult;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.cmsc495.bpo.dao.BarkPark;
import org.cmsc495.bpo.dao.Visit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BarkParkRepository implements CrudRepository<BarkPark> {
    protected static final Logger log = LoggerFactory.getLogger(BarkParkRepository.class);

    private MongoTemplate mongo;

    private ObjectMapper mapper;

    @Autowired
    public BarkParkRepository(MongoTemplate mongo, ObjectMapper mapper) {
        this.mongo = mongo;
        this.mapper = mapper;
    }
    
    @Override
    public Object create(BarkPark document) {
        return mongo.save(document);
    }

    @Override
    public BarkPark retrieve(String index) {
        BarkPark park = mongo.findById(index, BarkPark.class);
        return park;
    }

    public Visit retrieveVisit(String visitId) {
        BarkPark park = mongo.findOne(queryVisitId(visitId), BarkPark.class);
        if (park != null) {
            for (Visit v : park.getVisits()) {
                if (visitId.equals(v.getVisitId())) return v;
            }
        }
        return null;
    } 

    protected Query queryVisitId(String visitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("visits").elemMatch(Criteria.where("_id").is(visitId)));
        return query;
    }

    /**
     * Adds the given visits to the Park who has the given Park ID.
     * Returns the New Visits that were actually saved. Never null.
     * 
     * @param parkId
     * @param visits
     */
    public Set<Visit> addVisits(BarkPark park, Visit... visits) {
        Set<Visit> newVisits = new HashSet<>();

        for (Visit v : visits) {
            if (park.getVisits().add(v)) {
                newVisits.add(v);
            }
        }
        if (newVisits.size() > 0) mongo.save(park);
        return newVisits;
    }

    public void updateVisit(BarkPark park, Visit visit) {
        removeVisits(park, visit);
        addVisits(park, visit);
        mongo.save(park);
    }

    /**
     * Remove the given visits to the Park who has the given Park ID
     * 
     * @param parkId
     * @param visits
     */
    public Set<Visit> removeVisits(BarkPark park, Visit... visits) {
        Set<Visit> removed = new HashSet<>();
        Iterator<Visit> existingVisits = park.getVisits().iterator();

        while (existingVisits.hasNext()) {
            Visit existing = existingVisits.next();
            for (Visit v : visits) {
                if (existing.equals(v)) {
                    removed.add(existing);
                    existingVisits.remove();
                }
            }
        }
        if (removed.size() > 0) mongo.save(park);
        return removed;
    }

    @Override
    public DeleteResult delete(BarkPark document) {
        return delete(document.getId());
    }

    @Override
    public DeleteResult delete(String parkId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(parkId));
        return mongo.remove(query, BarkPark.class);
    }

    @Override
    @Deprecated
    public void update(BarkPark park) {
    }

    public Collection<BarkPark> getAll(int offset, int limit, String sortField) {
        FindIterable<Document> docs = mongo.getCollection("barkPark").find()
            .projection(Projections.exclude("visits"))
            .sort(new BasicDBObject(sortField, 1))
            .skip(offset)
            .limit(limit);

        List<BarkPark> parks = new ArrayList<>();
        for (Document doc : docs) {
            try {
                BarkPark park = mapper.readValue(doc.toJson(), BarkPark.class);
                park.setId(doc.get("_id").toString());
                parks.add(park);
            }
            catch (Exception e) {
                log.warn("Park [{}] could not be parsed. Reason: {}", 
                    doc.get("_id"), e.getLocalizedMessage());
            }
        }
        return parks;
    }

	public BarkPark getOne(String parkId) {
        FindIterable<Document> docs = mongo.getCollection("barkPark").find(Filters.eq("_id", new ObjectId(parkId)))
            .projection(Projections.exclude("visits"))
            .limit(1);

        try {
            for (Document doc : docs) {
                String json = doc.toJson();
                return mapper.readValue(json, BarkPark.class);
            }
        }
        catch (Exception e) {
            log.warn("Park [{}] could not be parsed. Reason: {}", 
                parkId, e.getLocalizedMessage());
        }
		return null;
	}
}
